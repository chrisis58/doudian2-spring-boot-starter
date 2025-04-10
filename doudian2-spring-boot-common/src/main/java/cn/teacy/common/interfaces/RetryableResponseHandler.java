package cn.teacy.common.interfaces;

import cn.teacy.common.doudian.api.CommonResponse;
import feign.Request;
import feign.RetryableException;

/**
 * 用于处理通用响应并判断是否需要抛出重试异常的接口
 */
public interface RetryableResponseHandler {

    /**
     * 处理通用响应并判断是否需要抛出重试异常
     *
     * @param response 通用响应对象
     * @throws RetryableException 如果需要重试，则抛出此异常
     */
    void handle(CommonResponse<?> response, Request request) throws RetryableException;
}
