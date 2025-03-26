package cn.teacy.common.doudian.api;

import cn.teacy.common.util.MarshalUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiRequest<P> {

    private String method;

    private String appKey;

    private String timestamp;

    private String accessToken;

    private String v;

    private String sign;

    private String signMethod;

    private P param;


    /**
     * 简单初始化
     *
     * @param method 接口名称
     * @param param 业务参数
     */
    public ApiRequest(String method, P param) {
        this.method = method;
        this.param = param;
    }

    public RequestParam toRequestParam() {
        return new RequestParam(method, appKey, timestamp, accessToken, v, sign, signMethod);
    }

    public String getParamJson() {
        return MarshalUtil.toJson(param);
    }


    /**
     * 这个类用于保存请求参数
     * 缺少了 param 字段，因为这个内容要作为 request body 发送
     *
     */
    @Data
    @AllArgsConstructor
    public static class RequestParam {
        private String method;
        private String app_key;
        private String timestamp;
        private String access_token;
        private String v;
        private String sign;
        private String sign_method;
    }

    public Map<String, String> toQueryMap() {
        return Map.of(
                "method", Optional.ofNullable(method).orElseGet(() -> ""),
                "app_key", Optional.ofNullable(appKey).orElseGet(() -> ""),
                "timestamp", Optional.ofNullable(timestamp).orElseGet(() -> ""),
                "access_token", Optional.ofNullable(accessToken).orElseGet(() -> ""),
                "v", Optional.ofNullable(v).orElseGet(() -> ""),
                "sign", Optional.ofNullable(sign).orElseGet(() -> ""),
                "sign_method", Optional.ofNullable(signMethod).orElseGet(() -> "")
        );
    }

}
