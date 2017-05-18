package com.xiao.config.cache.util;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.lambdaworks.redis.codec.ByteArrayCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 用与支持 session cache， 实例使用单机Redis
 * User: xiaojixiang
 * Date: 2017/5/8
 * Version: 1.0
 */
public class RedisCacheManagerClient {

    private static RedisCacheManagerClient instance;

    public static RedisCacheManagerClient getInstance() {
        if (instance == null) {
            synchronized (RedisCacheManagerClient.class) {
                if (instance == null) {
                    instance = new RedisCacheManagerClient();
                }
            }
        }
        return instance;
    }

    private RedisCacheManagerClient() {
        RedisClient redisClient = RedisClient.create("redis://192.168.8.102");
        this.redisCommands = redisClient.connect(new ByteArrayCodec()).sync();
        logger.info("Redis session cache init...");
    }


    private static final Logger logger = LoggerFactory.getLogger(RedisCacheManagerClient.class);

    RedisCommands<byte[], byte[]> redisCommands;


    /**
     * 获取
     * @param key
     * @return 如果没找到则返回null
     */
    public Object get(Object key) {
        byte[] bytes = redisCommands.get(SerializationUtil.serialize(key));
        if (bytes != null) {
            return SerializationUtil.deserialize(bytes);
        }
        return null;
    }

    /**
     * 保存
     * @param key
     * @param value
     * @return String simple-string-reply {@code OK} if {@code SET} was executed correctly.
     */
    public String set(Object key, Object value) {
        return redisCommands.set(SerializationUtil.serialize(key), SerializationUtil.serialize(value));
    }

    /**
     * 参数
     * @param key
     */
    public void remove(Object key) {
        redisCommands.del(SerializationUtil.serialize(key));
    }

    /**
     * 获取redis中缓存的数量
     * @return
     */
    public int size() {
        return Math.toIntExact(redisCommands.dbsize());
    }


}
