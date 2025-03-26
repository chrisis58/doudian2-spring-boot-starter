package cn.teacy.common.annotation;

import java.lang.annotation.*;

/**
 * SPI 的服务注解
 * 注册后，SPI 服务会启用 SPI 认证
 * - 标注在类上，表示该类下的所有方法都是 SPI 服务
 * - 标注在方法上，表示该方法是 SPI 服务
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SpiEndpoint {

    /**
     * SPI 服务名称
     */
    String value() default "";

    /**
     * 是否保存日志
     */
    boolean saveLog() default true;

}
