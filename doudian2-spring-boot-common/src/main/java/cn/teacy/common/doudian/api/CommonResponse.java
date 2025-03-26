package cn.teacy.common.doudian.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {

    private Number code;

    private String msg;

    private String subCode;

    private String subMsg;

    private T data;

    private String logId;

    public CommonResponse(Number code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
