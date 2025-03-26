package cn.teacy.doudian.config;


import cn.teacy.common.interfaces.ISignService;
import cn.teacy.doudian.codec.DoudianApiDecoder;
import cn.teacy.doudian.codec.DoudianApiEncoder;
import cn.teacy.doudian.interceptor.DoudianRequestInterceptor;
import cn.teacy.doudian.token.AccessTokenHolder;
import cn.teacy.doudian.token.RefreshTokenHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;


@Slf4j
public class DoudianApiClientConfig {

    @Value("${doudian.request-headers}")
    private String[] headers;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new DoudianRequestInterceptor(headers);
    }

    @Bean
    public Decoder doudianDecoder(
            @Qualifier("doudianObjectMapper") ObjectMapper objectMapper,
            AccessTokenHolder accessTokenHolder,
            RefreshTokenHolder refreshTokenHolder
    ) {
        return new DoudianApiDecoder(objectMapper, accessTokenHolder, refreshTokenHolder);
    }

    @Bean
    public Encoder doudianEncoder(ISignService signService) {
        return new DoudianApiEncoder(new SpringEncoder(HttpMessageConverters::new), signService);
    }

}
