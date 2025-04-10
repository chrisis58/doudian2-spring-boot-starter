package cn.teacy.config;

import cn.teacy.common.property.DoudianProperties;
import cn.teacy.doudian.aspect.ApiRequestLogAspect;
import cn.teacy.doudian.aspect.ClientRetryAspect;
import cn.teacy.doudian.aspect.SpiAccessLogAspect;
import cn.teacy.doudian.persistent.InteractLogPersistent;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@ConditionalOnClass({
        ClientRetryAspect.class,
        SpiAccessLogAspect.class
})
@AutoConfiguration(
        after = {DoudianDefaultConfig.class}
)
@EnableConfigurationProperties(DoudianProperties.class)
public class DoudianAspectConfig {

    @Bean
    public ClientRetryAspect clientRetryAspect(
            DoudianProperties doudianProperties
    ) {
        return new ClientRetryAspect();
    }

    @Bean
    public SpiAccessLogAspect spiAccessLogAspect(
            InteractLogPersistent persistent
    ) {
        return new SpiAccessLogAspect(persistent);
    }

    @Bean
    public ApiRequestLogAspect apiRequestLogAspect(
            InteractLogPersistent persistent
    ) {
        return new ApiRequestLogAspect(persistent);
    }

}
