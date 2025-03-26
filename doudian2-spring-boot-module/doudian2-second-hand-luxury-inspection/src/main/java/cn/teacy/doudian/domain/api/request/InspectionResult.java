package cn.teacy.doudian.domain.api.request;

import cn.teacy.common.annotation.OpField;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class InspectionResult {

    @OpField(
            required = false,
            desc = "是否复检，复检设置为true，非复检不传或者传false",
            example = "true"
    )
    private Boolean isReInspection = false;
    @OpField(
            required = false,
            desc = "标准项",
            example = ""
    )
    @NonNull
    private List<InspectionResultItem> inspectionItems;
    @OpField(
            required = false,
            desc = "驳回原因code 【Q301】禁售型号: 老旧型号 【Q302】成色不符: 成色差异驳回 【Q303】货不对版或商品信息不一致",
            example = "Q301"
    )
    private String rejectReasonCode = "";
    @OpField(
            required = false,
            desc = "质检结果图片url,最多上传50张，图片大小不超过2MB。",
            example = "http://xxxx"
    )
    private List<String> imageUrl = Collections.emptyList();
    @OpField(
            required = false,
            desc = "质检结果枚举，1-合格，2-不合格",
            example = "1"
    )
    private Long inspectionResult;
    @OpField(
            required = false,
            desc = "备注",
            example = "具体备注信息"
    )
    private String remark = "";


}

