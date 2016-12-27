package com.services.utils.log;

import org.apache.log4j.Logger;

/**
 * 基础日志类
 */
public class Console {

    /**
     * 控制台输出消息
     *
     * @param obj
     * @param msg
     */
    public synchronized static void info(Object obj, String msg) {
        Logger.getLogger("console").info(key(obj) + ": " + msg);
    }


    /**
     * 控制台输出调试信息
     *
     * @param obj
     * @param msg
     */
    public synchronized static void debug(Object obj, String msg) {
        Logger.getLogger("console").info(key(obj) + ": " + msg);
    }

    /**
     * 控制台输出错误消息
     *
     * @param obj
     * @param msg
     * @param e
     */
    public synchronized static void error(Object obj, String msg, Throwable e) {
        Logger.getLogger("console").error(key(obj) + ": " + msg + "; Err: " + e, e);
    }

    private static String key(Object obj) {
        String key;
        if (null == obj) {
            key = "";
        } else if (obj instanceof String) {
            key = (String) obj;
        } else if (obj instanceof Class) {
            key = ((Class<?>) obj).getSimpleName();
        } else {
            key = obj.getClass().getSimpleName();
        }
        return key;
    }

}
