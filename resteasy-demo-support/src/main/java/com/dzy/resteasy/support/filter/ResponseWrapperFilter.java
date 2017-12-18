package com.dzy.resteasy.support.filter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dzy.resteasy.support.result.BaseResult;
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
 */
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@WebFilter(urlPatterns = {"/common/*"},filterName = "responseWrapperFilter")
public class ResponseWrapperFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseWrapperFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ResponseWrapper wrapper = new ResponseWrapper(response);
        try {
            filterChain.doFilter(request,wrapper);

            String responseStr = new String(wrapper.toByteArray(), response.getCharacterEncoding());
            Object parse = JSON.parse(responseStr);

            BaseResult<Object> baseResult = new BaseResult<>();
            baseResult.setData(parse);
            if (parse instanceof JSONObject){
                JSONObject resultObject = (JSONObject) parse;
                if (resultObject.containsKey("status")&&resultObject.containsKey("message")&&resultObject.containsKey("data")){
                    baseResult = JSONObject.parseObject(resultObject.toJSONString(),new TypeReference<BaseResult<Object>>(){});
                }
            }
            LOGGER.info("response is ============{}",baseResult);

            response.setContentLength(JSON.toJSONBytes(baseResult).length);
            response.setContentType("application/json;charset=utf-8");
            response.getOutputStream().write(JSON.toJSONBytes(baseResult));
        } catch (Exception e) {
            LOGGER.error("数据包装器执行出错....{}", e);
        }
    }
}
