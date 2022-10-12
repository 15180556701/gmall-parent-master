package com.atguigu.gmall.common.util;

import com.atguigu.gmall.common.constant.RedisConst;
import com.atguigu.gmall.common.execption.GmallException;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Jsons {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toStr(Object object){

        String json = null;
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("对象{}， json转换异常：{}",object,e);
        }
        return json;
    }

    public static<T> T toObj(String jsonStr,TypeReference <T> tf) {
        if ("x".equals(jsonStr)){
            //以前有人查过，但数据库无此记录
            return null;
        }
        T t = null;
        try {
            t = objectMapper.readValue(jsonStr, tf);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("json{}, 转对象异常",jsonStr,e);
        }
        return t;
    }

    public static<T> T toObj(String jsonStr,Class <T> tf) {
        //如果redis缓存中存的是临时占位符就创建一个空对象，不过使用统一异常处理更方便
        /*T t = null;

        try {
            //创建一个T类型的空对象
            t = tf.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }*/



        if (RedisConst.REDIS_TEMP_DATA.equals(jsonStr)){
            //以前有人查过，但数据库无此记录
            throw new GmallException(ResultCodeEnum.REDIS_DATA_NOT_EXIST);
        }
        T t = null;
        try {
            t = objectMapper.readValue(jsonStr, tf);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("json{}, 转对象异常",jsonStr,e);
        }
        return t;
    }
}
