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



//@CacheConfig(cacheNames = "token")
@Service
public class TokenUtil {
	
	@Autowired
	private  CacheManager cacheManager;
	@CachePut(value="token",key="#p0")
	public  String PutToken(String key,List<String> roll){
		
//		Cache cache=cacheManager.getCache("token");
//		
//		String vString=cache.get(key, String.class);
//		if(vString!=null)
//		{
//			System.out.println("缓存命中");
//			return vString;
//		}
//		
		StringBuilder stringBuilder=new StringBuilder();
		for (String string : roll) {
			
			stringBuilder.append(string);
			stringBuilder.append(":");
		}
		return stringBuilder.toString();
		
	}
	@Cacheable(value="token")
	public  String  GetToken(String key) {
		System.out.println("没走缓存");
		return null;
	}

}
