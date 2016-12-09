package com.wang.rabbitmq;

import com.wang.rabbitmq.receiver.Receiver;
import com.wang.rabbitmq.sender.Sender;

import java.io.IOException;

public class ReceiverMsgUtil {
    protected static boolean sendQueueMsg(Sender sender) throws IOException {
        sender.send();
        return true;
    }

    public static boolean recvQueueMsg(Receiver receiver) throws IOException, InterruptedException {
        receiver.recv();
        return true;
    }
}
