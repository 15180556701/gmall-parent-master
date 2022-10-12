package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.constant.RedisConst;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.service.BloomService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "布隆过滤器")
@RestController
@RequestMapping("/admin/product")
public class BloomController {

    @Autowired
    BloomService bloomService;

    @Autowired
    RedissonClient redisson;

    @GetMapping("/bloom/resetBloom")
    public Result rebuildBloom(){

        //以后会有很多布隆，因此要对应名字来操作
        bloomService.resetBloom(RedisConst.BLOOM_SKUID);
//        RBloomFilter<Object> filter = redisson.getBloomFilter(RedisConst.BLOOM_SKUID);

        return Result.ok();
    }
}
