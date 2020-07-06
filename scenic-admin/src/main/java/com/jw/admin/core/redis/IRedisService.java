package com.jw.admin.core.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhouguangyue on 18/1/19.
 */
public interface IRedisService {
    public boolean set(String key, String value);

    public boolean set(String key, String value, int expire);

    public boolean set(String key, Object value, long expire, TimeUnit timeUnit);

    public String get(String key);

    public boolean expire(String key, long expire);

    public <T> boolean setList(String key, List<T> list);

    public <T> List<T> getList(String key, Class<T> clz);

    public long lpush(String key, Object obj);

    public long rpush(String key, Object obj);

    public String lpop(String key);

    public boolean exists(String key);

    public boolean delete(String key);
}
