package com.xiao.example1;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.junit.Test;

/**
 * Created by xiao on 2017/4/19.
 */
public class ShiroCustomRealmTest {

    @Test
    public void test1() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-customRealm.ini");

        SecurityManager securityManager = factory.getInstance();

        //自定义session id
        System.out.println(securityManager);
        DefaultSecurityManager defaultSecurityManager = (DefaultSecurityManager) securityManager;
        DefaultSessionManager defaultSessionManager = (DefaultSessionManager)defaultSecurityManager.getSessionManager();
        MemorySessionDAO sessionDAO = (MemorySessionDAO) defaultSessionManager.getSessionDAO();
        MysessionIdGenerator mySessionIdGenerator = new MysessionIdGenerator();
        sessionDAO.setSessionIdGenerator(mySessionIdGenerator);



        SecurityUtils.setSecurityManager(securityManager);

        Subject user = SecurityUtils.getSubject();

        AuthenticationToken token = new UsernamePasswordToken("xiaojixiang", "666666");

        try {
            user.login(token);

            Session session = user.getSession();
            System.out.println(session);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }


        System.out.println("user.isAuthenticated >>>>>" + user.isAuthenticated());

        System.out.println("session id :"+user.getSession().getId());

        System.out.println("user.hasRole(\"role2\")>>>"+user.hasRole("role2"));

        System.out.println("user.isPermitted(\"user:update\")>>>>>>>>>>>"+user.isPermitted("user:update1"));

        user.logout();
    }
}
