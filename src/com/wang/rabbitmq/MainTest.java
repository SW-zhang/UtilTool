package com.wang.rabbitmq;

import com.wang.rabbitmq.receiver.Receiver;

import java.io.IOException;

/**
 * Created by wang on 2016/12/7.
 */
public class MainTest {
    public static void main(String[] args) throws IOException, InterruptedException {
//        for (int i = 10; i < 20; i++) {
//            SenderMsgUtil.sendMsg("hello world" + i);
//        }
        default_receiver();
//        subscribe();
//        routingKey();
//        topic2();
    }

    public static void default_receiver() throws IOException, InterruptedException {
        Receiver receiver = new DefaultMessageReceiver() {

            @Override
            protected void handle(String message) {
                System.out.println(message);
            }
        };
        while (true) {
            ReceiverMsgUtil.recvQueueMsg(receiver);
        }
    }

    public static void subscribe() throws IOException, InterruptedException {
        Receiver receiver = new SubscribeMessageReceiver("Subscribe_queue_001", "exchange_demo001") {

            @Override
            protected void handle(String message) {
                System.out.println(message);
            }
        };
        while (true) {
            ReceiverMsgUtil.recvQueueMsg(receiver);
        }
    }

    public static void routingKey() throws IOException, InterruptedException {
        Receiver receiver = new DirectMessageReceiver("queue_002", "exchange_demo002", "info") {

            @Override
            protected void handle(String message) {
                System.out.println(message);
            }
        };
        while (true) {
            ReceiverMsgUtil.recvQueueMsg(receiver);
        }
    }

    public static void topic() throws IOException, InterruptedException {
        Receiver receiver = new TopicMessageReceiver("queue_004", "exchange_demo003", "info.*") {

            @Override
            protected void handle(String message) {
                System.out.println(message);
            }
        };
        while (true) {
            ReceiverMsgUtil.recvQueueMsg(receiver);
        }
    }

    public static void topic2() throws IOException, InterruptedException {
        Receiver receiver = new TopicMessageReceiver("queue_005", "exchange_demo003", "*.error") {

            @Override
            protected void handle(String message) {
                System.out.println(message);
            }
        };
        while (true) {
            ReceiverMsgUtil.recvQueueMsg(receiver);
        }
    }
}
