package cn.teacy.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface SpiResponseConstant {

    @Getter
    @AllArgsConstructor
    enum StatusCode {

        OK(0L, "成功"),

        INTERNAL_SERVER_ERROR(1001L, "内部错误"),

        PARAM_ERROR(1004L, "参数错误"),

        CANCEL_NOT_SUPPORTED(4018L, "不支持取消"),

        TASK_NOT_EXIST(6004L, "任务不存在"),

        FAIL_TO_CANCEL(6005L, "取消失败"),

        ;

        private final Long code;

        private final String message;

    }

}
