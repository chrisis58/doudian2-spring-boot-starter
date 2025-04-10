package cn.teacy.doudian.handler;

import cn.teacy.common.constant.ApiResponseConstant;
import cn.teacy.common.doudian.api.CommonResponse;
import cn.teacy.common.exception.InvalidAccessTokenException;
import cn.teacy.common.exception.InvalidRefreshTokenException;
import cn.teacy.common.interfaces.RetryableResponseHandler;
import cn.teacy.doudian.token.AccessTokenHolder;
import cn.teacy.doudian.token.RefreshTokenHolder;
import feign.Request;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenRetryHandler implements RetryableResponseHandler {

    private final AccessTokenHolder accessTokenHolder;
    private final RefreshTokenHolder refreshTokenHolder;

    @Override
    public void handle(CommonResponse<?> response, Request request) throws RetryableException {
        if (ApiResponseConstant.SubStatus.ACCESS_TOKEN_NOT_EXIST.getSubCode().equals(response.getSubCode())
                || ApiResponseConstant.SubStatus.ACCESS_TOKEN_EXPIRED.getSubCode().equals(response.getSubCode())
        ) {
            accessTokenHolder.clear();
            throw new InvalidAccessTokenException(request);
        } else if (
                ApiResponseConstant.SubStatus.REFRESH_TOKEN_NOT_EXIST.getSubCode().equals(response.getSubCode())
        ) {
            accessTokenHolder.clear();
            refreshTokenHolder.clear();
            throw new InvalidRefreshTokenException(request);
        }
    }
}
