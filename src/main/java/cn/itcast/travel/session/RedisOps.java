package cn.itcast.travel.session;

import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;

public class RedisOps {

    public static void set(String key, String value) {
        Jedis jedis = JedisUtil.getJedis();
        jedis.set(key, value);
        jedis.close();
    }

    public static String get(String key) {
        Jedis jedis = JedisUtil.getJedis();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    public static void setObject(String key, Object object) {
        Jedis jedis = JedisUtil.getJedis();
        jedis.set(key.getBytes(), SerializeUtil.serialize(object));
        jedis.close();
    }

    public static Object getObject(String key) {
        Jedis jedis = JedisUtil.getJedis();
        byte[] bytes = jedis.get(key.getBytes());
        jedis.close();
        return SerializeUtil.unserialize(bytes);
    }

    /**
     * 删除Key
     * @param key
     */
    public static void delKey(String key){
        Jedis jedis = JedisUtil.getJedis();
        jedis.del(key);
        jedis.close();
    }

    /**
     * 删除Key
     * @param key
     */
    public static void hdelKey(String key){
        Jedis jedis = JedisUtil.getJedis();
        jedis.del(key.getBytes());

        jedis.close();
    }
    /**
     * redis的map结构的添加
     *
     * @param key
     * @param field
     * @param object
     */
    public static void hsetObject(String key, String field, Object object) {
        Jedis jedis = JedisUtil.getJedis();
        jedis.hset(key.getBytes(), field.getBytes(), SerializeUtil.serialize(object));
        jedis.close();
    }

    /**
     * redis的map结构的获取
     *
     * @param key
     * @param field
     * @return
     */
    public static Object hgetObject(String key, String field) {
        Jedis jedis = JedisUtil.getJedis();
        byte[] bytes = jedis.hget(key.getBytes(), field.getBytes());
        jedis.close();
        return SerializeUtil.unserialize(bytes);
    }

    /**
     * redis的map结构的失效时间
     *
     * @param key
     * @param second
     */
    public static void hexpire(String key, int second) {
        Jedis jedis = JedisUtil.getJedis();
        jedis.expire(key.getBytes(), second);
        jedis.close();
    }

    /**
     * redis的map结构的删除
     *
     * @param key
     * @param field
     */
    public static void hdelete(String key, String field) {
        Jedis jedis = JedisUtil.getJedis();
        jedis.hdel(key.getBytes(), field.getBytes());
        jedis.close();
    }
}


