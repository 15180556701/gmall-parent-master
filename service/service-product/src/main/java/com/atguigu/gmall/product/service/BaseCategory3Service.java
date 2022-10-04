package com.atguigu.gmall.product.service;

import com.atguigu.gmall.product.entity.BaseCategory3;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author lfy
* @description 针对表【base_category3(三级分类表)】的数据库操作Service
* @createDate 2022-09-26 11:46:23
*/
public interface BaseCategory3Service extends IService<BaseCategory3> {

    /**
     * 根据2级分类id，查找对应的3级分类
     * @param c2Id
     * @return
     */
    List<BaseCategory3> getCategory3(Long c2Id);
}
