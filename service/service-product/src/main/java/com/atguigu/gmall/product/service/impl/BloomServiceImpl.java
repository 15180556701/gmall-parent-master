package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.common.constant.RedisConst;
import com.atguigu.gmall.product.service.BloomService;
import com.atguigu.gmall.product.service.SkuInfoService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BloomServiceImpl implements BloomService {

    @Autowired
    RedissonClient redisson;

    @Autowired
    SkuInfoService skuInfoService;

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 重建布隆
     * @param bloomSkuid
     */
    @Override
    public void resetBloom(String bloomSkuid) {

        //1创建一个新的布隆，保存查询过来的数据
        RBloomFilter<Object> newBloom = redisson.getBloomFilter(RedisConst.BLOOM_SKUID + "-new");
        if (!newBloom.isExists()){
            newBloom.tryInit(10000000,0.000001);
            skuInfoService.getAllSkuId().stream().forEach(item->{
                newBloom.add(item);
            });
        }


        //2、删除老的布隆
        RBloomFilter<Object> oldBloom = redisson.getBloomFilter(RedisConst.BLOOM_SKUID);
        oldBloom.delete();

        //3、将新的布隆改名成原来的
        newBloom.rename(RedisConst.BLOOM_SKUID);

        //4、记住任何关系密切的操作（比如加锁解锁）一定要保证是原子操作，将上面的操作变成原子操作（脚本写的有问题就先凑合）
//        String script = "redis.call(\"del\",KEYS[1]);" +
//                "redis.call(\"del\",\"{\"..KEYS[1]..\"}\":config);" +
//                "redis.call(\"rename\",KEYS[2],KEYS[1]);" +
//                "redis.call(\"rename\",\"{\"..KEYS[2]..\"}\":config\",\"{\"..KEYS[1]..\"}\":config\"); return 1;";
//
//        redisTemplate.execute(new DefaultRedisScript<>(script,
//                Long.class),
//                Arrays.asList(RedisConst.BLOOM_SKUID,RedisConst.BLOOM_SKUID + "-new"));

    }
}
