package com.dzy.resteasy.biz.impl;

import com.dzy.resteasy.model.City;
import com.dzy.resteasy.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/9/28
 * @since 1.0
 */
@Component
public class SingleCityBiz {

    private static final Logger logger = LoggerFactory.getLogger(SingleCityBiz.class);

    @Autowired
    private CityService cityService;

    //加入到当前事务里面
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void addOneCityV9(Integer i,Integer maxId){
        City city = new City();
        city.setName("south NanJin Road"+i);
        city.setState("shanghai"+i);
        city.setCountry("china"+i);
        cityService.addOneCity(city);
        logger.info("=========error show========此时成功插入state的 id为========{}",i);
        if (i==maxId+4){ //maxIdle的第四个抛异常
            logger.error("===========此时失败state的id为====================={}",i);
            throw new RuntimeException("测试~");
        }

    }
}
