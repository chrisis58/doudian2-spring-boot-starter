package cn.teacy.common.config;

import cn.teacy.common.register.RetryableHandlerRegistry;

public interface DoudianConfigurer {

    default void configurerRetryableHandler(RetryableHandlerRegistry registry) {}

}
