package com.infopush.PushServer.Control;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.infopush.PushServer.Entity.ActivityInfo;
import com.infopush.PushServer.Entity.JsonResult;
import com.infopush.PushServer.Model.UploadStatus;
import com.infopush.PushServer.Service.ActivityService;
import com.infopush.PushServer.Utils.MyLogUtils;

@RestController
@RequestMapping("/activity")
public class ActivityControl {
	
	@Autowired
	ActivityService activityService;
	
	@RequestMapping(value="checkexist/{name}",method=RequestMethod.POST)
	public JsonResult<Boolean> fileExist(@PathVariable("name") String name)
	{
		return activityService.CheckExist(name);
	}
	
	@RequestMapping(value="get/{name}",method=RequestMethod.POST)
	public JsonResult<ActivityInfo> getactivity(@PathVariable("name") String name)
	{
		return activityService.SelectByName(name);
	}

	@RequestMapping(value="save",method=RequestMethod.POST)
	public JsonResult<Boolean> save(
			@RequestParam(value="name") String name,
			@RequestParam(value="strjson") String strjson,
			@RequestParam(value="userid") Long userid,
			@RequestParam(value="md5") String md5
			)
	{
		ActivityInfo acinfo=new ActivityInfo();
				acinfo.setName(name);
				acinfo.setUserid(userid);
				acinfo.setJsonstr(strjson);
				acinfo.setMd5(md5);
				acinfo.setCreattime(new Date());
				acinfo.setUpdatetime(new Date());
		return activityService.Save(acinfo);	
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	public JsonResult<Boolean> update(
			@RequestParam(value="name") String name,
			@RequestParam(value="strjson") String strjson,
			@RequestParam(value="md5") String md5
			)
	{
		ActivityInfo acinfo=new ActivityInfo();
		        acinfo.setName(name);
				acinfo.setJsonstr(strjson);
				acinfo.setMd5(md5);
				acinfo.setUpdatetime(new Date());
		return activityService.UpdateByName(acinfo);	
	}

	@RequestMapping(value="delete",method=RequestMethod.POST)
	public JsonResult<Boolean> delete(
			@RequestParam(value="name") String name,
			@RequestParam(value="userid") Long userid
			/*@RequestParam(value="md5") String md5*/
			)
	{
		return activityService.DeleteByName(name);	
	}

}
