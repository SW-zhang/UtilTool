package com.services.rabbitmq.sender;

import java.io.IOException;


public interface Sender {
    public void send() throws IOException;
}
