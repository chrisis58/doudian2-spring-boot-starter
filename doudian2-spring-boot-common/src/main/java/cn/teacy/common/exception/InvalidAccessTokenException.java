package cn.teacy.common.exception;

import feign.Request;
import feign.RetryableException;

import java.util.Collection;
import java.util.Map;

public class InvalidAccessTokenException extends RetryableException {

    private static final String INVALID_ACCESS_TOKEN_MESSAGE = "无效的访问令牌";

    public InvalidAccessTokenException(Request request) {
        super(401, INVALID_ACCESS_TOKEN_MESSAGE, Request.HttpMethod.GET, 300L, request);
    }

    public InvalidAccessTokenException(int status, String message, Request.HttpMethod httpMethod, Long retryAfter, Request request, byte[] responseBody, Map<String, Collection<String>> responseHeaders) {
        super(status, message, httpMethod, retryAfter, request, responseBody, responseHeaders);
    }

    public InvalidAccessTokenException(int status, String message, Request.HttpMethod httpMethod, Long retryAfter, Request request) {
        super(status, message, httpMethod, retryAfter, request);
    }

    public InvalidAccessTokenException(int status, String message, Request.HttpMethod httpMethod, Throwable cause, Long retryAfter, Request request) {
        super(status, message, httpMethod, cause, retryAfter, request);
    }
}
