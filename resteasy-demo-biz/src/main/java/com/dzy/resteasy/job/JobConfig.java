package com.dzy.resteasy.job;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.dzy.resteasy.support.config.zk.ZkRegistryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/10/31
 * @since 1.0
 */
@Configuration
public class JobConfig {

    @Autowired
    private ZkRegistryConfig zkRegistryConfig;

    @Autowired
    private AddCityJob addCityJob;

    @Bean(initMethod = "init")
    public JobScheduler rmaRetryJobScheduler(){
        // 定义作业核心配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder("AddCityJob", "0/15 * * * * ?", 1).build();
        // 定义SIMPLE类型配置
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, addCityJob.getClass().getCanonicalName());
        // 定义Lite作业根配置,overwrite=true,允许覆盖客户端费用
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).overwrite(true).build();


        //初始化zk
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration(zkRegistryConfig.getZkUrl(), "elastic-job-itar"));
        regCenter.init();

        //为了跟spring兼容，new一个SpringJobScheduler
        return new SpringJobScheduler(addCityJob,regCenter, simpleJobRootConfig);
    }

}
