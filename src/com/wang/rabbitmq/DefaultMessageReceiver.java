package com.wang.rabbitmq;

import com.wang.rabbitmq.common.Consts;
import com.wang.rabbitmq.receiver.MessageReceiver;

import java.io.IOException;

/**
 * 默认队列接收
 */
public abstract class DefaultMessageReceiver extends MessageReceiver {
    /**
     * 默认的按队列名接收消息
     *
     * @param queue_name 队列名
     */
    protected DefaultMessageReceiver(String queue_name) throws IOException {
        super(queue_name, null, null, null);
    }

    /**
     * 默认的按队列名接收消息
     */
    protected DefaultMessageReceiver() throws IOException {
        super(Consts.default_queue_name, null, null, null);
    }
}
