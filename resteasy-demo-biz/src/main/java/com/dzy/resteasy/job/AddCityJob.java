package com.dzy.resteasy.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dzy.resteasy.biz.QueryUserInfoBiz;
import com.dzy.resteasy.biz.impl.QueryUserInfoBizImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/10/31
 * @since 1.0
 */
@Component
public class AddCityJob implements SimpleJob {

    public static final Logger LOG = LoggerFactory.getLogger(AddCityJob.class);

    @Resource
    private QueryUserInfoBiz queryUserInfoBiz;

    @Override
    public void execute(ShardingContext shardingContext) {
        LOG.info("===============>  job start");
        //queryUserInfoBiz.insertUserInfoV6("v6");
    }
}
