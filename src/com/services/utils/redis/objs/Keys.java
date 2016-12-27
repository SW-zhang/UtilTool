package com.services.utils.redis.objs;

import com.services.utils.redis.common.RedisBase;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;
import redis.clients.util.SafeEncoder;

import java.util.List;
import java.util.Set;

/**
 * 操作Key的方法
 */
public class Keys {

    /**
     * 清空所有key
     */
    public String delAll() {
        Jedis jedis = RedisBase.getJedis();
        String stata = jedis.flushAll();
        RedisBase.returnJedis(jedis);
        return stata;
    }

    /**
     * 更改key
     *
     * @param oldkey
     * @param newkey
     * @return 状态码
     */
    public String rename(String oldkey, String newkey) {
        return rename(SafeEncoder.encode(oldkey),
                SafeEncoder.encode(newkey));
    }

    /**
     * 更改key,仅当新key不存在时才执行
     *
     * @param oldkey
     * @param newkey
     * @return 状态码
     */
    public long renamenx(String oldkey, String newkey) {
        Jedis jedis = RedisBase.getJedis();
        long status = jedis.renamenx(oldkey, newkey);
        RedisBase.returnJedis(jedis);
        return status;
    }

    /**
     * 更改key
     *
     * @param oldkey
     * @param newkey
     * @return 状态码
     */
    private String rename(byte[] oldkey, byte[] newkey) {
        Jedis jedis = RedisBase.getJedis();
        String status = jedis.rename(oldkey, newkey);
        RedisBase.returnJedis(jedis);
        return status;
    }

    /**
     * 设置key的过期时间，以秒为单位
     *
     * @param key
     * @param seconds
     * @return 影响的记录数
     */
    public long expired(String key, int seconds) {
        Jedis jedis = RedisBase.getJedis();
        long count = jedis.expire(key, seconds);
        RedisBase.returnJedis(jedis);
        return count;
    }

    /**
     * 设置key的过期时间,它是距历元（即格林威治标准时间 1970 年 1 月 1 日的 00:00:00，格里高利历）的偏移量。
     *
     * @param key
     * @param timestamp
     * @return 影响的记录数
     */
    public long expireAt(String key, long timestamp) {
        Jedis jedis = RedisBase.getJedis();
        long count = jedis.expireAt(key, timestamp);
        RedisBase.returnJedis(jedis);
        return count;
    }

    /**
     * 查询key的过期时间
     *
     * @param key
     * @return 以秒为单位的时间表示
     */
    public long ttl(String key) {
        
        Jedis sjedis = RedisBase.getJedis();
        long len = sjedis.ttl(key);
        RedisBase.returnJedis(sjedis);
        return len;
    }

    /**
     * 取消对key过期时间的设置
     *
     * @param key
     * @return 影响的记录数
     */
    public long persist(String key) {
        Jedis jedis = RedisBase.getJedis();
        long count = jedis.persist(key);
        RedisBase.returnJedis(jedis);
        return count;
    }

    /**
     * 删除keys对应的记录,可以是多个key
     *
     * @param keys
     * @return 删除的记录数
     */
    public long del(String... keys) {
        Jedis jedis = RedisBase.getJedis();
        long count = jedis.del(keys);
        RedisBase.returnJedis(jedis);
        return count;
    }

    /**
     * 删除keys对应的记录,可以是多个key
     *
     * @param keys
     * @return 删除的记录数
     */
    private long del(byte[]... keys) {
        Jedis jedis = RedisBase.getJedis();
        long count = jedis.del(keys);
        RedisBase.returnJedis(jedis);
        return count;
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return boolean
     */
    public boolean exists(String key) {
        
        Jedis sjedis = RedisBase.getJedis();
        boolean exis = sjedis.exists(key);
        RedisBase.returnJedis(sjedis);
        return exis;
    }

    /**
     * 对List,Set,SortSet进行排序,如果集合数据较大应避免使用这个方法
     *
     * @param key
     * @return List<String> 集合的全部记录
     **/
    public List<String> sort(String key) {
        
        Jedis sjedis = RedisBase.getJedis();
        List<String> list = sjedis.sort(key);
        RedisBase.returnJedis(sjedis);
        return list;
    }

    /**
     * 对List,Set,SortSet进行排序或limit
     *
     * @param key
     * @param parame 定义排序类型或limit的起止位置.
     * @return List<String> 全部或部分记录
     **/
    public List<String> sort(String key, SortingParams parame) {
        
        Jedis sjedis = RedisBase.getJedis();
        List<String> list = sjedis.sort(key, parame);
        RedisBase.returnJedis(sjedis);
        return list;
    }

    /**
     * 返回指定key存储的类型
     *
     * @param key
     * @return String string|list|set|zset|hash
     **/
    public String type(String key) {
        
        Jedis sjedis = RedisBase.getJedis();
        String type = sjedis.type(key);
        RedisBase.returnJedis(sjedis);
        return type;
    }

    /**
     * 查找所有匹配给定的模式的键
     *
     * @param pattern key的表达式,*表示多个，？表示一个
     */
    public Set<String> keys(String pattern) {
        Jedis jedis = RedisBase.getJedis();
        Set<String> set = jedis.keys(pattern);
        RedisBase.returnJedis(jedis);
        return set;
    }
}