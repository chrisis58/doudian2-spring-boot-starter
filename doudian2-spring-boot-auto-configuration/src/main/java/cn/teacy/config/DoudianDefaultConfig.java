package cn.teacy.config;


import cn.teacy.common.interfaces.SupplierRegistry;
import cn.teacy.common.register.RetryableHandlerRegistry;
import cn.teacy.doudian.handler.TokenRetryHandler;
import cn.teacy.doudian.persistent.InteractLogPersistent;
import cn.teacy.common.register.HashSupplierRegistry;
import cn.teacy.doudian.token.AccessTokenHolder;
import cn.teacy.doudian.token.RefreshTokenHolder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import java.util.Date;

@ConditionalOnClass({
        AccessTokenHolder.class,
        RefreshTokenHolder.class,
        InteractLogPersistent.class,
        SupplierRegistry.class
})
@AutoConfiguration
@RequiredArgsConstructor
public class DoudianDefaultConfig {

    @Bean
    @ConditionalOnMissingBean
    public AccessTokenHolder accessTokenHolder() {
        return new AccessTokenHolder.DEFAULT();
    }

    @Bean
    @ConditionalOnMissingBean
    public RefreshTokenHolder refreshTokenHolder() {
        return new RefreshTokenHolder.DEFAULT();
    }

    @Bean
    @ConditionalOnMissingBean
    public InteractLogPersistent interactLogPersistent() {
        return new InteractLogPersistent.JUST_LOG();
    }

    @Bean
    @Qualifier("requestHeadersSupplierRegistry")
    @ConditionalOnMissingBean(name = "requestHeadersSupplierRegistry")
    public SupplierRegistry<String, String> supplierRegistry() {
        HashSupplierRegistry<String, String> supplierRegistry = new HashSupplierRegistry<>();

        supplierRegistry.register("date", () -> DateFormatUtils.format(new Date(), "yyyy-MM-dd"));

        return supplierRegistry;
    }

    @Bean
    @DependsOn({"accessTokenHolder", "refreshTokenHolder"})
    public RetryableHandlerRegistry retryableHandlerRegistry(
            AccessTokenHolder accessTokenHolder,
            RefreshTokenHolder refreshTokenHolder
    ) {
        return new RetryableHandlerRegistry()
                .addHandler(new TokenRetryHandler(accessTokenHolder, refreshTokenHolder));
    }

}
