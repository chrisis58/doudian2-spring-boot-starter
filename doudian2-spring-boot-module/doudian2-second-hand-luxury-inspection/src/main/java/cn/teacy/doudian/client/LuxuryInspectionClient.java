package cn.teacy.doudian.client;

import cn.teacy.common.doudian.api.CommonResponse;
import cn.teacy.doudian.annotation.DoudianApiClient;
import cn.teacy.doudian.domain.api.request.*;
import cn.teacy.doudian.domain.api.response.*;
import org.springframework.web.bind.annotation.PostMapping;

@DoudianApiClient("luxury-inspection-apis")
public interface LuxuryInspectionClient {

    /**
     * 仓储中心越库单状态回告接口
     *
     * @see <a href="https://op.jinritemai.com/docs/guide-docs/101/100/101">仓储中心越库单状态回告接口</a>
     */
    @PostMapping("/orderCode/crossdockingCallback")
    CommonResponse<CrossDockingCallbackResponse> crossDockingCallback(CrossDockingCallbackParam crossDockingCallbackParam);


    /**
     * 越库单质检取号打印接口
     * 服务商在操作实物发货时调用字节以获取面单和运单信息
     * 注意. 只有【待复核】，【已出仓】，【已退回商家】的单据允许取号获取打印信息
     *
     * @see <a href="https://connect.douyinec.com/view/doc/27/60/171">越库单质检取号打印接口</a>
     */
    @PostMapping("/logistics/wms/crossDockingPrintWaybill")
    CommonResponse<CrossDockingPrintWaybillResponse> getCrossDockingPrintWaybill(CrossDockingPrintWaybillParam crossDockingPrintWaybillParams);

    /**
     * 品控中心-质检结果回传
     *
     * @see <a href="https://connect.douyinec.com/view/doc/27/60/172">品控中心-质检结果回传</a>
     */
    @PostMapping("/inspection/inspectionInfo/submit")
    CommonResponse<InspectionSubmitResultResponse> submitInspectionInfo(InspectionSubmitResultParam inspectionSubmitResultParam);

    /**
     * 查询质检相关的标签[非物流连接平台新接口]
     * 需要给外部服务商提供一些订单/商品/商家等维度的标签数据查询，服务商会根据标签结果做对应的质检逻辑判定
     *
     * @see <a href="https://connect.douyinec.com/view/doc/27/60/180">查询质检相关的标签[非物流连接平台新接口]</a>
     */
    @PostMapping("/inspection/inspectionTags")
    CommonResponse<InspectionInspectionTagsResponse> getInspectionTags(InspectionInspectionTagsParam inspectionInspectionTagsParam);

    /**
     * 获取检测模版
     *
     * @see <a href="https://op.jinritemai.com/docs/tmp/1024/7245">获取检测模版</a>
     */
    @PostMapping("/inspection/inspectionTemplate/get")
    CommonResponse<GetInspectionTemplateResponse> getInspectionTemplate(GetInspectionTemplateParam getInspectionTemplateParam);


}
