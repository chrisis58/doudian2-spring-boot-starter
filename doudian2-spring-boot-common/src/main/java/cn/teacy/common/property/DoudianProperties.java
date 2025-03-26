package cn.teacy.common.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "doudian")
public class DoudianProperties {

    private String appKey;

    private String appSecret;

    private String authId;

    private String version = "2";

    private String[] additionalPackages = {};

    private String[] requestHeaders = {};

}
