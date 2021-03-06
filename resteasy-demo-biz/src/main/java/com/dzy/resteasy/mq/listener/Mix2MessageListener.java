package com.dzy.resteasy.mq.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.listener.*;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/11/2
 * @since 1.0
 * @function 小米mix2的一个messageListener,测试
 */
@Component
public class Mix2MessageListener implements MessageListenerConcurrently {

    public static final Logger LOGGER = LoggerFactory.getLogger(Mix2MessageListener.class);

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        LOGGER.info(Thread.currentThread().getName() + " Receive New mix2 Messages: " + msgs);
        for (MessageExt msg : msgs) {
            String m = new String(msg.getBody());
            LOGGER.info("message queueId is {}, and content = {}",msg.getQueueId(),m);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
