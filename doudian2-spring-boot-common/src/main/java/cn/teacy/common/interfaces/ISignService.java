package cn.teacy.common.interfaces;

import cn.teacy.common.doudian.api.ApiRequest;

public interface ISignService {

    <P> ApiRequest<P> sign(ApiRequest<P> request);

    <P> ApiRequest<P> sign(P params);

}
