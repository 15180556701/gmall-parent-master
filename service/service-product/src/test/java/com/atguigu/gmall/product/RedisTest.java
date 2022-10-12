package com.atguigu.gmall.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;

@SpringBootTest
public class RedisTest {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    void testRedisTemplate(){
        String s = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("a",s);
        String a = redisTemplate.opsForValue().get("a");
        System.out.println(a);
    }
}
