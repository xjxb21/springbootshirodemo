package com.xiao.config;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description: Shiro异常处理
 * User: xiaojixiang
 * Date: 2017/4/24
 * Version: 1.0
 */
@Component
public class ShiroExceptionHandlers implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e.getMessage());
        if (e instanceof UnauthorizedException) {
            modelAndView.setViewName("/403");
        } else {
            modelAndView.setViewName("/error");
        }
        return modelAndView;
    }
}
