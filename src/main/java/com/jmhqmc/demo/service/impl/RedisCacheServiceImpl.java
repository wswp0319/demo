package com.jmhqmc.demo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jmhqmc.demo.dao.RedisDao;
import com.jmhqmc.demo.service.RedisCacheService;

@Service("redisCacheService")
public class RedisCacheServiceImpl implements RedisCacheService{
	@Resource
	private RedisDao redisDao;

	/**
	 * 获取数据
	 */
	@SuppressWarnings("unchecked")
	public <T> T cacheResult(String key) {
		return (T) redisDao.getDataFromCache(key);
	}

	/**
	 * 清除缓存
	 */
	public void cacheRemove(String key) {
		redisDao.clearCache(key);
	}

	/**
	 * 放入缓存
	 */
	public <T> void cachePut(String key, T value) {
		redisDao.setDataToCache(key, value);
	}
}
