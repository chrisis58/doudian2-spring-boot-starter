package cn.teacy.doudian.domain.api.response;

import cn.teacy.common.annotation.OpField;
import lombok.Data;

import java.util.List;

@Data
public class MaterialRule {
    @OpField(
            desc = "值大小限定（材料类型为图片、文档、视频时限制大小）",
            example = "1"
    )
    private Long sizeLimit;
    @OpField(
            desc = "下拉项列表（材料类型为单选、复选时使用）",
            example = ""
    )
    private List<SelectItem> selectItemList;
    @OpField(
            desc = "是否必填",
            example = "false"
    )
    private Boolean isMust;
    @OpField(
            desc = "材料ID",
            example = "7409896542142284069"
    )
    private String materialRuleId;
    @OpField(
            desc = "材料类型，1--图片，2--视频，3--文档，4--数字，5--单行文本，6--多行文本，7--单选，8--多选",
            example = "1"
    )
    private Long materialType;
    @OpField(
            desc = "材料名称",
            example = "图片"
    )
    private String materialName;
    @OpField(
            desc = "文本字数下限，材料类型为单行文本/多行文本使用。",
            example = "0"
    )
    private Long lowerLimit;
    @OpField(
            desc = "文本字数上限，材料类型为单行文本/多行文本使用。",
            example = "100"
    )
    private Long upperLimit;
}
