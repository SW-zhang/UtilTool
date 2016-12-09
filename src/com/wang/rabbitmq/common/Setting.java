package com.wang.rabbitmq.common;

public class Setting {
    boolean durable; //持久化设置

    public Setting() {
    }

    public Setting(boolean durable) {
        this.durable = durable;
    }

    public boolean isDurable() {
        return durable;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }
}
