package com.wang.rabbitmq.receiver;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.wang.rabbitmq.common.RabbitMQConnection;
import com.wang.utils.encrypt.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 消费者（消费消息）
 * 本类不能直接使用 需要使用子类调用
 */
public abstract class MessageReceiver implements Receiver {

    private static final Logger log = LoggerFactory.getLogger(MessageReceiver.class);

    private String queue_name;
    private String exchange_name;
    private String exchange_type;
    private String routing_key;

    private Connection connection;
    private Channel channel;
    private QueueingConsumer consumer;

    protected MessageReceiver(String queue_name, String exchange_name, String routing_key, String exchange_type) throws IOException {
        this.queue_name = queue_name;
        this.exchange_name = exchange_name;
        this.routing_key = routing_key;
        this.exchange_type = exchange_type;
        init();
    }

    private void init() throws IOException {
        connection = RabbitMQConnection.getConnection();
        channel = RabbitMQConnection.getChannel(connection);

        channel.basicQos(1);//每次接收的数量
        channel.queueDeclare(queue_name, RabbitMQConnection.defaultSetting.isDurable(), false, false, null);
        if (exchange_name != null) {
            channel.exchangeDeclare(exchange_name, exchange_type, RabbitMQConnection.defaultSetting.isDurable(), false, false, null);
            channel.queueBind(queue_name, exchange_name, routing_key == null ? "" : routing_key, null);
        }

        consumer = new QueueingConsumer(channel);
        boolean ack = false; // 打开应答机制
        channel.basicConsume(queue_name, ack, consumer);
    }

    @Override
    public void recv() {
        QueueingConsumer.Delivery delivery = null;
        try {
            delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            log.info("body:" + message);
            //处理消息
            message = new String(Base64.decode(message), "utf-8");
            handle(message);

            //发送应答
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

        } catch (Exception e) {
            if (delivery != null) {
                try {
                    channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
                } catch (IOException e1) {
                    log.error("回退消息异常：" + e1.getMessage());
                }
            }
            log.error("接收消息异常：" + e.getMessage());
        }
    }

    //需要实现消息处理
    protected abstract void handle(String message);

}
