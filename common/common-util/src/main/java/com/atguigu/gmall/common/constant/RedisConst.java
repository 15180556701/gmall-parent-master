package com.atguigu.gmall.common.constant;

public class RedisConst {

    //redis的key的存储
    public static final String SKU_DETAIL_CACHE_PREFIX = "sku:info:";

    //处理缓存穿透对空值做的处理
    public static final String REDIS_TEMP_DATA = "x";
    public static final String LOCK_PREFIX = "lock-";
    public static final long TEMP_DATA_TTL = 1L;
    //skuid布隆过滤器
    public static final String BLOOM_SKUID = "skuid-bloom";
}
