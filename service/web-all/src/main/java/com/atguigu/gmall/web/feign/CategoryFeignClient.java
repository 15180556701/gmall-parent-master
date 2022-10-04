package com.atguigu.gmall.web.feign;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.web.CategoryVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 1、@FeignClient("service-product") 当前接口 是远程调用 service-product 服务的 feign客户端
 * 2、编写一个方法：
 *
 * 3、feign怎么发请求，发什么请求； feign都是发http
 *   1）、请求的完整地址
 *   2）、请求参数传递过去
 *   3）、请求头传递过去
 *   4）、对方收到请求执行业务
 *   5）、执行完成后返回数据
 *
 *   请求路径是什么？
 *   请求头、参数、体带什么？
 *   别人的响应数据怎么拿到？
 *
 *   为什么feign声明式客户端？
 *     不用写代码，只需要说清楚，给哪里发什么请求，怎么发？带什么东西即可
 *   编程式客户端？
 *     url = new URL("http://localhost:8000/api/inner/product/categorys/tree");
 *     url.openConnection();
 *     url.writeObject(写数据);
 *
 */
@RequestMapping("/api/inner/product")
@FeignClient("service-product")  //feign根据服务名自己取nacos中这个服务的地址
public interface CategoryFeignClient {

    //想办法给 http://localhost:8000/api/inner/product/categorys/tree
    //每个feign接口。Spring自己写好代理对象。
    //返回值类型：就是远程返回的数据转成我们能用的类型
//    @GetMapping("/api/inner/product/categorys/tree")
//    Result<List<CategoryVo>> getCategoryTree();

    @GetMapping("/categorys/tree")
    Result<List<CategoryVo>> getCategorys();



    //浏览器：
}
