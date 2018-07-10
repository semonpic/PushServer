package com.infopush.PushServer.Dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.infopush.PushServer.App;
import com.infopush.PushServer.Entity.ClientGroup;
import com.infopush.PushServer.Entity.User;

@RunWith(SpringRunner.class)

//@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest
@EnableAutoConfiguration
public class Daotest {
	@Resource
	private UserMapper userDao;
	@Resource
	private ClientGroupMapper clientGroupDao;
	
	
	
	
	@Value("${web.upload-path}")
    private String path;
	
	@Value("${spring.resources.static-locations}")
	private String staticpath;
	
	
	  @Test
	    public void test(){
		  User user=userDao.selectByPrimaryKey(new Long(1));
		  
		  ClientGroup group=clientGroupDao.selectByPrimaryKey(2);
		  
		  
		  List<ClientGroup> lGroups=clientGroupDao.selectAll();
		  List<ClientGroup> lGroups1=clientGroupDao.selectByParentId(1);
		  System.out.println(group);
	      System.out.println("-----测试完毕-------");

	    }
	  @Test
	  public void test1() {
		  {
			  System.out.println(staticpath);
		      System.out.println(path);
			  
		  }
		
	}

}
