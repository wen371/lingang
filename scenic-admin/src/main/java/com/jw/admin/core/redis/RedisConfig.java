package com.jw.admin.core.redis;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by zhouguangyue on 18/1/19.
 */
@Slf4j
@Configuration
@EnableAutoConfiguration
@Data
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int    redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${spring.redis.timeout}")
    private int    redisTimeout;

    @Value("${spring.redis.pool.max-active}")
    private String redisPoolMaxActive;

    @Value("${spring.redis.pool.max-idle}")
    private int    redisPoolMaxIdle;

    @Value("${spring.redis.pool.max-wait}")
    private int    redisPoolMaxWait;

    @Bean
    public JedisPoolConfig getRedisConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(redisPoolMaxIdle);
        config.setMaxWaitMillis(redisPoolMaxWait);
        config.setTestOnBorrow(true);
        return config;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        factory.setHostName(redisHost);
        factory.setPort(redisPort);
        factory.setTimeout(redisTimeout);
        log.info("JedisConnectionFactory bean init success.");
        return factory;
    }

    @Bean
    public RedisTemplate<?, ?> getRedisTemplate() {
        RedisTemplate<?, ?> template = new StringRedisTemplate(getConnectionFactory());
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
