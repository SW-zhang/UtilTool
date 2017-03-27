package com.services.utils.redis.objs;

import com.services.utils.redis.common.RedisBase;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.util.SafeEncoder;

import java.util.List;

/**
 * 对存储结构为List类型的操作
 */
public class Lists {
    /**
     * List长度
     *
     * @param key
     * @return 长度
     */
    public long llen(String key) {
        return llen(SafeEncoder.encode(key));
    }

    /**
     * List长度
     *
     * @param key
     * @return 长度
     */
    private long llen(byte[] key) {
        Jedis jedis = RedisBase.getJedis();
        long redis_value;
        try {
            redis_value = jedis.llen(key);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 覆盖操作,将覆盖List中指定位置的值
     *
     * @param key
     * @param index 位置
     * @param value 值
     * @return 状态码
     */
    private String lset(byte[] key, int index, byte[] value) {
        Jedis jedis = RedisBase.getJedis();
        String redis_value;
        try {
            redis_value = jedis.lset(key, index, value);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 覆盖操作,将覆盖List中指定位置的值
     *
     * @param key
     * @param index 位置
     * @param value 值
     * @return 状态码
     */
    public String lset(String key, int index, String value) {
        return lset(SafeEncoder.encode(key), index,
                SafeEncoder.encode(value));
    }

    /**
     * 在value的相对位置插入记录
     *
     * @param key
     * @param where 前面插入或后面插入
     * @param pivot 相对位置的内容
     * @param value 插入的内容
     * @return 记录总数
     */
    public long linsert(String key, BinaryClient.LIST_POSITION where, String pivot,
                        String value) {
        return linsert(SafeEncoder.encode(key), where,
                SafeEncoder.encode(pivot), SafeEncoder.encode(value));
    }

    /**
     * 在指定位置插入记录
     *
     * @param key
     * @param where 前面插入或后面插入
     * @param pivot 相对位置的内容
     * @param value 插入的内容
     * @return 记录总数
     */
    private long linsert(byte[] key, BinaryClient.LIST_POSITION where, byte[] pivot, byte[] value) {
        Jedis jedis = RedisBase.getJedis();
        long redis_value;
        try {
            redis_value = jedis.linsert(key, where, pivot, value);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 获取List中指定位置的值
     *
     * @param key
     * @param index 位置
     * @return 值
     **/
    public String lindex(String key, int index) {
        return SafeEncoder.encode(lindex(SafeEncoder.encode(key), index));
    }

    /**
     * 获取List中指定位置的值
     *
     * @param key
     * @param index 位置
     * @return 值
     **/
    private byte[] lindex(byte[] key, int index) {
        Jedis jedis = RedisBase.getJedis();
        byte[] redis_value;
        try {
            redis_value = jedis.lindex(key, index);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 将List中的第一条记录移出List
     *
     * @param key
     * @return 移出的记录
     */
    public String lpop(String key) {
        return SafeEncoder.encode(lpop(SafeEncoder.encode(key)));
    }

    /**
     * 将List中的第一条记录移出List
     *
     * @param key
     * @return 移出的记录
     */
    private byte[] lpop(byte[] key) {
        Jedis jedis = RedisBase.getJedis();
        byte[] redis_value;
        try {
            redis_value = jedis.lpop(key);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 将List中最后第一条记录移出List
     *
     * @param key
     * @return 移出的记录
     */
    public String rpop(String key) {
        Jedis jedis = RedisBase.getJedis();
        String redis_value;
        try {
            redis_value = jedis.rpop(key);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 向List尾部追加记录
     *
     * @param key
     * @param value
     * @return 记录总数
     */
    public long lpush(String key, String value) {
        return lpush(SafeEncoder.encode(key), SafeEncoder.encode(value));
    }

    /**
     * 向List头部追加记录
     *
     * @param key
     * @param value
     * @return 记录总数
     */
    public long rpush(String key, String value) {
        Jedis jedis = RedisBase.getJedis();
        long redis_value;
        try {
            redis_value = jedis.rpush(key, value);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 向List头部追加记录
     *
     * @param key
     * @param value
     * @return 记录总数
     */
    private long rpush(byte[] key, byte[] value) {
        Jedis jedis = RedisBase.getJedis();
        long redis_value;
        try {
            redis_value = jedis.rpush(key, value);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 向List中追加记录
     *
     * @param key
     * @param value
     * @return 记录总数
     */
    private long lpush(byte[] key, byte[] value) {
        Jedis jedis = RedisBase.getJedis();
        long redis_value;
        try {
            redis_value = jedis.lpush(key, value);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 获取指定范围的记录，可以做为分页使用
     *
     * @param key
     * @param start
     * @param end
     * @return List
     */
    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = RedisBase.getJedis();
        List<String> redis_value;
        try {
            redis_value = jedis.lrange(key, start, end);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 获取指定范围的记录，可以做为分页使用
     *
     * @param key
     * @param start
     * @param end   如果为负数，则尾部开始计算
     * @return List
     */
    private List<byte[]> lrange(byte[] key, int start, int end) {
        Jedis jedis = RedisBase.getJedis();
        List<byte[]> redis_value;
        try {
            redis_value = jedis.lrange(key, start, end);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 删除List中c条记录，被删除的记录值为value
     *
     * @param key
     * @param c     要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
     * @param value 要匹配的值
     * @return 删除后的List中的记录数
     */
    private long lrem(byte[] key, int c, byte[] value) {
        Jedis jedis = RedisBase.getJedis();
        long redis_value;
        try {
            redis_value = jedis.lrem(key, c, value);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 删除List中c条记录，被删除的记录值为value
     *
     * @param key
     * @param c     要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
     * @param value 要匹配的值
     * @return 删除后的List中的记录数
     */
    public long lrem(String key, int c, String value) {
        return lrem(SafeEncoder.encode(key), c, SafeEncoder.encode(value));
    }

    /**
     * 算是删除吧，只保留start与end之间的记录
     *
     * @param key
     * @param start 记录的开始位置(0表示第一条记录)
     * @param end   记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
     * @return 执行状态码
     */
    private String ltrim(byte[] key, int start, int end) {
        Jedis jedis = RedisBase.getJedis();
        String redis_value;
        try {
            redis_value = jedis.ltrim(key, start, end);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 算是删除吧，只保留start与end之间的记录
     *
     * @param key
     * @param start 记录的开始位置(0表示第一条记录)
     * @param end   记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
     * @return 执行状态码
     */
    public String ltrim(String key, int start, int end) {
        return ltrim(SafeEncoder.encode(key), start, end);
    }
}
