package com.infopush.PushServer.Control;

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
	
	@RequestMapping("/login")
	public JsonResult<LoginRuselt> login(@Valid @RequestBody LoginFormModel formModel,BindingResult result)
	{
		JsonResult<LoginRuselt> res=new JsonResult<LoginRuselt>();
		
		if (result.hasErrors()) {
            // 0101 存储错误信息的字符串变量
            StringBuffer msgBuffer = new StringBuffer();
            // 0102 错误字段集合
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                // 0103 获取错误信息
                msgBuffer.append(fieldError.getField() + ":" + fieldError.getDefaultMessage());
            }
            res.setErrcode(1);
            res.setErrmsg(msgBuffer.toString());
        }
		
		return userService.login(formModel);
	}
	


	
}
