package com.atguigu.gmall.item.service.impl;

import com.alibaba.nacos.common.utils.ConcurrentHashSet;
import com.atguigu.gmall.common.constant.RedisConst;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.item.annocation.MallCache;
import com.atguigu.gmall.item.feign.SkuDetailFeignClient;
import com.atguigu.gmall.item.service.SkuDetailService;
import com.atguigu.gmall.product.entity.SkuImage;
import com.atguigu.gmall.product.entity.SkuInfo;
import com.atguigu.gmall.product.entity.SpuSaleAttr;
import com.atguigu.gmall.web.SkuDetailVo;
import com.atguigu.gmall.web.SkuDetailVo.CategoryView;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SkuDetailServiceImpl implements SkuDetailService {

    @Autowired
    SkuDetailFeignClient skuDetailFeignClient;

    Set<Long> skuId = new ConcurrentHashSet<>();


    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    CatchOpsService catchOps;

    @Autowired
    RedissonClient redisson;

    @Autowired
    RBloomFilter<Object> skuIdBloom = null;

    //本地锁锁不住分布式
//    ReentrantLock lock = new ReentrantLock();

    @PostConstruct
    void init() {
//        //该组件创建好之后会执行这个方法
//        log.info("项目启动，正在初始化分布式布隆过滤器");
//        //所有skuId查出来放入容器中
////        filter = BloomFilter.create(Funnels.longFunnel(), 100000000, 0.00001);
//        skuIdBloom = redisson.getBloomFilter(RedisConst.BLOOM_SKUID);
//        if (!skuIdBloom.isExists()){
//            //如果不存在
//            skuIdBloom.tryInit(1000000, 0.00001);
//
//            //获取所有skuId
//            Result<List<Long>> skuIds = skuDetailFeignClient.getAllSkuId();
//            skuIds.getData().forEach(item -> {
//                skuIdBloom.add(item);
//            });
//
//        }
//        log.info("布隆过滤器初始化完成..49:{}和80:{}", skuIdBloom.contains(49L), skuIdBloom.contains(80L));

    }


    /**
     * 一号版本
     * @param skuId
     * @return
     */
    public SkuDetailVo skuDetailithRedisAndBloom(Long skuId) {

        log.info("商品详情查询开始 : {}", skuId);
        //该线程锁分配的锁的值
        String uuid = UUID.randomUUID().toString();

        //使用分布式锁，即在redis中设置一个信号量，让锁们去抢，setIfAbsent：拿到锁才放锁
        //加上过期时间可以防止万一在业务执行过程出现问题，redis也能把锁删了不造成死锁
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 10, TimeUnit.MINUTES);
        if (lock) {
            //强锁成功，回源数据
            System.out.println("执行业务");

            //解锁，直接把锁删了就行，要防止解掉别人的锁，这个用脚本来保证原子性
//            String lockValue = redisTemplate.opsForValue().get("lock");
//            if (uuid.equals(lockValue)){
//                redisTemplate.delete("lock");
//            }

            //自动续期
            Thread thread = new Thread(() -> {
                while (true) {
                    //每隔10秒续30秒
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    redisTemplate.expire("lock", 30, TimeUnit.MINUTES);
                }
            });
            thread.setDaemon(true);//守护线程

            String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1]\n" +
                    "then\n" +
                    "    return redis.call(\"del\",KEYS[1])\n" +
                    "else\n" +
                    "    return 0\n" +
                    "end";

            //返回0代表删锁失败，即没有锁或者锁是别人的，返回1则是成功删除自己的锁
            Long result = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class),
                    Arrays.asList("lock"), uuid);
            if (result == 0) {
                log.error("这个锁是别人的不能删");
            } else {
                log.info("成功删除了自己的锁");
            }

        } else {
            //强锁失败，等待500ms，继续抢
        }

        //1、先判断缓存里是否有该元素
