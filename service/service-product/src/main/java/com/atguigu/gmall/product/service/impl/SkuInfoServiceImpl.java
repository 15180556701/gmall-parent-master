package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.common.constant.RedisConst;
import com.atguigu.gmall.product.entity.SkuAttrValue;
import com.atguigu.gmall.product.entity.SkuImage;
import com.atguigu.gmall.product.entity.SkuSaleAttrValue;
import com.atguigu.gmall.product.service.SkuAttrValueService;
import com.atguigu.gmall.product.service.SkuImageService;
import com.atguigu.gmall.product.service.SkuSaleAttrValueService;
import com.atguigu.gmall.product.vo.SkuInfoSaveVo;
import com.atguigu.gmall.product.vo.SkuInfoUpdateVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.product.entity.SkuInfo;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.atguigu.gmall.product.mapper.SkuInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author lfy
 * @description 针对表【sku_info(库存单元表)】的数据库操作Service实现
 * @createDate 2022-09-26 11:46:23
 */
@SuppressWarnings("all")
@Slf4j
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo>
        implements SkuInfoService {

    @Autowired
    SkuInfoMapper skuInfoMapper;

    @Autowired
    SkuImageService skuImageService;

    @Autowired
    SkuAttrValueService skuAttrValueService;

    @Autowired
    SkuSaleAttrValueService saleAttrValueService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @PostConstruct
    public void initSkuIdBloom(){

        //1、布隆初始化
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(RedisConst.BLOOM_SKUID);
        if (!bloomFilter.isExists()){
            bloomFilter.tryInit(1000000,0.00001);
            List<Long> skuId = getAllSkuId();
            skuId.stream().forEach(item->{
                bloomFilter.add(item);
            });
        }
    }


    @Transactional
    @Override
    public void saveSkuInfo(SkuInfoSaveVo vo) {
        //1、保存sku_info
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(vo, skuInfo);
        skuInfoMapper.insert(skuInfo);

        Long skuId = skuInfo.getId();


        //2、保存sku_images
        List<SkuImage> images = vo.getSkuImageList().stream()
                .map(item -> {
                    SkuImage image = new SkuImage();
                    BeanUtils.copyProperties(item, image);
                    //回填sku_id
                    image.setSkuId(skuId);
                    return image;
                }).collect(Collectors.toList());
        skuImageService.saveBatch(images);

        //3、保存 sku平台属性名和值
        List<SkuAttrValue> attrValues = vo.getSkuAttrValueList().stream()
                .map(item -> {
                    SkuAttrValue skuAttrValue = new SkuAttrValue();
                    BeanUtils.copyProperties(item, skuAttrValue);
                    //回填sku_id
                    skuAttrValue.setSkuId(skuId);
                    return skuAttrValue;
                }).collect(Collectors.toList());
        skuAttrValueService.saveBatch(attrValues);


        //4、保存  sku销售属性名和值
        List<SkuSaleAttrValue> saleAttrValues = vo.getSkuSaleAttrValueList()
                .stream()
                .map(item -> {
                    SkuSaleAttrValue value = new SkuSaleAttrValue();
                    BeanUtils.copyProperties(item, value);
                    //回填sku_id、spu_id
                    value.setSkuId(skuId);
                    value.setSpuId(skuInfo.getSpuId());
                    return value;
                })
                .collect(Collectors.toList());

        saleAttrValueService.saveBatch(saleAttrValues);

        //把商品信息添加到过滤器
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(RedisConst.BLOOM_SKUID);
        bloomFilter.add(skuId);

        log.info("sku信息保存成功： skuId={}",skuId);

    }

    @Override
    public void changeOnSale(Long skuId, int status) {
        //1、修改数据库状态
        skuInfoMapper.updateSaleStatus(skuId,status);
        //TODO 2、上架的商品代表可以被检索到
    }

    @Override
    public BigDecimal getSkuPrice(Long skuId) {

        BigDecimal price =  skuInfoMapper.getSkuPrice(skuId);
        return price;
    }

    @Override
    public List<Long> getAllSkuId() {
        return skuInfoMapper.getAllSkuId();
    }

    @Override
    public void updateSkuInfo(SkuInfoUpdateVo vo) {
        //1、去数据库修改


        //2延迟双删
        //2.1、先立即删
        stringRedisTemplate.delete(RedisConst.SKU_DETAIL_CACHE_PREFIX+vo.getId());
        //2.2、再延迟删
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(4);
        pool.schedule(()->{
            stringRedisTemplate.delete(RedisConst.SKU_DETAIL_CACHE_PREFIX+vo.getId());
        }, 10, TimeUnit.SECONDS);

    }
}




