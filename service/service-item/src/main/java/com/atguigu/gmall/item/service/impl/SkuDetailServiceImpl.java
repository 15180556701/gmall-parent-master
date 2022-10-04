package com.atguigu.gmall.item.service.impl;
import java.math.BigDecimal;
import java.util.List;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.item.feign.SkuDetailFeignClient;
import com.atguigu.gmall.product.entity.SkuImage;
import com.atguigu.gmall.product.entity.SpuSaleAttr;
import com.google.common.collect.Lists;
import com.atguigu.gmall.product.entity.SkuInfo;
import com.atguigu.gmall.web.SkuDetailVo.CategoryView;

import com.atguigu.gmall.item.service.SkuDetailService;
import com.atguigu.gmall.web.SkuDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkuDetailServiceImpl implements SkuDetailService {

    @Autowired
    SkuDetailFeignClient skuDetailFeignClient;

    @Override
    public SkuDetailVo skuDetail(Long skuId) {
        SkuDetailVo data = new SkuDetailVo();

        //1、查询出sku_info信息
        Result<SkuInfo> skuInfo = skuDetailFeignClient.getSkuInfo(skuId);

        //2、查询当前sku的图片列表
        Result<List<SkuImage>> skuImages = skuDetailFeignClient.getSkuImages(skuId);
        SkuInfo info = skuInfo.getData();
        info.setSkuImageList(skuImages.getData());

        //设置好skuinfo的数据返回
        data.setSkuInfo(info);

        //3、查询sku的分类信息
        Result<CategoryView> categoryView = skuDetailFeignClient.getCategoryView(info.getCategory3Id());
        data.setCategoryView(categoryView.getData());


        //4、查询sku的价格信息。为了得到最新价格，每次都再查一遍
        Result<BigDecimal> skuPrice = skuDetailFeignClient.getSkuPrice(skuId);
        data.setPrice(skuPrice.getData());


        //5、查询sku的销售属性
        Long spuId = info.getSpuId();
        Result<List<SpuSaleAttr>> value = skuDetailFeignClient.getSpuSaleAttrAndValue(spuId, skuId);
        data.setSpuSaleAttrList(value.getData());

        //6、json 就是 map（"119|120":49,"122:123":50）、javaBean
        Result<String> skuJson = skuDetailFeignClient.getSpuValuesSkuJson(spuId);
        data.setValuesSkuJson(skuJson.getData());

        return data;
    }
}
