package cn.teacy.doudian.domain.request;

import cn.teacy.common.annotation.OpField;
import cn.teacy.common.annotation.OpParam;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@OpParam(
        value = "token.create",
        method = "token.create",
        needToken = false
)
public class CreateTokenParam {

    @OpField(
            required = false,
            desc = "授权码；参数必传，工具型应用: 传code值；自用型应用:传",
            example = "82bdc687-eff1-4f63-8444-0b43086c25fd"
    )
    private String code;
    @OpField(
            required = true,
            desc = "授权类型 ；【工具型应用:authorization_code  自用型应用:authorization_self】，如果自用型应用有授权code，传authorization_code",
            example = "authorization_code"
    )
    private String grantType;
    @OpField(
            required = false,
            desc = "判断测试店铺标识 ，非必传，若新增测试店铺传1，若不是则不必传",
            example = "2"
    )
    private String testShop;
    @OpField(
            required = false,
            desc = "店铺ID，抖店自研应用使用。当auth_subject_type不为空时，该字段请勿传值，请将值传入到auth_id字段中",
            example = "17239"
    )
    private String shopId;
    @OpField(
            required = false,
            desc = "授权id，配合auth_subject_type字段使用。当auth_subject_type不为空时，请使用auth_id字段传值，shop_id请勿使用。",
            example = "112334"
    )
    private String authId;
    @OpField(
            required = false,
            desc = "授权主体类型，配合auth_id字段使用，YunCang -云仓；WuLiuShang -物流商；WLGongYingShang -物流供应商；MiniApp -小程序；MCN-联盟MCN机构；DouKe-联盟抖客 ；Colonel-联盟团长；",
            example = "WuLiuShang"
    )
    private String authSubjectType;

}
