package com.atguigu.gmall.product.rpc;


import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.biz.CategoryBizSerivce;
import com.atguigu.gmall.web.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


//内部之间进行调用的
@RequestMapping("/api/inner/product")
@RestController
public class CategoryRpcController {


    //利用CAS原理
    AtomicInteger i = new AtomicInteger(0);
    //Spring容器中组件默认单实例。大并发进来，所有线程用的是同一个Controller对象。
    //多线程操作同一个变量，会引发线程安全问题【默认操作不安全】（默认这个变量会被多个人同时持有副本，每个人改的只是副本，导致与预期的业务结果不一致）
    //加锁： 多线程的异步变成串行
    //原子变量


    @Autowired
    CategoryBizSerivce categoryBizSerivce;

    /**
     * 数据库查询所有分类，封装成一个嵌套的树形结构
     * @return
     */
    @GetMapping("/categorys/tree")
    public Result<List<CategoryVo>> getCategorys(){
        int i = this.i.incrementAndGet();

        List<CategoryVo>  tree = categoryBizSerivce.getCategorysTree();
        return Result.ok(tree);
    }

}
