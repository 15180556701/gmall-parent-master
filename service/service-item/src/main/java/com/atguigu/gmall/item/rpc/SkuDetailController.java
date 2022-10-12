package com.atguigu.gmall.item.rpc;


import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.item.service.SkuDetailService;
import com.atguigu.gmall.web.SkuDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/inner/item")
@RestController
public class SkuDetailController {


    @Autowired
    SkuDetailService skuDetailService;

    /**
     * 返回skuDetail信息
     * @param skuId
     * @return
     */


    @GetMapping("/detail/{skuId}")
    public Result<SkuDetailVo> skuDetail(@PathVariable("skuId") Long skuId){

        log.info("商品详情服务：正在查询 {} 商品详情",skuId);

        SkuDetailVo vo = skuDetailService.skuDetail(skuId);

        return Result.ok(vo);
    }


}
