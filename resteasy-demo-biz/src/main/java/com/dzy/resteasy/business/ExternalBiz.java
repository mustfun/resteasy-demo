package com.dzy.resteasy.business;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.dzy.resteasy.annotation.Reader;
import com.dzy.resteasy.model.UserCourseDto;
import com.dzy.resteasy.result.Result;
import com.dzy.resteasy.service.task.CourseTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/8/25
 * @since 1.0
 */
@Component
public class ExternalBiz {

    private static final Object LOCK = new Object();

    private static final Logger log = LoggerFactory.getLogger(ExternalBiz.class);

    public static final String MOCK_JSON_URL="http://rapapi.org/mockjsdata/23108/order.soa.biwan.com/getMockJson";

    private static final String XIAOMI_6S_KEY="product:android:xiaomi:6s";

    private  ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CourseTaskService courseTaskService;

    @Autowired
    private Jedis jedis;

    public Result<List<UserCourseDto>> getUserCourse(){
        String result = restTemplate.getForObject(MOCK_JSON_URL, String.class);
        return JSONObject.parseObject(result, new TypeReference<Result<List<UserCourseDto>>>() {});
    }

    /**
     * 并发请求
     * @return
     */
    public Result<List<UserCourseDto>> getUserCourseByThreadPool(){
        long startTime=System.currentTimeMillis();
        courseTaskService.executeAsyncTaskA();//这里说明异步了，根本不耗时间
        System.out.println("单个请求耗时"+(System.currentTimeMillis()-startTime));
        ListenableFuture<ResponseEntity<String>> responseEntityListenableFutureB = courseTaskService.executeAsyncTaskB();
        //C请求有返回值,拿到返回值再返回
        ListenableFuture<ResponseEntity<String>> responseEntityListenableFutureC = courseTaskService.executeAsyncTaskC();
        try {
            //get是一个阻塞方法，会一直等待线程执行完毕
            System.out.println("==============马上会阻塞=============="+startTime);
            ResponseEntity<String> stringResponseEntityB = responseEntityListenableFutureB.get(300l, TimeUnit.MILLISECONDS);
            ResponseEntity<String> stringResponseEntityC = responseEntityListenableFutureC.get(300l, TimeUnit.MILLISECONDS);
            System.out.println("======================阻塞耗时============="+(System.currentTimeMillis()-startTime));
            System.out.println("stringResponseEntityB = " + stringResponseEntityB.toString());
            System.out.println("stringResponseEntityC = " + stringResponseEntityC.toString());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e){
            e.printStackTrace();
        }
        System.out.println("三个请求耗时" + (System.currentTimeMillis()-startTime));
        return new Result<>();
    }

    /**
     * 我这里故意用注解的方式去获取参数，因为在实际工作中注解获取会用得很多
     * <p>当然也可以直接传2个class过来就可以处理呀
     * @param annotations
     * @return
     */
    public Result<List<UserCourseDto>> getUserCourseV2(Annotation[] annotations) {
        final Reader[] readers = {null};
        if(annotations != null) {
            Arrays
                    .stream(annotations)
                    .filter(annotation -> annotation.annotationType().equals(Reader.class))
                    .findFirst().ifPresent(x -> readers[0] = (Reader) x);
        }
        Class[] classes = readers[0].extParamClass();
        String result = restTemplate.getForObject(MOCK_JSON_URL, String.class);
        //这里不用TypeReference方式，直接用class对象来处理

        ParameterizedTypeImpl beforeType = null;
        if (classes.length!=0){
            //支持多级泛型的解析
            for (int i = classes.length-1; i >0 ; i--) {
                beforeType = new ParameterizedTypeImpl(new Type[]{beforeType == null? classes[i]:beforeType}, null, classes[i - 1]);
            }
        }
        return  JSON.parseObject(result,beforeType);
    }

    /**
     * 这样写会抛异常,socket write error，应该是set的时候会并发异常
     * @return
     */
    public  Result<Boolean> buyXiaoMi() {
        Result<Boolean> result=new Result<>();
        int i = Integer.parseInt(jedis.get(XIAOMI_6S_KEY));
        if (i<=0){
            log.warn("正常返回，但是没有抢购到~");
            result.setMessage("抱歉您来晚了，已经卖完啦");
            result.setStatus(-1);
            return result;
        }else{
            jedis.set(XIAOMI_6S_KEY,(i-1)+"");
            log.info("抢购成功，当前库存剩余======{}",(i-1));
        }
        result.setStatus(0);
        result.setMessage("抢购成功，正在跳转订单页面");
        return result;
    }

    /**
     * 利用轻量锁
     */
    public  Result<Boolean> buyXiaoMiWithLiteLock() {
        Result<Boolean> result=new Result<>();
        readWriteLock.writeLock().lock();
        int i = 0;
        try {
            i = Integer.parseInt(jedis.get(XIAOMI_6S_KEY));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }finally {
            readWriteLock.writeLock().unlock();
        }
        if (i<=0){
            result.setStatus(-1);
            result.setMessage("抱歉您来晚了，已经卖完啦");
            log.warn("正常返回，但是没有抢购到~");
            return result;
        }else{
            readWriteLock.writeLock().lock();
            try {
                jedis.set(XIAOMI_6S_KEY,(i-1)+"");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readWriteLock.writeLock().unlock();
            }
            log.info("抢购成功，当前库存剩余======{}",(i-1));
        }
        result.setStatus(0);
        result.setMessage("抢购成功，正在跳转订单页面");
        return result;
    }


    /**
     * 利用重量锁
     * 可以直接放在方法上面，锁方法synchronized
     * 如果想粒度小一点，那么就要保证锁的是同一个对象，否则，socket(写的时候)还是会报不够用，这里是特殊情况，redis公用同一个socket
     * @return
     */
    public  Result<Boolean> buyXiaoMiWithLock() {
        Result<Boolean> result=new Result<>();
        int i;
        synchronized (LOCK){
            i = Integer.parseInt(jedis.get(XIAOMI_6S_KEY));
        }
        if (i<=0){
            log.warn("正常返回，但是没有抢购到~");
            result.setMessage("抱歉您来晚了，已经卖完啦");
            result.setStatus(-1);
            return result;
        }else{
            synchronized (LOCK) {
                jedis.set(XIAOMI_6S_KEY, (i - 1) + "");
            }
            log.info("抢购成功，当前库存剩余======{}", (i - 1));
        }
        result.setStatus(0);
        result.setMessage("抢购成功，正在跳转订单页面");
        return result;
    }


    /**
     * 使用redis特性实现redis分布式锁
     * setnx需要设置一个过期时间
     * @return
     */
    public  Result<Boolean> buyXiaoMiWithRedisLock() {
        Result<Boolean> result=new Result<>();
        int i = Integer.parseInt(jedis.get(XIAOMI_6S_KEY));
        if (i<=0){
            log.warn("正常返回，但是没有抢购到~");
            result.setMessage("抱歉您来晚了，已经卖完啦");
            result.setStatus(-1);
            return result;
        }else{
            jedis.set(XIAOMI_6S_KEY,(i-1)+"");
            jedis.setnx(XIAOMI_6S_KEY,(i-1)+"");
            log.info("抢购成功，当前库存剩余======{}",(i-1));
        }
        result.setStatus(0);
        result.setMessage("抢购成功，正在跳转订单页面");
        return result;
    }
}
