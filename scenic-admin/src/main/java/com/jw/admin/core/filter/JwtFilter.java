package com.jw.admin.core.filter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jw.admin.core.redis.IRedisService;
import com.jw.admin.core.util.JwtUtil;
import com.jw.admin.core.util.Response;
import com.jw.admin.core.util.ValidatorUtil;
import com.jw.admin.core.util.WebUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebFilter(filterName = "jwtFilter", urlPatterns = {"/apitest/*"})
@Slf4j
public class JwtFilter implements Filter {

    private IRedisService redisHelper;

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("===============“授权签名”过滤=============");
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {

        log.info("进入 token过滤器");

        if (response instanceof HttpServletResponse) {
            HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
            HttpServletResponse httpServletResponse = ((HttpServletResponse) response);

            Boolean flag = false;
            String url = httpServletRequest.getServletPath();
            PathMatcher matcher = new AntPathMatcher();
            List<String> excludeUrls = Lists.newArrayList();
            excludeUrls.add("/1123/343");
            for (int i = 0; i < excludeUrls.size(); i++) {
                if (matcher.match(excludeUrls.get(i), url)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                try {
                    chain.doFilter(request, response);
                    return;
                } catch (Exception e) {
                }
            }

            String authorizationToken = httpServletRequest.getHeader("Authorization");
            if (ValidatorUtil.isEmpty(authorizationToken)) {
                WebUtil.sendJson(httpServletResponse, Response.error(110, "缺失TOKEN信息!"));
                return;
            }
            try {
                final Claims claims = JwtUtil.parseJWT(authorizationToken);
                if (chain != null) {
                    JSONObject jsonObject = JSONObject.parseObject(claims.getSubject());
                    String appUserId = jsonObject.getString("openId");
                    if (ValidatorUtil.isEmpty(appUserId)) appUserId = jsonObject.getString("appStaffId");
                    String key = "u:" + jsonObject.getString("userId") + ":" + appUserId;
                    String value = "" + redisHelper.get(key);
                    log.info("key:{}", key);
                    log.info("value:{}", value);
                    if (ValidatorUtil.notEmpty(value)
                            && !value.equals("" + claims.getIssuedAt().getTime())) {//用户最新token签发时间
                        if (log.isDebugEnabled()) {
                            log.error("账号{},鉴权登录失败:{},当前TOKEN签发时间{}!=最后签发时间{}", jsonObject.getString("account"), claims.getIssuedAt().getTime(), value);
                        }
                        WebUtil.sendJson(httpServletResponse, Response.error(110, "当前TOKEN签发时间不等于最后签发时间!"));
                        return;
                    }
                }
                chain.doFilter(request, response);
            } catch (final SignatureException e) {
                WebUtil.sendJson(httpServletResponse, Response.error(403, "gt签名验证失败!"));
            } catch (ExpiredJwtException e) {
                WebUtil.sendJson(httpServletResponse, Response.error(403, "gt授权过期!"));
            } catch (Throwable e) {
                log.error("授权检查异常", e);
                WebUtil.sendJson(httpServletResponse, Response.error(403, "gt授权检查异常!"));
            }
        }
    }

    public IRedisService getRedisHelper() {
        return redisHelper;
    }

    public void setRedisHelper(IRedisService redisHelper) {
        this.redisHelper = redisHelper;
    }

}
