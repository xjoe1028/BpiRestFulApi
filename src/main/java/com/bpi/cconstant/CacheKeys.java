package com.bpi.cconstant;

import lombok.Getter;

/**
 * cache 名稱
 * 
 * @author Joe
 *
 * @Date 2022/10/26
 */
@Getter
public enum CacheKeys {

	// cache name
	BPI_CACHE("BPI_CACHE", "幣別cache"),
	BPIS_CACHE("BPIS", "所有幣別cache")
	;
	
	private String cacheName;
	private String message;
	
	CacheKeys(String cacheName, String message) {
		this.cacheName = cacheName;
		this.message = message;
	}
	
	public static String getCacheName(CacheKeys cacheKey) {
		return cacheKey.getCacheName();
	}
	
}
