package com.wang.test;

import com.wang.rabbitmq.DefaultMessageReceiver;
import com.wang.rabbitmq.ReceiverMsgUtil;
import com.wang.rabbitmq.receiver.Receiver;

import java.io.IOException;

/**
 * Created by wang on 2016/12/7.
 */
public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
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


}
