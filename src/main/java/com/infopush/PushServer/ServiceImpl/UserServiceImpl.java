package com.infopush.PushServer.ServiceImpl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infopush.PushServer.Dao.RoleMapper;
import com.infopush.PushServer.Dao.UserMapper;
import com.infopush.PushServer.Dao.UserRoleMapper;
import com.infopush.PushServer.Entity.JsonResult;
import com.infopush.PushServer.Entity.Role;
import com.infopush.PushServer.Entity.User;
import com.infopush.PushServer.Entity.UserRole;
import com.infopush.PushServer.Model.LoginFormModel;
import com.infopush.PushServer.Model.LoginRuselt;
import com.infopush.PushServer.Service.UserService;
import com.infopush.PushServer.Utils.EncryptUtils;

import javassist.expr.NewArray;
import lombok.Data;
@Service

public class UserServiceImpl implements UserService {
	@Resource
	UserMapper userDao;
	
	@Resource
	UserRoleMapper userRoleDao;
	
	@Resource
	RoleMapper roleDao;
	
	@Resource
	TokenUtil tokenUtil;
	
	@Override
	public int addUser(User record) {
		
		return userDao.insert(record);
	}

	@Override
	public User getUserById(Long userId) {
		// TODO Auto-generated method stub
		return userDao.selectByPrimaryKey(userId);
	}

	
	private List<String>  getRoleById(Long id) {
		List<String> rolelist =new ArrayList<String>();
		List<UserRole> userrolllist= userRoleDao.getUserRole(id);
		if(userrolllist!=null)
		{
			for (UserRole userRole : userrolllist) {
				Role role=roleDao.selectByPrimaryKey(userRole.getRoleId());
				if(role!=null)
				{
					rolelist.add(role.getName());
					
				}
				
				
			}
			
		}
		
		return  rolelist;
	}
	
	@Override
	public JsonResult<LoginRuselt> login(LoginFormModel model) {
		JsonResult<LoginRuselt> result =new JsonResult<LoginRuselt>();
		try {
			User tmpUser=userDao.selectByName(model.getUserName());
			if(tmpUser==null){
				throw new Exception("用户名或密码错误");
			}
			String tmpmd5=EncryptUtils.encryptMD5(model.getPwd(),tmpUser.getSalt());
			
			if(tmpmd5.equals(tmpUser.getPassword()))
			{
				
				List<String> rollList=getRoleById(tmpUser.getId());
				LoginRuselt res=new LoginRuselt();
				res.setUserId(tmpUser.getId());
				res.setUserName(tmpUser.getUsername());
				res.setToken(tmpUser.getPassword());
				tokenUtil.PutToken(res.getToken(), rollList);
				res.setRole(rollList);
				result.setErrcode(0);
				result.setData(res);
			
				System.out.println(tokenUtil.GetToken(res.getToken()));
			}
			else {
				throw new Exception("用户名或密码错误");
			}
		} catch (Exception e) {
			
			result.setErrcode(1);
			result.setErrmsg("用户名或密码错误");
		}
		return result;
	}

}
