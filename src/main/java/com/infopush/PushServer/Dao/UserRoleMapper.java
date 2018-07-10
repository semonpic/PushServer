package com.infopush.PushServer.Dao;

import java.util.List;

import com.infopush.PushServer.Entity.UserRole;

public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);
    
    List<UserRole> getUserRole(Long userId);
}