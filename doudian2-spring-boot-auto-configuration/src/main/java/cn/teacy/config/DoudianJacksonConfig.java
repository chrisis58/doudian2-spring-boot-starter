package cn.teacy.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@ConditionalOnClass(ObjectMapper.class)
@AutoConfiguration
public class DoudianJacksonConfig {

    @Bean
    @Qualifier("doudianJacksonCustomizer")
    public Jackson2ObjectMapperBuilderCustomizer doudianJacksonCustomizer() {
        return builder -> builder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    @Bean
    @ConditionalOnMissingBean(name = "doudianObjectMapper")
    @Qualifier("doudianObjectMapper")
    public ObjectMapper doudianObjectMapper(@Qualifier("doudianJacksonCustomizer") Jackson2ObjectMapperBuilderCustomizer customizer) {
        Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = new Jackson2ObjectMapperBuilder();
        customizer.customize(jackson2ObjectMapperBuilder);

        return jackson2ObjectMapperBuilder.createXmlMapper(false).build();
    }

}
