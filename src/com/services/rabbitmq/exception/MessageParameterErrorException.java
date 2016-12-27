package com.services.rabbitmq.exception;

public class MessageParameterErrorException extends RuntimeException {

    private static final long serialVersionUID = 948299741776433786L;

    public MessageParameterErrorException() {
        super();
    }

    public MessageParameterErrorException(String msg) {
        super(msg);
    }
}
