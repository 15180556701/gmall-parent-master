package com.atguigu.gmall.item.cache;

import com.atguigu.gmall.common.constant.RedisConst;
import com.atguigu.gmall.item.annocation.MallCache;
import com.atguigu.gmall.item.service.impl.CatchOpsService;
import com.atguigu.gmall.web.SkuDetailVo;
import com.fasterxml.jackson.core.type.TypeReference;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

@Aspect//声明这是一个切面
@Component
public class MallCacheAspect {

    @Autowired
    CatchOpsService catchOpsService;

    @Autowired
    RedissonClient redissonClient;

    //完整的方法签名=修饰符+返回值+返回类型  必须都是全路径名
    //最精细的写法：@Before("execution(public com.atguigu.gmall.web.SkuDetailVo com.atguigu.gmall.item.service.SkuDetailService.skuDetail(Long))")//切入点表达式，里面填的是这个通知方法要切入的目标方法，里面填其方法的引用地址
    //只切入我们标了注解的方法
//    @Before("@annotation(com.atguigu.gmall.item.annocation.MallCache)")
//    public void before(){
//        System.out.println("aa");
//    }
//
//    @After("@annotation(com.atguigu.gmall.item.annocation.MallCache)")
//    public void after(){
//        System.out.println("bb");
//    }

    /**
     *
     * @param pjp ：封装了目标方法所有信息的连接点
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.atguigu.gmall.item.annocation.MallCache)")
    public Object cacheIntercept(ProceedingJoinPoint pjp) throws Throwable {

        //1、拿到目标方法的参数
        Object[] args = pjp.getArgs();

        Object result = null;

        boolean lockStatus = false;

        try {
            //2、获取缓存key
            String cacheKey = determinCacheKey(pjp);
            //2.1、得到方法的返回值类型
            Object data = catchOpsService.getCacheData(cacheKey, new TypeReference<Object>() {
                @Override
                public Type getType() {
                    return getMethodReturnType(pjp);
                }
            });
            if (data!=null){
                //缓存中有，直接返回数据
                return data;
            }
            result = pjp.proceed(args);

            //缓存中没有，回源
            RBloomFilter<Object> filter = redissonClient.getBloomFilter(RedisConst.BLOOM_SKUID);
            if (!filter.contains(args[0])){
                return null;
            }

            //布隆判定有，获取锁
            RLock lock = redissonClient.getLock(RedisConst.LOCK_PREFIX + args[0]);

            //加锁
            boolean tryLock = lock.tryLock();
            if (tryLock){
                //加锁成功
                lockStatus = true;
                result = pjp.proceed();
                //放入缓存
                catchOpsService.saveData(cacheKey,result,7L, TimeUnit.DAYS);

                return result;
            } else {
                //加锁失败，则睡500ms，然后从缓存中拿成功抢到锁的线程锁查的数据，就无须去数据库查了
                Thread.sleep(500);
                return catchOpsService.getCacheData(cacheKey, SkuDetailVo.class);
            }

        } catch (Exception e) {
            //异常通知
        } finally {
            //解锁
            if (lockStatus){
                //先把锁拿来
                RLock lock = redissonClient.getLock(RedisConst.LOCK_PREFIX + args[0]);
                lock.unlock();
            }
        }
        return result;
    }

    private Type getMethodReturnType(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Type returnType = signature.getMethod().getGenericReturnType();
        return returnType;
    }

    /**
     * 决定用哪个key去缓存中查询
     * @param pjp
     * @return
     */
    private String determinCacheKey(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        //1、（拿到方法的完整签名）拿到当前目标方法上标注的@MallCache注解
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        //2、拿到当前方法
        Method method = signature.getMethod();
        //3、获取方法上标记的注解
        MallCache annotation = method.getAnnotation(MallCache.class);
        //4、拿到注解的cacheKey属性值
        String expr = annotation.cacheKey();
        //5、计算出cacheKey
        String cacheKey = calculateExpression(expr,pjp,String.class);

        return cacheKey;
    }

    /**
     * 根据表达式语法，动态计算表达式的值
     * @param expr
     * @param pjp
     * @param returnType
     * @param <T>
     * @return
     */
    SpelExpressionParser parser = new SpelExpressionParser();
    private <T> T calculateExpression(String expr, ProceedingJoinPoint pjp, Class<T> returnType) {

        Expression expression = parser.parseExpression(expr, ParserContext.TEMPLATE_EXPRESSION);

        //sku:info:#{#args[0]}
        expression.getValue();

        //1、准备计算上下文
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("args",pjp.getArgs());

        //上下文扩充变量可以允许更多操作
        context.setVariable("redisson",redissonClient);

        //2、计算值
        T value = expression.getValue(context, returnType);


        return value;
    }
}
