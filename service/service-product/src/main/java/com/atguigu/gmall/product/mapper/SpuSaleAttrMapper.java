package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.product.dto.ValueSkuJsonDTO;
import com.atguigu.gmall.product.entity.SpuSaleAttr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author lfy
* @description 针对表【spu_sale_attr(spu销售属性)】的数据库操作Mapper
* @createDate 2022-09-26 11:46:24
* @Entity com.atguigu.gmall.product.entity.SpuSaleAttr
*/
public interface SpuSaleAttrMapper extends BaseMapper<SpuSaleAttr> {

    List<SpuSaleAttr> getSpuSaleAttrAndValue(@Param("spuId") Long spuId);

    List<SpuSaleAttr> getSpuSaleAttrAndValueAndMarkCurrentSku(@Param("skuId") Long skuId, @Param("spuId") Long spuId);

    List<ValueSkuJsonDTO> getSpuValuesSkuJson(@Param("spuId") Long spuId);
}




