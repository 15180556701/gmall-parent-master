package com.atguigu.gmall.product.biz.impl;

import com.atguigu.gmall.common.util.Jsons;
import com.atguigu.gmall.product.biz.CategoryBizSerivce;
import com.atguigu.gmall.product.entity.CategroyViewEntity;
import com.atguigu.gmall.product.mapper.BaseCategory1Mapper;
import com.atguigu.gmall.web.CategoryVo;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class CategoryBizSerivceImpl implements CategoryBizSerivce {

    @Autowired
    BaseCategory1Mapper category1Mapper;

    @Autowired
    StringRedisTemplate redisTemplate;


    @Override
    public List<CategoryVo> getCategorysTree() {
        //1、先查缓存
        String categorys = redisTemplate.opsForValue().get("categorys");
        //由于对缓存中值为空的的元素也做了处理，有可能会拿到x
        /*if ("x".equals(categorys)){
            //以前有人查过，但数据库无此记录
            return null;
        }*/
        if (StringUtils.isEmpty(categorys)){
            //2、缓存中没有，则回源
            List<CategoryVo> vos = category1Mapper.getCategorysTree();
            //3、将查询道德结果放入缓存，将对象形式的转成json格式
            if (vos==null){
                //4、防止缓存穿透，即使数据库没有此数据，也要缓存一个x
                redisTemplate.opsForValue().set("categorys", "x",30, TimeUnit.MINUTES);
            }else {
                //5、若有数据则真正放入数据库
                redisTemplate.opsForValue().set("categorys", Jsons.toStr(vos));
            }
            return vos;
        }


        //4、缓存中有的话，从缓存中获取，另外缓存中存的是对象序列化成json的数据，那么我们现在就要反序列化回来成对象
        List<CategoryVo> categoryVos = Jsons.toObj(categorys, new TypeReference<List<CategoryVo>>() {
        });

        return categoryVos;

    }

    @Override
    public CategroyViewEntity getCategoryView(Long c3Id) {

        return  category1Mapper.getCategoryView(c3Id);
    }

}
