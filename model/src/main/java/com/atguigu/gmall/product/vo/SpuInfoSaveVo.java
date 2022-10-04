/**
  * Copyright 2022 jb51.net 
  */
package com.atguigu.gmall.product.vo;
import java.util.List;

import lombok.Data;

/**
 * Auto-generated: 2022-09-29 9:31:39
 *
 * @author jb51.net (i@jb51.net)
 * @website http://tools.jb51.net/code/json2javabean
 */
@Data
public class SpuInfoSaveVo {

    private Long id;
    private String spuName;
    private String description;
    private Long category3Id;

    private List<Spuimagelist> spuImageList;
    private List<Spusaleattrlist> spuSaleAttrList;

    private Long tmId;


}