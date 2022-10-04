package com.atguigu.gmall.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 登录页
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(){

        return "login";//登录页  classpath:/templates/login.html
    }
}
