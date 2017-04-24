package com.xiao.example1;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义Realm
 */
public class CustomRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //get principal
        String principal = (String)principals.getPrimaryPrincipal();

        Set<String> roles = new HashSet<>();
        roles.add("role1");
        roles.add("role2");

        Set<String> permissions = new HashSet<>();
        permissions.add("user:add");
        permissions.add("user:update");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(roles);
        authorizationInfo.addStringPermission("user:add");
        authorizationInfo.addStringPermissions(permissions);

        //Permission all = new AllPermission();
        //authorizationInfo.addObjectPermission(all);

        return authorizationInfo;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String userCode = (String) token.getPrincipal();

        //模拟在数据库中找到账号...
        String moniUserCode = "xiaojixiang";
        if (!userCode.equals(moniUserCode)) {
            return null;    //模拟没有找到账号，返回null
        }

        //模拟实际找到的密码
        String moniPassword = "666666";
        return new SimpleAuthenticationInfo(userCode, moniPassword, "Custom-Realm");
    }
}
