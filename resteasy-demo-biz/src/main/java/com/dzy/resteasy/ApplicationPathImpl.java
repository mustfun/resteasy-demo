package com.dzy.resteasy;

import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/8/22
 * @since 1.0
 */
@Component
@ApplicationPath("/v1/")
public class ApplicationPathImpl extends Application {

}
