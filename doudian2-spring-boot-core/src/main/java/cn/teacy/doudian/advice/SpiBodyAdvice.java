package cn.teacy.doudian.advice;

import cn.teacy.common.annotation.SpiEndpoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class SpiBodyAdvice extends RequestBodyAdviceAdapter implements ResponseBodyAdvice<Object> {

    private final ObjectMapper defaultObjectMapper;
    private final ObjectMapper spiObjectMapper;

    private final boolean isNamingStrategySame;

    public SpiBodyAdvice(
            ObjectMapper defaultObjectMapper,
            ObjectMapper spiObjectMapper
    ) {
        this.defaultObjectMapper = defaultObjectMapper;
        this.spiObjectMapper = spiObjectMapper;

        this.isNamingStrategySame = Objects.equals(
                defaultObjectMapper.getPropertyNamingStrategy(),
                spiObjectMapper.getPropertyNamingStrategy()
        );
    }

    @Override
    public boolean supports(
            MethodParameter methodParameter,
            @NonNull Type targetType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType
    ) {
        // 仅对标记了 @SpiEndpoint 的接口生效
        return Objects.nonNull(
                Optional.ofNullable(methodParameter.getMethodAnnotation(SpiEndpoint.class))
                        .orElseGet(() -> methodParameter.getContainingClass().getAnnotation(SpiEndpoint.class))
        );
    }

    @Override
    @NonNull
    public HttpInputMessage beforeBodyRead(
            @NonNull HttpInputMessage inputMessage,
            @NonNull MethodParameter parameter,
            @NonNull Type targetType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType
    ) throws IOException {

        if (isNamingStrategySame) {
            return inputMessage;
        }

        try {
            // 读取 snake case 的 JSON 字符串
            String originalJson = StreamUtils.copyToString(inputMessage.getBody(), StandardCharsets.UTF_8);
            JavaType javaType = spiObjectMapper.getTypeFactory().constructType(targetType);
            Object parsedObject = spiObjectMapper.readValue(originalJson, javaType);

            // 翻译为默认 objectMapper 支持的 JSON 字符串
            String newJson = defaultObjectMapper.writeValueAsString(parsedObject);
            ByteArrayInputStream bis = new ByteArrayInputStream(newJson.getBytes(StandardCharsets.UTF_8));
            return new HttpInputMessage() {
                @Override
                @NonNull
                public InputStream getBody() {
                    return bis;
                }
                @Override
                @NonNull
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };
        } catch (JsonProcessingException e) {
            log.error("转换 SPI 请求体失败", e);
            throw new RuntimeException("转换 SPI 请求体失败", e);
        }
    }

    // --------------------- ResponseBodyAdvice 实现 ---------------------

    @Override
    public boolean supports(
            MethodParameter returnType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType
    ) {
        // 对标记了 @SpiEndpoint 的接口生效
        SpiEndpoint endpoint = returnType.getMethodAnnotation(SpiEndpoint.class);
        if (endpoint == null) {
            endpoint = returnType.getContainingClass().getAnnotation(SpiEndpoint.class);
        }
        return endpoint != null;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull org.springframework.http.server.ServerHttpRequest request,
            @NonNull org.springframework.http.server.ServerHttpResponse response
    ) {
        try {
            // 使用自定义 ObjectMapper 序列化响应体
            String json = spiObjectMapper.writeValueAsString(body);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return json;
        } catch (JsonProcessingException e) {
            log.error("序列化 SPI 响应失败", e);
            throw new RuntimeException("序列化 SPI 响应失败", e);
        }
    }
}
