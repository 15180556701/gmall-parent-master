package com.atguigu.gmall.web;


import com.atguigu.gmall.product.entity.SkuInfo;
import com.atguigu.gmall.product.entity.SpuSaleAttr;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


/**
 * 一个sku详情页面需要的数据模型
 */
@Data
public class SkuDetailVo {
    //当前商品分类信息
    CategoryView categoryView;

    //sku详情信息、以及图片列表
    SkuInfo skuInfo;

    //sku的价格
    BigDecimal price;

    //当前sku对应的spu定义的所有销售属性名和值
    List<SpuSaleAttr> spuSaleAttrList;


    String valuesSkuJson;


    @Data
    public static class CategoryView {
        private Long category1Id;
        private String category1Name;

        private Long category2Id;
        private String category2Name;

        private Long category3Id;
        private String category3Name;
    }
}
