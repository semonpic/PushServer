package com.infopush.PushServer.Service;


import com.infopush.PushServer.Entity.ActivityInfo;
import com.infopush.PushServer.Entity.JsonResult;

public interface ActivityService {
	
	JsonResult<Boolean> CheckExist(String activityName);
	JsonResult<Boolean> Save(ActivityInfo activity);
	JsonResult<ActivityInfo> SelectByName(String name);
	JsonResult<Boolean> UpdateByName(ActivityInfo activity);
	JsonResult<Boolean> DeleteByName(String name);

}
