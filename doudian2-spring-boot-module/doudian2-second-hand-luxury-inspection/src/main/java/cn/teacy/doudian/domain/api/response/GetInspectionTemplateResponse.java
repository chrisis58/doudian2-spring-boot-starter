package cn.teacy.doudian.domain.api.response;

import cn.teacy.common.annotation.OpField;
import lombok.Data;

@Data
public class GetInspectionTemplateResponse {

    @OpField(
            desc = "检测模版信息",
            example = ""
    )
    private InspectionTemplate inspectionTemplate;

}