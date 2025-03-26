package cn.teacy.doudian.domain.api.response;

import cn.teacy.common.annotation.OpField;
import lombok.Data;

import java.util.List;

@Data
public class InspectionItem {
    @OpField(
            desc = "操作示例",
            example = ""
    )
    private List<OperateExample> operateExampleList;
    @OpField(
            desc = "合格规则解释",
            example = "合格规则解释"
    )
    private String qualifiedRuleExplain;
    @OpField(
            desc = "合格类型：1 =【判定型】，展示不合格原因；2 = 【数值型】，根据合格规则计算 3 = 【材料型】，可以上传文件、图片、文本等材料信息；",
            example = "1"
    )
    private Long qualifiedType;
    @OpField(
            desc = "操作说明",
            example = "操作说明"
    )
    private String operateExplain;
    @OpField(
            desc = "两种情况下使用，情况1：合格类型为【材料型】，使用该检测模版；情况2：合格类型为【判定型】或者【数值型】时，作为材料补充。",
            example = ""
    )
    private List<MaterialRule> materialRuleList;
    @OpField(
            desc = "标准项code",
            example = "7468357016798314771_2"
    )
    private String itemCode;
    @OpField(
            desc = "标准项名称",
            example = "机身外观"
    )
    private String itemName;
    @OpField(
            desc = "是否必检",
            example = "false"
    )
    private Boolean isMustInspection;
    @OpField(
            desc = "操作示例",
            example = ""
    )
    private List<OperateExample> operateExample;
    @OpField(
            desc = "依据标准",
            example = "依据标准"
    )
    private String nationalStandard;
    @OpField(
            desc = "标准项详情",
            example = "标准项详情"
    )
    private String itemDetail;
}
