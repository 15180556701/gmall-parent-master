package com.atguigu.gmall.product.controller;


import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.entity.BaseAttrInfo;
import com.atguigu.gmall.product.entity.BaseAttrValue;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import com.atguigu.gmall.product.service.BaseAttrValueService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "平台属性接口")
@RequestMapping("/admin/product")
@RestController
public class BaseAttrController {


    @Autowired
    BaseAttrInfoService baseAttrInfoService;

    @Autowired
    BaseAttrValueService attrValueService;

    @GetMapping("/hello")
    public Result hello(){
        return Result.ok();
    }


    /**
     * 查询平台属性名和值
     * @param c1Id
     * @param c2Id
     * @param c3Id
     * @return
     */
    @GetMapping("/attrInfoList/{c1id}/{c2id}/{c3id}")
    public Result getattrInfoList(@PathVariable("c1id") Long c1Id,
                                  @PathVariable("c2id") Long c2Id,
                                  @PathVariable("c3id") Long c3Id){

        List<BaseAttrInfo> infos = baseAttrInfoService.getBaseAttrInfoAndValue(c1Id,c2Id,c3Id);
        return Result.ok(infos);
    }


    /**
     * 保存平台属性
     * @param baseAttrInfo
     * @return
     */
    @PostMapping("/saveAttrInfo")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        if(baseAttrInfo.getId() == null){
            //属性新增
            baseAttrInfoService.saveAttrInfo(baseAttrInfo);
        }else {
            baseAttrInfoService.updateAttrInfo(baseAttrInfo);
        }


        return Result.ok();
    }


    /**
     * 根据属性id，查询出属性的所有值列表
     * @param attrId
     * @return
     */
    @GetMapping("/getAttrValueList/{attrId}")
    public Result getAttrValueList(@PathVariable("attrId") Long attrId){

       List<BaseAttrValue> baseAttrValues =  attrValueService.getAttrValueList(attrId);

        return Result.ok(baseAttrValues);
    }

}
