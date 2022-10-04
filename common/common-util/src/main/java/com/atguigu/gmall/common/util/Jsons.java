package com.atguigu.gmall.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
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
}
