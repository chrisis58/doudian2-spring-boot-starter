package cn.teacy.doudian.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class DoudianRequestInterceptor implements RequestInterceptor {

    private final Map<String, String> headers;

    public DoudianRequestInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    public DoudianRequestInterceptor(String[] headers) {
        this.headers = Arrays.stream(headers)
                .map(header -> {
                    String[] split = header.split(":");
                    if (split.length != 2) {
                        log.warn("Invalid header: {}", header);
                        return null;
                    }
                    return split;
                })
                .filter(Objects::nonNull)
                .collect(
                        java.util.stream.Collectors.toMap(
                                split -> split[0],
                                split -> split[1]
                        )
                );
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Content-Type", "application/json");
        headers.forEach(requestTemplate::header);
    }
}
