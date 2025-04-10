package cn.teacy.common.holder;

import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class InteractLogContextHolder {

    private static final ThreadLocal<String> route = new ThreadLocal<>();

    private static final ThreadLocal<String> logId = new ThreadLocal<>();

    private static final ThreadLocal<Map<String, String>> query = new ThreadLocal<>();

    private static final ThreadLocal<String> requestBody = new ThreadLocal<>();

    private static final ThreadLocal<String> responseBody = new ThreadLocal<>();

    public static void setRoute(String r) {
        route.set(r);
    }

    public static String getRoute() {
        return route.get();
    }

    public static void setLogId(String id) {
        logId.set(id);
    }

    public static String getLogId() {
        return logId.get();
    }

    public static void setQuery(Map<String, String> q) {
        query.set(q);
    }

    public static Map<String, String> getQuery() {
        return query.get();
    }

    public static void setRequestBody(String body) {
        requestBody.set(body);
    }

    public static String getRequestBody() {
        return requestBody.get();
    }

    public static void setResponseBody(String body) {
        responseBody.set(body);
    }

    public static String getResponseBody() {
        return responseBody.get();
    }

    public static void clearAll() {
        route.remove();
        logId.remove();
        query.remove();
        requestBody.remove();
        responseBody.remove();
    }

}
