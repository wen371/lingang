package com.jw.admin.core.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhouguangyue on 18/1/19.
 */
@Service("redisService")
public class RedisServiceImpl implements IRedisService{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean set(final String key, final String value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    public String get(final String key){
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value =  connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

    @Override
    public boolean expire(final String key, long expire) {

        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public <T> boolean setList(String key, List<T> list) {

        String value = JSON.toJSONString(list);
        return set(key,value);
    }

    @Override
    public <T> List<T> getList(String key,Class<T> clz) {
        String json = get(key);
        if(json!=null){
            List<T> list =(List)JSON.parseObject(json, clz);
            return list;
        }
        return null;
    }

    @Override
    public long lpush(final String key, Object obj) {
        final String value = JSON.toJSONString(obj);
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    @Override
    public long rpush(final String key, Object obj) {
        final String value = JSON.toJSONString(obj);
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    @Override
    public String lpop(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] res =  connection.lPop(serializer.serialize(key));
                return serializer.deserialize(res);
            }
        });
        return result;
    }

    @Override
    public boolean exists(String key) {
        return  redisTemplate.hasKey(key);
    }

    @Override
    public boolean delete(String key) {
         redisTemplate.delete(key);
         return true;
    }

    @Override
    public boolean set(String key, String value, int expire) {
        try {
            redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean set(String key, Object value, long expire, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value, expire, timeUnit);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
