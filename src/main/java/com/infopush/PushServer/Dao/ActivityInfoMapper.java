package com.infopush.PushServer.Dao;

import com.infopush.PushServer.Entity.ActivityInfo;

public interface ActivityInfoMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(ActivityInfo record);
    int insertSelective(ActivityInfo record);
    ActivityInfo selectByPrimaryKey(Integer id);
    ActivityInfo selectByName(String name);
    int updateByPrimaryKeySelective(ActivityInfo record);

    int updateByPrimaryKeyWithBLOBs(ActivityInfo record);

    int updateByPrimaryKey(ActivityInfo record);
    int updateByName(ActivityInfo record);
}