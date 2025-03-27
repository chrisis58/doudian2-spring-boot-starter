package cn.teacy.common.constant;

public interface ApiResponseConstant {

    interface StatusCode {
        /**
         * 成功
         */
        Integer OK = 10000;

        /**
         * 通用参数错误
         */
        Integer GENERAL_PARAM_ERROR = 30000;

        /**
         * 上传结果个数不正确，共 xxx 个结果
         */
        Integer TASK_QUANTITY_MISMATCH = 30201;

        /**
         * 备注过长，超过 1000 个字
         */
        Integer REMARK_TOO_LONG = 30202;

        /**
         * 驳回原因未填写
         */
        Integer MISSING_REJECT_REASON = 30203;

        /**
         * 图片大小超过限制
         */
        Integer IMAGE_TOO_LARGE = 30204;

        /**
         * 图片数量超过限制
         */
        Integer TOO_MANY_IMAGES = 30205;

        /**
         * access_token不存在，请使用最新的access_token访问
         * TODO: 这个好像是通用的参数错误码，而不仅仅是access_token不存在
         */
        Integer ACCESS_TOKEN_NOT_EXIST = 40003;

        /**
         * 通用系统错误
         */
        Integer GENERAL_SERVER_ERROR = 50000;

        /**
         * refresh_token不存在
         */
        Integer REFRESH_TOKEN_NOT_EXIST = 50002;

    }

}
