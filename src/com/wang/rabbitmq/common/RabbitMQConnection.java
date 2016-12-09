package com.wang.rabbitmq.common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class RabbitMQConnection {
    public final static Setting defaultSetting = new Setting(true);//持久化

    private final static ConnectionFactory factory = new ConnectionFactory();

    static {
        factory.setHost(RabbitMQConfig.host());
        factory.setPort(RabbitMQConfig.port());
        factory.setUsername(RabbitMQConfig.rabbitmq_user());
        factory.setPassword(RabbitMQConfig.rabbitmq_pwd());
    }

    public static Connection getConnection() throws IOException {
        return factory.newConnection();
    }

    public static Channel getChannel(Connection connection) throws IOException {
        return connection.createChannel();
    }

}
