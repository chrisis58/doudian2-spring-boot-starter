package cn.teacy.doudian.domain.api.response;

import cn.teacy.common.annotation.OpField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CrossDockingPrintWaybillResponse {

    @OpField(
            desc = "越库单号",
            example = "CD7397266657065943340"
    )
    private String crossDockingOrderNo;
    @OpField(
            desc = "订单码",
            example = "0755057550"
    )
    private String orderCode;
    @OpField(
            desc = "快递承运商",
            example = "shunfeng"
    )
    private String logisticsCode;
    @OpField(
            desc = "快递单号",
            example = "SF0001"
    )
    private String logisticsNo;
    @OpField(
            desc = "面单打印信息",
            example = "{cmd:print,requestID:123458976,version:1.0,isOpen:false,task:{taskID:01_CD7397266657065943340_20240809143106,printer:,preview:false,config:,documents:[{docNo:CD7397266657065943340,documentID:41010469075,copy:1,contents:[{params:AppKey=edf6edb8-4882-46e1-a557-67c0d795fb25u0026ShopId=1001672549713098u0026TimeStamp=2024-08-09 06:31:06u0026V=1u0026Sign=6bde0c4d723de0f540563d6a18d5d70a,signature:SvVuZio1X8gjOx49i0Rk5m1pnT8fHMp+O3IrFmtZyhmxnGxLJG81JdsYHbM39fSvItrLM76JeXfl1JUmc3lf2x9nwjMqiBI2glRSsHZqXmXuRNVDAcxwzsvWsplOlhT64XJoaU2Wz7muitKw8dI3/0wFXfwV0gVT7FBFjRykA6scuZdpK0dqnva/BP4ksMhAQHAqcag4DoyZtytZOoXPy92H1ORnfB3bFYzEiDYTmS0LylUPZB0N4mywS+hsTmEMAEUI9MmZqMlM1/DAybWfWQ5YrpQN6jYZDO2o9SmcxDj574KmFaPgjeGhXr3iCzaitvaxOj+Y+PPVAMIFmhrCEQ==,encryptedData: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,templateURL:https://tosv.boe.byted.org/obj/logistics-davinci/template/v2/yuantong_76_130.xml}]}]}}"
    )
    private String printInfo;
    @OpField(
            desc = "订单流向（枚举值：consumer消费者，seller商家）",
            example = "消费者"
    )
    private String orderFlow;
    @OpField(
            desc = "备注",
            example = "无"
    )
    private String remark;
    @OpField(
            desc = "仓编码",
            example = "01"
    )
    private String warehouseCode;
}
