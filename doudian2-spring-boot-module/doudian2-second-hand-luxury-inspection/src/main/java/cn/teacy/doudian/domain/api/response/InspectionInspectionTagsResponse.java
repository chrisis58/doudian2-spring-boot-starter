package cn.teacy.doudian.domain.api.response;

import cn.teacy.common.annotation.OpField;
import lombok.Data;

import java.util.Map;

@Data
public class InspectionInspectionTagsResponse {

    @OpField(
            desc = "标签信息 key是标签的类型，value是标签的值 方便后面扩展，inspection_check_use_purchase_channel 表示质检结果是否依赖货品渠道，值为标准项的ID，反序列化后，不为空则认为不依赖",
            example = "tagMap: {        inspection_check_use_purchase_channel: [7369139374136492297,7369139374136492297]    },"
    )
    private Map<String, String> tagMap;

}
