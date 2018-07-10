package com.infopush.PushServer.Model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;


public class LoginFormModel {
    @NotEmpty(message = "用户名不能为空")
    @NotBlank(message = "用户名不能为空")
	private String userName;
    public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@NotEmpty(message = "密码不能为空")
    @NotBlank(message = "密码不能为空")
	private String pwd;
}
