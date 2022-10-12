package com.atguigu.gmall.item;

import com.atguigu.gmall.common.exception.EnableAutoExceptionHandler;
import com.atguigu.gmall.common.redisson.annotation.EnableRedisson;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableRedisson
@EnableAutoExceptionHandler
@EnableFeignClients
@SpringCloudApplication
public class ItemMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemMainApplication.class,args);
    }
}
