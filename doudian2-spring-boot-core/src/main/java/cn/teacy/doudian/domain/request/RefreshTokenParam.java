package cn.teacy.doudian.domain.request;

import cn.teacy.common.annotation.OpField;
import cn.teacy.common.annotation.OpParam;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@RequiredArgsConstructor
@SuperBuilder
@OpParam(
        name = "刷新token请求参数",
        method = "token.refresh",
        needToken = false
)
public class RefreshTokenParam {

    @OpField(
            required = true,
            desc = "用于刷新access_token的刷新令牌；有效期：14 天；",
            example = "82bdc687-eff1-4f63-8444-0b43086c25fd"
    )
    @NonNull
    private String refreshToken;

    @OpField(
            required = true,
            desc = "授权类型；请传入默认值：refresh_token",
            example = "refresh_token1"
    )
    private String grantType = "refresh_token";
}
