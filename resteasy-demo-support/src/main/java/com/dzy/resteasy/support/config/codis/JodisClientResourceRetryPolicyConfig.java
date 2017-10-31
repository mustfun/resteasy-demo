package com.dzy.resteasy.support.config.codis;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/8/30
 * @since 1.0
 */
@ConfigurationProperties(prefix = "codis.policy")
public class JodisClientResourceRetryPolicyConfig {

    /**
     * 重试次数
     */
    private int retryTimes;

    /**
     * 重试间隔时间(ms)
     */
    private int retryIntervalInMs;

    /**
     * 重试间隔是否线性增加
     */
    private boolean retryIntervalIncreasable;

    /**
     * Instantiates a new Jodis client resource retry policy config.
     */
    public JodisClientResourceRetryPolicyConfig(){
        retryTimes = 3;
        retryIntervalInMs = 3000;
        retryIntervalIncreasable = true;
    }


    /**
     * Sets new 重试间隔时间ms.
     *
     * @param retryIntervalInMs New value of 重试间隔时间ms.
     */
    public void setRetryIntervalInMs(int retryIntervalInMs) {
        this.retryIntervalInMs = retryIntervalInMs;
    }

    /**
     * Gets 重试次数.
     *
     * @return Value of 重试次数.
     */
    public int getRetryTimes() {
        return retryTimes;
    }

    /**
     * Sets new 重试次数.
     *
     * @param retryTimes New value of 重试次数.
     */
    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    /**
     * Gets 重试间隔时间ms.
     *
     * @return Value of 重试间隔时间ms.
     */
    public int getRetryIntervalInMs() {
        return retryIntervalInMs;
    }

    /**
     * Sets new 重试间隔是否线性增加.
     *
     * @param retryIntervalIncreasable New value of 重试间隔是否线性增加.
     */
    public void setRetryIntervalIncreasable(boolean retryIntervalIncreasable) {
        this.retryIntervalIncreasable = retryIntervalIncreasable;
    }

    /**
     * Gets 重试间隔是否线性增加.
     *
     * @return Value of 重试间隔是否线性增加.
     */
    public boolean isRetryIntervalIncreasable() {
        return retryIntervalIncreasable;
    }
}

