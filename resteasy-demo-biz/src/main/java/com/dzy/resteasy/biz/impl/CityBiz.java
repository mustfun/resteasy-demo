package com.dzy.resteasy.biz.impl;

import com.dzy.resteasy.biz.QueryUserInfoBiz;
import com.dzy.resteasy.model.City;
import com.dzy.resteasy.model.UserInfo;
import com.dzy.resteasy.service.CityService;
import com.dzy.resteasy.service.UserInfoService;
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
public class CityBiz {

    private static  final Logger logger = LoggerFactory.getLogger(CityBiz.class);

    @Autowired
    private CityService cityService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SingleCityBiz singleCityBiz;


    private void createUserInfo(String version){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("xiaoming"+version);
        userInfo.setPwd(UUID.randomUUID().toString());
        userInfo.setCityId(1L);
        userInfo.setIsStudent(1);
        userInfoService.insertSelective(userInfo);
        logger.error("===========此时dzy_UserInfo成功插入id为====================={}",userInfo.getId());
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public void addOneCityV6(Integer i,Integer maxId){
        City city = new City();
        city.setName("south NanJin Road"+i);
        city.setState("shanghai"+i);
        city.setCountry("china"+i);
        cityService.addOneCity(city);
        logger.info("=================此时成功插入state的 id为========{}",i);
        if (i==maxId+4){ //maxIdle的第四个抛异常
            logger.error("=========error==此时失败state的id为====================={}",i);
            throw new RuntimeException("测试~");
        }


    }

    public void addOneCityV7(Integer i,Integer maxId){
        City city = new City();
        city.setName("south NanJin Road"+i);
        city.setState("shanghai"+i);
        city.setCountry("china"+i);
        cityService.addOneCity(city);
        logger.info("============error===此时成功插入state的 id为========{}",i);
        if (i==maxId+4){ //maxIdle的第四个抛异常
            logger.error("===========此时失败state的id为====================={}",i);
            throw new RuntimeException("测试~");
        }


    }


    //#########################   V8 ###########################

    @Transactional(rollbackFor = Exception.class)
    public void createUserInfoAndFullCityV8(String version) {
        createUserInfo(version);
        City maxCity = cityService.selectMaxCity();
        for (int i=maxCity.getId()+1;i<maxCity.getId()+11;i++){
            try {
                addOneCityV8(i,maxCity.getId());//V7上面没有另开事务
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public void addOneCityV8(Integer i,Integer maxId){
        City city = new City();
        city.setName("south NanJin Road"+i);
        city.setState("shanghai"+i);
        city.setCountry("china"+i);
        cityService.addOneCity(city);
        logger.info("=========error happened========此时成功插入state的 id为========{}",i);
        if (i==maxId+4){ //maxIdle的第四个抛异常
            logger.error("===========此时失败state的id为====================={}",i);
            throw new RuntimeException("测试~");
        }

    }

    //#########################   V9 ###########################


    /**
     * 测试结果，不管createUserInfoAndFullCityV9加不加事务，都不会回滚
     * @param version
     */
    @Transactional(rollbackFor = Exception.class)
    public void createUserInfoAndFullCityV9(String version) {
        createUserInfo(version);
        City maxCity = cityService.selectMaxCity();
        for (int i=maxCity.getId()+1;i<maxCity.getId()+11;i++){
            try {
                singleCityBiz.addOneCityV9(i,maxCity.getId());//V9上面没有另开事务
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
