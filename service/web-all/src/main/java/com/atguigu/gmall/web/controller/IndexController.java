package com.atguigu.gmall.web.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.web.CategoryVo;
import com.atguigu.gmall.web.feign.CategoryFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 */
@Controller
public class IndexController {


    @Autowired
    CategoryFeignClient categoryFeignClient;
    /**
     * 首页
     * @return
     */
    @GetMapping("/")
    public String indexpage(Model model){


        //RPC 商品服务，得到结果
        List<CategoryVo> list = new ArrayList<>();
//        String tree = categoryFeignClient.getCategoryTree();
        Result<List<CategoryVo>> categorys = categoryFeignClient.getCategorys();

        List<CategoryVo> data = categorys.getData();

        model.addAttribute("list",data);
        return "index/index";
    }


}
