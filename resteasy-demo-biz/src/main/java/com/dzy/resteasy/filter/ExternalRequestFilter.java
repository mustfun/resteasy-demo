package com.dzy.resteasy.filter;

import com.dzy.resteasy.annotation.Reader;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.interception.jaxrs.PostMatchContainerRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/8/25
 * @since 1.0
 */
@Component
@Provider
@Priority(100)//filter优先级
@ConstrainedTo(RuntimeType.SERVER)
@Reader
public class ExternalRequestFilter implements ContainerRequestFilter,ContainerResponseFilter {

    private static final Logger logger = LoggerFactory.getLogger(ExternalRequestFilter.class);

    private static final String REQUEST_LOG_ID = "ExternalRequestFilter.request.id";

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String url = requestContext.getUriInfo().getRequestUri().toString();
        String body = "";
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(requestContext.getEntityStream(), writer, "UTF-8");
            body = writer.toString();
            requestContext.setEntityStream(new ByteArrayInputStream(body.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info(body);//上面一段代码是取出请求，不需要就可以删掉
        //PostMatchContainerRequestContext request = (PostMatchContainerRequestContext) requestContext;
        //ResourceMethodInvoker resourceMethod = request.getResourceMethod();

    }

}
