package cn.teacy.common.register;

import cn.teacy.common.interfaces.RetryableResponseHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RetryableHandlerRegistry {

    private final Set<RetryableResponseHandler> handlers = new HashSet<>();

    public RetryableHandlerRegistry addHandler(RetryableResponseHandler handler) {
        handlers.add(handler);
        return this;
    }

    public RetryableHandlerRegistry excludeHandler(RetryableResponseHandler handler) {
        handlers.remove(handler);
        return this;
    }

    public Set<RetryableResponseHandler> getHandlers() {
        return handlers.stream().collect(Collectors.toUnmodifiableSet());
    }

}
