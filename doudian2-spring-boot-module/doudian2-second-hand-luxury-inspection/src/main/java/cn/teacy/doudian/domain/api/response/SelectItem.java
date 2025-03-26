package cn.teacy.doudian.domain.api.response;

import cn.teacy.common.annotation.OpField;
import lombok.Data;

@Data
public class SelectItem {
    @OpField(
            desc = "下拉项code",
            example = "item_1_xx"
    )
    private String selectCode;
    @OpField(
            desc = "下拉项名称",
            example = "测试名称"
    )
    private String selectDesc;
}
