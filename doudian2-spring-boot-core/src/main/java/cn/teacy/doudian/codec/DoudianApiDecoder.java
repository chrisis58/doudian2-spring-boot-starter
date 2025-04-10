package cn.teacy.doudian.codec;

import cn.teacy.common.constant.ApiResponseConstant;
import cn.teacy.common.doudian.api.CommonResponse;
import cn.teacy.common.exception.InvalidAccessTokenException;
import cn.teacy.common.exception.InvalidRefreshTokenException;
import cn.teacy.common.holder.InteractLogContextHolder;
import cn.teacy.doudian.token.AccessTokenHolder;
import cn.teacy.doudian.token.RefreshTokenHolder;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@Slf4j
public class DoudianApiDecoder implements Decoder {

    private final ObjectMapper objectMapper;

    private final AccessTokenHolder accessTokenHolder;

    private final RefreshTokenHolder refreshTokenHolder;

    public DoudianApiDecoder(
            ObjectMapper objectMapper,
            AccessTokenHolder accessTokenHolder,
            RefreshTokenHolder refreshTokenHolder
    ) {
        this.objectMapper = objectMapper;
        this.accessTokenHolder = accessTokenHolder;
        this.refreshTokenHolder = refreshTokenHolder;
    }

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

        if (ApiResponseConstant.SubStatus.ACCESS_TOKEN_NOT_EXIST.getSubCode().equals(commonResponse.getSubCode())
                || ApiResponseConstant.SubStatus.ACCESS_TOKEN_EXPIRED.getSubCode().equals(commonResponse.getSubCode())
        ) {
            accessTokenHolder.clear();
            throw new InvalidAccessTokenException(response.request());
        } else if (
                ApiResponseConstant.SubStatus.REFRESH_TOKEN_NOT_EXIST.getSubCode().equals(commonResponse.getSubCode())
        ) {
            accessTokenHolder.clear();
            refreshTokenHolder.clear();
            throw new InvalidRefreshTokenException(response.request());
        }

        return commonResponse;
    }

}
