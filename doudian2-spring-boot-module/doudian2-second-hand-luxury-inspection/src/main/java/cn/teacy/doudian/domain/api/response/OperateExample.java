package cn.teacy.doudian.domain.api.response;

import cn.teacy.common.annotation.OpField;
import lombok.Data;

@Data
public class OperateExample {
    @OpField(
            desc = "url，具备有效期，不可持久化存储",
            example = "url"
    )
    private String url;
    @OpField(
            desc = "操作示例名称",
            example = "操作示例名称"
    )
    private String exampleName;
}
