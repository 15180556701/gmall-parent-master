package com.atguigu.gmall.item.feign;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.entity.SkuImage;
import com.atguigu.gmall.product.entity.SkuInfo;
import com.atguigu.gmall.product.entity.SpuSaleAttr;
import com.atguigu.gmall.web.SkuDetailVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;


@RequestMapping("/api/inner/product")
@FeignClient("service-product")
public interface SkuDetailFeignClient {

    /**
     * 1、查询sku_info
     */
    @GetMapping("/skuinfo/{skuId}")
    Result<SkuInfo> getSkuInfo(@PathVariable("skuId") Long skuId);

    /**
     * 2、查询sku_images
     */
    @GetMapping("/skuinfo/images/{skuId}")
    Result<List<SkuImage>> getSkuImages(@PathVariable("skuId") Long skuId);

    /**
     * 3、根据sku的三级分类id，查询分类完整路径信息
     */
    @GetMapping("/skuinfo/categoryview/{c3Id}")
    Result<SkuDetailVo.CategoryView> getCategoryView(
            @PathVariable("c3Id") Long c3Id);


    /**
     * 4、查询sku的实时价格
     * @param skuId
     * @return
     */
    @GetMapping("/skuinfo/price/{skuId}")
    Result<BigDecimal> getSkuPrice(@PathVariable("skuId") Long skuId);


    /**
     * 5、根据spuId查询出销售属性名和值
     */
    @GetMapping("/skuinfo/spusaleattrandvalue/{skuId}/{spuId}")
    public Result<List<SpuSaleAttr>> getSpuSaleAttrAndValue(@PathVariable("spuId") Long spuId,
                                                            @PathVariable("skuId") Long skuId);


    /**
     * 6、根据spu_id，查询这个spu下面的所有sku涉及到的所有销售属性值组合，
     * 返回：Map（"127|128":49, "126|129": 50） 的 json字符串
     */
    @GetMapping("/skuinfo/valuesskujson/{spuId}")
    public Result<String>  getSpuValuesSkuJson(@PathVariable("spuId")Long spuId);



}
