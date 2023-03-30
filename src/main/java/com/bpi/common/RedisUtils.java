package com.bpi.common;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RedisUtils {

	@Autowired
	protected StringRedisTemplate stringRedisTemplate;
	
	public static final int ONE_DAY = 84600; // 一天秒數86400s
	
	/**
	 * 寫入redis緩存(不設置expire存活時間)
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, String value) {
		boolean result = false;
		
		try {
			ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			log.error("寫入redis緩存失敗，錯誤訊息；{}", e.getMessage());
			log.error("exception : {}", e);
		}
		
		return result;
	}
	
	/**
	 * 寫入redis緩存(設置expire存活時間)
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public boolean set(final String key, String value, int expire) {
		boolean result = false;
		
		try {
			ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
			operations.set(key, value);
			stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			log.error("寫入redis緩存失敗，錯誤訊息；{}", e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 讀取 redis 緩存
	 * 
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		try {
			ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
			return operations.get(key);
		} catch (Exception e) {
			log.error("讀取redis緩存失敗，錯誤訊息；{}", e.getMessage());
		}
		
		return "";
	}
	
	/**
	 * 判斷 key 是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		boolean result = false;
        try {
            result = stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("判斷redis緩存中是否有對應的key失敗，錯誤訊息：" + e.getMessage());
        }
        
        return result;
	}
	
	/**
	 * redis 根據key删除對應的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean remove(final String key) {
		boolean result = false;
		try {
			if (exists(key)) {
				stringRedisTemplate.delete(key);
			}
			result = true;
		} catch (Exception e) {
			log.error("redis根據key刪除對應的value失敗！錯誤訊息為：" + e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 刪除多個緩存
	 * 
	 * @param keys
	 */
	public void remove(final String... keys) {
		for(String key : keys) {
			remove(key);
		}
	}

}
