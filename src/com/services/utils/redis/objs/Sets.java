package com.services.utils.redis.objs;

import com.services.utils.redis.common.RedisBase;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * 对存储结构为Set类型的操作
 */
public class Sets {

    /**
     * 向Set添加一条记录，如果member已存在返回0,否则返回1
     *
     * @param key
     * @param member
     * @return 操作码, 0或1
     */
    public long sadd(String key, String member) {
        Jedis jedis = RedisBase.getJedis();
        long redis_value;
        try {
            redis_value = jedis.sadd(key, member);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    private long sadd(byte[] key, byte[] member) {
        Jedis jedis = RedisBase.getJedis();
        long redis_value;
        try {
            redis_value = jedis.sadd(key, member);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 获取给定key中元素个数
     *
     * @param key
     * @return 元素个数
     */
    public long scard(String key) {
        Jedis jedis = RedisBase.getJedis();
        long redis_value;
        try {
            redis_value = jedis.scard(key);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 返回从第一组和所有的给定集合之间的差异的成员
     *
     * @param keys
     * @return 差异的成员集合
     */
    public Set<String> sdiff(String... keys) {
        Jedis jedis = RedisBase.getJedis();
        Set<String> redis_value;
        try {
            redis_value = jedis.sdiff(keys);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 这个命令等于sdiff,但返回的不是结果集,而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
     *
     * @param newkey 新结果集的key
     * @param keys   比较的集合
     * @return 新集合中的记录数
     **/
    public long sdiffstore(String newkey, String... keys) {
        Jedis jedis = RedisBase.getJedis();
        long redis_value;
        try {
            redis_value = jedis.sdiffstore(newkey, keys);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 返回给定集合交集的成员,如果其中一个集合为不存在或为空，则返回空Set
     *
     * @param keys
     * @return 交集成员的集合
     **/
    public Set<String> sinter(String... keys) {
        Jedis jedis = RedisBase.getJedis();
        Set<String> redis_value;
        try {
            redis_value = jedis.sinter(keys);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 这个命令等于sinter,但返回的不是结果集,而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
     *
     * @param newkey 新结果集的key
     * @param keys   比较的集合
     * @return 新集合中的记录数
     **/
    public long sinterstore(String newkey, String... keys) {
        Jedis jedis = RedisBase.getJedis();
        long redis_value;
        try {
            redis_value = jedis.sinterstore(newkey, keys);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 确定一个给定的值是否存在
     *
     * @param key
     * @param member 要判断的值
     * @return 存在返回1，不存在返回0
     **/
    public boolean sismember(String key, String member) {
        Jedis jedis = RedisBase.getJedis();
        boolean redis_value;
        try {
            redis_value = jedis.sismember(key, member);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 返回集合中的所有成员
     *
     * @param key
     * @return 成员集合
     */
    public Set<String> smembers(String key) {
        Jedis jedis = RedisBase.getJedis();
        Set<String> redis_value;
        try {
            redis_value = jedis.smembers(key);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    private Set<byte[]> smembers(byte[] key) {
        Jedis jedis = RedisBase.getJedis();
        Set<byte[]> redis_value;
        try {
            redis_value = jedis.smembers(key);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 将成员从源集合移出放入目标集合 <br/>
     * 如果源集合不存在或不包哈指定成员，不进行任何操作，返回0<br/>
     * 否则该成员从源集合上删除，并添加到目标集合，如果目标集合中成员已存在，则只在源集合进行删除
     *
     * @param srckey 源集合
     * @param dstkey 目标集合
     * @param member 源集合中的成员
     * @return 状态码，1成功，0失败
     */
    public long smove(String srckey, String dstkey, String member) {
        Jedis jedis = RedisBase.getJedis();
        long redis_value;
        try {
            redis_value = jedis.smove(srckey, dstkey, member);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 从集合中删除成员
     *
     * @param key
     * @return 被删除的成员
     */
    public String spop(String key) {
        Jedis jedis = RedisBase.getJedis();
        String redis_value;
        try {
            redis_value = jedis.spop(key);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 从集合中删除指定成员
     *
     * @param key
     * @param member 要删除的成员
     * @return 状态码，成功返回1，成员不存在返回0
     */
    public long srem(String key, String member) {
        Jedis jedis = RedisBase.getJedis();
        long redis_value;
        try {
            redis_value = jedis.srem(key, member);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 合并多个集合并返回合并后的结果，合并后的结果集合并不保存<br/>
     *
     * @param keys
     * @return 合并后的结果集合
     */
    public Set<String> sunion(String... keys) {
        Jedis jedis = RedisBase.getJedis();
        Set<String> redis_value;
        try {
            redis_value = jedis.sunion(keys);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }

    /**
     * 合并多个集合并将合并后的结果集保存在指定的新集合中，如果新集合已经存在则覆盖
     *
     * @param newkey 新集合的key
     * @param keys   要合并的集合
     **/
    public long sunionstore(String newkey, String... keys) {
        Jedis jedis = RedisBase.getJedis();
        long redis_value;
        try {
            redis_value = jedis.sunionstore(newkey, keys);
        } finally {
            RedisBase.returnJedis(jedis);
        }

        return redis_value;
    }
}
