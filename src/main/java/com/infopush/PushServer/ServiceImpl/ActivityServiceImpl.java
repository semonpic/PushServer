package com.infopush.PushServer.ServiceImpl;

import static org.mockito.Mockito.reset;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.infopush.PushServer.Dao.ActivityInfoMapper;
import com.infopush.PushServer.Entity.ActivityInfo;
import com.infopush.PushServer.Entity.JsonResult;
import com.infopush.PushServer.Service.ActivityService;
@Service
public class ActivityServiceImpl implements ActivityService{

	@Resource
	ActivityInfoMapper activityDao;
	
	/**
	 * 
	 */
	@Override
	public JsonResult<Boolean> CheckExist(String activityName) {
		// TODO Auto-generated method stub
		JsonResult<Boolean> result=new JsonResult<Boolean>();
		try {
			ActivityInfo activitytmp=activityDao.selectByName(activityName);
			result.setErrcode(0);
			if(activitytmp!=null)
			{
				result.setData(true);
			}
			else {
				result.setData(false);
			}
		} catch (Exception e) {
			result.setErrcode(1);
			result.setErrmsg(e.toString());
		}

		
		return result;
	}

	@Override
	public JsonResult<Boolean> Save(ActivityInfo activity) {
		// TODO Auto-generated method stub
		JsonResult<Boolean> result=new JsonResult<Boolean>();
		try {
			activityDao.insert(activity);
			result.setErrcode(0);
			result.setData(true);
		
		} catch (Exception e) {
			result.setErrcode(1);
			result.setErrmsg(e.toString());
		}
		return result;
	}

	@Override
	public JsonResult<ActivityInfo> SelectByName(String name) {
		// TODO Auto-generated method stub
		
		
		
		// TODO Auto-generated method stub
				JsonResult<ActivityInfo> result=new JsonResult<ActivityInfo>();
				try {
					ActivityInfo activitytmp=activityDao.selectByName(name);
					
					if(activitytmp!=null)
					{
						
						result.setData(activitytmp);
					}
					else {
						result.setErrcode(1);
						result.setErrmsg("No Record");
					}
				} catch (Exception e) {
					result.setErrcode(1);
					result.setErrmsg(e.toString());
				}

				
				return result;
	}

	@Override
	public JsonResult<Boolean> UpdateByName(ActivityInfo activity) {
		// TODO Auto-generated method stub
				JsonResult<Boolean> result=new JsonResult<Boolean>();
				try {
					activityDao.updateByName(activity);
					result.setErrcode(0);
					result.setData(true);
				
				} catch (Exception e) {
					result.setErrcode(1);
					result.setErrmsg(e.toString());
				}
				return result;
	}

	@Override
	public JsonResult<Boolean> DeleteByName(String name) {
		JsonResult<Boolean> result=new JsonResult<Boolean>();
		ActivityInfo activitytmp=activityDao.selectByName(name);
		try {
			activityDao.deleteByPrimaryKey(activitytmp.getId());
			result.setErrcode(0);
			result.setData(true);
			
		} catch (Exception e) {
			result.setErrcode(0);
			result.setData(false);
			// TODO: handle exception
		}
		return result;
	}

	
	
}
