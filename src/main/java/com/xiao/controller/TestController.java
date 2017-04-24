package com.xiao.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Description: 测试权限
 * User: xiaojixiang
 * Date: 2017/4/24
 * Version: 1.0
 */
@Controller
public class TestController {

    @RequestMapping(value = "/testRole", method = RequestMethod.GET)
    @RequiresRoles(value = {"admin_role"})
    public ModelAndView testRole() {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/web/testRole");

        return modelAndView;
    }
}
