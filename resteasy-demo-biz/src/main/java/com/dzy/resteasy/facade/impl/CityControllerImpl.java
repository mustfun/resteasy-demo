package com.dzy.resteasy.facade.impl;

import com.dzy.resteasy.biz.QueryUserInfoBiz;
import com.dzy.resteasy.facade.CityController;
import com.dzy.resteasy.model.City;
import com.dzy.resteasy.model.UserInfo;
import com.dzy.resteasy.service.CityService;
import com.dzy.resteasy.service.UserInfoService;
import com.dzy.resteasy.support.result.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/5/4
 * @since 1.0
 */
//如果把city前缀放在这里，单元测试就会失败
@RestController
public class CityControllerImpl implements CityController {

    @Autowired
    private CityService cityService;
    @Autowired
    private QueryUserInfoBiz queryUserInfoBiz;

    @Override
    @RequestMapping(value = "/city/getCity",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<City> getCity(Integer id) {
        City one = cityService.getOne(id);
        BaseResult<City> baseResult=new BaseResult<>();
        baseResult.setData(one);
        return baseResult;
    }

    @Override
    @RequestMapping(value = "/city/addOneCity",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResult<Integer> addOneCity(@RequestBody City id) {
        Integer integer = cityService.addOneCity(id);
        BaseResult<Integer> baseResult=new BaseResult<>();
        baseResult.setData(integer);
        return baseResult;
    }

    @Override
    @RequestMapping(value = "/city/saveAndGet",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<City> saveAndGet(@RequestBody City id) {
        BaseResult<City> baseResult=new BaseResult<>();
        baseResult.setData(cityService.saveAndGet(id));
        return baseResult;
    }

    @Override
    @RequestMapping(value = "/city/addUserInfo",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResult<Integer> addUserInfo(String version) {
        BaseResult<Integer> result = new BaseResult<>();
        switch (version){
            case "v1":
                result = queryUserInfoBiz.insertUserInfoV1(version);
                break;
            case "v2":
                try {
                    result = queryUserInfoBiz.insertUserInfoV2(version);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "v3":
                result = queryUserInfoBiz.insertUserInfoV3(version);
                break;
            case "v4":
                result = queryUserInfoBiz.insertUserInfoV4(version);
                break;
            case "v5":
                result = queryUserInfoBiz.insertUserInfoV5(version);
                break;
            case "v6":
                result = queryUserInfoBiz.insertUserInfoV6(version);
                break;
            case "v7":
                result = queryUserInfoBiz.insertUserInfoV7(version);
                break;
            case "v8":
                result = queryUserInfoBiz.insertUserInfoV8(version);
                break;
            case "v9":
                result = queryUserInfoBiz.insertUserInfoV9(version);
                break;
            default:
                result = queryUserInfoBiz.insertUserInfoV1(version);
                break;
        }
        return result;
    }
}
