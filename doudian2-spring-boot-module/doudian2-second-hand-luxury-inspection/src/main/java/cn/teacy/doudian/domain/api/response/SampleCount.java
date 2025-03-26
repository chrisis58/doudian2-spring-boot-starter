package cn.teacy.doudian.domain.api.response;

import cn.teacy.common.annotation.OpField;
import lombok.Data;

@Data
public class SampleCount {
    @OpField(
            desc = "抽样名称",
            example = "抽样名称"
    )
    private String name;
    @OpField(
            desc = "抽样code",
            example = "抽样code"
    )
    private String code;
    @OpField(
            desc = "抽样数量",
            example = "1"
    )
    private Long sampleCount;
    @OpField(
            desc = "可接受数量",
            example = "1"
    )
    private Long acceptCount;
}
