package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.product.entity.BaseAttrValue;
import com.atguigu.gmall.product.service.BaseAttrValueService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.product.entity.BaseAttrInfo;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import com.atguigu.gmall.product.mapper.BaseAttrInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lfy
 * @description 针对表【base_attr_info(属性表)】的数据库操作Service实现
 * @createDate 2022-09-26 11:46:23
 */
@Service
public class BaseAttrInfoServiceImpl extends ServiceImpl<BaseAttrInfoMapper, BaseAttrInfo>
        implements BaseAttrInfoService {


    @Autowired
    BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    BaseAttrValueService baseAttrValueService;

    @Override
    public List<BaseAttrInfo> getBaseAttrInfoAndValue(Long c1Id,
                                                      Long c2Id,
                                                      Long c3Id) {


        List<BaseAttrInfo> infos = baseAttrInfoMapper.getBaseAttrInfoAndValue(c1Id, c2Id, c3Id);


        return infos;
    }

    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        //1、保存平台属性名
        baseAttrInfoMapper.insert(baseAttrInfo);

        //拿到属性名的自增id
        Long id = baseAttrInfo.getId();

        //2、保存平台属性值
        List<BaseAttrValue> valueList = baseAttrInfo.getAttrValueList();
        for (BaseAttrValue attrValue : valueList) {
            //给每个属性值回填 属性id
            attrValue.setAttrId(id);
        }
        baseAttrValueService.saveBatch(valueList);

    }

    @Override
    public void updateAttrInfo(BaseAttrInfo baseAttrInfo) {
        //1、修改属性名表数据
        baseAttrInfoMapper.updateById(baseAttrInfo);

        //2、修改属性值表数据
        List<BaseAttrValue> valueList = baseAttrInfo.getAttrValueList();

        //带id是修改，不带是新增，
        //删除哪些？1、查出attrId当时的所有属性值id集合（59,60,61）
        // 2、对比前端提交的（59,61） 计算出差集。 删除差集数据
        //delete * from base_attr_value where attr_id=12 and id not in(前端提交的集合)

        //3、拿到前端提交的值id集合
        //3.1、先移除所有前端未提交的数据
        List<Long> ids = valueList.stream()
                .filter(item -> item.getId() != null)
                .map(item -> item.getId())
                .collect(Collectors.toList());
        if (ids.size() > 0) {
            //删除前端没带来的属性
            QueryWrapper<BaseAttrValue> wrapper = new QueryWrapper<>();
            wrapper.eq("attr_id", baseAttrInfo.getId());
            wrapper.notIn("id", ids);
            baseAttrValueService.remove(wrapper);
        } else {
            //全删
            QueryWrapper<BaseAttrValue> wrapper = new QueryWrapper<>();
            wrapper.eq("attr_id", baseAttrInfo.getId());
            baseAttrValueService.remove(wrapper);
        }


        //3.2、遍历，有值id就修改，没有就新增
        valueList.stream().forEach(item -> {
            if (item.getId() == null) {
                //新增
                item.setAttrId(baseAttrInfo.getId());
                baseAttrValueService.save(item);
            } else {
                //修改
                baseAttrValueService.updateById(item);
            }
        });


    }

//    public static void main(String[] args) {
//
//        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
//        for (Integer integer : list) {
//            // item + 2;
//            // filter %2==0
//            // sleep(3000)
//        }
//        //声明式编程
//
//
//        //1、集合中每个元素加2。 拿到所有偶数. 一个元素要流完所有流程才会下一个
//        List<Integer> collect = list.stream()
//                .parallel()  //默认使用ForkJoinPool 开很多线程执行。
//                .map(item -> {
//                    System.out.println("map:" + item + "==>" +Thread.currentThread());
//                    return item + 2;
//                })
//                .filter(item -> {
//                    System.out.println("filter:" + (item-2) + "==>" +Thread.currentThread());
////                    try {
////                        Thread.sleep(3000);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
//                    return item % 2 == 0;
//                })
//                .collect(Collectors.toList());
//
//
//    }
}




