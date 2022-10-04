package com.atguigu.gmall.product.biz;

import com.atguigu.gmall.product.entity.CategroyViewEntity;
import com.atguigu.gmall.web.CategoryVo;

import java.util.List;

/**
 * 分类所有复杂业务相关操作
 */
public interface CategoryBizSerivce {
    List<CategoryVo> getCategorysTree();

    /**
     * 根据三级分类id，查询分类完整路径
     * @param c3Id
     * @return
     */
    CategroyViewEntity getCategoryView(Long c3Id);
}
