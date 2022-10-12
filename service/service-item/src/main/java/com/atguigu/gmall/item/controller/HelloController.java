package com.atguigu.gmall.item.controller;

import com.atguigu.gmall.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class HelloController {

    @Autowired
    StringRedisTemplate redisTemplate;

    //要保证这个锁是唯一的
//    ReentrantLock lock = new ReentrantLock();

    @Value("${server.port}")
    String port;

    //1w请求。加到1w
    @GetMapping("/increment")
    public Result increment(){

        String uuid = UUID.randomUUID().toString();
        //阻塞式抢锁
        lock(uuid);

        incr();

        unlock(uuid);

        return Result.ok();
    }

    private void unlock(String uuid) {
        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1]\n" +
                "then\n" +
                "    return redis.call(\"del\",KEYS[1])\n" +
                "else\n" +
                "    return 0\n" +
                "end";

        //使用脚本来解锁
        redisTemplate.execute(new DefaultRedisScript<>(script,Long.class), Arrays.asList("lock"), uuid);
    }

    private void lock(String uuid) {
        while (!redisTemplate.opsForValue().setIfAbsent("lock", uuid, 60, TimeUnit.SECONDS)){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void incr () {
        System.out.println(port + "端口服务开始执行");
        String hello = redisTemplate.opsForValue().get("hello");
        int i = Integer.parseInt(hello);
        i++;
        redisTemplate.opsForValue().set("hello",""+i);
    }
}
