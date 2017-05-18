package com.xiao.config.cache;

import com.xiao.config.cache.util.RedisCacheManagerClient;
import com.xiao.config.cache.util.RedisPersistentSessionClient;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * Description: session cache, 仅使用Cache<String, Object>
 * User: xiaojixiang
 * Date: 2017/5/8
 * Version: 1.0
 */
public class RedisSessionCache implements Cache<String, Object> {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);

    private RedisCacheManagerClient redisCacheManagerClient;

    private RedisPersistentSessionClient redisPersistentSessionClient;

    public RedisSessionCache() {
        this.redisCacheManagerClient = RedisCacheManagerClient.getInstance();
        this.redisPersistentSessionClient = RedisPersistentSessionClient.getInstance();
    }

    @Override
    public Object get(String key) throws CacheException {
        logger.info("cache get");
        return redisCacheManagerClient.get(key);
    }

    /**
     * 参考接口说明
     *
     * @return the previous value associated with the given {@code key} or {@code null} if there was previous value
     */
    @Override
    public Object put(String key, Object value) throws CacheException {
        logger.info("cache put");
        Object preValue = redisCacheManagerClient.get(key);    //接口描述说返回上一个值，不知其意义，应该可以忽略
        redisCacheManagerClient.set(key, value);
        return preValue;
    }

    @Override
    public Object remove(String key) throws CacheException {
        logger.info("cache remove");
        Object preValue = redisCacheManagerClient.get(key);    //接口描述说返回上一个值，不知其意义，应该可以忽略
        redisCacheManagerClient.remove(key);

        //当前cache的session被删除后，也需要将持久化层的session删除
        redisPersistentSessionClient.delete(key);

        return preValue;
    }

    @Override
    public void clear() throws CacheException {
        logger.info("cache clear");
    }

    @Override
    public int size() {
        return redisCacheManagerClient.size();
    }

    /**
     * 最好不要使用，性能慢
     *
     * @return
     */
    @Override
    public Set<String> keys() {
        logger.info("cache set");
        return null;
    }

    /**
     * 最好不要使用，性能慢
     *
     * @return
     */
    @Override
    public Collection<Object> values() {
        return null;
    }
}
