package cn.teacy.doudian.domain.api.request;

import cn.teacy.common.annotation.OpField;
import cn.teacy.common.annotation.OpParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@OpParam("orderCode.crossdockingCallback")
public class CrossDockingCallbackParam {

    public interface EventType {

        Integer INBOUND = 300;
        Integer INBOUND_REJECTION = 801;
        Integer OUTBOUND = 600;

    }

    @OpField(
            required = true,
            desc = "越库单号，必填",
            example = "CD739774441779845xxxx"
    )
    private String crossDockingOrderNo;
    @OpField(
            required = false,
            desc = "出库参数 eventType= 600（已出库）时，必传",
            example = ""
    )
    private OutboundParam outboundParam;
    @OpField(
            required = false,
            desc = "入库参数 eventType= 801（入库拒收）时，必传",
            example = ""
    )
    private InboundParam inboundParam;
    @OpField(
            required = true,
            desc = "仓code，必填",
            example = "501"
    )
    private String warehouseCode;
    @OpField(
            required = true,
            desc = "越库事件类型，必填300-入库(收货)完成801-入库拒收，退回商家600-发货完成（已出仓）350-质检通过750-质检不通过，退回商家",
            example = "300"
    )
    private Integer eventType;
    @OpField(
            required = true,
            desc = "订单码，必填",
            example = "0520254260"
    )
    private String orderCode;
    @OpField(
            required = false,
            desc = "质检参数 eventType= 750（质检不通过）时，必传",
            example = ""
    )
    private QualifyParam qualifyParam;
    @OpField(
            required = true,
            desc = "事件发生时间 秒级时间戳",
            example = "1722515548"
    )
    private Long eventTime;
    @OpField(
            required = false,
            desc = "备注",
            example = "这是备注信息"
    )
    private String remark;


    @Data
    @AllArgsConstructor
    public static class OutboundParam {

        @OpField(
                required = false,
                desc = "快递信息",
                example = ""
        )
        private ExpressInfo expressInfo;
        @OpField(
                required = false,
                desc = "出库单明细",
                example = ""
        )
        private List<OutboundDetailsItem> outboundDetails;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExpressInfo {
        @OpField(
                required = false,
                desc = "通过平台取号，回传的快递单号以及快递公司编码，应与从平台取的面单号保持一致。线下取号，平台不会对快递单号和快递公司编码做平台取号一致性校验。但是需要注意快递商Code只能回传指定类型（目前支持：jd/shunfeng，如需新增可以联系平台配置）",
                example = "0-平台取号1-线下取号"
        )
        private Long logistisSource;
        @OpField(
                required = false,
                desc = "物流单号",
                example = "75530161931237"
        )
        private String logisticsNo;
        @OpField(
                required = false,
                desc = "快递公司编码",
                example = "zhongtong"
        )
        private String logisticsCode;
    }


    @Data
    @AllArgsConstructor
    public static class OutboundDetailsItem {
        @OpField(
                required = false,
                desc = "商品ID",
                example = "3697741457885666185"
        )
        private String productId;
        @OpField(
                required = false,
                desc = "包材信息，选填",
                example = ""
        )
        private PmInfo pmInfo;
    }


    @Data
    @AllArgsConstructor
    public static class PmInfo {
        @OpField(
                required = false,
                desc = "包材id",
                example = "305468"
        )
        private Long pmId;
        @OpField(
                required = false,
                desc = "包材名称",
                example = "机构内包装盒"
        )
        private String pmName;
        @OpField(
                required = false,
                desc = "包材数量",
                example = "1"
        )
        private Long pmNum;
    }


    @Data
    @AllArgsConstructor
    public static class InboundParam {
        @OpField(
                required = false,
                desc = "拒收原因",
                example = ""
        )
        private RejectionReason rejectionReason;
    }


    @Data
    @AllArgsConstructor
    public static class RejectionReason {
        @OpField(
                required = false,
                desc = "拒收原因Code",
                example = "枚举值参考：E301：货不对版商品信息不一致E302：无货少件E303：空包裹"
        )
        private String rejectionReasonCode;
        @OpField(
                required = false,
                desc = "拒收原因描述",
                example = "枚举值参考：E301：货不对版商品信息不一致E302：无货少件E303：空包裹"
        )
        private String rejectionReasonDesc;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QualifyParam {
        @OpField(
                required = false,
                desc = "质检不通过原因",
                example = "货不对板"
        )
        private String failReason;
    }
}
