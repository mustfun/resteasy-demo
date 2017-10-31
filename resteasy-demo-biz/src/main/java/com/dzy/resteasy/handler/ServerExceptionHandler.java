package com.dzy.resteasy.handler;



import com.dzy.resteasy.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Priority;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

import static javax.ws.rs.core.Response.Status.OK;

/**
 * 服务端异常处理(5XX)
 * <p>返回实体的构造取决于请求头中accept的属性值</p>
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/8/30
 * @since 1.0
 */
@Provider
@Priority(1000)
@Component
public class ServerExceptionHandler  implements ExceptionMapper<Exception> {

    private static final Logger log= LoggerFactory.getLogger(ServerExceptionHandler.class);

    @Context
    protected HttpHeaders httpHeaders;

    @Override
    public Response toResponse(Exception exception) {
        log.error(exception.getMessage(), exception);

        List<MediaType> acceptableMediaTypes = httpHeaders.getAcceptableMediaTypes();
        log.info("acceptable media types: {}", new Object[]{acceptableMediaTypes});


        /*return Response
                .status(INTERNAL_SERVER_ERROR)
                .entity(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .header(HttpHeaders.CONTENT_TYPE, getAcceptableMediaType(acceptableMediaTypes))
                .build();*/
        Result<Boolean> result=new Result<>();
        result.setStatus(500);
        result.setMessage("服务器开小差啦，等会儿哈");
        return Response
                .status(OK)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .entity(result)
                .build();

    }

    /**
     * 获取accept media type, 遵循First Match Win的原则
     * @param acceptableMediaTypes
     * @return
     */
    protected String getAcceptableMediaType(List<MediaType> acceptableMediaTypes){
        if(CollectionUtils.isEmpty(acceptableMediaTypes)){
            return MediaType.WILDCARD;
        }
        MediaType acceptableMediaType = acceptableMediaTypes.get(0);
        return acceptableMediaType.getType().concat("/").concat(acceptableMediaType.getSubtype());
    }


}
