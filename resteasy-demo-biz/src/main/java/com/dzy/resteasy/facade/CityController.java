package com.dzy.resteasy.facade;

import com.dzy.resteasy.model.City;
import com.dzy.resteasy.model.UserInfo;
import com.dzy.resteasy.support.result.BaseResult;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by dengzhiyuan on 2017/4/6.
 */
@Mapper
public interface CityController {

    BaseResult<City> getCity(Integer id);

    BaseResult<Integer> addOneCity(City id);

    BaseResult<City> saveAndGet(City id);

    BaseResult<Integer> addUserInfo(String version);
}
