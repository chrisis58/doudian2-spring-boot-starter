package cn.teacy.doudian.annotation;

import cn.teacy.common.annotation.SkipLog;
import cn.teacy.common.constant.DoudianApiConstant;
import cn.teacy.doudian.config.DoudianApiClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@FeignClient
@SkipLog
public @interface DoudianApiClient {

    @AliasFor(annotation = FeignClient.class)
    String value() default "";

    @AliasFor(annotation = FeignClient.class)
    String contextId() default "";

    @AliasFor(annotation = FeignClient.class)
    String name() default "";

    @AliasFor(annotation = FeignClient.class)
    String[] qualifiers() default {};

    @AliasFor(annotation = FeignClient.class)
    String url() default DoudianApiConstant.API_URL;

    @AliasFor(annotation = FeignClient.class)
    boolean dismiss404() default false;

    @AliasFor(annotation = FeignClient.class)
    Class<?>[] configuration() default {DoudianApiClientConfig.class};

    @AliasFor(annotation = FeignClient.class)
    Class<?> fallback() default void.class;

    @AliasFor(annotation = FeignClient.class)
    Class<?> fallbackFactory() default void.class;

    @AliasFor(annotation = FeignClient.class)
    String path() default "";

    @AliasFor(annotation = FeignClient.class)
    boolean primary() default true;

    @AliasFor(annotation = SkipLog.class, value = "value")
    boolean saveLog() default true;

}
