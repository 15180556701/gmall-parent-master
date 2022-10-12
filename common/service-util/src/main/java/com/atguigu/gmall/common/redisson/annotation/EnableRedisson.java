package com.atguigu.gmall.common.redisson.annotation;

import com.atguigu.gmall.common.redisson.RedissonConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(RedissonConfiguration.class)
public @interface EnableRedisson {
}
