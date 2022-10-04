package com.atguigu.gmall.web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 所有前端页面渲染
 * 无需数据源：排除 @SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
 */
//@SpringCloudApplication
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
public class WebMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebMainApplication.class,args);
    }
}
