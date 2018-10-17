package com.infopush.PushServer.Model;

import static org.mockito.Matchers.booleanThat;

import java.util.List;

import com.infopush.PushServer.Entity.User;

public class UserSession {
	private User user;
	private List<String> rooList;
	public List<String> getRooList() {
		return rooList;
	}
	public void setRooList(List<String> rooList) {
		this.rooList = rooList;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public  UserSession(User _user,List<String> _roolList) {
		
		this.rooList=_roolList;
		this.user=_user;
	}
	@Override
	public String toString() {
		return "UserSession [user=" + user + ", rooList=" + rooList + "]";
	}
	
	public boolean hasRool(String _rool) {
		boolean res=false;
		if(_rool!=null && _rool.length()>0)
		{
			if(this.rooList!=null)
			{
				for (String str : rooList) {
					if(str.equals(_rool))
					{
						res=true;
						break;
					}
				}
			}
			
		}
		
		return res;
	}
}
