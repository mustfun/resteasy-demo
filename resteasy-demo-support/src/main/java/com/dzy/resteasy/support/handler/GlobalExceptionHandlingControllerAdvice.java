package com.dzy.resteasy.support.handler;

import com.dzy.resteasy.support.result.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.ws.rs.ClientErrorException;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/11/1
 * @since 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandlingControllerAdvice {

    protected Logger logger;

    public GlobalExceptionHandlingControllerAdvice() {
        logger = LoggerFactory.getLogger(getClass());
    }

    /**
     * Convert a predefined exception to an HTTP Status code
     * 可以将当前请求参数加到参数列表里面
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "页面没有找到")
    @ResponseBody
    @ExceptionHandler(ClientErrorException.class)
    public BaseResult conflict() {
        logger.error("请求发生404异常");
        BaseResult baseResult = new BaseResult();
        baseResult.setStatus(404);
        baseResult.setMessage("页面丢失啦");
        return baseResult;
    }


    @ResponseBody
    @ExceptionHandler(Exception.class)
    public BaseResult handlerAllException() {
        logger.error("请求发生404异常");
        BaseResult baseResult = new BaseResult();
        baseResult.setStatus(-500);
        baseResult.setMessage("服务器开小差啦，怎么办呀");
        return baseResult;
    }

}