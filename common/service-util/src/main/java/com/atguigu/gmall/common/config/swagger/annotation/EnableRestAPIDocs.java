package com.atguigu.gmall.common.config.swagger.annotation;


import com.atguigu.gmall.common.config.swagger.Swagger2Config;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(Swagger2Config.class)
public @interface EnableRestAPIDocs {
}
