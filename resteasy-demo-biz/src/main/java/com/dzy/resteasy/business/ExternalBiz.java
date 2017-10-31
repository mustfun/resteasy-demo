package com.dzy.resteasy.business;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.dzy.resteasy.annotation.Reader;
import com.dzy.resteasy.model.UserCourseDto;
import com.dzy.resteasy.result.Result;
import com.dzy.resteasy.service.task.CourseTaskService;
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

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/8/25
 * @since 1.0
 */
@Component
public class ExternalBiz {

    public static final String MOCK_JSON_URL="http://rapapi.org/mockjsdata/23108/order.soa.biwan.com/getMockJson";

    private static final String XIAOMI_6S_KEY="product:android:xiaomi:6s";

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
     * 这样写会抛异常,socket write error
     * @return
     */
    public  Result<Boolean> buyXiaoMi() {
        Result<Boolean> result=new Result<>();
        int i = Integer.parseInt(jedis.get(XIAOMI_6S_KEY));
        if (i<=0){
            result.setStatus(-1);
            result.setMessage("抱歉您来晚了，已经卖完啦");
        }else{
            jedis.set(XIAOMI_6S_KEY,(i-1)+"");
        }
        result.setStatus(0);
        result.setMessage("抢购成功，正在跳转订单页面");
        return result;
    }

    public synchronized   Result<Boolean> buyXiaoMiWithLock() {
        Result<Boolean> result=new Result<>();
        int i = Integer.parseInt(jedis.get(XIAOMI_6S_KEY));
        if (i<=0){
            result.setStatus(-1);
            result.setMessage("抱歉您来晚了，已经卖完啦");
        }else{
            System.out.println("抢购成功，当前库存剩余======"+(i-1));
            jedis.set(XIAOMI_6S_KEY,(i-1)+"");
        }
        result.setStatus(0);
        result.setMessage("抢购成功，正在跳转订单页面");
        return result;
    }
}
