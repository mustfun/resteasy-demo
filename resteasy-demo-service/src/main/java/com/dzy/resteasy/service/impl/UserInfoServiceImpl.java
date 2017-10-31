package com.dzy.resteasy.service.impl;

import com.dzy.resteasy.dao.mapper.UserInfoMapper;
import com.dzy.resteasy.model.UserInfo;
import com.dzy.resteasy.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/9/25
 * @since 1.0
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public Integer insertSelective(UserInfo userInfo) {
        return userInfoMapper.insertSelective(userInfo);
    }
}
