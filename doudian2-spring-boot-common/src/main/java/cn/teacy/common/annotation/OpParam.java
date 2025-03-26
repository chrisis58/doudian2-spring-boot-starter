package cn.teacy.common.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 用于标记请求参数
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface OpParam {
    String name();

    String method();

    boolean needToken() default true;

    String docs() default "";

    String desc() default "";

    String lastUpdateTime() default "";

}
