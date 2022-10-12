package com.atguigu.gmall.web.controller;


import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.web.SkuDetailVo;
import com.atguigu.gmall.web.feign.ItemFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemController {


    @Autowired
    ItemFeignClient itemFeignClient;
    /**
     * 商品详情页
     * @param skuId
     * @return
     */
    @GetMapping("/{skuId}.html")
    public String item(@PathVariable("skuId") Long skuId,
                       Model model){

        //远程调用 service-item 获取到商品详情数据
        Result<SkuDetailVo> result = itemFeignClient.skuDetail(skuId);
        SkuDetailVo skuDetailVo = result.getData();

        if (skuDetailVo==null){
            return "item/error";
        }


        // {category1Id,category1Name,category2Id,category2Name,category3Id,category3Name}
        //1、当前sku所属的完整分类信息
        model.addAttribute("categoryView",skuDetailVo.getCategoryView());

        //{id、skuName、skuDefaultImg、skuImageList（imgUrl、）skuName}
        //2、当前sku的信息（基本信息、图片列表）
        model.addAttribute("skuInfo",skuDetailVo.getSkuInfo());

        //3、当前sku的价格
        model.addAttribute("price",skuDetailVo.getPrice());

        //4、当前sku的销售属性列表
        model.addAttribute("spuSaleAttrList",skuDetailVo.getSpuSaleAttrList());

        //5、值json
        model.addAttribute("valuesSkuJson",skuDetailVo.getValuesSkuJson());

        return "item/index";
    }
}
