package com.atguigu.gmall.product;


import com.atguigu.gmall.product.entity.SpuImage;
import com.atguigu.gmall.product.mapper.SpuImageMapper;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest //这是SpringBoot测试类
public class ShardingJdbcTest {


    @Autowired
    SpuImageMapper spuImageMapper;


    @Transactional //事务自动都是操作主库
    @Test
    public void testQuery2(){
        SpuImage image = new SpuImage();
        image.setSpuId(0L);
        image.setImgName("bbb");
        image.setImgUrl("bbb");

        //有可能刚保存的数据查不出来？从库可能还没同步上。
        spuImageMapper.insert(image);
        System.out.println("保存完成...");
        //接下来的一次查询会主动路由给主库，而不用从库负载均衡
//        HintManager.getInstance().setWriteRouteOnly(); //强制主库路由
        SpuImage spuImage = spuImageMapper.selectById(image.getId());
        System.out.println("查询结果："+spuImage);

        //刚才保存的结果，一定要查出来！
        //1、同一个事务不会切库。
        //2、强制查询主库。



    }


    @Test
    public void testQuery(){
        //5次查询负载均衡到各个从库
        System.out.println("第1次："+spuImageMapper.selectById(299L));
        System.out.println("第2次："+spuImageMapper.selectById(299L));
        System.out.println("第3次："+spuImageMapper.selectById(299L));
        System.out.println("第4次："+spuImageMapper.selectById(299L));
        System.out.println("第5次："+spuImageMapper.selectById(299L));
    }

    @Test
    public void spuImageTest(){
        SpuImage image = new SpuImage();
        image.setSpuId(0L);
        image.setImgName("aaa");
        image.setImgUrl("aaa");

        spuImageMapper.insert(image);
        System.out.println("插入完成....");
    }
}
