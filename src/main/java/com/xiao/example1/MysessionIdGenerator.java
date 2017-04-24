package com.xiao.example1;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;

import java.io.Serializable;

/**
 * Description: 自定义sessionId生成器
 * User: xiaojixiang
 * Date: 2017/4/20
 * Version: 1.0
 */

public class MysessionIdGenerator extends JavaUuidSessionIdGenerator {

    private String prefix = "xiao-";

    @Override
    public Serializable generateId(Session session) {
        Serializable oldSessionId = super.generateId(session);

        return prefix + oldSessionId;
    }
}
