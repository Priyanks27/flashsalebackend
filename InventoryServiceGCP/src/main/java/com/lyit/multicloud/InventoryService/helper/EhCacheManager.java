package com.lyit.multicloud.InventoryService.helper;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.springframework.stereotype.Component;

@org.springframework.context.annotation.Configuration
@Component
public class EhCacheManager {

    private Cache<String, Object> localCache;

    private CacheManager cacheManager;

    private Cache<String, Object> getLocalCache() {
        try {
            if (localCache == null) {
                Configuration xmlConfig = new XmlConfiguration(EhCacheManager.class.getResource("/ehcache.xml"));
                cacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
                cacheManager.init();
                localCache = cacheManager.getCache("ehCache", String.class, Object.class);
            }
            return localCache;
        } catch (Exception ex) {
            return null;
        }
    }

    public void putCache(String key, Object value) {
        try {
            localCache = getLocalCache();
            if (localCache != null && value != null) localCache.put(key, value);
        } catch (Exception ex) {
        }
    }

    public Object getCache(String key) {
        localCache = getLocalCache();
        if (localCache != null && localCache.containsKey(key)) {
            return localCache.get(key);
        } else {
            return null;
        }
    }

    public void removeCacheByKey(String key) {
        localCache = getLocalCache();
        if (localCache != null) localCache.remove(key);
    }

    public void removeAllCache() {
        localCache = getLocalCache();
        if (localCache != null) localCache.clear();
    }
}

