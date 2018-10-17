package com.infopush.PushServer.Dao;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.infopush.PushServer.Entity.User;
@CacheConfig(cacheNames = "dataCache")
public interface UserMapper {
	
	@CacheEvict
    int deleteByPrimaryKey(Long id);
	
	//@CachePut(key="#user.id")
    int insert(User record);

    int insertSelective(User record);

    @Cacheable
    User selectByPrimaryKey(Long id);
    @Cacheable
    User selectByName(String name);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}