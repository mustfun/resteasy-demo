package com.dzy.resteasy.support.filter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : itar
 * @date : 2017-12-17
 * @time : 13:39
 * @Version: 1.0
 * @since: JDK 1.8
 * 本栏目采用复制流的方式来实现
 */
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@WebFilter(urlPatterns = {"/never/*"},filterName = "branchResponseWrapperFilter")
public class BranchResponseWrapperFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BranchResponseWrapperFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        BranchResponseWrapper wrapper = new BranchResponseWrapper(response);
        try {
            filterChain.doFilter(request,wrapper);

            String respStr= new String(wrapper.toByteArray(), response.getCharacterEncoding());
            Object parse = JSON.parse(respStr);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status",0);
            jsonObject.put("data",parse);
            LOGGER.info("response is ============{}",jsonObject);
            response.setContentType("application/json;charset=utf-8");
            //将buffer重置，因为我们要重新写入流进去
            response.resetBuffer();
            response.setContentLength(JSON.toJSONBytes(jsonObject).length);
            response.getOutputStream().write(JSON.toJSONBytes(jsonObject));
        } catch (Exception e) {
            LOGGER.error("数据包装器执行出错....{}", e);
        }
    }
}
