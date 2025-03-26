package cn.teacy.doudian.domain.api.response;

import cn.teacy.common.annotation.OpField;
import lombok.Data;

import java.util.List;

@Data
public class InspectionTemplate {

    @OpField(
            desc = "是否需要备注",
            example = "false"
    )
    private Boolean isNeedRemark;
    @OpField(
            desc = "是否需要不合格总数",
            example = "false"
    )
    private Boolean isNeedUnqualifiedCount;
    @OpField(
            desc = "质检流程列表",
            example = ""
    )
    private List<InspectionFlow> inspectionFlowList;
    @OpField(
            desc = "标准集名称",
            example = "钟表类商品质检标准"
    )
    private String collectionName;
    @OpField(
            desc = "抽样信息",
            example = ""
    )
    private SampleInfo sampleInfo;
    @OpField(
            desc = "标准集code",
            example = "7409903349104361778_1"
    )
    private String collectionCode;
}
