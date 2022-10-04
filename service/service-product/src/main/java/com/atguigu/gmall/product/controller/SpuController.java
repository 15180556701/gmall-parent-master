package com.atguigu.gmall.product.controller;


import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.entity.SpuImage;
import com.atguigu.gmall.product.entity.SpuInfo;
import com.atguigu.gmall.product.service.SpuImageService;
import com.atguigu.gmall.product.service.SpuInfoService;
import com.atguigu.gmall.product.vo.SpuInfoSaveVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/admin/product")
@Api(tags = "SPU管理")
@RestController
public class SpuController {


    @Autowired
    SpuInfoService spuInfoService;

    @Autowired
    SpuImageService spuImageService;
    /**
     * 分页查询SPU列表
     * @param category3Id
     * @return
     */
    @GetMapping("/{pn}/{ps}")
    public Result getSpuPage(@RequestParam("category3Id") Long category3Id,
                             @PathVariable("pn") Long pn,
                             @PathVariable("ps") Long ps){

        Page<SpuInfo> result = spuInfoService.getSpuInfoPage(category3Id,pn,ps);


        return Result.ok(result);
    }


    @PostMapping("/saveSpuInfo")
    public Result saveSpuInfo(@RequestBody SpuInfoSaveVo spuInfoSaveVo){

        //保存spu信息
        spuInfoService.saveSpuInfo(spuInfoSaveVo);

        return Result.ok();
    }

    /**
     * 查询spu的图片列表
     * @param spuId
     * @return
     */
    @GetMapping("/spuImageList/{spuId}")
    public Result spuImageList(@PathVariable("spuId") Long spuId){

        LambdaQueryWrapper<SpuImage> wrapper = new LambdaQueryWrapper<SpuImage>()
                .eq(SpuImage::getSpuId, spuId);

        List<SpuImage> list = spuImageService.list(wrapper);

        return Result.ok(list);
    }

}
