package com.neuedu.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

// 缓存类
public class TokenCache {

    private static LoadingCache<String,String> cacheLoader = CacheBuilder.newBuilder()
            .initialCapacity(1000) // 初始化缓存项
            .maximumSize(10000) // 最大缓存项10000
            .refreshAfterWrite(2, TimeUnit.HOURS) // 定时回收
            .build(new CacheLoader<String, String>() {

                // 当从缓存中读取数据时，如果不存在数据，就会调用load()

        @Override
        public String load(String s) throws Exception {
            return "null";
        }
    });

    // 往缓存中设置数据
    public static void set(String key,String value){
        cacheLoader.put(key,value);
    }

    // 从缓存中读取数据
    public static String get(String key){
        try {
            return cacheLoader.get(key);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
