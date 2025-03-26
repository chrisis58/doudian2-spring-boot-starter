package cn.teacy.doudian.domain.spi.request;

import cn.teacy.common.annotation.OpField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsWmsCrossDockingPushParam {
    @OpField(
            required = true,
            desc = "越库单号",
            example = "CD7395868102464487724"
    )
    private String crossDockingOrderNo;
    @OpField(
            required = true,
            desc = "仓库编码",
            example = "501"
    )
    private String warehouseCode;
    @OpField(
            required = true,
            desc = "订单码",
            example = "1023632178"
    )
    private String orderCode;
    @OpField(
            required = true,
            desc = "货主编码",
            example = "1138885426"
    )
    private String ownerCode;
    @OpField(
            required = true,
            desc = "货主名称",
            example = "ly二手测试"
    )
    private String ownerName;
    @OpField(
            required = false,
            desc = "一段送仓物流",
            example = ""
    )
    private String expressCode;
    @OpField(
            required = false,
            desc = "一段送仓物流单号",
            example = ""
    )
    private String expressNo;
    @OpField(
            required = false,
            desc = "备注",
            example = ""
    )
    private String remark;
    @OpField(
            required = true,
            desc = "版本号",
            example = "0"
    )
    private Integer version;
    @OpField(
            required = true,
            desc = "商品信息[]",
            example = ""
    )
    private List<ProductDataItem> productData;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDataItem {
        @OpField(
                required = false,
                desc = "商品属性（json字段）",
                example = "{是否支持无线充电:是,机身材质:复合材料,型号:时尚手机,CCC证书编号:123123,存储容量:256GB,耳机接口类型:Type-C,屏幕外观:屏幕完美,速度类型(cpm):123,成色:准新品,售后服务:全国联保,实体键功能:按键正常,网络类型:SA/NSA双模(5G),电池:电池正常}"
        )
        private Map<String, String> productProperty;
        @OpField(
                required = true,
                desc = "商品ID",
                example = "3697741457885666300"
        )
        private Long productId;
        @OpField(
                required = true,
                desc = "商品名称",
                example = "准新品 无品牌 手机整机测试"
        )
        private String productName;
        @OpField(
                required = true,
                desc = "商品图片",
                example = "https://tosv.boe.byted.org/obj/ecom-shop-material/FQPZVpnSv_m_54317d7b0a6caa96a60433d11756369d_sx_265057_www1232-1232"
        )
        private String imageUrl;
        @OpField(
                required = true,
                desc = "是否赠品（枚举值：1是，0否）",
                example = "0"
        )
        private Integer isGift;
        @OpField(
                required = false,
                desc = "品牌名称",
                example = "测试品牌名称"
        )
        private String brandName;
        @OpField(
                required = false,
                desc = "品类名称",
                example = "手机整机"
        )
        private String categoryName;
        @OpField(
                required = true,
                desc = "商品数量",
                example = "1"
        )
        private Long productQty;
        @OpField(
                required = false,
                desc = "包耗材信息（内包装盒）",
                example = ""
        )
        private SupInfo supInfo;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SupInfo {
        @OpField(
                required = false,
                desc = "包耗材数量",
                example = "1"
        )
        private Long supNum;
        @OpField(
                required = false,
                desc = "包耗材id",
                example = "305468"
        )
        private Long supId;
        @OpField(
                required = false,
                desc = "包耗材名称",
                example = ""
        )
        private String supName;
    }

}

