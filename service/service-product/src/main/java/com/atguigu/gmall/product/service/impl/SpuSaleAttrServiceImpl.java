package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.common.util.Jsons;
import com.atguigu.gmall.product.dto.ValueSkuJsonDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.product.entity.SpuSaleAttr;
import com.atguigu.gmall.product.service.SpuSaleAttrService;
import com.atguigu.gmall.product.mapper.SpuSaleAttrMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author lfy
* @description 针对表【spu_sale_attr(spu销售属性)】的数据库操作Service实现
* @createDate 2022-09-26 11:46:24
*/
@Service
public class SpuSaleAttrServiceImpl extends ServiceImpl<SpuSaleAttrMapper, SpuSaleAttr>
    implements SpuSaleAttrService{

    @Autowired
    SpuSaleAttrMapper spuSaleAttrMapper;
    @Override
    public List<SpuSaleAttr> getSpuSaleAttrAndValue(Long spuId) {

        List<SpuSaleAttr> list =
                spuSaleAttrMapper.getSpuSaleAttrAndValue(spuId);
        return list;
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrAndValueAndMarkCurrentSku(Long skuId, Long spuId) {

        return spuSaleAttrMapper.getSpuSaleAttrAndValueAndMarkCurrentSku(skuId,spuId);
    }

    @Override
    public String getSpuValuesSkuJson(Long spuId) {

        List<ValueSkuJsonDTO> dtos = spuSaleAttrMapper.getSpuValuesSkuJson(spuId);

        // 响应式编程
        Map<String,Long> skuValues = new HashMap<>();
        dtos.stream().forEach(item->{
            skuValues.put(item.getAttrValueConcat(),item.getSkuId());
        });

        String toStr = Jsons.toStr(skuValues);
        return toStr;
    }
}




