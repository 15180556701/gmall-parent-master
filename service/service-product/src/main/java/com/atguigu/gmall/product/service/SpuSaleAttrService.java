package com.atguigu.gmall.product.service;

import com.atguigu.gmall.product.entity.SpuSaleAttr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author lfy
* @description 针对表【spu_sale_attr(spu销售属性)】的数据库操作Service
* @createDate 2022-09-26 11:46:24
*/
public interface SpuSaleAttrService extends IService<SpuSaleAttr> {

    /**
     *  查询指定spu定义的所有销售属性名和值
     * @param spuId
     * @return
     */
    List<SpuSaleAttr> getSpuSaleAttrAndValue(Long spuId);

    /**
     * 查询指定sku对应的spu定义的所有销售属性名和值，并标记出当前sku是那一组销售属性，并固定顺序
     * @param skuId
     * @param spuId
     * @return
     */
    List<SpuSaleAttr> getSpuSaleAttrAndValueAndMarkCurrentSku(Long skuId, Long spuId);

    /**
     * 根据spu_id，查询这个spu下面的所有sku涉及到的所有销售属性值组合
     * @param spuId
     * @return
     */
    String getSpuValuesSkuJson(Long spuId);
}
