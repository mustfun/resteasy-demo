package com.dzy.resteasy.support.intercepter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/11/14
 * @since 1.0
 * @ 这篇文章也可以：https://webcache.googleusercontent.com/search?q=cache:p9zGiEIvcPMJ:https://www.isostech.com/blogs/software-development/preventing-duplicate-submissions-spring-mvc/+&cd=6&hl=en&ct=clnk&gl=nl
 * 不过用到了spring security
 * @function 下面这种方式实现比较简单
 */
//@Component
public class SingleSubmitInterceptor extends HandlerInterceptorAdapter{

    private static final String UNIQUE_ONE_TIME_TOKEN = "unique-one-time-token";

    private static final Logger LOG = LoggerFactory.getLogger(SingleSubmitInterceptor.class);

    private Set<String> tokens                = Collections.synchronizedSet(new HashSet<String>());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getParameter(UNIQUE_ONE_TIME_TOKEN);

        LOG.debug("preHandle: token = {}", token);

        if (token == null || tokens.contains(token)) {
            LOG.error("token {} is null or allready used", token);
            // ignore request
            response.sendError(202);
            return false;
        }
        tokens.add(token);
        return true;
    }

    //
    // GET CALLED AFTER CONTROLLER DID HIS JOB
    //

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        String token = request.getParameter(UNIQUE_ONE_TIME_TOKEN);

        LOG.debug("postHandle: token = {}", token);

        if (token != null) {
            tokens.remove(token);
        }
    }
}
