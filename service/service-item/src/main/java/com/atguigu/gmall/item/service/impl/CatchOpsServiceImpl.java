package com.atguigu.gmall.item.service.impl;

import com.atguigu.gmall.common.constant.RedisConst;
import com.atguigu.gmall.common.util.Jsons;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
public class CatchOpsServiceImpl implements CatchOpsService{

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 1、查到东西返回对象，没有返回null
     * 2、返回null：缓存中没有该数据
     * 3、返回对象：对象里面属性一个值都没有，说明以前就有线程去查了库，并发现没有后，放了个占位符"x"在数据库中
     * 3、返回对象，对象里属性所有值存在，说明缓存中有这个值存在
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> T getCacheData(String key, Class<T> clazz) {


        //1、查询redis
        String json = redisTemplate.opsForValue().get(key);

        if (StringUtils.isEmpty(json)){
            //没有这个key的值返回Null
            return null;
        }else {
            //先处理缓存穿透问题，不过jsons里面判断了，就不需要了
//            if ("x".equals(json)){
//                return null;
//            }

            //有json数据,那就使用jsons工具类将查到的json数据转成对象
            T t = Jsons.toObj(json, clazz);
            return t;
        }

    }

    /**
     * 当方法返回值有复杂泛型嵌套，用这个
     * @param key
     * @param tr
     * @param <T>
     * @return
     */
    @Override
    public <T> T getCacheData(String key, TypeReference<T> tr) {

        //1、查询redis
        String json = redisTemplate.opsForValue().get(key);

        if (StringUtils.isEmpty(json)){
            //没有这个key的值返回Null
            return null;
        }else {

            //有json数据,那就使用jsons工具类将查到的json数据转成对象
            T t = Jsons.toObj(json, tr);
            return t;
        }
    }

    @Override
    public void saveData(String key, Object data, Long ttl, TimeUnit timeUnit) {
        String jsonData = data==null ? RedisConst.REDIS_TEMP_DATA : Jsons.toStr(data);

        //缓存中的每一个数据都应该有过期时间
        //如果回源拿不到数据
        if (data==null){
            redisTemplate.opsForValue().set(key, jsonData,RedisConst.TEMP_DATA_TTL,TimeUnit.HOURS);
        }else {
            //拿到数据可以自己设置过期时间
            redisTemplate.opsForValue().set(key, jsonData,ttl,timeUnit);
        }
    }


}
