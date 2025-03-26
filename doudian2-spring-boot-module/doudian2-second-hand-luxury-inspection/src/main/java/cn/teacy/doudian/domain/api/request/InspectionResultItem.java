package cn.teacy.doudian.domain.api.request;

import cn.teacy.common.annotation.OpField;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class InspectionResultItem {
    @OpField(
            required = false,
            desc = "材料信息",
            example = ""
    )
    private List<ResultMaterialInfo> materialInfoList;
    @OpField(
            required = false,
            desc = "标准项code",
            example = "7369142527871615258_6"
    )
    @NonNull
    private String itemCode;

    @OpField(
            required = false,
            desc = "填写材料信息，只给二手3C业务场景定制使用，其余场景使用material_info_list字段填写材料信息",
            example = "九成新"
    )
    private ResultMaterialTextInfo materialInfo = new ResultMaterialTextInfo();


    public InspectionResultItem(List<ResultMaterialInfo> materialInfoList, String itemCode) {
        this.materialInfoList = materialInfoList;
        this.itemCode = itemCode;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResultMaterialTextInfo {

        @OpField(
                required = false,
                desc = "文本形式结果",
                example = "九成新"
        )
        private String text = "";
    }

}

