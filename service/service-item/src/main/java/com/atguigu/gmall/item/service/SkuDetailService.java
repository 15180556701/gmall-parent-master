package com.atguigu.gmall.item.service;

import com.atguigu.gmall.web.SkuDetailVo;

public interface SkuDetailService {

    /**
     * 查询商品详情，并返回指定类型数据
     * @param skuId
     * @return
     */
    SkuDetailVo skuDetail(Long skuId);
}
