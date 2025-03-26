package cn.teacy.doudian.domain.api.response;

import cn.teacy.common.annotation.OpField;
import lombok.Data;

import java.util.List;

@Data
public class InspectionFlow {
    @OpField(
            desc = "质检流程名称",
            example = "基础信息"
    )
    private String flowName;
    @OpField(
            desc = "标准项列表",
            example = ""
    )
    private List<InspectionItem> inspectionItemList;
    @OpField(
            desc = "操作说明",
            example = "操作说明"
    )
    private String operateExplain;
    @OpField(
            desc = "操作示例",
            example = ""
    )
    private List<OperateExample> operateExampleList;
    @OpField(
            desc = "标准流程ID",
            example = "7409900754275909915"
    )
    private Long flowId;
}
