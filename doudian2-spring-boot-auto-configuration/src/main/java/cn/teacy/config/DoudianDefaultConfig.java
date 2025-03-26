package cn.teacy.config;


import cn.teacy.doudian.persistent.SpiAccessLogPersistent;
import cn.teacy.doudian.token.AccessTokenHolder;
import cn.teacy.doudian.token.RefreshTokenHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass({
        AccessTokenHolder.class,
        RefreshTokenHolder.class,
        SpiAccessLogPersistent.class
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
    public SpiAccessLogPersistent spiAccessLogPersistent() {
        return new SpiAccessLogPersistent.JUST_LOG();
    }

}
