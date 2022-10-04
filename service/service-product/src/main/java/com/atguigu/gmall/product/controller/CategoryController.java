package com.atguigu.gmall.product.controller;


import com.atguigu.gmall.common.result.Result;

import com.atguigu.gmall.product.entity.BaseCategory1;
import com.atguigu.gmall.product.entity.BaseCategory2;
import com.atguigu.gmall.product.entity.BaseCategory3;
import com.atguigu.gmall.product.service.BaseCategory1Service;
import com.atguigu.gmall.product.service.BaseCategory2Service;
import com.atguigu.gmall.product.service.BaseCategory3Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/product")
@RestController
public class CategoryController {


    @Autowired
    BaseCategory1Service baseCategory1Service;

    @Autowired
    BaseCategory2Service baseCategory2Service;

    @Autowired
    BaseCategory3Service baseCategory3Service;


    @GetMapping("/getCategory1")
    public Result getCategory1(){

        List<BaseCategory1> list = baseCategory1Service.list();
        return Result.ok(list);
    }


    /**
     * 根据1级分类id，查找对应的2级分类
     * @param cid
     * @return
     */
    @GetMapping("/getCategory2/{category_id}")
    public Result getCategory2(@PathVariable("category_id")Long cid){

        List<BaseCategory2> list  =  baseCategory2Service.getCategory2(cid);
        return Result.ok(list);
    }


    /**
     * 根据2级分类id，查找对应的3级分类
     * @return
     */
    @GetMapping("/getCategory3/{c2Id}")
    public Result getCategory3(@PathVariable("c2Id") Long c2Id){

        List<BaseCategory3> list = baseCategory3Service.getCategory3(c2Id);
        //haha
        return Result.ok(list);
    }



}
