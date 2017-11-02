package com.dzy.resteasy.mq;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/11/2
 * @since 1.0
 */
@ConfigurationProperties(prefix = "rocketmq.consumer")
public class MqConsumerConfig {

    private String namesrvAddr;

    private String groupName;

    private String topic;
    private String tag;
    private Integer consumeThreadMin;

    private Integer consumeThreadMax;

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getConsumeThreadMin() {
        return consumeThreadMin;
    }

    public void setConsumeThreadMin(Integer consumeThreadMin) {
        this.consumeThreadMin = consumeThreadMin;
    }

    public Integer getConsumeThreadMax() {
        return consumeThreadMax;
    }

    public void setConsumeThreadMax(Integer consumeThreadMax) {
        this.consumeThreadMax = consumeThreadMax;
    }
}
