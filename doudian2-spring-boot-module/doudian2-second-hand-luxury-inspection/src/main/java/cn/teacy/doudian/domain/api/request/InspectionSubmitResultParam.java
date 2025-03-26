package cn.teacy.doudian.domain.api.request;

import cn.teacy.common.annotation.OpField;
import cn.teacy.common.annotation.OpParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@OpParam("inspection.inspectionInfo/submit")
@AllArgsConstructor
@NoArgsConstructor
public class InspectionSubmitResultParam {

    @OpField(
            required = true,
            desc = "质检对象列表，批量上传数量不能超过100个",
            example = ""
    )
    private List<InspectionResult> inspectionResultList;
    @OpField(
            required = true,
            desc = "业务身份参数，二手：second_hand_inspection",
            example = "second_hand_inspection"
    )
    private String bizCode = "second_hand_inspection";
    @OpField(
            required = true,
            desc = "业务身份参数，前置质检：machine_pre_inspection",
            example = "machine_pre_inspection"
    )
    private String taskType = "each_goods_inspection";
    @OpField(
            required = true,
            desc = "订单码",
            example = "1057157943"
    )
    private String orderCode;
    @OpField(
            required = false,
            desc = "结果回传时间秒级时间戳，如果传入则以传入为准，不传则以接口调用时间作为回传时间",
            example = "1719370910"
    )
    private Long eventTime;
    @OpField(
            required = true,
            desc = "1.平板传入：7369403576672141577_21 2.笔记本电脑传入：7369424017386406171_23 3.手机传入：7369157534777409802_22 后续业务标准集如果有变动，需要修改标准集code参数",
            example = "7460063059139674403_1"
    )
    private String collectionCode;

}
