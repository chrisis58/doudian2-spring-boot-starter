package cn.teacy.common.holder;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class SpiContextHolder {

    private static final ThreadLocal<String> LOG_ID = new ThreadLocal<>();

    public static void setLogId(String logId) {
        LOG_ID.set(logId);
    }

    public static String getLogId() {
        return LOG_ID.get();
    }

}
