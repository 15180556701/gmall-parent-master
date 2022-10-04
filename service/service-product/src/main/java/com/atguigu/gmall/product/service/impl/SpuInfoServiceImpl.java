package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.product.entity.SpuImage;
import com.atguigu.gmall.product.entity.SpuSaleAttr;
import com.atguigu.gmall.product.entity.SpuSaleAttrValue;
import com.atguigu.gmall.product.service.SpuImageService;
import com.atguigu.gmall.product.service.SpuSaleAttrService;
import com.atguigu.gmall.product.service.SpuSaleAttrValueService;
import com.atguigu.gmall.product.vo.SpuInfoSaveVo;
import com.atguigu.gmall.product.vo.Spuimagelist;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.product.entity.SpuInfo;
import com.atguigu.gmall.product.service.SpuInfoService;
import com.atguigu.gmall.product.mapper.SpuInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lfy
 * @description 针对表【spu_info(商品表)】的数据库操作Service实现
 * @createDate 2022-09-26 11:46:24
 */
@Service
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfo>
        implements SpuInfoService {

    @Autowired
    SpuImageService imageService;

    @Autowired
    SpuSaleAttrService saleAttrService;

    @Autowired
    SpuSaleAttrValueService saleAttrValueService;

    @Override
    public Page<SpuInfo> getSpuInfoPage(Long category3Id, Long pn, Long ps) {
        Page<SpuInfo> page = new Page<>(pn, ps);

        QueryWrapper<SpuInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("category3_id", category3Id);
        Page<SpuInfo> result = page(page, wrapper);
        return result;
    }

    @Transactional
    @Override
    public void saveSpuInfo(SpuInfoSaveVo vo) {
        //1、保存spuInfo
        SpuInfo spuInfo = new SpuInfo();
        //复制属性字段值
        BeanUtils.copyProperties(vo, spuInfo);
        this.save(spuInfo);
        //拿到spuId
        Long id = spuInfo.getId();

        //2、保存图片信息
        List<SpuImage> images = vo.getSpuImageList().stream()
                .map(item -> {
                    SpuImage image = new SpuImage();
                    //属性对拷
                    BeanUtils.copyProperties(item, image);
                    //回填spu_id
                    image.setSpuId(id);
                    return image;
                }).collect(Collectors.toList());
        imageService.saveBatch(images);


        //3、保存销售属性名  spu_sale_attr
        //List<Spusaleattrlist>
        vo.getSpuSaleAttrList().stream()
                .forEach(item -> {
                    SpuSaleAttr saleAttr = new SpuSaleAttr();
                    BeanUtils.copyProperties(item, saleAttr);
                    saleAttr.setSpuId(id);
                    //保存销售属性名
                    saleAttrService.save(saleAttr);

                    //拿到销售属性值；  List<Spusaleattrvaluelist>
                    List<SpuSaleAttrValue> attrValues = item.getSpuSaleAttrValueList().stream()
                            .map(val -> {
                                SpuSaleAttrValue value = new SpuSaleAttrValue();
                                BeanUtils.copyProperties(val, value);
                                //回填
                                value.setSpuId(id);
                                value.setSaleAttrName(saleAttr.getSaleAttrName());
                                return value;
                            }).collect(Collectors.toList());
                    //保存销售属性值
                    saleAttrValueService.saveBatch(attrValues);
                });


    }
}




