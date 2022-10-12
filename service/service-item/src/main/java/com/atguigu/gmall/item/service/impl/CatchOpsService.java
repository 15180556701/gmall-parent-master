package com.atguigu.gmall.item.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.concurrent.TimeUnit;

/**
 * 缓存操作接口
 */

public interface CatchOpsService {
    //由于这个查询缓存的接口，我们是要用于多个业务，因此这里的对象的类型不确定，
    //注意用字母的话，得先声明，首部的这个<T>就是在声明
    <T> T getCacheData(String key, Class<T> clazz);

    <T> T getCacheData(String key, TypeReference<T> tr);

    //在缓存中存入该数据
    void saveData(String key, Object object, Long ttl, TimeUnit timeUnit);
}
