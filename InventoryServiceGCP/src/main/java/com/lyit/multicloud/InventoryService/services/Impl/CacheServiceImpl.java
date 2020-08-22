package com.lyit.multicloud.InventoryService.services.Impl;

import com.lyit.multicloud.InventoryService.helper.EhCacheManager;
import com.lyit.multicloud.InventoryService.services.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    EhCacheManager ehCacheManager;

    @Override
    public Object getCache(String key) {
        return ehCacheManager.getCache(key);
    }

    @Override
    public Boolean putCache(String key, Object object) {
        try {
            ehCacheManager.putCache(key, object);
        }
        catch (Exception ex){
            return false;
        }
        return true;
    }

    @Override
    public Boolean removeCacheByKey(String key) {
        try {
            ehCacheManager.removeCacheByKey(key);
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    @Override
    public Boolean removeAllKeys() {
        try {
            ehCacheManager.removeAllCache();
        }catch (Exception ex){
            return false;
        }
        return true;
    }
}
