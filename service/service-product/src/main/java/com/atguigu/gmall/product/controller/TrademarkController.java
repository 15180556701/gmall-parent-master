package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.entity.BaseTrademark;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/product")
@RestController
public class TrademarkController {



    @Autowired
    BaseTrademarkService baseTrademarkService;
    /**
     * 查询品牌分页数据
     * @return
     */
    @GetMapping("/baseTrademark/{pn}/{ps}")
    public Result baseTrademark(@PathVariable("pn") Long pn,
                                @PathVariable("ps") Long ps){

        Page<BaseTrademark> page = new Page<>(pn,ps);

        //分页查询
        Page<BaseTrademark> result = baseTrademarkService.page(page);

        return Result.ok(result);
    }

    /**
     * 品牌保存
     * @return
     */
    @PostMapping("/baseTrademark/save")
    public Result saveTrademark(@RequestBody BaseTrademark trademark){

        baseTrademarkService.save(trademark);

        return Result.ok();
    }


    ///admin/product/
    //405 请求方式不允许（请求路径和分页的完全格式一致，springmvc准备用分页处理这个请求）
    //      但是GET和DELETE又没匹配上，所以405
    // 响应状态码？  200，404，405，503
    //而不是 404
    @DeleteMapping("/baseTrademark/remove/{id}")
    public Result remove(@PathVariable("id") Long id){
        baseTrademarkService.removeById(id);
        return Result.ok();
    }


    //查询指定的品牌信息，要在页面回显
    //http://192.168.200.1/admin/product/baseTrademark/get/16
    //400 Bad Request  请求方式GET请求路径 /baseTrademark/get/16 完全匹配到分页上了
    //但是 参数类型转化出现问题。 400 请求参数传递的不对，服务器无法转换使用
    //4xx：  全部前端问题   404, 400
    //5xx：  全部后台问题
    @GetMapping("/baseTrademark/get/{id}")
    public Result getbaseTrademark(@PathVariable("id") Long id){
        BaseTrademark trademark = baseTrademarkService.getById(id);
        return Result.ok(trademark);
    }


    /**
     * 修改
     */
    @PutMapping("/baseTrademark/update")
    public Result update(@RequestBody BaseTrademark trademark){

        baseTrademarkService.updateById(trademark);
        return Result.ok();
    }


    //

    /**
     * 获取所有品牌
     * @return
     */
    @GetMapping("/baseTrademark/getTrademarkList")
    public Result getTrademarkList(){

        List<BaseTrademark> list = baseTrademarkService.list();
        return Result.ok(list);
    }



}
