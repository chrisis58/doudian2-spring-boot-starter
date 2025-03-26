package cn.teacy.common.doudian.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SpiAccessLog {

    private Long id;

    private String logId;

    private String request;

    private String response;

    private Date accessTime;

}
