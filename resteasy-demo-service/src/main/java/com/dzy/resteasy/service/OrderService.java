package com.dzy.resteasy.service;

import com.dzy.resteasy.model.Order;

import java.util.List;

/**
 * Created by dengzhiyuan on 2017/4/6.
 */
public interface OrderService {
    Order getOne(Integer id);

    List<Order> selectByIdList(List<Integer> ids);

}
