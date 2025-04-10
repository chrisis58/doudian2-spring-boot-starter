package cn.teacy.doudian.aspect;

import cn.teacy.common.annotation.SkipLog;
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
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ApiRequestLogAspect {

    private final InteractLogPersistent persistent;

    @Around("@within(cn.teacy.doudian.annotation.DoudianApiClient)")
    public Object logApiRequest(ProceedingJoinPoint joinPoint) throws Throwable {

        // 获取注解
        SkipLog skipLog = Optional.ofNullable(
                        (MethodSignature) joinPoint.getSignature()
                ).map(MethodSignature::getMethod)
                .map(m -> AnnotatedElementUtils.findMergedAnnotation(m, SkipLog.class))
                .orElseGet(() ->
                        AnnotatedElementUtils.findMergedAnnotation(joinPoint.getTarget().getClass(), SkipLog.class)
                );

        Object result = null;
        Throwable throwable = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throwable = e;
            log.error("API request failed", e);
        }

        if (Objects.nonNull(skipLog) && skipLog.value()) {
            log.debug("Skip log for this request");
            return returnValue(result, throwable);
        }

        try {
            log.info("LogId: {}", InteractLogContextHolder.getLogId());
            InteractLogContextHolder.setResponseBody(MarshalUtil.toJson(result));

            persistent.save(InteractLog.fromContext(InteractLog.Type.API));
        } catch (Exception e) {
            log.error("Save access log failed", e);
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
