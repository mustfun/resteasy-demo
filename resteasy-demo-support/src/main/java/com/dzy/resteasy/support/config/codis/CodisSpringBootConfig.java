package com.dzy.resteasy.support.config.codis;

import com.dzy.resteasy.support.config.zk.ZkRegistryConfig;
import io.codis.jodis.JedisResourcePool;
import io.codis.jodis.RoundRobinJedisPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import javax.ws.rs.Produces;
import java.lang.ref.WeakReference;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/8/30
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties({
        ZkRegistryConfig.class,
        JodisClientResourceRetryPolicyConfig.class,
        PoolConfig.class
})
public class CodisSpringBootConfig {

    @Autowired
    private ZkRegistryConfig zkRegistryConfig;

    @Autowired
    private JodisClientResourceRetryPolicyConfig jodisClientResourceRetryPolicyConfig;

    @Autowired
    private PoolConfig poolConfig;


    @Bean
    public Jedis jedis(){

        JedisResourcePool jedisPool = RoundRobinJedisPool.create()
                .curatorClient(zkRegistryConfig.getZkUrl(), zkRegistryConfig.getZkSessionTimeoutInMs()).zkProxyDir(zkRegistryConfig.getCodisProxyPath())
                .poolConfig(jedisPoolConfig())
                .build();

        return jedisPool.getResource();
    }



    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(poolConfig.getMinIdle());
        jedisPoolConfig.setMaxIdle(poolConfig.getMaxIdle());
        jedisPoolConfig.setMaxTotal(poolConfig.getMaxTotal());
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTestWhileIdle(true);
        return jedisPoolConfig;
    }
}
