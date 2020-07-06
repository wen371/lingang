package com.jw.admin.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * 类名称:提取链接根目录放在request中<br>
 * -- jsp页面用${basePath}获取当前路径的根路径 类描述：
 *
 * @author LL 作者单位：
 * @version 1.7
 */
public class BasePathInterceptor extends HandlerInterceptorAdapter {
    private static final transient Logger log = Logger.getLogger(BasePathInterceptor.class);
    private static final String EQUAL_SIGN = "=";
    private static final String PLUS_SIGN = "+";
    private static final String AND = "&";
    @Value("${https.domainName}")//取配置文件的值
    private  String domainName;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getContextPath();
        String  basePath = domainName;
        request.setAttribute("path", path);
        request.setAttribute("basePath", basePath);
        request.setAttribute("ctxPath", basePath);
        log.info("basepath:" + basePath);
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
