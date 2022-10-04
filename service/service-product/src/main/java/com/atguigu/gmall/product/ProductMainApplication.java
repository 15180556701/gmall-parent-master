package com.atguigu.gmall.product;

import com.atguigu.gmall.common.config.minio.annotation.EnableMinio;
import com.atguigu.gmall.common.config.swagger.annotation.EnableRestAPIDocs;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 主类
 * 1、导入mybatis-plus；要求必须配置数据库
 * 2、配置数据库
 * 3、配置nacos；
 * <p>
 * SpringBoot启动扫描主程序所在的包及其子包下的所有组件
 * 主程序： com.atguigu.gmall.product
 * 配置类:  com.atguigu.gmall.common.config.minio
 */
//@EnableCircuitBreaker //开启服务熔断
//@EnableDiscoveryClient  //开启服务发现
//@SpringBootApplication

@EnableTransactionManagement
@EnableRestAPIDocs
@EnableMinio
@MapperScan("com.atguigu.gmall.product.mapper")
@SpringCloudApplication
public class ProductMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductMainApplication.class, args);
    }
}
