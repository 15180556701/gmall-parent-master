package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.product.entity.BaseCategory1;
import com.atguigu.gmall.product.entity.CategroyViewEntity;
import com.atguigu.gmall.web.CategoryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author lfy
* @description 针对表【base_category1(一级分类表)】的数据库操作Mapper
* @createDate 2022-09-26 11:46:23
* @Entity com.atguigu.gmall.product.entity.BaseCategory1
*/
@Repository
public interface BaseCategory1Mapper extends BaseMapper<BaseCategory1> {

    List<CategoryVo> getCategorysTree();


    CategroyViewEntity getCategoryView(@Param("c3Id") Long c3Id);
}




