package com.dzy.resteasy.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/8/29
 * @since 1.0
 */
@Service
public class CourseTaskService {


    public static final String MOCK_JSON_URL="http://rapapi.org/mockjsdata/23108/order.soa.biwan.com/getMockJson";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    /**
     * 执行异步请求
     * @param
     */
    @Async  //标记为异步
    public void executeAsyncTaskA(){
        String result = restTemplate.getForObject(MOCK_JSON_URL, String.class);
        System.out.println(Thread.currentThread().getName());
    }

    @Async  //标记为异步
    public ListenableFuture<ResponseEntity<String>> executeAsyncTaskB(){
        //String result = restTemplate.getForObject(MOCK_JSON_URL, String.class);
        System.out.println(Thread.currentThread().getName());
        return asyncRestTemplate.getForEntity(MOCK_JSON_URL,String.class);
    }

    @Async  //标记为异步
    public ListenableFuture<ResponseEntity<String>> executeAsyncTaskC(){
        //String result = restTemplate.getForObject(MOCK_JSON_URL, String.class);
        System.out.println(Thread.currentThread().getName());
        return asyncRestTemplate.getForEntity(MOCK_JSON_URL, String.class);
    }

}
