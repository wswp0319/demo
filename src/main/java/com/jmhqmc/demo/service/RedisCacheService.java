package com.jmhqmc.demo.service;

public interface RedisCacheService {
	/**
	 * 获取缓存
	 * 
	 * @param key
	 * @return
	 */
	public <T> T cacheResult(String key);

	/**
	 * 移除缓存
	 * 
	 * @param key
	 */
	public void cacheRemove(String key);

	/**
	 * 从缓存中获取值
	 * 
	 * @param key
	 * @param value
	 */
	public <T> void cachePut(String key, T value);
}
