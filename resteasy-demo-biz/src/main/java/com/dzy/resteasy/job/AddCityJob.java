package com.dzy.resteasy.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.stereotype.Component;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/10/31
 * @since 1.0
 */
@Component
public class AddCityJob implements SimpleJob {



    @Override
    public void execute(ShardingContext shardingContext) {

    }
}
