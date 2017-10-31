package com.dzy.resteasy.service;

import com.dzy.resteasy.model.UserInfo;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/9/25
 * @since 1.0
 */
public interface UserInfoService {

    Integer insertSelective(UserInfo userInfo);
}
