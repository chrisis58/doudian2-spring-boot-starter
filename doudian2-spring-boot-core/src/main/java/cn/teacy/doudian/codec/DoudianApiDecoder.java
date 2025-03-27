package cn.teacy.doudian.codec;

import cn.hutool.core.io.IoUtil;
import cn.teacy.common.constant.ApiResponseConstant;
import cn.teacy.common.doudian.api.CommonResponse;
import cn.teacy.common.exception.InvalidAccessTokenException;
import cn.teacy.common.exception.InvalidRefreshTokenException;
import cn.teacy.doudian.token.AccessTokenHolder;
import cn.teacy.doudian.token.RefreshTokenHolder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

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
        String body = IoUtil.read(response.body().asInputStream(), StandardCharsets.UTF_8);

        CommonResponse<?> commonResponse = objectMapper.readValue(body, new TypeReference<>() {
            @Override
            public Type getType() {
                return type;
            }
        });

        if (!ApiResponseConstant.StatusCode.OK.equals(commonResponse.getCode())) {
            log.warn("API response error: {}", commonResponse);
        }

        if (ApiResponseConstant.StatusCode.ACCESS_TOKEN_NOT_EXIST.equals(commonResponse.getCode())) {
            accessTokenHolder.clear();
            throw new InvalidAccessTokenException(response.request());
        } else if (ApiResponseConstant.StatusCode.REFRESH_TOKEN_NOT_EXIST.equals(commonResponse.getCode())) {
            refreshTokenHolder.clear();
            throw new InvalidRefreshTokenException(response.request());
        }

        return commonResponse;
    }

}
