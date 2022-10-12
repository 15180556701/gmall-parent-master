package com.atguigu.gmall.product.service;

import com.atguigu.gmall.product.entity.SkuInfo;
import com.atguigu.gmall.product.vo.SkuInfoSaveVo;
import com.atguigu.gmall.product.vo.SkuInfoUpdateVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
* @author lfy
* @description 针对表【sku_info(库存单元表)】的数据库操作Service
* @createDate 2022-09-26 11:46:23
*/
public interface SkuInfoService extends IService<SkuInfo> {

    /**
     * 保存sku信息
     * @param vo
     */
    void saveSkuInfo(SkuInfoSaveVo vo);

    /**
     * 修改上下架状态
     * @param skuId
     * @param status
     */
    void changeOnSale(Long skuId, int status);

    /**
     * 查询sku价格
     * @param skuId
     * @return
     */
    BigDecimal getSkuPrice(Long skuId);

    /*
    查询所有skuid
     */
    List<Long> getAllSkuId();

    void updateSkuInfo(SkuInfoUpdateVo vo);
}
