package cn.teacy.doudian.interceptor;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.teacy.common.constant.SpiResponseConstant;
import cn.teacy.common.doudian.api.CommonResponse;
import cn.teacy.common.doudian.domain.SpiAccessLog;
import cn.teacy.common.holder.SpiContextHolder;
import cn.teacy.common.util.MarshalUtil;
import cn.teacy.common.util.SignUtil;
import cn.teacy.doudian.persistent.SpiAccessLogPersistent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
public class SpiAuthInterceptor implements HandlerInterceptor {

    private final String appKey;
    private final String appSecret;
    private final SpiAccessLogPersistent persistent;

    public SpiAuthInterceptor(
            String appKey,
            String appSecret,
            SpiAccessLogPersistent persistent
    ) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.persistent = persistent;
    }

    private static final CommonResponse<Object> AUTH_FAILED_RESPONSE = new CommonResponse<>(
            SpiResponseConstant.StatusCode.PARAM_ERROR.getCode(), // TODO: 修改为 SPI 的验签失败错误码
            "验签失败",
            "invalid-sign",
            "缺失验签失败的错误码定义，暂用参数错误代替",
            null,
            IdUtil.simpleUUID()
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String logId = request.getHeader("logId");
        SpiContextHolder.setLogId(logId);

        if (StrUtil.isNotBlank(logId)) {
            log.debug("SPI request received, logId: {}, uri: {}, method: {}",
                    logId, request.getRequestURI(), request.getMethod());
        }

        String appKey = request.getParameter("app_key");

        if (!StrUtil.equals(appKey, this.appKey, true)) {
            log.warn("App key not match, local app key: {}, request app key: {}", this.appKey, appKey);
            writeResponse(response, "App key not match");
            return false;
        }

        String timestamp = request.getParameter("timestamp");
        long timestampLong = StrUtil.isNumeric(timestamp) ? Long.parseLong(timestamp) : convertToTimestamp(timestamp);
        if (timestampLong < System.currentTimeMillis() / 1000 - 600) {
            // 如果请求时间戳比当前时间早 10 分钟以上，拒绝请求
            log.warn("Request timestamp expired, request timestamp: {}", timestamp);
            writeResponse(response, "Request timestamp expired");
            return false;
        }

        String sign = request.getParameter("sign");
        String signMethod = Optional.ofNullable(request.getParameter("sign_method"))
                .orElseGet(() -> "md5"); // 文档里写的是默认 md5

        String paramJson = "GET".equalsIgnoreCase(request.getMethod())
                ? request.getParameter("param_json")
                : IoUtil.read(request.getReader());

        String localSign = SignUtil.signForValidate(appKey, appSecret, paramJson, timestamp, signMethod);
        if (!StrUtil.equals(sign, localSign, true)) {
            log.warn("Sign not match, local sign: {}, request sign: {}", localSign, sign);
            writeResponse(response, "Sign not match");
            return false;
        }

        return true;
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static long convertToTimestamp(String dateTimeStr) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr, formatter);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("GMT+8"));
        return zonedDateTime.toEpochSecond();
    }

    private void writeResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        AUTH_FAILED_RESPONSE.setLogId(IdUtil.simpleUUID());
        AUTH_FAILED_RESPONSE.setMsg("验签失败: " + message);

        String responseBody = MarshalUtil.toJson(AUTH_FAILED_RESPONSE);

        response.getWriter().write(responseBody);

        persistent.save(SpiAccessLog.builder()
                .logId(SpiContextHolder.getLogId())
                .request("")
                .response(responseBody)
                .build());
        SpiContextHolder.setLogId(null);
    }

}
