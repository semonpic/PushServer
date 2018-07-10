package com.infopush.PushServer.Dao;

import com.infopush.PushServer.Entity.ClientInfo;

public interface ClientInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ClientInfo record);

    int insertSelective(ClientInfo record);

    ClientInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ClientInfo record);

    int updateByPrimaryKey(ClientInfo record);
}