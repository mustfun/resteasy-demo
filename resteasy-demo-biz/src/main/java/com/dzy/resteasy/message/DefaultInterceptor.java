package com.dzy.resteasy.message;

import com.dzy.resteasy.annotation.Reader;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.*;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/8/25
 * @since 1.0
 */
@Component
@Provider
@Priority(200)
@ConstrainedTo(RuntimeType.SERVER)
public class DefaultInterceptor implements ReaderInterceptor,WriterInterceptor{

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext readerInterceptorContext) throws IOException, WebApplicationException {
        final Reader[] logThirdAnnotations = {null};
        Annotation[] annotations = readerInterceptorContext.getAnnotations();
        if(annotations != null) {
            Arrays
                    .stream(annotations)
                    .filter(annotation -> annotation.annotationType().equals(Reader.class))
                    .findFirst().ifPresent(x -> logThirdAnnotations[0] = (Reader) x);
        }

        return readerInterceptorContext.proceed();
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext) throws IOException, WebApplicationException {
        writerInterceptorContext.proceed();
    }
}
