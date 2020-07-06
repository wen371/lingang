package com.jw.admin.core.config.web;

import com.jw.admin.core.shiro.ShiroDbRealm;
import com.jw.admin.core.config.properties.AdminProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro权限管理的配置
 *
 * @author fengshuonan
 * @date 2016年11月14日 下午3:03:44
 */
@Configuration
@Slf4j
public class ShiroConfig {

    /**
     * 安全管理器
     */
    @Bean
    public DefaultWebSecurityManager securityManager(CookieRememberMeManager rememberMeManager, CacheManager cacheShiroManager, AdminProperties adminProperties, RedisSessionDao sessionDao) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(this.shiroDbRealm());
        securityManager.setCacheManager(cacheShiroManager);
        securityManager.setRememberMeManager(rememberMeManager);
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(cacheShiroManager);
        sessionManager.setSessionValidationInterval(adminProperties.getSessionValidationInterval() * 1000);
        // sessionManager.setGlobalSessionTimeout(adminProperties.getSessionInvalidateTime() * 1000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionDAO(sessionDao);// 设置SessionDao
        sessionManager.setGlobalSessionTimeout(sessionDao.getExpireTime());// 设置全局session超时时间

        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        cookie.setName("shiroCookie");
        cookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(cookie);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    /* *//**
     * spring session管理器（多机环境）
     *//*
    @Bean
    @ConditionalOnProperty(prefix = "admin", name = "spring-session-open", havingValue = "true")
    public ServletContainerSessionManager servletContainerSessionManager() {
        return new ServletContainerSessionManager();
    }

    *//**
     * session管理器(单机环境)
     *//*
    @Bean
    @ConditionalOnProperty(prefix = "admin", name = "spring-session-open", havingValue = "false")
    public DefaultWebSessionManager defaultWebSessionManager(CacheManager cacheShiroManager, AdminProperties adminProperties) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(cacheShiroManager);
        sessionManager.setSessionValidationInterval(adminProperties.getSessionValidationInterval() * 1000);
        sessionManager.setGlobalSessionTimeout(adminProperties.getSessionInvalidateTime() * 1000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        cookie.setName("shiroCookie");
        cookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }*/


    /**
     * 缓存管理器 使用Ehcache实现
     */
    @Bean
    public CacheManager getCacheShiroManager(EhCacheManagerFactoryBean ehcache) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(ehcache.getObject());
        return ehCacheManager;
    }

    /**
     * 项目自定义的Realm
     */
    @Bean
    public ShiroDbRealm shiroDbRealm() {
        return new ShiroDbRealm();
    }

    /**
     * rememberMe管理器, cipherKey生成见{@code Base64Test.java}
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie) {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCipherKey(Base64.decode("Z3VucwAAAAAAAAAAAAAAAA=="));
        manager.setCookie(rememberMeCookie);
        return manager;
    }

    /**
     * 记住密码Cookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(7 * 24 * 60 * 60);//7天
        return simpleCookie;
    }

    /**
     * Shiro的过滤器链
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager, AdminProperties adminProperties) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        log.info("httpsUrl:{}", adminProperties.getHttpsUrl());
        String httpsUrl = adminProperties.getHttpsUrl();
        /**
         * 默认的登陆访问url
         */
        shiroFilter.setLoginUrl(httpsUrl+"/newlogin");
        /**
         * 登陆成功后跳转的url
         */
        shiroFilter.setSuccessUrl(httpsUrl+"/");
        /**
         * 没有权限跳转的url
         */
        shiroFilter.setUnauthorizedUrl(httpsUrl+"/global/error");
        /**
         * 配置shiro拦截器链
         *
         * anon  不需要认证
         * authc 需要认证
         * user  验证通过或RememberMe登录的都可以
         *
         * 当应用开启了rememberMe时,用户下次访问时可以是一个user,但不会是authc,因为authc是需要重新认证的
         *
         * 顺序从上到下,优先级依次降低
         *
         */
        Map<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("/iservice/**", "anon");
        hashMap.put("/orgtree/innerCodeInitialization", "anon");
        hashMap.put("/api/**", "anon");
        hashMap.put("/verification/**", "anon");
        //hashMap.put("/zjlogin", "anon");
        hashMap.put("/newlogin", "anon");
        hashMap.put("/alogin", "anon");
        hashMap.put("/static/**", "anon");
        hashMap.put("/front/**", "anon");// 过滤器不拦截前端页面请求 add by tanqiaomu
        hashMap.put("/login", "anon");
        hashMap.put("/weborder/**", "anon");
        hashMap.put("/**/*.jpg", "anon");
        hashMap.put("/**/*.jpeg", "anon");
        hashMap.put("/**/*.png", "anon");
        hashMap.put("/**/*.mp4", "anon");
        hashMap.put("/image/**", "anon");
        hashMap.put("/global/sessionError", "anon");
        hashMap.put("/kaptcha", "anon");
        hashMap.put("/wechat/portal","anon");
        hashMap.put("/wechat/jsSdk","anon");
        hashMap.put("/wechat/jsSdk","anon");
        hashMap.put("/wechatCheckQrcode/**","anon");//微信验证
        hashMap.put("/article/view/*","anon");
        hashMap.put("/miniapp/**","anon");//小程序
        hashMap.put("/verifyapp/**","anon");//核销APP
        hashMap.put("/bigScreen/**", "anon");
        hashMap.put("/**", "user");

        shiroFilter.setFilterChainDefinitionMap(hashMap);
        return shiroFilter;
    }

    /**
     * 在方法中 注入 securityManager,进行代理控制
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager securityManager) {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        bean.setArguments(new Object[]{securityManager});
        return bean;
    }

    /**
     * Shiro生命周期处理器:
     * 用于在实现了Initializable接口的Shiro bean初始化时调用Initializable接口回调(例如:UserRealm)
     * 在实现了Destroyable接口的Shiro bean销毁时调用 Destroyable接口回调(例如:DefaultSecurityManager)
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 启用shrio授权注解拦截方式，AOP式方法级权限检查
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
