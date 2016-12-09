package com.wang.rabbitmq;

import com.wang.rabbitmq.common.Consts;
import com.wang.rabbitmq.exception.MessageParameterErrorException;
import com.wang.rabbitmq.model.ExchangeType;
import com.wang.rabbitmq.sender.MessageSender;

import java.io.IOException;

public class SenderMsgUtil {

    /***
     * 普通消息
     * (注意：使用默认的队列 和 默认的交换机)
     *
     * @param message 消息
     * @return
     * @throws MessageParameterErrorException {参数都不能为空}
     * @throws IOException                    {网络异常，发不出消息}
     */
    public static boolean sendMsg(String message) throws MessageParameterErrorException, IOException {
        if (message == null) {
            throw new MessageParameterErrorException("message is null.");
        }
        return sendMsg(Consts.default_queue_name, message);
    }

    /***
     * 普通消息
     * (注意：使用默认的交换机)
     *
     * @param queue_name 队列名称
     * @param message    消息
     * @return
     * @throws MessageParameterErrorException {参数都不能为空}
     * @throws IOException                    {网络异常，发不出消息}
     */
    public static boolean sendMsg(String queue_name, String message) throws MessageParameterErrorException, IOException {
        if (queue_name == null) {
            throw new MessageParameterErrorException("queue_name is null.");
        }
        if (message == null) {
            throw new MessageParameterErrorException("message is null.");
        }
        return ReceiverMsgUtil.sendQueueMsg(new MessageSender(null, null, queue_name, message));
    }

    /***
     * 广播消息（注意：广播的消息会发送给跟传入的交换机绑定的队列，如果该交换机没有绑定队列，会造成消息丢失。
     * 建议：先开启消费者进程声明队列并绑定此交换机，然后在调用此方法广播消息）
     *
     * @param exchange_name 交换机名称
     * @param message       消息
     * @return
     * @throws MessageParameterErrorException {参数都不能为空}
     * @throws IOException                    {网络异常，发不出消息}
     */
    public static boolean sendSubscribeMsg(String exchange_name, String message) throws MessageParameterErrorException, IOException {
        if (exchange_name == null) {
            throw new MessageParameterErrorException("exchange_name is null.");
        }
        if (message == null) {
            throw new MessageParameterErrorException("message is null.");
        }
        return ReceiverMsgUtil.sendQueueMsg(new MessageSender(exchange_name, ExchangeType.FANOUT, null, message));
    }

    /***
     * 按routing_key给队列发送消息
     * （注意：消息会发送给跟传入的交换机绑定的队列，如果该交换机没有绑定队列，会造成消息丢失。
     * 建议：先开启消费者进程声明队列并绑定此交换机，然后在调用此方法发送消息）
     *
     * @param exchange_name 交换机名称
     * @param routing_key   路由关键字(发送给绑定了本交换机并且routing_key等于传入的参数的队列)
     * @param message       消息
     * @return
     * @throws MessageParameterErrorException {参数都不能为空}
     * @throws IOException                    {网络异常，发不出消息}
     */
    public static boolean sendDirectMsg(String exchange_name, String routing_key, String message) throws MessageParameterErrorException, IOException {
        if (exchange_name == null) {
            throw new MessageParameterErrorException("exchange_name is null.");
        }
        if (routing_key == null) {
            throw new MessageParameterErrorException("routing_key is null.");
        }
        if (message == null) {
            throw new MessageParameterErrorException("message is null.");
        }
        return ReceiverMsgUtil.sendQueueMsg(new MessageSender(exchange_name, ExchangeType.DIRECT, routing_key, message));
    }

    /***
     * 按字符串匹配的方式发送消息
     * （注意：消息会发送给跟传入的交换机绑定的队列，如果该交换机没有绑定队列，会造成消息丢失。
     * 建议：先开启消费者进程声明队列并绑定此交换机，然后在调用此方法广播消息）
     *
     * @param exchange_name 交换机名称
     * @param routing_key   路由关键字（接收队列规则：*代表一个单词， # 代表0个或多个单词，例：rout.info）
     * @param message       消息
     * @return
     * @throws MessageParameterErrorException {参数都不能为空}
     * @throws IOException                    {网络异常，发不出消息}
     */
    public static boolean sendTopicMsg(String exchange_name, String routing_key, String message) throws MessageParameterErrorException, IOException {
        if (exchange_name == null) {
            throw new MessageParameterErrorException("exchange_name is null.");
        }
        if (routing_key == null) {
            throw new MessageParameterErrorException("routing_key is null.");
        }
        if (message == null) {
            throw new MessageParameterErrorException("message is null.");
        }
        return ReceiverMsgUtil.sendQueueMsg(new MessageSender(exchange_name, ExchangeType.TOPIC, routing_key, message));
    }

}
