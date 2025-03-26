package cn.teacy.doudian.domain.api.response;

import cn.teacy.common.annotation.OpField;
import lombok.Data;

import java.util.List;

@Data
public class SampleInfo {
    @OpField(
            desc = "抽样数量列表",
            example = ""
    )
    private List<SampleCount> sampleCountList;
}