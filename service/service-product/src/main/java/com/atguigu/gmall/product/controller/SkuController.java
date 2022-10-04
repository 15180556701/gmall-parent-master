package com.atguigu.gmall.product.controller;


import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.entity.SkuInfo;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.atguigu.gmall.product.vo.SkuInfoSaveVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Api(tags = "SKU管理")
@RequestMapping("/admin/product")
@RestController
public class SkuController {



    @Autowired
    SkuInfoService skuInfoService;
    /**
     * SKU分页列表
     * @param pn
     * @param ps
     * @return
     */
    @GetMapping("/list/{pn}/{ps}")
    public Result skuList(@PathVariable("pn")Long pn,
                          @PathVariable("ps")Long ps){
        Page<SkuInfo> page = new Page<>(pn,ps);

        Page<SkuInfo> result = skuInfoService.page(page);
        return Result.ok(result);
    }


    /**
     * SKU大保存
     * @param vo
     * @return
     */
    @PostMapping("/saveSkuInfo")
    public Result saveSkuInfo(@RequestBody SkuInfoSaveVo vo){
        log.info("准备保存sku: {}",vo);
        skuInfoService.saveSkuInfo(vo);
        return Result.ok();
    }

    /**
     * 上架
     */
    @GetMapping("/onSale/{skuId}")
    public Result onSale(@PathVariable("skuId") Long skuId){

        skuInfoService.changeOnSale(skuId,1);
        return Result.ok();
    }

    /**
     * 下架
     * @param skuId
     * @return
     */
    @GetMapping("/cancelSale/{skuId}")
    public Result cancelSale(@PathVariable("skuId") Long skuId){

        skuInfoService.changeOnSale(skuId,0);
        return Result.ok();
    }
}
