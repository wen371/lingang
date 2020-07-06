package com.jw.admin.core.filter;

import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with com.jw.admin.core.filter.
 * User: q
 * Date: 2020/2/14
 */
@Order(1)
//@WebFilter(filterName = "wechatCheckFilter", urlPatterns = "/wechatCheckQrcode/getQCode")
@Slf4j
public class WechatCheckFilter implements Filter {

    //顾村公园code
    private static final String CH_CODE = "GCGY";

    @Value("${https.url}")
    private String url;

    @Value("${https.mxp_url}")
    private String mxp_url;

    @Autowired
    private WxMpService wxService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");

        String code = request.getParameter("code");
        if (StringUtil.isNotEmpty(code)) {
            try {
                WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
                WxMpUser user = wxService.getUserService().userInfo(wxMpOAuth2AccessToken.getOpenId());
                if (user != null) {
                    //判断如果已经关注过则重定向到明信片链接地址
                    if (user.getSubscribe()) {
                        String replace = mxp_url.replace("CHCODE", CH_CODE)
                                .replace("SCANPOINT", request.getParameter("scanpoint")).replace("USRFLAG", "0");
                        response.sendRedirect(replace);
                        return;
                    }
                }
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(url+"/wechatCheckQrcode/getQCode" + "?scanpoint=" + request.getParameter("scanpoint"));
            String urls = wxService.oauth2buildAuthorizationUrl(
                    url+"/wechatCheckQrcode/getQCode" + "?scanpoint=" + request.getParameter("scanpoint"), WxConsts.OAuth2Scope.SNSAPI_BASE, null);
            response.sendRedirect(urls);
            return;
        }

        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }

    public String message(WxMpUser user, HttpServletRequest request) {

        return "";
    }
}
