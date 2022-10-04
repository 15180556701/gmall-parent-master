package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.product.entity.BaseAttrInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author lfy
* @description 针对表【base_attr_info(属性表)】的数据库操作Mapper
* @createDate 2022-09-26 11:46:23
* @Entity com.atguigu.gmall.product.entity.BaseAttrInfo
*/
public interface BaseAttrInfoMapper extends BaseMapper<BaseAttrInfo> {

    /**
     * 根据分类id 查询平台属性名和值
     * @param c1Id
     * @param c2Id
     * @param c3Id
     * @return
     */
    List<BaseAttrInfo> getBaseAttrInfoAndValue(@Param("c1Id") Long c1Id,
                                               @Param("c2Id") Long c2Id,
                                               @Param("c3Id") Long c3Id);
}




