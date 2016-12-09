package com.wang.rabbitmq.common;

import com.wang.utils.properties.ReadProperties;


public class RabbitMQConfig {

    public static String path = "rabbitmqconf.properties";

    static String host() {
        return ReadProperties.getValue(path, "rabbitmq.host");
    }

    static int port() {
        return Integer.parseInt(ReadProperties.getValue(path, "rabbitmq.port"));
    }

    static String rabbitmq_user() {
        return ReadProperties.getValue(path, "rabbitmq.user");
    }

    static String rabbitmq_pwd() {
        return ReadProperties.getValue(path, "rabbitmq.password");
    }
}
