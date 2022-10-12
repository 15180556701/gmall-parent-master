package com.atguigu.gmall.common.redisson;

import com.atguigu.gmall.common.constant.RedisConst;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfiguration {

    @Autowired
    RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://"+ redisProperties.getHost()+":"+redisProperties.getPort())
                .setPassword(redisProperties.getPassword());
        //1、创建redisson客户端
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

    @Bean
    public RBloomFilter<Object> skuIdBloom(){
        RedissonClient client = redissonClient();
        RBloomFilter<Object> bloomFilter = client.getBloomFilter(RedisConst.BLOOM_SKUID);
        if (!bloomFilter.isExists()){
            //如果不存在初始化布隆
            bloomFilter.tryInit(1000000,0.00001);
        }
        return bloomFilter;
    }
}
