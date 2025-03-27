package cn.teacy.common.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("doudian.api")
public class DoudianApiProperties {

    private String[] requestHeaders = {};

}
