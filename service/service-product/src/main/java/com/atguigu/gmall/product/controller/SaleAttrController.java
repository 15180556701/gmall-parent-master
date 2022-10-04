package com.atguigu.gmall.product.controller;


import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.entity.BaseSaleAttr;
import com.atguigu.gmall.product.entity.SpuSaleAttr;
import com.atguigu.gmall.product.service.BaseSaleAttrService;
import com.atguigu.gmall.product.service.SpuSaleAttrService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "销售属性管理")
@RequestMapping("/admin/product")
@RestController
public class SaleAttrController {



    @Autowired
    BaseSaleAttrService baseSaleAttrService;

    @Autowired
    SpuSaleAttrService spuSaleAttrService;

    /**
     * 获取所有 销售属性名
     * @return
     */
    @GetMapping("/baseSaleAttrList")
    public Result  baseSaleAttrList(){
        List<BaseSaleAttr> list = baseSaleAttrService.list();
        return Result.ok(list);
    }


    /**
     * 查询指定spu定义的所有销售属性名和值
     * @return
     */
    @GetMapping("/spuSaleAttrList/{spuId}")
    public Result spuSaleAttrList(@PathVariable("spuId") Long spuId){

        List<SpuSaleAttr> spuSaleAttrs = spuSaleAttrService.getSpuSaleAttrAndValue(spuId);
        return Result.ok(spuSaleAttrs);
    }
}
