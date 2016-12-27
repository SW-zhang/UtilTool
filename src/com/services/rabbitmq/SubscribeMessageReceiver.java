package com.services.rabbitmq;

import com.services.rabbitmq.model.ExchangeType;
import com.services.rabbitmq.receiver.MessageReceiver;

import java.io.IOException;

/**
 * 广播消息接收
 */
public abstract class SubscribeMessageReceiver extends MessageReceiver {
    /**
     * 广播方式接收队列
     *
     * @param queue_name    队列名
     * @param exchange_name 交换机名
     */
    protected SubscribeMessageReceiver(String queue_name, String exchange_name) throws IOException {
        super(queue_name, exchange_name, null, ExchangeType.FANOUT);
    }

}
