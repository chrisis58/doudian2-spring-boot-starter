package cn.teacy.doudian.aspect;

import cn.teacy.common.annotation.SpiEndpoint;
import cn.teacy.common.doudian.domain.SpiAccessLog;
import cn.teacy.common.holder.SpiContextHolder;
import cn.teacy.common.util.MarshalUtil;
import cn.teacy.doudian.persistent.SpiAccessLogPersistent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class SpiAccessLogAspect {

    private final SpiAccessLogPersistent persistent;

    @Around("@annotation(cn.teacy.common.annotation.SpiEndpoint) || @within(cn.teacy.common.annotation.SpiEndpoint)")
    public Object saveLog(ProceedingJoinPoint joinPoint) throws Throwable {

        // 获取注解
        SpiEndpoint endpoint = Optional.ofNullable(
                        (MethodSignature) joinPoint.getSignature()
                ).map(MethodSignature::getMethod)
                .map(m -> m.getAnnotation(SpiEndpoint.class))
                .orElseGet(() ->
                        joinPoint.getTarget().getClass().getAnnotation(SpiEndpoint.class)
                );

        Object result = joinPoint.proceed();

        if (Objects.isNull(endpoint) || !endpoint.saveLog()) {
            if (Objects.isNull(endpoint)) {
                log.warn("SpiEndpoint annotation not found, skip save log");
            }

            return result;
        }

        try {
            String logId = SpiContextHolder.getLogId();

            log.info("Access Log: {}, LogId: {}", endpoint.value(), logId);

            String requestBody = Arrays.stream(joinPoint.getArgs()).filter(
                            arg -> !arg.getClass().isAnnotationPresent(RequestBody.class)
                    ).limit(1)
                    .map(MarshalUtil::toJson)
                    .findFirst().orElse("");

            String responseBody = MarshalUtil.toJson(result);

            log.debug("Request: {}, Response: {}", requestBody, responseBody);

            persistent.save(SpiAccessLog.builder()
                    .logId(logId)
                    .request(requestBody)
                    .response(responseBody)
                    .build());
        } catch (Exception e) {
            log.error("Save access log failed", e);
        } finally {
            SpiContextHolder.setLogId(null);
        }

        return result;
    }

}
