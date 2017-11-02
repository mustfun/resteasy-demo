package com.dzy.resteasy.mq;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/11/2
 * @since 1.0
 */
@ConfigurationProperties(prefix = "rocketmq.producer")
public class MqProducerConfig {

    private String groupName;
    private String namesrvAddr;
    private String instanceName;
    private String topic;
    private String tag;
    private Integer maxMessageSize;
    private Integer sendMsgTimeout;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
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

    public Integer getMaxMessageSize() {
        return maxMessageSize;
    }

    public void setMaxMessageSize(Integer maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
    }

    public Integer getSendMsgTimeout() {
        return sendMsgTimeout;
    }

    public void setSendMsgTimeout(Integer sendMsgTimeout) {
        this.sendMsgTimeout = sendMsgTimeout;
    }
}
