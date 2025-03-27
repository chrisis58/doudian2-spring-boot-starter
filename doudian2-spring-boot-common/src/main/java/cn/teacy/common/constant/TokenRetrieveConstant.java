package cn.teacy.common.constant;

import lombok.Getter;

public interface TokenRetrieveConstant {

    @Getter
    enum GRANT_TYPE {
        AUTHORIZATION_CODE("authorization_code", "工具型应用"),
        AUTHORIZATION_SELF("authorization_self", "工具型应用"),

        EMPTY("", "空")

        ;

        GRANT_TYPE(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        private final String value;
        private final String desc;
    }

    @Getter
    enum AUTH_SUBJECT_TYPE {
        YUN_CANG("YunCang", "云仓"),
        WU_LIU_SHANG("WuLiuShang", "物流商"),
        WL_GONG_YING_SHANG("WLGongYingShang", "物流供应商"),
        MINI_APP("MiniApp", "小程序"),
        MCN("MCN", "联盟MCN机构"),
        DOU_KE("DouKe", "联盟抖客"),
        COLONEL("Colonel", "联盟团长"),

        EMPTY("", "空")

        ;

        AUTH_SUBJECT_TYPE(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        private final String value;
        private final String desc;
    }

}
