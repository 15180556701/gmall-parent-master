package com.atguigu.gmall.item.annocation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MallCache {
    String cacheKey() default "";
}
