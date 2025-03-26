package cn.teacy.doudian.domain.api.request;

import cn.teacy.common.annotation.OpField;
import cn.teacy.common.annotation.OpParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@OpParam("logistics.wms/crossDockingPrintWaybill")
public class CrossDockingPrintWaybillParam {

    @OpField(
            required = true,
            desc = "仓库编码",
            example = "01"
    )
    private String warehouseCode;
    @OpField(
            required = true,
            desc = "越库单号",
            example = "CD7397266657065943340"
    )
    private String crossDockingOrderNo;
    @OpField(
            required = true,
            desc = "订单码",
            example = "0755057550"
    )
    private String orderCode;

}
