package com.jw.admin.core.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * spring session配置
 *
 * @author fengshuonan
 * @date 2017-07-13 21:05
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)  //session过期时间  如果部署多机环境,需要打开注释
public class SpringSessionConfig {


}
