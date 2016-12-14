package com.wang.utils.redis.common;

import com.wang.utils.properties.ReadProperties;
import com.wang.utils.type.IntegerUtil;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisBase {
    private static Logger logger = Logger.getLogger(RedisBase.class);
    private static String config_path = "redisconf.properties";

    private static String HOST;
    private static int PORT;
    private static int MAX_ACTIVE;// 最大连接数
    private static int MAX_IDLE;// 设置最大空闲数
    private static int MAX_WAIT;// 最大连接时间
    private static int TIMEOUT;// 超时时间

    private static String AUTH = "";// 密码(原始默认是没有密码)
    private static boolean BORROW = true;// 在borrow一个事例时是否提前进行validate操作
    private static JedisPool pool = null;

    /**
     * 初始化线程池
     */
    static {
        try {
            if (pool == null) {
                init();
                JedisPoolConfig config = new JedisPoolConfig();
                config.setMaxTotal(MAX_ACTIVE);
                config.setMaxIdle(MAX_IDLE);
                config.setMaxWaitMillis(MAX_WAIT);
                config.setTestOnBorrow(BORROW);
                pool = new JedisPool(config, HOST, PORT, TIMEOUT);
            }
        } catch (Exception e) {
            logger.info("初始化redis连接异常");
        }
    }

    private static void init() {
        HOST = ReadProperties.getValue(config_path, "HOST");
        PORT = IntegerUtil.parseInt(ReadProperties.getValue(config_path, "PORT"));
        MAX_ACTIVE = IntegerUtil.parseInt(ReadProperties.getValue(config_path, "MAX_ACTIVE"));
        MAX_IDLE = IntegerUtil.parseInt(ReadProperties.getValue(config_path, "MAX_IDLE"));
        MAX_WAIT = IntegerUtil.parseInt(ReadProperties.getValue(config_path, "MAX_WAIT"));
        TIMEOUT = IntegerUtil.parseInt(ReadProperties.getValue(config_path, "TIMEOUT"));
    }

    /**
     * 获取连接
     */
    public static Jedis getJedis() {
        try {
            if (pool != null) {
                return pool.getResource();
            } else {
                logger.info("连接池未被初始化");
                return null;
            }
        } catch (Exception e) {
            logger.info("连接池连接异常");
            return null;
        }

    }

    /**
     * 回收jedis(放到finally中)
     *
     * @param jedis
     */
    public static void returnJedis(Jedis jedis) {
        if (null != jedis) {
            jedis.close();
//            pool.returnResource(jedis);
        }
    }

    /**
     * 销毁连接(放到catch中)
     *
     * @param jedis
     */
    public static void returnBrokenResource(Jedis jedis) {
        if (null != jedis) {
            jedis.close();
//            pool.returnResource(jedis);
        }
    }

    /**
     * 关闭连接池
     */
    public static void distory() {
        if (pool != null && !pool.isClosed()) {
            pool.close();
        }
    }
}