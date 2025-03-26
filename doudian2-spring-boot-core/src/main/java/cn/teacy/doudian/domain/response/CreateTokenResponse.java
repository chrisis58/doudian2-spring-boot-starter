package cn.teacy.doudian.domain.response;

import cn.teacy.common.annotation.OpField;
import lombok.Data;

@Data
public class CreateTokenResponse {

    @OpField(
            desc = "token值。 Tips： 1. 在 access_token 过期前1h之前，ISV使用 refresh_token 刷新时，会返回原来的 access_token 和 refresh_token，但是二者有效期不会变； 2. 在 access_token 过期前1h之内，ISV使用 refresh_token 刷新时，会返回新的 access_token 和 refresh_token，但是原来的 access_token 和 refresh_token 继续有效一个小时； 3. 在 access_token 过期后，ISV使用 refresh_token 刷新时，将获得新的 acces_token 和 refresh_token，同时原来的 acces_token 和 refresh_token 失效；",
            example = "5a3bd7d0-1b48-46d6-811e-7e05ace08a2f"
    )
    private String accessToken;
    @OpField(
            desc = "过期时间(秒级时间戳)",
            example = "412219354"
    )
    private Long expiresIn;
    @OpField(
            desc = "刷新token值。用于刷新access_token的刷新令牌（有效期：14 天）",
            example = "50b6ae40-ed8f-4b60-a4af-2ed743a4b903"
    )
    private String refreshToken;
    @OpField(
            desc = "范围",
            example = "SCOPE"
    )
    private String scope;
    @OpField(
            desc = "店铺ID",
            example = "11111172"
    )
    private Long shopId;
    @OpField(
            desc = "店铺名称",
            example = "李飞测试放心花人店一体"
    )
    private String shopName;
    @OpField(
            desc = "授权ID",
            example = "3445403781179769176"
    )
    private String authorityId;
    @OpField(
            desc = "授权主体类型",
            example = "WuLiuShang"
    )
    private String authSubjectType;
    @OpField(
            desc = "操作店铺账号（加密），调用解密接口时候传入",
            example = "x123xxxxxxxxxx"
    )
    private String encryptOperator;
    @OpField(
            desc = "操作店铺账号昵称（脱敏）",
            example = "*磊"
    )
    private String operatorName;
    @OpField(
            desc = "店铺业务类型。0：普通店铺，1：即时零售连锁店，2：即时零售个体店",
            example = "0"
    )
    private Long shopBizType;
    @OpField(
            desc = "token生成对应的账号",
            example = "121212"
    )
    private String toutiaoId;
    @OpField(
            desc = "0表示token是由主账号生成，1表示token由子账号生成",
            example = "0"
    )
    private Long tokenType;

}
