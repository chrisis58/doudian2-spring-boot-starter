package cn.teacy.doudian.token;

import cn.hutool.core.util.StrUtil;
import cn.teacy.common.constant.ApiResponseConstant;
import cn.teacy.common.constant.TokenRetrieveConstant;
import cn.teacy.common.doudian.api.CommonResponse;
import cn.teacy.common.interfaces.TokenRetriever;
import cn.teacy.common.property.DoudianTokenRetrieverProperties;
import cn.teacy.doudian.client.CommonClient;
import cn.teacy.doudian.domain.request.CreateTokenParam;
import cn.teacy.doudian.domain.request.RefreshTokenParam;
import cn.teacy.doudian.domain.response.CreateTokenResponse;
import cn.teacy.doudian.domain.response.RefreshTokenResponse;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

public interface AccessTokenRetriever extends TokenRetriever<String> {

    @Override
    String retrieveToken();

    @RequiredArgsConstructor
    class DEFAULT implements AccessTokenRetriever {

        private final AccessTokenHolder accessTokenHolder;
        private final RefreshTokenHolder refreshTokenHolder;
        private final CommonClient commonClient;
        private final DoudianTokenRetrieverProperties doudianTokenRetrieverProperties;

        /**
         * 获取 accessToken
         * 如果没有 accessToken，会尝试使用 refreshToken 刷新 accessToken
         * 如果没有 refreshToken，会使用 authId 创建新的 token
         */
        @Override
        public String retrieveToken() {
            String accessToken = accessTokenHolder.getToken();

            if (StringUtils.isNotBlank(accessToken)) {
                // 如果有accessToken，直接返回
                return accessToken;
            }

            String refreshToken = refreshTokenHolder.getToken();

            if (StringUtils.isNotBlank(refreshToken)) {
                // 如果有refreshToken，使用refreshToken刷新accessToken
                CommonResponse<RefreshTokenResponse> commonResponse = commonClient.refreshToken(new RefreshTokenParam(refreshToken));

                if (ApiResponseConstant.StatusCode.OK.equals(commonResponse.getCode())) {
                    RefreshTokenResponse data = commonResponse.getData();

                    String newAccessToken = data.getAccessToken();
                    String newRefreshToken = data.getRefreshToken();

                    accessTokenHolder.setToken(newAccessToken);
                    refreshTokenHolder.setToken(newRefreshToken);

                    return newAccessToken;
                }
            }

            // 如果 refresh token 已经失效
            // 或者两个 token 都没有，使用 authId 创建新的 token
            CreateTokenParam createTokenParam = new CreateTokenParam();

            createTokenParam.setTestShop(doudianTokenRetrieverProperties.getTestShop());
            createTokenParam.setAuthSubjectType(doudianTokenRetrieverProperties.getAuthSubjectType().getValue());

            if (StrUtil.isNotBlank(doudianTokenRetrieverProperties.getAuthSubjectType().getValue())) {
                createTokenParam.setAuthId(doudianTokenRetrieverProperties.getAuthId());
            } else {
                createTokenParam.setShopId(doudianTokenRetrieverProperties.getShopId());
            }

            createTokenParam.setGrantType(doudianTokenRetrieverProperties.getGrantType().getValue());
            if (TokenRetrieveConstant.GRANT_TYPE.AUTHORIZATION_CODE.equals(doudianTokenRetrieverProperties.getGrantType())) {
                createTokenParam.setCode(doudianTokenRetrieverProperties.getCode());
            }

            CreateTokenResponse data = commonClient.createToken(createTokenParam).getData();

            if (data == null) {
                throw new RuntimeException("获取 token 失败");
            }

            String newAccessToken = data.getAccessToken();
            String newRefreshToken = data.getRefreshToken();

            accessTokenHolder.setToken(newAccessToken);
            refreshTokenHolder.setToken(newRefreshToken);

            return newAccessToken;
        }
    }

}
