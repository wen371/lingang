package com.jw.admin.core.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CrossFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
        log.info("===============设置“跨域”访问头=============");
    }

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
        if (response instanceof HttpServletResponse) {
            HttpServletRequest servletRequest = ((HttpServletRequest) request);
            HttpServletResponse servletResponse = ((HttpServletResponse) response);
            addHeadersFor200Response(servletRequest,servletResponse);
            chain.doFilter(request, response);
        }
    }

    private void addHeadersFor200Response(HttpServletRequest request,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.setHeader("Access-Control-Allow-Headers", "Origin,Authorization,Cache-Control,Content-Language,Content-Type,Expires,Last-Modified,Pragma,X-Cache");
    }
}