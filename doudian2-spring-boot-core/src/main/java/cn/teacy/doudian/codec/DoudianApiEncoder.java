package cn.teacy.doudian.codec;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.teacy.common.annotation.OpParam;
import cn.teacy.common.doudian.api.ApiRequest;
import cn.teacy.common.interfaces.ISignService;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;

import java.lang.reflect.Type;
import java.util.Map;

public class DoudianApiEncoder implements Encoder {

    private final Encoder delegate;
    private final ISignService signService;

    public DoudianApiEncoder(Encoder delegate, ISignService signService) {
        this.delegate = delegate;
        this.signService = signService;
    }

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        if (object instanceof ApiRequest<?> apiRequest) {
            Map<String, String> queryMap = apiRequest.toQueryMap();
            queryMap.forEach((key, value) -> {
                if (value != null) {
                    template.query(key, value);
                }
            });
            String body = apiRequest.getParamJson();
            template.body(body);
        } else if (AnnotationUtil.hasAnnotation(object.getClass(), OpParam.class)) {
            ApiRequest<Object> signed = signService.sign(object);

            signed.toQueryMap().forEach((key, value) -> {
                if (value != null) {
                    template.query(key, value);
                }
            });
            template.body(signed.getParamJson());

        } else {
            delegate.encode(object, bodyType, template);
        }
    }

}
