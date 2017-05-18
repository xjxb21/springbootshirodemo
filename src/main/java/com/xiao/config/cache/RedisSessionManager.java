package com.xiao.config.cache;

import com.xiao.config.cache.util.RedisCacheManagerClient;
import com.xiao.config.cache.util.RedisPersistentSessionClient;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: 配置持久化session Dao
 *              以及相关的组件
 * User: xiaojixiang
 * Date: 2017/5/2
 * Version: 1.0
 */

@Configuration
public class RedisSessionManager {

    @Autowired
    RedisCacheManager redisCacheManager;

    @Bean
    public DefaultWebSessionManager getWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        RedisSessionDao redisSessionDao = new RedisSessionDao();
        sessionManager.setSessionDAO(redisSessionDao);
        sessionManager.setGlobalSessionTimeout(60 * 1000 * 3);  //全局3分钟过期

        sessionManager.setCacheManager(redisCacheManager);
        return sessionManager;
    }
}
