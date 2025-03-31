package cn.teacy.doudian.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.teacy.common.annotation.OpParam;
import cn.teacy.common.doudian.api.ApiRequest;
import cn.teacy.common.interfaces.ISignService;
import cn.teacy.common.property.DoudianProperties;
import cn.teacy.common.util.MarshalUtil;
import cn.teacy.common.util.SignUtil;
import cn.teacy.doudian.token.AccessTokenRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Optional;

@RequiredArgsConstructor
public class DoudianSignService implements ISignService {

    private final DoudianProperties doudianProperties;

    /**
     * 对 api 参数进行签名
     * 会修改原有对象，返回签名后的对象
     */
    public <P> ApiRequest<P> sign(ApiRequest<P> apiRequest) {

        apiRequest.setAppKey(doudianProperties.getAppKey());
        apiRequest.setSignMethod("hmac-sha256");
        apiRequest.setTimestamp(
                Long.toString(System.currentTimeMillis() / 1000)
        );

        apiRequest.setV(doudianProperties.getVersion());

        apiRequest.setSign(
                SignUtil.sign(
                        doudianProperties.getAppKey(),
                        doudianProperties.getAppSecret(),
                        apiRequest.getMethod(),
                        apiRequest.getTimestamp(),
                        MarshalUtil.marshal(apiRequest.getParam())
                )
        );

        if (Optional.ofNullable(
                        AnnotationUtils.findAnnotation(apiRequest.getClass(), OpParam.class)
                ).map(OpParam::needToken)
                .orElse(true)
        ) {
            // 默认都需要token
            // 这里使用 SpringUtil 来获取 AccessTokenRetriever 的实例，避免循环依赖
            AccessTokenRetriever retriever = SpringUtil.getBean(AccessTokenRetriever.class);

            apiRequest.setAccessToken(retriever.retrieveToken());
        }

        return apiRequest;
    }

    public <P> ApiRequest<P> sign(P apiParam) {
        String method = Optional.ofNullable(
                        AnnotationUtils.findAnnotation(apiParam.getClass(), OpParam.class)
                ).map(OpParam::method)
                .orElseThrow(() -> new IllegalArgumentException(
                        "使用此方法必须使用 @OpParam 注解，并提供 method 值"
                ));

        if (StrUtil.isBlank(method)) {
            throw new IllegalArgumentException("method 不能为空");
        }

        return sign(new ApiRequest<>(method, apiParam));
    }

}
