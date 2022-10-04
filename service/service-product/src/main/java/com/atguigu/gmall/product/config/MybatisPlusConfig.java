package com.atguigu.gmall.product.config;


import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MybatisPlusConfig {

//    @Bean
//    DataSource dataSource3(){
//        HikariDataSource source = new HikariDataSource();
//        source.setJdbcUrl("xxx/3307/xx");
//        return source;
//    }
//
//    @Bean
//    DataSource dataSource2(){
//        HikariDataSource source = new HikariDataSource();
//        source.setJdbcUrl("xxx/3308/xx");
//        return source;
//    }
//
//    @Bean
//    DataSource dataSource1(){
//        HikariDataSource source = new HikariDataSource();
//        source.setJdbcUrl("xxx/3306/xx");
//        return source;
//    }
    /**
     * 最大的拦截器，主体拦截器
     * @return
     */
    @Bean
    public MybatisPlusInterceptor  mybatisPlusInterceptor(){
        //1、创建拦截器主体
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //2、引入分页功能拦截器
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOverflow(true);

        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}
