package com.wang.rabbitmq.sender;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.wang.rabbitmq.common.RabbitMQConnection;
import com.wang.utils.encrypt.Base64;

import java.io.IOException;

/**
 * 生产者（生产消息放入队列）
 */
public class MessageSender implements Sender {

    private String exchange_name;
    private String exchange_type;
    private String message;

    private String routing_key;// 当topic 或者 direct 模式时必传，如果不需要此字段请传null

    /**
     * @param exchange_name 交换机名字
     * @param exchange_type 交换机类型
     * @param routing_key   路由关键字（用于 direct 或 topic 模式）
     * @param message       消息字符串
     */
    public MessageSender(String exchange_name, String exchange_type, String routing_key, String message) {
        this.exchange_name = exchange_name;
        this.exchange_type = exchange_type;
        this.routing_key = routing_key;
        this.message = message;
    }

    @Override
    public void send() throws IOException {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = RabbitMQConnection.getConnection();
            channel = RabbitMQConnection.getChannel(connection);

            if (exchange_name != null) {
                //声明交换机
                channel.exchangeDeclare(exchange_name, exchange_type,
                        RabbitMQConnection.defaultSetting.isDurable(), false, null);
            } else {
                channel.queueDeclare(routing_key, RabbitMQConnection.defaultSetting.isDurable(), false, false, null);
            }

            //发送消息，并持久化消息【 MessageProperties.PERSISTENT_TEXT_PLAIN】
            String base64 = Base64.encode(message.getBytes("utf-8"));
            channel.basicPublish(exchange_name == null ? "" : exchange_name,
                    routing_key == null ? "" : routing_key,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    base64.getBytes());

        } catch (IOException e) {
            throw e;
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
