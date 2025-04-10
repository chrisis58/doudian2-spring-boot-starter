package cn.teacy.doudian.aspect;

import cn.teacy.common.annotation.SpiEndpoint;
import cn.teacy.common.doudian.domain.InteractLog;
import cn.teacy.common.holder.InteractLogContextHolder;
import cn.teacy.common.util.MarshalUtil;
import cn.teacy.doudian.persistent.InteractLogPersistent;
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

    private final InteractLogPersistent persistent;

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

        Object result = null;
        Throwable throwable = null;

        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throwable = e;
            log.error("Access log error: ", e);
        }

        if (Objects.isNull(endpoint) || !endpoint.saveLog()) {
            log.debug("Access log skip, endpoint: {}", endpoint);
            return returnValue(result, throwable);
        }

        try {
            log.info("Access Log: {}, LogId: {}", endpoint.value(), InteractLogContextHolder.getLogId());

            String requestBody = Arrays.stream(joinPoint.getArgs()).filter(
                            arg -> !arg.getClass().isAnnotationPresent(RequestBody.class)
                    ).limit(1)
                    .map(MarshalUtil::toJson)
                    .findFirst().orElse("");

            String responseBody = MarshalUtil.toJson(result);

            log.debug("Request: {}, Response: {}", requestBody, responseBody);

            persistent.save(InteractLog.fromContext(InteractLog.Type.SPI));
        } catch (Exception e) {
            log.error("Save access log failed: ", e);
        } finally {
            InteractLogContextHolder.clearAll();
        }

        return returnValue(result, throwable);
    }

    private Object returnValue(Object result, Throwable throwable) throws Throwable {
        if (Objects.nonNull(throwable)) {
            throw throwable;
        }
        return result;
    }

}
