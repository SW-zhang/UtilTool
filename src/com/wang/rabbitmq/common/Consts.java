package com.wang.rabbitmq.common;

import com.wang.utils.encrypt.Base64;


public class Consts {

    /**
     * 默认队列名
     */
    public static String default_queue_name = Base64.encode("default_queueName".getBytes());
    /**
     * 默认队列名
     */
    public static String default_queue_name_routing_key = Base64.encode("default_queueName_routing_key".getBytes());
    /**
     * 默认队列名
     */
    public static String default_queue_name_subscribe = Base64.encode("default_queueName_subscribe".getBytes());
    /**
     * 默认队列名
     */
    public static String default_queue_name_topic = Base64.encode("default_queueName_topic".getBytes());

}
