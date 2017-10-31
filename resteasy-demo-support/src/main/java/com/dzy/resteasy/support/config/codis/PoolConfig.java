package com.dzy.resteasy.support.config.codis;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/8/30
 * @since 1.0
 */
@ConfigurationProperties(prefix = "codis.pool")
public class PoolConfig {

    /**
     * 控制一个pool最多有多少个状态为"空闲"的jodis实例
     */
    private int maxIdle;


    /**
     * 控制一个pool最少有多少个状态为"空闲"的jodis实例
     */
    private int minIdle;

    /**
     * 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取
     */
    private int maxTotal;

    /**
     * The Connection timeout ms.
     */
    private int connectionTimeoutMs;

    /**
     * The So timeout ms.
     */
    private int soTimeoutMs;

    /**
     * The password
     */
    private String password;


    /**
     * Instantiates a new  pool config.
     */
    public PoolConfig(){
        maxIdle = 80;
        minIdle = 8;
        maxTotal = 512;
    }


    /**
     * Sets new 控制一个pool最少有多少个状态为"空闲"的jodis实例.
     *
     * @param minIdle New value of 控制一个pool最少有多少个状态为"空闲"的jodis实例.
     */
    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    /**
     * Sets new 控制一个pool最多有多少个状态为"空闲"的jodis实例.
     *
     * @param maxIdle New value of 控制一个pool最多有多少个状态为"空闲"的jodis实例.
     */
    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    /**
     * Gets 控制一个pool可分配多少个jedis实例，通过pool.getResource来获取.
     *
     * @return Value of 控制一个pool可分配多少个jedis实例，通过pool.getResource来获取.
     */
    public int getMaxTotal() {
        return maxTotal;
    }

    /**
     * Gets 控制一个pool最少有多少个状态为"空闲"的jodis实例.
     *
     * @return Value of 控制一个pool最少有多少个状态为"空闲"的jodis实例.
     */
    public int getMinIdle() {
        return minIdle;
    }

    /**
     * Sets new 控制一个pool可分配多少个jedis实例，通过pool.getResource来获取.
     *
     * @param maxTotal New value of 控制一个pool可分配多少个jedis实例，通过pool.getResource来获取.
     */
    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    /**
     * Gets 控制一个pool最多有多少个状态为"空闲"的jodis实例.
     *
     * @return Value of 控制一个pool最多有多少个状态为"空闲"的jodis实例.
     */
    public int getMaxIdle() {
        return maxIdle;
    }


    /**
     * Gets The password.
     *
     * @return Value of The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets The Connection timeout ms..
     *
     * @return Value of The Connection timeout ms..
     */
    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    /**
     * Sets new The Connection timeout ms..
     *
     * @param connectionTimeoutMs New value of The Connection timeout ms..
     */
    public void setConnectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    /**
     * Sets new The So timeout ms..
     *
     * @param soTimeoutMs New value of The So timeout ms..
     */
    public void setSoTimeoutMs(int soTimeoutMs) {
        this.soTimeoutMs = soTimeoutMs;
    }

    /**
     * Sets new The password.
     *
     * @param password New value of The password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets The So timeout ms..
     *
     * @return Value of The So timeout ms..
     */
    public int getSoTimeoutMs() {
        return soTimeoutMs;
    }
}
