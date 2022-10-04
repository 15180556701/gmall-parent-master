package com.atguigu.gmall.web;


import lombok.Data;

import java.util.List;

/**
 * 分类的嵌套结构
 */
@Data
public class CategoryVo {

    private Long categoryId;  //当前分类id
    private String categoryName; //当前分类名
    private List<CategoryVo> categoryChild; //子分类

}
