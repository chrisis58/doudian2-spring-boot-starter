package cn.teacy.doudian.domain.response;

import cn.teacy.common.annotation.OpField;
import lombok.Data;

@Data
public class RefreshTokenResponse {

    @OpField(
            desc = "用于调用API的access_token 过期时间为expires_in值 可通过refresh_token刷新获取新的access_token，过期时间仍为expires_in值",
            example = "82bdc687-eff1-4f63-8444-0b43086c25fd"
    )
    private String accessToken;
    @OpField(
            desc = "access_token过期时间；Unix时间戳：秒",
            example = "530808"
    )
    private Long expiresIn;
    @OpField(
            desc = "用于刷新access_token的刷新令牌（有效期：14 天）",
            example = "ed14a703-1f27-4a0b-9b94-759242744ec8"
    )
    private String refreshToken;
    @OpField(
            desc = "权限范围",
            example = "SCOPE"
    )
    private String scope;
    @OpField(
            desc = "店铺ID",
            example = "222"
    )
    private Long shopId;
    @OpField(
            desc = "店铺名称",
            example = "测试店铺"
    )
    private String shopName;
    @OpField(
            desc = "授权ID,店铺授权为店铺id，达人授权为达人id；",
            example = "1321324"
    )
    private String authorityId;
    @OpField(
            desc = "授权主体类型",
            example = "WuLiuShang"
    )
    private String authSubjectType;
    @OpField(
            desc = "店铺业务类型。0：普通店铺，1：即时零售连锁店，2：即时零售个体店",
            example = "0"
    )
    private Long shopBizType;

}

