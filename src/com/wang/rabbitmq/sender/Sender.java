package com.wang.rabbitmq.sender;

import java.io.IOException;


public interface Sender {
    public void send() throws IOException;
}
