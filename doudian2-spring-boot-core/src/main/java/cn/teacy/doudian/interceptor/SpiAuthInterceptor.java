package cn.teacy.doudian.interceptor;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.teacy.common.constant.SpiResponseConstant;
import cn.teacy.common.doudian.api.CommonResponse;
import cn.teacy.common.doudian.domain.InteractLog;
import cn.teacy.common.holder.InteractLogContextHolder;
import cn.teacy.common.util.MarshalUtil;
import cn.teacy.common.util.SignUtil;
import cn.teacy.doudian.persistent.InteractLogPersistent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class SpiAuthInterceptor implements HandlerInterceptor {

    private final String appKey;
    private final String appSecret;
    private final InteractLogPersistent persistent;

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
        setInteractContext(request);

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
        InteractLogContextHolder.setRequestBody(paramJson);

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

    private void setInteractContext(HttpServletRequest request) {
        String logId = request.getHeader("Log-id");
        InteractLogContextHolder.setLogId(logId);
        InteractLogContextHolder.setRoute(request.getRequestURI());
        InteractLogContextHolder.setLogId(logId);
        Map<String, String> queryMap = request.getParameterMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Arrays.stream(entry.getValue())
                                .map(value -> StrUtil.sub(value, 0, 100))
                                .collect(Collectors.joining(","))
                ));
        InteractLogContextHolder.setRequestBody(queryMap.toString());
    }

    private void writeResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        AUTH_FAILED_RESPONSE.setLogId(IdUtil.simpleUUID());
        AUTH_FAILED_RESPONSE.setMsg("验签失败: " + message);

        String responseBody = MarshalUtil.toJson(AUTH_FAILED_RESPONSE);

        response.getWriter().write(responseBody);

        try {
            persistent.save(InteractLog.fromContext(InteractLog.Type.API));
        } finally {
            InteractLogContextHolder.clearAll();
        }
    }

}
