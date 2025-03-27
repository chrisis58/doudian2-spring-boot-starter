package cn.teacy.config;

import cn.teacy.common.interfaces.ISignService;
import cn.teacy.common.property.DoudianApiProperties;
import cn.teacy.common.property.DoudianProperties;
import cn.teacy.common.property.DoudianTokenRetrieverProperties;
import cn.teacy.doudian.client.CommonClient;
import cn.teacy.doudian.service.DoudianSignService;
import cn.teacy.doudian.token.AccessTokenHolder;
import cn.teacy.doudian.token.AccessTokenRetriever;
import cn.teacy.doudian.token.RefreshTokenHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@ConditionalOnClass({
        ISignService.class,
        DoudianSignService.class
})
@AutoConfiguration(
        after = {DoudianDefaultConfig.class}
)
@RequiredArgsConstructor
@EnableConfigurationProperties({DoudianProperties.class, DoudianTokenRetrieverProperties.class, DoudianApiProperties.class})
@EnableFeignClients(basePackages = "cn.teacy")
public class DoudianSignConfig {

    @Bean
    @ConditionalOnMissingBean
    public AccessTokenRetriever accessTokenRetriever(
            AccessTokenHolder accessTokenHolder,
            RefreshTokenHolder refreshTokenHolder,
            CommonClient commonClient,
            DoudianTokenRetrieverProperties doudianTokenRetrieverProperties
    ) {
        return new AccessTokenRetriever.DEFAULT(
                accessTokenHolder,
                refreshTokenHolder,
                commonClient,
                doudianTokenRetrieverProperties
        );
    }

    @Bean
    @Primary
    public ISignService signService(
            DoudianProperties doudianProperties
    ) {
        return new DoudianSignService(
                doudianProperties
        );
    }


}
