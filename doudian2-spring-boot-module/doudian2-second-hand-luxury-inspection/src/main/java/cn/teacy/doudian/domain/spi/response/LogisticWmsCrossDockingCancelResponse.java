package cn.teacy.doudian.domain.spi.response;

import cn.teacy.common.annotation.OpField;
import cn.teacy.common.doudian.spi.ISpiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogisticWmsCrossDockingCancelResponse implements ISpiResponse {

    @OpField(
            desc = "状态/错误码",
            example = "0"
    )
    private Long code;
    @OpField(
            desc = "状态描述",
            example = "success"
    )
    private String msg;
    @OpField(
            desc = "仓库编码",
            example = "01"
    )
    private String warehouseCode;
    @OpField(
            desc = "越库单号",
            example = "CD000001"
    )
    private String crossDockingOrderNo;
    @OpField(
            desc = "订单码",
            example = "384290257"
    )
    private String orderCode;
    @OpField(
            desc = "备注",
            example = "无"
    )
    private String remark;

}
