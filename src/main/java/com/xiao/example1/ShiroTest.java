package com.xiao.example1;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xiao on 2017/4/18.
 */
public class ShiroTest {

    @Test
    public void test1() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

        SecurityManager securityManager = factory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);

        //创建一个subject
        Subject currentUser = SecurityUtils.getSubject();

        //AuthenticationToken token = new UsernamePasswordToken("root", "secret");
        AuthenticationToken token = new UsernamePasswordToken("darkhelmet", "ludicrousspeed");

        try {
            currentUser.login(token);
        } catch (UnknownAccountException uae) {
            System.out.println("There is no user with username of " + token.getPrincipal());
        } catch (IncorrectCredentialsException ice) {
            System.out.println("Password for account " + token.getPrincipal() + " was incorrect!");
        } catch (LockedAccountException lae) {
            System.out.println("The account for username " + token.getPrincipal() + " is locked.  " +
                    "Please contact your administrator to unlock it.");
        }
        // ... catch more exceptions here (maybe custom ones specific to your application?
        catch (AuthenticationException ae) {
            //unexpected condition?  error?
        }

        System.out.println("currentUser.isAuthenticated():" + currentUser.isAuthenticated());

        System.out.println("currentUser.hasRole(admin):"+currentUser.hasRole("admin"));

        List<String> roles = new ArrayList<>();
        roles.add("admin");
        roles.add("guest");
        System.out.println("currentUser.hasAllRoles():"+currentUser.hasAllRoles(roles));

        boolean[] booleans = currentUser.hasRoles(roles);
        System.out.println("currentUser.hasRoles(roles):"+ Arrays.toString(booleans));

        System.out.println("currentUser.isPermitted(\"*\")"+currentUser.isPermitted("lightsaber:*"));

        currentUser.logout();
    }

}
