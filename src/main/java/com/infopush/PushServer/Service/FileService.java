package com.infopush.PushServer.Service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.infopush.PushServer.Entity.JsonResult;
import com.infopush.PushServer.Model.UploadStatus;


public interface FileService {

	void fileUpload(HttpServletRequest request, MultipartFile file) throws java.lang.Exception;
	JsonResult<UploadStatus> upload(	String fileName,MultipartFile file,int chunk,int chunks);
	 //
	JsonResult<UploadStatus> uploadRandomAccessFile(	String fileName,MultipartFile file,int chunk,int chunks);
	JsonResult<String> checkFileExistByMd5(String md5str);
}