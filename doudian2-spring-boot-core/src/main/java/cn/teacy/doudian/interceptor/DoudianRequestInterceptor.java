package cn.teacy.doudian.interceptor;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.teacy.common.interfaces.SupplierRegistry;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class DoudianRequestInterceptor implements RequestInterceptor {

    private final Map<String, String> headers;
    private final SupplierRegistry<String, String> supplierRegistry;

    private static final Pattern TEMPLATE_PATTERN = Pattern.compile("`\\w+`");

    public DoudianRequestInterceptor(String[] headers, SupplierRegistry<String, String> supplierRegistry) {
        this.headers = resolveHeaders(headers);
        this.supplierRegistry = supplierRegistry;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Content-Type", "application/json");
        headers.forEach((key, value) -> {

            StringWrapper wrapper = new StringWrapper(value);

            ReUtil.findAll(TEMPLATE_PATTERN, wrapper.getValue(), (matcher) -> {
                String group = matcher.group();
                String eval = supplierRegistry.eval(group.substring(1, group.length() - 1));
                wrapper.setValue(StrUtil.replace(wrapper.getValue(), group, eval));
            });

            requestTemplate.header(key, wrapper.getValue());
        });
    }

    @Data
    @AllArgsConstructor
    static class StringWrapper {
        String value;
    }

    private static Map<String, String> resolveHeaders(String[] headers) {
        return Arrays.stream(headers)
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
}
