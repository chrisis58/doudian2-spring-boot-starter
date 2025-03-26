package cn.teacy.doudian.domain.api.request;

import cn.teacy.common.annotation.OpField;
import cn.teacy.common.annotation.OpParam;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@OpParam(value = "inspection.inspectionTags")
@AllArgsConstructor
public class InspectionInspectionTagsParam {

    @OpField(
            required = true,
            desc = "订单码",
            example = "1234566"
    )
    private String orderCode;

}
