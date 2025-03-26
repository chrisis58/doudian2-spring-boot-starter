package cn.teacy.doudian.domain.api.response;

import cn.teacy.common.annotation.OpField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrossDockingCallbackResponse {

    @OpField(
            desc = "失败异常码",
            example = "1004"
    )
    private Long code;
    @OpField(
            desc = "详细失败原因",
            example = "参数非法"
    )
    private String msg;
    @OpField(
            desc = "越库单回告事件结果",
            example = ""
    )
    private String data;
}
