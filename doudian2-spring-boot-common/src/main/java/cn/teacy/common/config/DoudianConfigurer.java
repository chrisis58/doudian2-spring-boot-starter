package cn.teacy.common.config;

import cn.teacy.common.interfaces.SupplierRegistry;
import cn.teacy.common.register.RetryableHandlerRegistry;
import org.springframework.beans.factory.annotation.Qualifier;

public interface DoudianConfigurer {

    default void configureRetryableHandler(RetryableHandlerRegistry registry) {}

    default void configureSupplierRegistry(@Qualifier("requestHeadersSupplierRegistry") SupplierRegistry<String, String> registry) {}

}
