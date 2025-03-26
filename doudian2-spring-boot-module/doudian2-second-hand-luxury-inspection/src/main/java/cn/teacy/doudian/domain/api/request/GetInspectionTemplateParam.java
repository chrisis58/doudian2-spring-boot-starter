package cn.teacy.doudian.domain.api.request;

import cn.teacy.common.annotation.OpField;
import cn.teacy.common.annotation.OpParam;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@OpParam("inspection.inspectionTemplate/get")
public class GetInspectionTemplateParam {

    @OpField(
            required = true,
            desc = "业务身份，固定值，由对接运营/研发提供",
            example = "second_luxury"
    )
    private String bizCode = "second_hand_luxury";
    @OpField(
            required = true,
            desc = "任务类型，固定值，由对接运营/研发提供",
            example = "each_goods_inspection"
    )
    private String taskType = "each_goods_inspection";
    @OpField(
            required = false,
            desc = "订单码",
            example = "123123"
    )
    @NonNull
    private String orderCode;

}

