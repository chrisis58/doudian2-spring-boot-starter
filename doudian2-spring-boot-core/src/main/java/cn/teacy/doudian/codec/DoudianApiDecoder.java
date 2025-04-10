package cn.teacy.doudian.codec;

import cn.teacy.common.constant.ApiResponseConstant;
import cn.teacy.common.doudian.api.CommonResponse;
import cn.teacy.common.holder.InteractLogContextHolder;
import cn.teacy.common.register.RetryableHandlerRegistry;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class DoudianApiDecoder implements Decoder {

    private final ObjectMapper objectMapper;
    private final RetryableHandlerRegistry registry;

    @SneakyThrows
    @Override
    public Object decode(Response response, Type type) throws FeignException {
        String body = StreamUtils.copyToString(response.body().asInputStream(), StandardCharsets.UTF_8);
        InteractLogContextHolder.setResponseBody(body);

        JavaType javaType = objectMapper.getTypeFactory().constructType(type);
        CommonResponse<?> commonResponse = objectMapper.readValue(body, javaType);

        if (!ApiResponseConstant.StatusCode.OK.getCode().equals(commonResponse.getCode())) {
            log.warn("API response error: {}", commonResponse);
        }

        registry.getHandlers()
                .forEach(handler -> handler.handle(commonResponse, response.request()));

        return commonResponse;
    }

}
