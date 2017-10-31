package com.dzy.resteasy.biz;

import com.dzy.resteasy.support.result.BaseResult;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/9/25
 * @since 1.0
 */
public interface QueryUserInfoBiz {

    BaseResult<Integer> insertUserInfoV1(String version);

    BaseResult<Integer> insertUserInfoV2(String version) throws Exception;

    BaseResult<Integer> insertUserInfoV3(String version);

    BaseResult<Integer> insertUserInfoV4(String version);

    BaseResult<Integer> insertUserInfoV5(String version);

    BaseResult<Integer> insertUserInfoV6(String version);
    BaseResult<Integer> insertUserInfoV7(String version);
    BaseResult<Integer> insertUserInfoV8(String version);
    BaseResult<Integer> insertUserInfoV9(String version);
}
