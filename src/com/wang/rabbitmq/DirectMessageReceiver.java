package com.wang.rabbitmq;

import com.wang.rabbitmq.model.ExchangeType;
import com.wang.rabbitmq.receiver.MessageReceiver;

import java.io.IOException;

/**
 * 按照routing_key（路由关键字） 匹配的方式接收
 */
public abstract class DirectMessageReceiver extends MessageReceiver {
    /**
     * 按routing_key 匹配的方式接收消息
     *
     * @param queue_name    队列名
     * @param exchange_name 交换机名
     * @param routing_key   路由关键字
     */
    protected DirectMessageReceiver(String queue_name, String exchange_name, String routing_key) throws IOException {
        super(queue_name, exchange_name, routing_key, ExchangeType.DIRECT);
    }

}
