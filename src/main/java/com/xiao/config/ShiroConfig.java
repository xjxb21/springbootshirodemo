package com.xiao.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Description:<p>shiro的配置文件</p>
 * User: xiaojixiang
 * Date: 2017/4/20
 * Version: 1.0
 */

@Configuration
public class ShiroConfig {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    public static final String defaultHashAlgorithmName = "MD5";

    //凭证匹配器，默认设置MD5
    public HashedCredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(defaultHashAlgorithmName);
        return credentialsMatcher;
    }

    @Bean(name = "myJdbcRealm")
    @Autowired
    public Realm myJdbcRealm(@Qualifier("myDataSource") DataSource dataSource) {
        logger.debug(">>>>Shiro init Realm object.");
        //使用shiro中提供的 jdbcRealm
        //如有必要可自定义
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setCredentialsMatcher(credentialsMatcher());
        //暂时不加盐
        //COLUMN - salt is in a separate column in the database.
        //jdbcRealm.setSaltStyle(JdbcRealm.SaltStyle.COLUMN);
        //用户密码匹配
        jdbcRealm.setAuthenticationQuery("select password, salt from ek_base_account where id = ?");
        //用户角色名称
        jdbcRealm.setUserRolesQuery("select name from ek_base_role where id = ?");
        //简单账号角色测试，下列未使用
//        jdbcRealm.setPermissionsLookupEnabled(true);  //默认为false
//        jdbcRealm.setPermissionsQuery("");

        return jdbcRealm;
    }

    @Bean
    @Autowired
    public DefaultWebSecurityManager getSecurityManager(Realm realm) {
        logger.debug(">>>>Shiro init SecurityManager.");
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        webSecurityManager.setRealm(realm);
        return webSecurityManager;
    }

    /**
     * shiro基本配置信息
     * @param securityManager
     * @return
     */
    @Bean
    @Autowired
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/loginPage");   //登陆页面地址
        shiroFilterFactoryBean.setSuccessUrl("/web/index"); //指定Thymeleaf的页面地址
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");  //使用注解，该处无作用

        //注意拦截链顺序
        Map<String, String> filterChainMap = new LinkedHashMap<>();
        filterChainMap.put("/logout", "logout");    //shiro已经实现
        filterChainMap.put("/loginPage", "anon");
        filterChainMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public FormAuthenticationFilter getFormAuthenticationFilter() {
        FormAuthenticationFilter authc = new FormAuthenticationFilter();
        return authc;
    }

    /**
     * 开启Shiro注解的支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }

    /**
     * 开启Shiro注解的支持
     */
    @Bean
    @Autowired
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
