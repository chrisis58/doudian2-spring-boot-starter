package cn.teacy.doudian.domain.spi.request;

import cn.teacy.common.annotation.OpField;
import lombok.Data;

@Data
public class LogisticsWmsCrossDockingCancelParam {
    @OpField(
            required = true,
            desc = "仓库编码",
            example = "01"
    )
    private String warehouseCode;
    @OpField(
            required = true,
            desc = "越库单号",
            example = "CD7387677015379804460"
    )
    private String crossDockingOrderNo;
    @OpField(
            required = true,
            desc = "订单码",
            example = "0973337391"
    )
    private String orderCode;
    @OpField(
            required = false,
            desc = "取消原因",
            example = "无"
    )
    private String reason;
}
