package com.dzy.resteasy.facade.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/5/4
 * @since 1.0
 */
@RestController
@RequestMapping("/common")
public class CommonControllerImpl{

    @RequestMapping(value = "/getBaseResult",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE) @ResponseBody public JSONObject getBaseResult() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imgUrl","htt://img.baidu.com/images/11.jpg");
        jsonObject.put("name","itar");
        return jsonObject;
    }
}
