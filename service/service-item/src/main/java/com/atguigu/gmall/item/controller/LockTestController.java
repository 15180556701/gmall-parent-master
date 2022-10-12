package com.atguigu.gmall.item.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LockTestController {

    @Autowired
    RedissonClient redissonClient;

    @GetMapping("/lock")
    public String ReentrantLock() throws InterruptedException {
        //1得到锁
        RLock lock = redissonClient.getLock("lock");

        //2、加锁
        lock.lock();
        System.out.println(Thread.currentThread()+"得锁");

        //执行业务
        Thread.sleep(10000);

        //3、解锁
        System.out.println(Thread.currentThread()+"解锁");
        lock.unlock();
        return "ok";
    }
}
