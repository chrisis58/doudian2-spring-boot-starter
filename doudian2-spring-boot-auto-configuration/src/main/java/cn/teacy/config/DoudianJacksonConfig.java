package cn.teacy.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@ConditionalOnClass(ObjectMapper.class)
@AutoConfiguration
public class DoudianJacksonConfig {

    @Bean
    @ConditionalOnMissingBean(name = "doudianObjectMapper")
    @Qualifier("doudianObjectMapper")
    public ObjectMapper doudianObjectMapper(Jackson2ObjectMapperBuilderCustomizer customizer) {
        Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = new Jackson2ObjectMapperBuilder();
        customizer.customize(jackson2ObjectMapperBuilder);

        return jackson2ObjectMapperBuilder
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .createXmlMapper(false).build();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(name = "objectMapper")
    @Qualifier("objectMapper")
    public ObjectMapper defaultObjectMapper(Jackson2ObjectMapperBuilderCustomizer customizer) {
        Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = new Jackson2ObjectMapperBuilder();
        customizer.customize(jackson2ObjectMapperBuilder);

        return jackson2ObjectMapperBuilder.createXmlMapper(false).build();
    }

}
