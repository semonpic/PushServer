package com.infopush.PushServer.Control;


import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.infopush.PushServer.Entity.JsonResult;
import com.infopush.PushServer.Model.UploadStatus;
import com.infopush.PushServer.Service.FileService;
import com.infopush.PushServer.Utils.MyLogUtils;



@RestController
@RequestMapping("/upload")
public class FileControl {
	
	private static final Logger logger = LoggerFactory.getLogger(FileControl.class);
	
	@Value("${web.upload-path}")
    private String path;
	
	@Autowired
	FileService fileservice;
	
	@RequestMapping("/img")
	public JsonResult<String> imgupload(HttpServletRequest request,MultipartFile file)
	{
		String param1 =  request.getParameter("md5");
		logger.info("md5:"+param1);
		JsonResult<String> result=new JsonResult<String>();
		
		if (file.isEmpty()) {
			result.setErrcode(1);
			result.setErrmsg("文件为空");
            return result;
        }
        // 获取文件名
        String fileName = file.getOriginalFilename(); 
        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = path+"img//";

        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        	result.setErrcode(0);
			result.setErrmsg("上传成功");
            return result;
        } catch (IllegalStateException e) {
           logger.warn(e.toString());
        } catch (IOException e) {
        	logger.warn(e.toString());
        }
		result.setErrcode(1);
		result.setErrmsg("上传失败");
        return result;
	}

	
	@RequestMapping("/file")
	public JsonResult<String> upload(HttpServletRequest request,MultipartFile file)
	{
		JsonResult<String> result=new JsonResult<String>();
        try {

        	fileservice.fileUpload(request, file);
        	result.setErrcode(0);
			result.setErrmsg("上传成功");
            return result;
        } catch (IllegalStateException e) {
           logger.warn(e.toString());
        } catch (IOException e) {
        	logger.warn(e.toString());
        } catch (Exception e) {
			// TODO Auto-generated catch block
        	logger.warn(e.toString());
		}
		result.setErrcode(1);
		result.setErrmsg("上传失败");
        return result;
	}

	@Resource
	private FileService fileService;
	/**
	 * 上传文件
	 * @param file		//上传的文件
	 * @param chunk		//分片文件当前的分片
	 * @param chunks	//分片文件的总片数
	 * @return
	 */
	@RequestMapping(value="file/up",method=RequestMethod.POST)
	public JsonResult<UploadStatus> upload(
			@RequestParam(value="fileName") String fileName,
			@RequestParam(value="file") MultipartFile file,
			@RequestParam(value="chunk") int chunk,
			@RequestParam(value="chunks") int chunks){
		MyLogUtils.getInstance().log("开始上传文件");
		//return fileService.upload(fileName,file,chunk,chunks);
		return fileService.uploadRandomAccessFile(fileName,file,chunk,chunks);
	}
	
	@RequestMapping(value="file/checkexist/{md5}",method=RequestMethod.POST)
	public JsonResult<String> fileExist(@PathVariable("md5") String md5)
	{
		
		return fileservice.checkFileExistByMd5(md5);
		
	}

}
