package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.product.entity.SkuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
* @author lfy
* @description 针对表【sku_info(库存单元表)】的数据库操作Mapper
* @createDate 2022-09-26 11:46:23
* @Entity com.atguigu.gmall.product.entity.SkuInfo
*/
public interface SkuInfoMapper extends BaseMapper<SkuInfo> {

    /**
     * 修改指定商品的 sale 状态
     * @param skuId
     * @param status
     */
    void updateSaleStatus(@Param("skuId") Long skuId,
                          @Param("status") int status);

    /**
     * 查询sku价格
     * @param skuId
     * @return
     */
    BigDecimal getSkuPrice(@Param("skuId") Long skuId);

    /**
     * 查询所有skuId
     * @return
     */
    List<Long> getAllSkuId();
}




