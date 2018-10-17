package com.infopush.PushServer.ServiceImpl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.infopush.PushServer.Model.UserSession;



//@CacheConfig(cacheNames = "token")
@Service
public class TokenUtil {
	
	@Autowired
	private  CacheManager cacheManager;
	@CachePut(value="token",key="#p0")
	public  UserSession PutToken(String key,UserSession userSession){
		
//		Cache cache=cacheManager.getCache("token");
//		
//		String vString=cache.get(key, String.class);
//		if(vString!=null)
//		{
//			System.out.println("缓存命中");
//			return vString;
//		}
//		
		System.out.println("缓存写入");
		return userSession;
		
	}
	@Cacheable(value="token")
	public  UserSession GetToken(String key) {
		System.out.println("没走缓存");
		return null;
	}

}
