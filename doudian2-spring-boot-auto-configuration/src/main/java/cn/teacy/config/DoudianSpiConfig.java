package cn.teacy.config;

import cn.teacy.common.property.DoudianProperties;
import cn.teacy.doudian.advice.SpiBodyAdvice;
import cn.teacy.doudian.interceptor.SpiAuthInterceptor;
import cn.teacy.doudian.persistent.SpiAccessLogPersistent;
import cn.teacy.doudian.register.SpiServiceRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(SpiServiceRegistry.class)
@AutoConfiguration(
        after = {DoudianDefaultConfig.class}
)
@EnableConfigurationProperties({DoudianProperties.class})
public class DoudianSpiConfig {

    @Bean
    public SpiServiceRegistry spiServiceRegistry(
            DoudianProperties doudianProperties
    ) {
        return new SpiServiceRegistry(doudianProperties.getAdditionalPackages());
    }

    @Bean
    @ConditionalOnMissingBean
    public SpiAuthInterceptor spiAuthInterceptor(
            DoudianProperties doudianProperties,
            SpiAccessLogPersistent spiAccessLogPersistent
    ) {
        return new SpiAuthInterceptor(
                doudianProperties.getAppKey(),
                doudianProperties.getAppSecret(),
                spiAccessLogPersistent
        );
    }

    @Bean
    public SpiBodyAdvice spiBodyAdvice(
            ObjectMapper defaultObjectMapper,
            @Qualifier("doudianObjectMapper") ObjectMapper doudianObjectMapper
    ) {
        return new SpiBodyAdvice(defaultObjectMapper, doudianObjectMapper);
    }

}
