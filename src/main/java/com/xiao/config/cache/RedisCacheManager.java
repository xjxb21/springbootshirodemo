package com.xiao.config.cache;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description:
 * User: xiaojixiang
 * Date: 2017/5/8
 * Version: 1.0
 */
@Component
public class RedisCacheManager extends AbstractCacheManager {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);

    @Override
    protected Cache createCache(String name) throws CacheException {
        logger.info("***************createCache：" + name);
        return new RedisSessionCache();
    }
}
