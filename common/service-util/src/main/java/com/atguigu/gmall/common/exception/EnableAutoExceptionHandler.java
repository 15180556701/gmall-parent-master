package com.atguigu.gmall.common.exception;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(GmallGlobalExceptionHandler.class)
public @interface EnableAutoExceptionHandler {

}
