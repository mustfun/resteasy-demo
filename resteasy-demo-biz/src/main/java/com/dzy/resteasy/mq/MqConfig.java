package com.dzy.resteasy.mq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.dzy.resteasy.mq.listener.Mix2MessageListener;
import com.dzy.resteasy.support.config.exception.RocketMQException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/11/2
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties({MqProducerConfig.class,MqConsumerConfig.class})
public class MqConfig {

    public static final Logger LOGGER = LoggerFactory.getLogger(MqConfig.class);

    @Autowired
    private Mix2MessageListener messageListener;

    @Bean
    public DefaultMQProducer getRocketMQProducer(MqProducerConfig producerConfig) throws RocketMQException {
        if (StringUtils.isBlank(producerConfig.getGroupName())) {
            throw new RocketMQException("groupName is blank");
        }
        if (StringUtils.isBlank(producerConfig.getNamesrvAddr())) {
            throw new RocketMQException("nameServerAddr is blank");
        }
        if (StringUtils.isBlank(producerConfig.getInstanceName())){
            throw new RocketMQException("instanceName is blank");
        }
        DefaultMQProducer producer;
        producer = new DefaultMQProducer(producerConfig.getGroupName());
        producer.setNamesrvAddr(producerConfig.getNamesrvAddr());
        producer.setInstanceName(producerConfig.getInstanceName());
        producer.setMaxMessageSize(producerConfig.getMaxMessageSize());
        producer.setSendMsgTimeout(producerConfig.getSendMsgTimeout());
        try {
            producer.start();
            LOGGER.info(String.format("producer is start ! groupName:[%s],namesrvAddr:[%s]"
                    , producerConfig.getGroupName(), producerConfig.getNamesrvAddr()));
        } catch (MQClientException e) {
            LOGGER.error("producer is error {},{}",e.getMessage(),e);
            throw new RocketMQException(e);
        }
        return producer;
    }


    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer(MqConsumerConfig consumerConfig) throws RocketMQException {
        if (StringUtils.isBlank(consumerConfig.getGroupName())){
            throw new RocketMQException("groupName is null !!!");
        }
        if (StringUtils.isBlank(consumerConfig.getNamesrvAddr())){
            throw new RocketMQException("namesrvAddr is null !!!");
        }
        if (StringUtils.isBlank(consumerConfig.getTopic())){
            throw new RocketMQException("topic is null !!!");
        }
        if (StringUtils.isBlank(consumerConfig.getTag())){
            throw new RocketMQException("tag is null !!!");
        }
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerConfig.getGroupName());
        consumer.setNamesrvAddr(consumerConfig.getNamesrvAddr());
        consumer.setConsumeThreadMin(consumerConfig.getConsumeThreadMin());
        consumer.setConsumeThreadMax(consumerConfig.getConsumeThreadMax());
        consumer.registerMessageListener(messageListener);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        try {
            consumer.subscribe(consumerConfig.getTopic(),consumerConfig.getTag());
            consumer.start();
            LOGGER.info("consumer is start !!! groupName:{},topic:{},namesrvAddr:{}",consumerConfig.getGroupName(),consumerConfig.getTopic(),consumerConfig.getNamesrvAddr());
        }catch (MQClientException e){
            LOGGER.error("consumer is start !!! groupName:{},topic:{},namesrvAddr:{}",consumerConfig.getGroupName(),consumerConfig.getTopic(),consumerConfig.getNamesrvAddr(),e);
            throw new RocketMQException(e);
        }
        return consumer;
    }
}
