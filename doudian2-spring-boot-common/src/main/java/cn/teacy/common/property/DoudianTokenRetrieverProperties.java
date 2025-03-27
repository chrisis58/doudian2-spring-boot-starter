package cn.teacy.common.property;

import cn.teacy.common.constant.TokenRetrieveConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("doudian.token-retrieve")
public class DoudianTokenRetrieverProperties {

    private String code;

    private TokenRetrieveConstant.GRANT_TYPE grantType;

    private String testShop;

    private String shopId;

    private String authId;

    private TokenRetrieveConstant.AUTH_SUBJECT_TYPE authSubjectType;

}
