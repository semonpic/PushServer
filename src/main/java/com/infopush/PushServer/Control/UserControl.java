package com.infopush.PushServer.Control;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.infopush.PushServer.Entity.JsonResult;
import com.infopush.PushServer.Entity.User;
import com.infopush.PushServer.Model.LoginFormModel;
import com.infopush.PushServer.Model.LoginRuselt;
import com.infopush.PushServer.Service.UserService;
import com.infopush.PushServer.ServiceImpl.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserControl {
	@Resource
	UserServiceImpl userService;
	
	@RequestMapping("/showUser")
	public JsonResult<User> toIndex(HttpServletRequest request, Model model){
	    Long userId = Long.parseLong(request.getParameter("id"));
	    User user = this.userService.getUserById(userId);
	    JsonResult<User> result=new JsonResult<User>();
	    if(user!=null)
	    {
	    	result.setErrcode(0);
	    	result.setData(user);
	    }
	    else {
	    	result.setErrcode(1);
	    	result.setErrmsg("用户不存");
		}
	    return result;
	}
	
	
	
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public JsonResult<LoginRuselt> login(@RequestParam(value="name") String name,@RequestParam(value="pwd") String pwd)
	{
		JsonResult<LoginRuselt> res=new JsonResult<LoginRuselt>();
		LoginFormModel formModel=new LoginFormModel();
		formModel.setUserName(name);
		formModel.setPwd(pwd);
		return userService.login(formModel);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public JsonResult<Boolean> useradd(@RequestParam(value="name") String name,@RequestParam(value="token") String token)
	{
		JsonResult<LoginRuselt> res=new JsonResult<LoginRuselt>();
		
		return userService.addUser(token, name);
	}

	
}
