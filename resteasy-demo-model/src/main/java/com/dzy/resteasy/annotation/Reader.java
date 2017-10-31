package com.dzy.resteasy.annotation;

import javax.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther dengzhiyuan
 * @Date 2017-08-25
 */
@NameBinding
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Reader {

    /**
     * 扩展Class参数
     * @return
     */
    Class[] extParamClass() default {};

    /**
     * 扩展String参数
     * @return
     */
    String[] extParam() default {};
}
