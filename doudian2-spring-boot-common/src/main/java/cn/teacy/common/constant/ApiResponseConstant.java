package cn.teacy.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * API 请求常量定义
 *
 */
public interface ApiResponseConstant {


    /**
     * 返回值
     *
     * @see <a href="https://op.jinritemai.com/docs/guide-docs/212/1427">平台基础错误码汇总</a>
     */
    @Getter
    @AllArgsConstructor
    enum StatusCode {
        OK(10000, "success"),

        BUSINESS_PART_FAILED(10001, "请求部分失败"),

        SERVICE_UNAVAILABLE(20000, "服务不可用"),

        SERVICE_TIMEOUT(20001, "内部服务超时"),

        GENERAL_PARAM_ERROR(30000, "通用参数错误"),

        PERMISSION_DENIED(30001, "操作权限不足"),

        INVALID_IP(30002, "IP不在白名单内"),

        TASK_QUANTITY_MISMATCH(30201, "上传结果个数不正确"),

        REMARK_TOO_LONG(30202, "备注过长，超过 1000 个字"),

        MISSING_REJECT_REASON(30203, "驳回原因未填写"),

        IMAGE_TOO_LARGE(30204, "图片大小超过限制"),

        TOO_MANY_IMAGES(30205, "图片数量超过限制"),

        MISSING_PARAM(40001, "缺少必选参数（平台校验）"),

        MISSING_BUSINESS_PARAM(40002, "缺少必选参数（业务校验）"),

        INVALID_PARAM(40003, "非法的参数（平台校验）"),

        INVALID_BUSINESS_PARAM(40004, "非法的参数（业务校验）"),

        GENERAL_SERVER_ERROR(50000, "通用服务器错误"),

        PROCESSING_ERROR(50001, "平台处理失败"),

        BUSINESS_PROCESSING_ERROR(50002, "业务处理失败"),

        EXCEED_LIMIT(60000, "超过限流限制"),

        SERVICE_OFFLINE(70000, "服务已下线"),

        SECURITY_ERROR(80000, "安全错误"),

        UNKNOWN_ERROR(90000, "其他错误"),

        ;

        private final Integer code;
        private final String msg;

    }

    /**
     * 子状态码
     *
     * @see <a href="https://op.jinritemai.com/docs/guide-docs/212/1427">平台基础错误码汇总</a>
     */
    @Getter
    @AllArgsConstructor
    enum SubStatus {

        // TODO: 补充其他子状态码

        ACCESS_TOKEN_EXPIRED(40003, "isv.access-token-expired", "access_token 已过期"),

        ACCESS_TOKEN_NOT_EXIST(40003, "isv.access-token-no-existed", "access_token不存在，请使用最新的access_token访问"),

        REFRESH_TOKEN_EXPIRED(50001, "dop.token-generate-failed:token-expired", "生成token失败，token已过期"),

        REFRESH_TOKEN_INVALID(50001, "dop.token-generate-failed:token-invalid", "生成token失败，token不存在"),

        REFRESH_TOKEN_NOT_EXIST(50002, "isv.business-failed:31009", "生成token失败, token不存在"),

        ;

        private final Integer code;
        private final String subCode;
        private final String subMsg;
    }

}
