package com.xiao.config.cache.util;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.lambdaworks.redis.codec.ByteArrayCodec;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 用户session持久化的操作
 * User: xiaojixiang
 * Date: 2017/5/8
 * Version: 1.0
 */
public class RedisPersistentSessionClient {

    private static RedisPersistentSessionClient instance;

    public static RedisPersistentSessionClient getInstance() {
        if (instance == null) {
            synchronized (RedisPersistentSessionClient.class) {
                if (instance == null) {
                    instance = new RedisPersistentSessionClient();
                }
            }
        }
        return instance;
    }

    private String redisUrl;
    private RedisClient redisClient;
    private RedisCommands redisCommands;

    private RedisPersistentSessionClient() {
        this.redisUrl = "redis://192.168.8.101";
        this.redisClient = RedisClient.create(redisUrl);
        this.redisCommands = redisClient.connect(new ByteArrayCodec()).sync();
        logger.info("Redis session Persistent client init...");
    }

    private static final Logger logger = LoggerFactory.getLogger(RedisPersistentSessionClient.class);

    /**
     * 更新【添加】操作
     */
    public void update(Session session) {
        redisCommands.set(SerializationUtil.serialize(session.getId().toString()), SerializationUtil.serialize(session));
    }

    /**
     * 删除
     */
    public void delete(Session session) {
        redisCommands.del(SerializationUtil.serialize(session.getId().toString()));
    }

    public void delete(String key) {
        redisCommands.del(SerializationUtil.serialize(key));
    }

}
