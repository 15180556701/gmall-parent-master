package com.atguigu.gmall.product.service;

import com.atguigu.gmall.product.entity.BaseCategory2;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author lfy
* @description 针对表【base_category2(二级分类表)】的数据库操作Service
* @createDate 2022-09-26 11:46:23
*/
public interface BaseCategory2Service extends IService<BaseCategory2> {

    List<BaseCategory2> getCategory2(Long cid);
}
