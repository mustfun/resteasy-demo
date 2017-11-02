package com.dzy.resteasy.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
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
public class Mix2StoreJob implements SimpleJob {

    public static final Logger LOG = LoggerFactory.getLogger(Mix2StoreJob.class);

    @Autowired
    private DefaultMQProducer defaultMQProducer;
    @Autowired
    private MqProducerConfig mqProducerConfig;

    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            Message msg = new Message(mqProducerConfig.getTopic(),// topic
                    mqProducerConfig.getTag(),// tag
                    "mix2 sale development",//key用于标识业务的唯一性
                    ("Mix2 add new store~" ).getBytes(RemotingHelper.DEFAULT_CHARSET)// body 二进制字节数组
            );
            SendResult result = defaultMQProducer.send(msg);
            LOG.info("send message status {}", JSON.toJSONString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
