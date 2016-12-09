package com.wang.rabbitmq;

import com.wang.rabbitmq.model.ExchangeType;
import com.wang.rabbitmq.receiver.MessageReceiver;

import java.io.IOException;

/**
 * 主题消息接收
 */
public abstract class TopicMessageReceiver extends MessageReceiver {
    /**
     * 主题方式接收队列
     *
     * @param queue_name    队列名
     * @param exchange_name 交换机名
     * @param routing_key   路由关键字 （* 代表一个单词，#代表多个单词 例：message.*）
     */
    protected TopicMessageReceiver(String queue_name, String exchange_name, String routing_key) throws IOException {
        super(queue_name, exchange_name, routing_key, ExchangeType.TOPIC);
    }

}
