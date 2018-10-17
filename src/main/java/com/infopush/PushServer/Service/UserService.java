package com.infopush.PushServer.Service;

import com.infopush.PushServer.Entity.JsonResult;
import com.infopush.PushServer.Entity.User;
import com.infopush.PushServer.Model.LoginFormModel;
import com.infopush.PushServer.Model.LoginRuselt;

public interface UserService {
    public User getUserById(Long userId);
    int addUser(User record);
    JsonResult<LoginRuselt> login(LoginFormModel model);
    JsonResult<Boolean> addUser(String token,String name);
}
