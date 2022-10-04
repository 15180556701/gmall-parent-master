package com.atguigu.gmall.product.biz.impl;

import com.atguigu.gmall.product.biz.CategoryBizSerivce;
import com.atguigu.gmall.product.entity.CategroyViewEntity;
import com.atguigu.gmall.product.mapper.BaseCategory1Mapper;
import com.atguigu.gmall.web.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryBizSerivceImpl implements CategoryBizSerivce {

    @Autowired
    BaseCategory1Mapper category1Mapper;

    @Override
    public List<CategoryVo> getCategorysTree() {

        List<CategoryVo> vos = category1Mapper.getCategorysTree();
        return vos;
    }

    @Override
    public CategroyViewEntity getCategoryView(Long c3Id) {

        return  category1Mapper.getCategoryView(c3Id);
    }

}
