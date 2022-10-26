package com.bpi.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Bpi CacheManager
 * 
 * @author Joe
 * 
 * @Date 2022/10/25
 */
@Slf4j
@Component
public class BpiCacheManager {

	@Autowired
	CacheManager cacheManager;

	/**
	 * 清除cache : cacheName對應key值
	 * 
	 * @param cacheName
	 * @param cacheKey
	 */
	public void evictSingleCacheValue(String cacheName, String cacheKey) {
		if (checkValidCacheName(cacheName)) {
			Cache cache = cacheManager.getCache(cacheName);
			ValueWrapper value = cache.get(cacheKey);
			if (value == null) {
				log.error("flush fail invalid cache name : {} and  cache key : {}", cacheName, cacheKey);
			} else {
				cache.evict(cacheKey);
				log.info("flush success cache name : {} and  cache key : {}", cacheName, cacheKey);
			}
		} else {
			log.error("flush fail invalid cache name : {} ", cacheName);
		}
	}

	/**
	 * 清除cache : cacheName全部key值
	 * 
	 * @param cacheName
	 */
	public void evictAllCacheValues(String cacheName) {
		if (checkValidCacheName(cacheName)) {
			cacheManager.getCache(cacheName).clear();
			log.info("flush success cache name : {}", cacheName);
		} else {
			log.error("flush fail invalid cache name : {} ", cacheName);
		}
	}

	/**
	 * 檢查CacheName是否存在於cache
	 * 
	 * @param cacheName
	 * @return
	 */
	private boolean checkValidCacheName(String cacheName) {
		return cacheManager.getCacheNames().contains(cacheName) && cacheManager.getCache(cacheName) != null;
	}
	
}
