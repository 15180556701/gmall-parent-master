package com.atguigu.gmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.product.entity.BaseCategory3;
import com.atguigu.gmall.product.service.BaseCategory3Service;
import com.atguigu.gmall.product.mapper.BaseCategory3Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lfy
* @description 针对表【base_category3(三级分类表)】的数据库操作Service实现
* @createDate 2022-09-26 11:46:23
*/
@Service
public class BaseCategory3ServiceImpl extends ServiceImpl<BaseCategory3Mapper, BaseCategory3>
    implements BaseCategory3Service{

    @Override
    public List<BaseCategory3> getCategory3(Long c2Id) {

        QueryWrapper<BaseCategory3> wrapper = new QueryWrapper<BaseCategory3>();
        wrapper.eq("category2_id",c2Id);

        List<BaseCategory3> list = list(wrapper);

        return list;
    }
}




