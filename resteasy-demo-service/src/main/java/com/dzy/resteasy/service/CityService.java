package com.dzy.resteasy.service;

import com.dzy.resteasy.model.City;

/**
 * Created by dengzhiyuan on 2017/4/6.
 */
public interface CityService {
    City getOne(Integer id);

    Integer addOneCity(City city);

    City saveAndGet(City city);

    City selectMaxCity();
}
