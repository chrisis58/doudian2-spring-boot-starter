package cn.teacy.doudian.aspect;

import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Optional;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class ClientRetryAspect {
    private static final int MAX_RETRIES = 3;
    private static final long DEFAULT_RETRY_DELAY = 300;

    @Around("@within(cn.teacy.doudian.annotation.DoudianApiClient)")
    public Object retry(ProceedingJoinPoint joinPoint) throws Throwable {
        int attempt = 0;
        Throwable lastException = null;

        while (attempt < MAX_RETRIES) {
            try {
                return joinPoint.proceed();
            } catch (RetryableException retryableException) {
                lastException = retryableException;
                attempt++;
                log.warn("Retry attempt {} for {} failed due to: {}", attempt, joinPoint.getSignature().toShortString(), retryableException.getMessage());
                log.debug("Stack trace: ", retryableException);
                Thread.sleep(Optional.ofNullable(retryableException.retryAfter()).orElse(DEFAULT_RETRY_DELAY));
            }
        }

        throw lastException;
    }
}
