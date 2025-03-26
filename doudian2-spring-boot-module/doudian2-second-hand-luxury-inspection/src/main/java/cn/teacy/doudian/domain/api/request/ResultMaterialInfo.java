package cn.teacy.doudian.domain.api.request;

import cn.teacy.common.annotation.OpField;
import cn.teacy.doudian.domain.api.response.MaterialRule;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class ResultMaterialInfo {

    @OpField(
            required = true,
            desc = "需要将【标准集查询接口】对应的material_rule_id字段带过来，用于识别具体材料",
            example = "7369142527871615258"
    )
    @NonNull
    private String materialRuleId;

    @OpField(
            desc = "下拉项列表（材料类型为单选、复选时使用）",
            example = ""
    )
    private List<SelectedItem> selectItemList = Collections.emptyList();

    @OpField(
            required = false,
            desc = "文本形式结果",
            example = "九成新"
    )
    private String text = "";

    @OpField(
            required = false,
            desc = "图片、文档、视频等附件",
            example = ""
    )
    private List<Attachment> files = Collections.emptyList();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Attachment {

        @OpField(
                required = true,
                desc = "附件名称",
                example = "附件名称"
        )
        private String url;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SelectedItem {
        private String selectCode;
    }

    public ResultMaterialInfo(MaterialRule rule) {
        this.materialRuleId = rule.getMaterialRuleId();
    }

}