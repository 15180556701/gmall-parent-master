package com.atguigu.gmall.product.service;

import com.atguigu.gmall.product.entity.SpuInfo;
import com.atguigu.gmall.product.vo.SpuInfoSaveVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author lfy
* @description 针对表【spu_info(商品表)】的数据库操作Service
* @createDate 2022-09-26 11:46:24
*/
public interface SpuInfoService extends IService<SpuInfo> {

    /**
     * 分页查询
     * @param category3Id
     * @param pn
     * @param ps
     * @return
     */
    Page<SpuInfo> getSpuInfoPage(Long category3Id, Long pn, Long ps);

    /**
     * 保存前端提交的SPU信息
     * @param spuInfoSaveVo
     */
    void saveSpuInfo(SpuInfoSaveVo spuInfoSaveVo);
}
