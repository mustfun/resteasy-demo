package com.dzy.resteasy.facade.resteasy.impl;

import com.alibaba.fastjson.JSON;
import com.dzy.resteasy.annotation.Reader;
import com.dzy.resteasy.business.ExternalBiz;
import com.dzy.resteasy.facade.resteasy.ExternalFacade;
import com.dzy.resteasy.model.MyForm;
import com.dzy.resteasy.model.UserCourseDto;
import com.dzy.resteasy.result.Result;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/8/22
 * @since 1.0
 */
@Component
@Path("province/")
public class ExternalFacadeImpl implements ExternalFacade {

    @Context
    private HttpServletRequest request;

    @Autowired
    private ExternalBiz externalBiz;
    /**
     * http://localhost:8080/v1/province/city/1
     * @param id
     * @return
     */
    @Path("city/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Result<Boolean> getCity(@PathParam("id")@Encoded Long id) {
        System.out.println(id);
        return new Result<>();
    }

    /**
     * http://localhost:8080/v1/province/en_city/小明
     * @param name
     * @return
     */
    @Path(value = "en_city/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Result<Boolean> getCity(@PathParam("name")@Encoded String name) {
        System.out.println(name);
        return new Result<>();
    }

    /**
     * http://localhost:8080/v1/province/queryCity/1;from=wh?name=sh
     * @param id
     * @return
     */
    @Path("queryCity/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Result<Boolean> queryCity(@MatrixParam("from")String from,@CookieParam("__utma")String sessionId,@HeaderParam("auth")String authKey,@PathParam("id") Long id,@QueryParam("name")String name) {
        System.out.println(id);
        System.out.println(name);
        System.out.println(sessionId);
        System.out.println(authKey);
        System.out.println(from);
        return new Result<>();
    }

    /**
     * GET http://localhost:8080/v1/resources/city
     * GET http://localhost:8080/v1/resources/foo/city/1
     * GET http://localhost:8080/v1/resources/on/and/on/city/1
     * @param id
     * @return
     */
    @Path("{var:.*}/city/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Result<Boolean> getObscureCity(@PathParam("id") Long id) {

        return new Result<>();
    }


    /**
     *  http://localhost:8080/v1/province/city/1?name=%22%E5%B0%8F%E6%98%8E%22
     * @param id
     * @param name
     * @return
     */
    @POST
    @Path("city/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Result<Boolean> addBook(@FormParam("age")Integer age,@PathParam("id") String id, @QueryParam("name") String name) {
        System.out.println(id);
        System.out.println(name);
        System.out.println(age);
        return new Result<>();
    }

    @PUT
    @Path("city/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Result<Boolean> addForm(@Form MyForm form) {
        System.out.println(JSON.toJSONString(form));
        return new Result<>();
    }

    /**
     *
     * @param id
     * @return
     */
    @DELETE
    @Path("city/{id}")
    public Result<Boolean> removeBook(@PathParam("id") String id){
        return new Result<>();
    }


    @GET
    @Path("getUserCourse")
    @Produces(MediaType.APPLICATION_JSON)
    public Result<List<UserCourseDto>> getUserCourse(){
        return externalBiz.getUserCourse();
    }

    @GET
    @Path("getUserCourseByThreadPool")
    @Produces(MediaType.APPLICATION_JSON)
    public Result<List<UserCourseDto>> getUserCourseByThreadPool(){
        return externalBiz.getUserCourseByThreadPool();
    }

    @GET
    @Reader(extParamClass = {Result.class,List.class,UserCourseDto.class})
    @Path("getUserCourseV2")
    @Produces(MediaType.APPLICATION_JSON)
    public Result<List<UserCourseDto>> getUserCourseV2(){
        Annotation[] annotations = new Annotation[0];
        for (Method method : this.getClass().getMethods()) {
            if (method.getName().equals("getUserCourseV2")){
                annotations = method.getAnnotations();
            }
        }
        return externalBiz.getUserCourseV2(annotations);
    }

    /**
     * 模拟抢购，做一些锁的实际应用吧，单机1000qps，没有往上面压了
     * @return
     */
    @GET
    @Path("buyXiaoMi")
    @Produces(MediaType.APPLICATION_JSON)
    public Result<Boolean> buyXiaoMi(){
        //return externalBiz.buyXiaoMiWithLock();
        return externalBiz.buyXiaoMiWithLiteLock();
    }

    /**
     * 模拟抢购，使用rocket-mq流量削峰
     */
    @GET
    @Path("buyXiaoMiV2")
    @Produces(MediaType.APPLICATION_JSON)
    public Result<Boolean> buyXiaoMiV2(){
        return externalBiz.buyXiaoMiWithMq();
    }
}
