package cn.teacy.common.exception;

import feign.Request;
import feign.RetryableException;

import java.util.Collection;
import java.util.Map;

public class InvalidRefreshTokenException extends RetryableException {

    private static final String INVALID_REFRESH_TOKEN_MESSAGE = "无效的刷新令牌";

    public InvalidRefreshTokenException(Request request) {
        super(401, INVALID_REFRESH_TOKEN_MESSAGE, Request.HttpMethod.GET, 300L, request);
    }

    public InvalidRefreshTokenException(int status, String message, Request.HttpMethod httpMethod, Long retryAfter, Request request, byte[] responseBody, Map<String, Collection<String>> responseHeaders) {
        super(status, message, httpMethod, retryAfter, request, responseBody, responseHeaders);
    }

    public InvalidRefreshTokenException(int status, String message, Request.HttpMethod httpMethod, Long retryAfter, Request request) {
        super(status, message, httpMethod, retryAfter, request);
    }

    public InvalidRefreshTokenException(int status, String message, Request.HttpMethod httpMethod, Throwable cause, Long retryAfter, Request request) {
        super(status, message, httpMethod, cause, retryAfter, request);
    }
}
