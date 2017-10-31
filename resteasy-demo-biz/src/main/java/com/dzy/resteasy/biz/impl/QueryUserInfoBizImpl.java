package com.dzy.resteasy.biz.impl;

import com.dzy.resteasy.biz.QueryUserInfoBiz;
import com.dzy.resteasy.model.City;
import com.dzy.resteasy.model.UserInfo;
import com.dzy.resteasy.service.CityService;
import com.dzy.resteasy.service.UserInfoService;
import com.dzy.resteasy.support.result.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/9/25
 * @since 1.0
 */
@Component
public class QueryUserInfoBizImpl implements QueryUserInfoBiz {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private CityService cityService;
    @Autowired
    private CityBiz cityBiz;

    private void createUserInfo(String version){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("xiaoming"+version);
        userInfo.setPwd(UUID.randomUUID().toString());
        userInfo.setCityId(1L);
        userInfo.setIsStudent(1);
        userInfoService.insertSelective(userInfo);
        logger.error("========== s=此时 dzy_UserInfo成功插入id为====================={}",userInfo.getId());
    }

    private void insertUserCityIntenal(){
        City maxCity = cityService.selectMaxCity();
        for (int i=maxCity.getId()+1;i<maxCity.getId()+11;i++){
            City city = new City();
            city.setName("south NanJin Road"+i);
            city.setState("shanghai"+i);
            city.setCountry("china"+i);
            cityService.addOneCity(city);
            logger.info("=================此时成功插入state的 id为========{}",i);
            if (i==maxCity.getId()+4){ //maxIdle的第四个抛异常
                logger.error("===========some error happened 此时失败state的id为====================={}",i);
                throw new RuntimeException("测试~");
            }
        }
    }


    //#################################################################################

    @Transactional
    @Override
    public BaseResult<Integer> insertUserInfoV1(String version) {
        createUserInfo(version);
        try {
            insertUserCityV1();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BaseResult<>();
    }

    private void insertUserCityV1() throws Exception{
        insertUserCityIntenal();
    }

    //#################################################################################

    @Transactional
    @Override
    public BaseResult<Integer> insertUserInfoV2(String version) throws Exception {
        createUserInfo(version);
        insertUserCityV2();
        return new BaseResult<>();
    }

    public void insertUserCityV2() throws Exception{
        insertUserCityIntenal();
    }


    //#################################################################################

    @Transactional
    @Override
    public BaseResult<Integer> insertUserInfoV3(String version) {
        createUserInfo(version);
        try {
            insertUserCityV3();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BaseResult<>();
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void insertUserCityV3(){
        insertUserCityIntenal();
    }

    //#################################################################################
    @Transactional
    @Override
    public BaseResult<Integer> insertUserInfoV4(String version) {
        createUserInfo(version);
        insertUserCityV4();
        return new BaseResult<>();
    }


    @Transactional(propagation = Propagation.NESTED)
    public void insertUserCityV4(){
        insertUserCityIntenal();
    }

    //##################################################################################

    @Transactional
    @Override
    public BaseResult<Integer> insertUserInfoV5(String version) {
        createUserInfo(version);
        City maxCity = cityService.selectMaxCity();
        for (int i=maxCity.getId()+1;i<maxCity.getId()+11;i++){
            addOneCityV5(i,maxCity.getId());
        }
        return new BaseResult<>();
    }

    @Transactional(propagation = Propagation.NESTED)
    public void addOneCityV5(Integer i,Integer maxId){
        City city = new City();
        city.setName("south NanJin Road"+i);
        city.setState("shanghai"+i);
        city.setCountry("china"+i);
        cityService.addOneCity(city);
        logger.info("=================此时成功插入state的 id为========{}",i);
        if (i==maxId+4){ //maxIdle的第四个抛异常
            logger.error("===========发生一些异常此时失败state的id为====================={}",i);
            throw new RuntimeException("测试~");
        }
    }

    //##################################################################################

    @Transactional
    @Override
    public BaseResult<Integer> insertUserInfoV6(String version) {
        createUserInfo(version);
        City maxCity = cityService.selectMaxCity();
        for (int i=maxCity.getId()+1;i<maxCity.getId()+11;i++){
            try {
                cityBiz.addOneCityV6(i,maxCity.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new BaseResult<>();
    }


    //##################################################################################


    @Transactional
    public BaseResult<Integer> insertUserInfoV7(String version) {
        createUserInfo(version);
        City maxCity = cityService.selectMaxCity();
        for (int i=maxCity.getId()+1;i<maxCity.getId()+11;i++){
            try {
                cityBiz.addOneCityV7(i,maxCity.getId());//V7上面没有另开事务
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new BaseResult<>();
    }

    //################################### V8 ############################################


    public BaseResult<Integer> insertUserInfoV8(String version) {
        cityBiz.createUserInfoAndFullCityV8(version);
        return new BaseResult<>();
    }


    //################################### V9 ############################################

    public BaseResult<Integer> insertUserInfoV9(String version){
        cityBiz.createUserInfoAndFullCityV9(version);
        return new BaseResult<>();
    }



    //################################### V10 ############################################


}
