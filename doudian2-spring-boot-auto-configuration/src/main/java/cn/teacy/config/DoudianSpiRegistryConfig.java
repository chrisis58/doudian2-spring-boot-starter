package cn.teacy.config;

import cn.teacy.common.property.DoudianProperties;
import cn.teacy.doudian.register.SpiServiceRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(SpiServiceRegistry.class)
@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(DoudianProperties.class)
public class DoudianSpiRegistryConfig {

    private final DoudianProperties doudianProperties;

    @Bean
    @ConditionalOnMissingBean
    public SpiServiceRegistry spiServiceRegistry() {
        return new SpiServiceRegistry(doudianProperties.getAdditionalPackages());
    }

}
