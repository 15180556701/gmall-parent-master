package com.atguigu.gmall.product.rpc;


import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.biz.CategoryBizSerivce;
import com.atguigu.gmall.product.entity.CategroyViewEntity;
import com.atguigu.gmall.product.entity.SkuImage;
import com.atguigu.gmall.product.entity.SkuInfo;
import com.atguigu.gmall.product.entity.SpuSaleAttr;
import com.atguigu.gmall.product.service.SkuImageService;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.atguigu.gmall.product.service.SpuSaleAttrService;
import com.atguigu.gmall.web.SkuDetailVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;


/**
 * 商品服务查询商品信息远程接口
 */
@RequestMapping("/api/inner/product")
@RestController
public class SkuDetailRpcController {

    @Autowired
    SkuInfoService skuInfoService;

    @Autowired
    SkuImageService skuImageService;

    @Autowired
    CategoryBizSerivce categoryBizSerivce;

    @Autowired
    SpuSaleAttrService spuSaleAttrService;

    /**
     * 1、查询sku_info
     */
    @GetMapping("/skuinfo/{skuId}")
    public Result<SkuInfo> getSkuInfo(@PathVariable("skuId") Long skuId){

        SkuInfo skuInfo = skuInfoService.getById(skuId);
        return Result.ok(skuInfo);
    }

    /**
     * 2、查询sku_images
     */
    @GetMapping("/skuinfo/images/{skuId}")
    public Result<List<SkuImage>> getSkuImages(@PathVariable("skuId") Long skuId){

        List<SkuImage> images = skuImageService.list(new LambdaQueryWrapper<SkuImage>()
                .eq(SkuImage::getSkuId, skuId));
        return Result.ok(images);
    }

    /**
     * 3、根据sku的三级分类id，查询分类完整路径信息
     */
    @GetMapping("/skuinfo/categoryview/{c3Id}")
    public Result<SkuDetailVo.CategoryView> getCategoryView(
            @PathVariable("c3Id") Long c3Id){

        CategroyViewEntity categoryView = categoryBizSerivce.getCategoryView(c3Id);

        //对数据进行转换
        SkuDetailVo.CategoryView newView = new SkuDetailVo.CategoryView();
        newView.setCategory1Id(categoryView.getC1id());
        newView.setCategory1Name(categoryView.getC1name());
        newView.setCategory2Id(categoryView.getC2id());
        newView.setCategory2Name(categoryView.getC2name());
        newView.setCategory3Id(categoryView.getC3id());
        newView.setCategory3Name(categoryView.getC3name());


        return Result.ok(newView);
    }


    /**
     * 4、查询sku的实时价格
     * @param skuId
     * @return
     */
    @GetMapping("/skuinfo/price/{skuId}")
    public Result<BigDecimal> getSkuPrice(@PathVariable("skuId") Long skuId){

        BigDecimal price = skuInfoService.getSkuPrice(skuId);
        return Result.ok(price);
    }

    /**
     * 5、根据spuId查询出销售属性名和值
     */
    @GetMapping("/skuinfo/spusaleattrandvalue/{skuId}/{spuId}")
    public Result<List<SpuSaleAttr>> getSpuSaleAttrAndValue(@PathVariable("spuId") Long spuId,
                                                            @PathVariable("skuId") Long skuId){

        List<SpuSaleAttr> attrAndValue = spuSaleAttrService
                .getSpuSaleAttrAndValueAndMarkCurrentSku(skuId,spuId);
        return Result.ok(attrAndValue);
    }

    /**
     * 6、根据spu_id，查询这个spu下面的所有sku涉及到的所有销售属性值组合，
     * 返回：Map（"127|128":49, "126|129": 50） 的 json字符串
     */
    @GetMapping("/skuinfo/valuesskujson/{spuId}")
    public Result<String>  getSpuValuesSkuJson(@PathVariable("spuId")Long spuId){

        String json = spuSaleAttrService.getSpuValuesSkuJson(spuId);
        return Result.ok(json);
    }
}
