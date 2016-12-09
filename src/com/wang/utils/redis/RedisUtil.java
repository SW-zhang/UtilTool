package com.wang.utils.redis;

import com.wang.utils.redis.common.RedisBase;
import com.wang.utils.redis.objs.*;
import redis.clients.jedis.Jedis;

/**
 * redis工具类
 */
public class RedisUtil {
    public static Hash redis_hash = new Hash();
    public static Keys redis_key = new Keys();
    public static Lists redis_list = new Lists();
    public static Sets redis_set = new Sets();
    public static SortSet redis_sortSet = new SortSet();
    public static Strings redis_string = new Strings();

    /**
     * 设置过期时间
     *
     * @param key
     * @param seconds
     */
    public static void expire(String key, int seconds) {
        if (seconds <= 0) {
            return;
        }
        Jedis jedis = RedisBase.getJedis();
        jedis.expire(key, seconds);
        RedisBase.returnJedis(jedis);
    }

    /**
     * 设置默认过期时间
     *
     * @param key
     */
    public static void expire(String key) {
        expire(key, 60000);
    }

    /**
     * 关闭连接池 在项目关闭时调用此方法关闭连接池
     */
    public static void distory() {
        RedisBase.distory();
    }

    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    RedisUtil.redis_string.set("key_" + System.nanoTime(), "value_" + System.nanoTime());
//                }
//            }).start();
//        }
        RedisUtil.redis_key.delAll();
    }
}
