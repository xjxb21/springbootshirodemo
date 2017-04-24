package com.xiao.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 系统页面设置
 * User: xiaojixiang
 * Date: 2017/4/22
 * Version: 1.0
 */

@Controller
public class SysController {

    //登陆首页
    @RequestMapping(value = "/index")
    public String index() {
        return "/web/index";
    }

    //登陆首页
    @RequestMapping(value = "/")
    public String indexRoot() {
        return "/web/index";
    }

    @RequestMapping(value = "/loginPage")
    public String login(HttpServletRequest httpServletRequest) throws Exception {

        httpServletRequest.getParameter("username");
        Object shiroLoginFailure = httpServletRequest.getAttribute("shiroLoginFailure");

        if (shiroLoginFailure != null) {
            if (UnknownAccountException.class.getName().equals(shiroLoginFailure)) {
                throw new Exception("账号不存在");
            } else if (IncorrectCredentialsException.class.getName().equals(shiroLoginFailure)) {
                throw new Exception("密码不正确");
            }
        }

        return "/loginPage";
    }

    @RequestMapping(value = "/403")
    public String page403() {
        return "/403";
    }


//    @RequestMapping(value = "/unauthorized")
//    public String unauthorized() {
//        System.out.println("unauthorized....");
//        return "/unauthorized";
//    }
}