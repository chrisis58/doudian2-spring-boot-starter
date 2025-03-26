package cn.teacy.common.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface OpField {
    boolean required() default false;

    String desc() default "";

    String example() default "";
}
