package cn.teacy.common.doudian.domain;

import cn.teacy.common.holder.InteractLogContextHolder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class InteractLog {

    private Type type;

    private String logId;

    private String route;

    private Map<String, String> requestQuery;

    private String requestBody;

    private String responseBody;

    private Date accessTime;

    public enum Type {
        SPI,
        API;
    }

    public static InteractLog fromContext(Type type) {
        return InteractLog.builder()
                .type(type)
                .logId(InteractLogContextHolder.getLogId())
                .route(InteractLogContextHolder.getRoute())
                .requestQuery(InteractLogContextHolder.getQuery())
                .requestBody(InteractLogContextHolder.getRequestBody())
                .responseBody(InteractLogContextHolder.getResponseBody())
                .accessTime(new Date())
                .build();
    }

}
