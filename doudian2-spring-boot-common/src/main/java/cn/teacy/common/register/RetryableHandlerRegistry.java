package cn.teacy.common.register;

import cn.teacy.common.interfaces.RetryableResponseHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RetryableHandlerRegistry {

    private final Set<RetryableResponseHandler> handlers = new HashSet<>();

    private Set<RetryableResponseHandler> cachedHandlers = null;

    public RetryableHandlerRegistry addHandler(RetryableResponseHandler handler) {
        handlers.add(handler);
        evictCache();
        return this;
    }

    public RetryableHandlerRegistry excludeHandler(RetryableResponseHandler handler) {
        handlers.remove(handler);
        evictCache();
        return this;
    }

    public Set<RetryableResponseHandler> getHandlers() {
        if (cachedHandlers == null) {
            cachedHandlers = handlers.stream().collect(Collectors.toUnmodifiableSet());
        }
        return cachedHandlers;
    }

    private void evictCache() {
        cachedHandlers = null;
    }

}
