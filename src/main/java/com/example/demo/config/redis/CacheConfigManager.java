package com.example.demo.config.redis;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * Created by zonzie on 2018/2/22.
 */
@Configuration
@EnableCaching
public class CacheConfigManager {
    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;
//    @Resource
//    private CacheConfigProperties cacheConfigProperties;

    @Bean
    public CacheManager cacheManagerCustomizer() {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(3600);//默认缓存一小时
//        cacheManager.setExpires(cacheConfigProperties.getExpires());
        return cacheManager;
    }
}
