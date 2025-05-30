package cn.teacy.common.annotation;

import java.lang.annotation.*;

/**
 * 标记 API 方法是否需要跳过日志记录
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface SaveInteractLog {

    /**
     * 是否保存交互日志
     *
     * @return true 跳过，false 不跳过
     */
    boolean value() default true;

}
