package com.atguigu.gmall.product.service;

import com.atguigu.gmall.product.entity.BaseAttrInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author lfy
* @description 针对表【base_attr_info(属性表)】的数据库操作Service
* @createDate 2022-09-26 11:46:23
*/
public interface BaseAttrInfoService extends IService<BaseAttrInfo> {

    /**
     * 根据分类id 获取平台属性名和值
     * @param c1Id
     * @param c2Id
     * @param c3Id
     * @return
     */
    List<BaseAttrInfo> getBaseAttrInfoAndValue(Long c1Id, Long c2Id, Long c3Id);

    /**
     * 保存平台属性
     * @param baseAttrInfo
     */
    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    /**
     * 修改平台属性
     * @param baseAttrInfo
     */
    void updateAttrInfo(BaseAttrInfo baseAttrInfo);
}
