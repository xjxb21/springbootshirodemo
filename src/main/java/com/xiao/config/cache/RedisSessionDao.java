package com.xiao.config.cache;

import com.xiao.config.cache.util.RedisCacheManagerClient;
import com.xiao.config.cache.util.RedisPersistentSessionClient;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Description: redis session DAO
 * User: xiaojixiang
 * Date: 2017/4/26
 * Version: 1.0
 */

public class RedisSessionDao extends EnterpriseCacheSessionDAO {

    private static final Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

    private RedisCacheManagerClient redisCacheManagerClient;

    private RedisPersistentSessionClient redisPersistentSessionClient;

    public RedisSessionDao() {
        this.redisCacheManagerClient = RedisCacheManagerClient.getInstance();
        this.redisPersistentSessionClient = RedisPersistentSessionClient.getInstance();
    }

    @Override
    protected Serializable doCreate(Session session) {
        logger.info(">>>>>>doCreate session(nothing)：" + session.getId());
        Serializable serializable = super.doCreate(session);
        return serializable;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.info(">>>>>>doReadSession(nothing):" + sessionId);
        return super.doReadSession(sessionId);
    }

    @Override
    protected void doUpdate(Session session) {
        logger.info(">>>>>>doUpdate session:" + session.getId());
        logger.info(session.toString());
        redisPersistentSessionClient.update(session);
    }

    @Override
    protected void doDelete(Session session) {
        logger.info(">>>>>doDelete session:" + session.getId());
        redisPersistentSessionClient.delete(session);
        //当前持久化层的session被删除后，最好将cache的session删除
        //......
        redisCacheManagerClient.remove(session.getId().toString());
    }
}
