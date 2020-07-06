package com.jw.admin.core.config;

import com.jw.admin.core.redis.IRedisService;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.jw.common.support.http.HttpClientUtils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.MultipartConfigElement;

@Configuration
@Slf4j
public class BeanConfig {
    @Autowired
    private IRedisService redisService;

    @Bean("simpleRestTemplate")
    public RestTemplate getRestTemplate() {
        try {
            CloseableHttpClient httpClient = HttpClientUtils.acceptsUntrustedCertsHttpClient();
            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                    httpClient);
            RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

            return restTemplate;
        } catch (Exception e) {
            log.error("restTemplate远程调用bean初始化失败!");
        }

        return new RestTemplate();
    }

    /**
     * 文件上传配置
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("30720KB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("30720KB");
        return factory.createMultipartConfig();
    }

    @Bean
    EmbeddedServletContainerCustomizer containerCustomizer() throws Exception {
        return (ConfigurableEmbeddedServletContainer container) -> {
            if (container instanceof TomcatEmbeddedServletContainerFactory) {
                TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
                tomcat.addConnectorCustomizers((connector) -> {
                    connector.setMaxPostSize(10000000); // 10 MB
                });
            }
        };
    }

   /* @Bean
    public FilterRegistrationBean jwtFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        JwtFilter jwtFilter = new JwtFilter();
        jwtFilter.setRedisHelper(redisService);
        registration.setFilter(jwtFilter);
        registration.addUrlPatterns("/api/*");
        registration.setName("jwtFilter");
        registration.setOrder(2);
        return registration;
    }

    @Bean
    public FilterRegistrationBean crossFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CrossFilter());
        registration.addUrlPatterns("/api/*");
        registration.setName("crossFilter");
        registration.setOrder(1);
        return registration;
    }*/
}
