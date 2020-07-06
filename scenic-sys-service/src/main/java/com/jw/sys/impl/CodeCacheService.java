package com.jw.sys.impl;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Service;

/**
 * 验证码缓存
 */
@Service
@Slf4j
public class CodeCacheService {
    private static volatile CacheManager cacheManager = CacheManager.create();
    private static volatile Cache cache = cacheManager.getCache("sms_code");
    private static volatile Cache cache2 = cacheManager.getCache("sms_send_interval");

    public String getInterval(String key) {
        Element element = cache2.get(key);
        return element == null || element.getObjectValue()==null?null:element.getObjectValue().toString();
    }

    public void setInterval(String key, String value) {
        Element element = new Element(key, value);
        cache2.put(element);
    }

    public void set(String key, String value){
        Element element = new Element(key, value);
        cache.put(element);
    }

    public String get(String key){
        Element element = cache.get(key);
        return element == null || element.getObjectValue()==null?null:element.getObjectValue().toString();
    }
}
