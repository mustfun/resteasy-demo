package com.dzy.resteasy.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.dzy.resteasy.model.UserInfo;
import com.dzy.resteasy.mq.MqProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/11/2
 * @since 1.0
 */
@Component
public class MqBiz {

    private static final Logger LOG = LoggerFactory.getLogger(MqBiz.class);

    @Autowired
    private DefaultMQProducer defaultMQProducer;
    @Autowired
    private MqProducerConfig mqProducerConfig;

    /**
     * 发送小米被抢购了的消息
     */
    public void sendXiaoMiBeBoughtMessage(UserInfo userInfo){
        try {
            Message msg = new Message(mqProducerConfig.getTopic(),// topic
                    mqProducerConfig.getTag(),// tag
                    "mix2 sale development",//key用于标识业务的唯一性
                    (JSON.toJSONString(userInfo)).getBytes(RemotingHelper.DEFAULT_CHARSET)// body 二进制字节数组
            );
            SendResult result = defaultMQProducer.send(msg);
            LOG.info("send message status {}", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
