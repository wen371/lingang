package com.jw.admin.core.config.web;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

@Service
public class RedisSessionDao extends AbstractSessionDAO {

    // Session超时时间，单位为毫秒
    private long expireTime = 1000*60*30;

    @Autowired
    private RedisTemplate redisTemplate;// Redis操作类，对这个使用不熟悉的，可以参考前面的博客

    public RedisSessionDao() {
        super();
    }

    @Override // 更新session
    public void update(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            return;
        }
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(session.getId().toString(), session);
        redisTemplate.expire(session.getId().toString(), expireTime, TimeUnit.MILLISECONDS);
    }

    @Override // 删除session
    public void delete(Session session) {
        if (null == session) {
            return;
        }
        redisTemplate.opsForValue().getOperations().delete(session.getId().toString());
    }

    public void delete(String sessionId) {
        if (null == sessionId) {
            return;
        }
        redisTemplate.opsForValue().getOperations().delete(sessionId);
    }

    @Override
// 获取活跃的session，可以用来统计在线人数，如果要实现这个功能，可以在将session加入redis时指定一个session前缀，统计的时候则使用keys("session-prefix*")的方式来模糊查找redis中所有的session集合
    public Collection<Session> getActiveSessions() {
        return null;
    }

    @Override// 加入session
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);

        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(session.getId().toString(), session);
        redisTemplate.expire(session.getId().toString(), expireTime, TimeUnit.MILLISECONDS);

        return sessionId;
    }

    @Override// 读取session
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            return null;
        }
        return (Session) redisTemplate.opsForValue().get(sessionId.toString());
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }
}
