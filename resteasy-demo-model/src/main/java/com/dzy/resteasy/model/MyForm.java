package com.dzy.resteasy.model;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/8/23
 * @since 1.0
 */
public class MyForm {
    @FormParam("age")
    private int age;
    @HeaderParam("auth")
    private String authKey;
    @PathParam("id")
    private String id;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getAuthKey() {
        return authKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
