package com.infopush.PushServer.Dao;

import com.infopush.PushServer.Entity.FileInfo;

public interface FileInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(FileInfo record);

    int insertSelective(FileInfo record);

    FileInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FileInfo record);

    int updateByPrimaryKey(FileInfo record);
}