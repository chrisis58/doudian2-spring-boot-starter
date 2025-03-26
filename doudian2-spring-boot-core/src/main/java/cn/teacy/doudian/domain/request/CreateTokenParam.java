package cn.teacy.doudian.domain.request;

import cn.teacy.common.annotation.OpField;
import cn.teacy.common.annotation.OpParam;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@OpParam(
        value = "token.create",
        method = "token.create",
        needToken = false
)
public class CreateTokenParam {

    @OpField(
            required = true,
            desc = "授权id，该编码就是实际的auth_id，后台动态生成的",
            example = "powertestexpress"
    )
    @NonNull
    private String authId;

    @OpField(
            required = true,
            desc = "授权主体类型,固定传值:【WuLiuShang】",
            example = "WuLiuShang"
    )
    private String authSubjectType = "WuLiuShang";

    @OpField(
            required = true,
            desc = "授权类型,固定传值【authorization_self】",
            example = "authorization_self"
    )
    private String grantType = "authorization_self";

}