//        if (!filter.mightContain(skuId)){
//            //布隆过滤器里没有该元素
//            log.info("商品详情查询,布隆过滤器里没有该元素 : {}",skuId );
//            return null;
//        }
//
//        //2、先查缓存
//        log.info("商品查询-正在数据回源: {}",skuId);
//        String json = redisTemplate.opsForValue().get("sku:info:" + skuId);
//        if ("x".equals(json)){
//            return null;
//        }
////        boolean tryLock = lock.tryLock();
//        if (StringUtils.isEmpty(json)){
//            if (lock){
//                //3、redis中没有缓存数据,查询数据
//                SkuDetailVo skuDetail = getSkuDetailFromRpc(skuId);
//                //把对象转为json字符串
//                String data = skuDetail == null ? "x":Jsons.toStr(skuDetail);
//                redisTemplate.opsForValue().set("sku:info:" + skuId,data);
//                log.info("数据回源完毕，并已缓存到redis: {}",skuId);
//                lock.unlock();
//                return skuDetail;
//            }
//        }else {
//            SkuDetailVo skuDetailVo = Jsons.toObj(json, SkuDetailVo.class);
//            return skuDetailVo;
//        }
        return null;
    }

    //查商品详情
    private SkuDetailVo getSkuDetailFromRpc(Long skuId) {
        SkuDetailVo data = new SkuDetailVo();

        //1、查询出sku_info信息
        Result<SkuInfo> skuInfo = skuDetailFeignClient.getSkuInfo(skuId);

        //2、查询当前sku的图片列表
        Result<List<SkuImage>> skuImages = skuDetailFeignClient.getSkuImages(skuId);
        SkuInfo info = skuInfo.getData();
        info.setSkuImageList(skuImages.getData());

        //设置好skuinfo的数据返回
        data.setSkuInfo(info);

        //3、查询sku的分类信息
        Result<CategoryView> categoryView = skuDetailFeignClient.getCategoryView(info.getCategory3Id());
        data.setCategoryView(categoryView.getData());


        //4、查询sku的价格信息。为了得到最新价格，每次都再查一遍
        Result<BigDecimal> skuPrice = skuDetailFeignClient.getSkuPrice(skuId);
        data.setPrice(skuPrice.getData());


        //5、查询sku的销售属性
        Long spuId = info.getSpuId();
        Result<List<SpuSaleAttr>> value = skuDetailFeignClient.getSpuSaleAttrAndValue(spuId, skuId);
        data.setSpuSaleAttrList(value.getData());

        //6、json 就是 map（"119|120":49,"122:123":50）、javaBean
        Result<String> skuJson = skuDetailFeignClient.getSpuValuesSkuJson(spuId);
        data.setValuesSkuJson(skuJson.getData());

        return data;
    }

    /**二号版本
     * 在使用redis而是redisson来实现分布式锁
     *
     */
    public SkuDetailVo skuDetailWithRedissonLockAndBloonFilter(Long skuId) {


            //key，前面的是redis的命名空间格式
            String key = RedisConst.SKU_DETAIL_CACHE_PREFIX + skuId;

            //1、先查询缓存
            SkuDetailVo data = catchOps.getCacheData(key, SkuDetailVo.class);

            //2、判断缓存中是否存在
            if (data != null) {
                //3、缓存中有则直接返回
                return data;
            }

            //4、缓存中没有，则进行回源，先问过滤器，若连过滤器中都没有就无须查询
            boolean contain = redisson.getBloomFilter(RedisConst.BLOOM_SKUID).contains(skuId);
            if (!contain) {
                return null;
            }

            //5、布隆过滤器说有，开始回源，不过在这先要解决缓存击穿问题，准备一个分布式锁，一个商品只能放进去一个查询
            RLock lock = redisson.getLock(RedisConst.LOCK_PREFIX + skuId);
            //6、试一下获取锁
            boolean tryLock = lock.tryLock();
            if (!tryLock) {
                try {
                    //7、没拿到锁等5秒，再重新查缓存
                    Thread.sleep(500);
                    //也可以递归调用该方法继续差缓存
//                    return skuDetail(skuId);
                    return catchOps.getCacheData(key, SkuDetailVo.class);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                //9、拿到锁，开始回源，为了再次确认缓存中有没有该数据，防止重复查数据库，开启双检查机制
                SkuDetailVo cacheData = catchOps.getCacheData(key, SkuDetailVo.class);
                try {
                    if (cacheData == null) {
                        //10、回源
                        log.info("商品详情回源:",skuId);
                        SkuDetailVo fromRpc = getSkuDetailFromRpc(skuId);
                        cacheData = fromRpc;
                        //11、放入缓存
                        catchOps.saveData(key, fromRpc, 1L, TimeUnit.DAYS);
                    }
                    return cacheData;
                } finally {
                    lock.unlock();
                }

            }

            //这玩意没用随便写，因为逻辑都在上面
            return data;
    }


    /**
     * 三号版本，将之前的分布式锁做一个抽取，这里需要AOP的知识
     * @param skuId
     * @return
     */
    @MallCache(cacheKey="sku:info:#{#args[0]}")
    @Override
    public SkuDetailVo skuDetail(Long skuId) {
        SkuDetailVo fromRpc = getSkuDetailFromRpc(skuId);
        return fromRpc;
    }
}
