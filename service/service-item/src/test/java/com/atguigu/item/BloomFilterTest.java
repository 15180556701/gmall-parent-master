package com.atguigu.item;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BloomFilterTest {

    @Test
    public void testBloom(){
       /* Funnel<? super T> funnel, 这是一个通道，指定给布隆过滤器什么类型数据
        int expectedInsertions,期望保存的数据量
         double fpp 误判率*/
        Funnel<Long> funnel = Funnels.longFunnel();
        //1、创建布隆过滤器
        BloomFilter<Long> filter = BloomFilter.create(funnel, 1000000, 0.0001);

        //2、给过滤器放数据
        filter.put(49L);
        filter.put(50L);
        filter.put(70L);

        //3、测试是否可能有这些数据
        System.out.println(filter.mightContain(49L));
        System.out.println(filter.mightContain(50L));
        System.out.println(filter.mightContain(70L));
        System.out.println(filter.mightContain(66L));
        System.out.println(filter.mightContain(100L));
    }
}
