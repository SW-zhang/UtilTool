package com.wang.utils.signal;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public class SignalUtil {

    /**
     * kill 信号
     */
    public static final String SIGN_TERM = "TERM";
    /**
     * ctrl + c 信号
     */
    public static final String SIGN_INT = "INT";

    public static final void addSignalHandler(String[] signs, SignalHandler handler) {
        // 增加终止信号，通过kill可正常终止程序
        for (String sign : signs) {
            Signal.handle(new Signal(sign), handler);
        }
    }
}
