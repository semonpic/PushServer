package com.infopush.PushServer.Dao;

import java.util.List;

import com.infopush.PushServer.Entity.ClientGroup;

public interface ClientGroupMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(ClientGroup record);
    int insertSelective(ClientGroup record);
    ClientGroup selectByPrimaryKey(Integer id);
    
    List<ClientGroup> selectByParentId(Integer pid);
    List<ClientGroup> selectAll();
    int updateByPrimaryKeySelective(ClientGroup record);

    int updateByPrimaryKey(ClientGroup record);
}