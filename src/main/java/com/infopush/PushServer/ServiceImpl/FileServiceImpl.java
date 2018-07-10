package com.infopush.PushServer.ServiceImpl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.infopush.PushServer.Dao.FileInfoMapper;
import com.infopush.PushServer.Entity.FileInfo;
import com.infopush.PushServer.Entity.JsonResult;
import com.infopush.PushServer.Model.UploadStatus;
import com.infopush.PushServer.Service.FileService;
import com.infopush.PushServer.Utils.MyFileUtils;
import com.infopush.PushServer.Utils.MyLogUtils;
import com.sun.tools.internal.ws.processor.model.java.JavaException;
import com.sun.tools.internal.ws.wsdl.document.jaxws.Exception;


@Service
public class FileServiceImpl implements FileService {
	
	private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	
	@Value("${web.upload-path}")
	private String path;
	
	private static long CHUNK_SIZE=1048576;
	@Autowired
	FileInfoMapper fileDao;
	/* (non-Javadoc)
	 * @see com.infopush.PushServer.ServiceImpl.FileService#fileUpload(javax.servlet.http.HttpServletRequest, org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public void fileUpload(HttpServletRequest request,MultipartFile file) throws java.lang.Exception {
		
		String md5 =  request.getParameter("md5");
		if(md5==null)
		{
			throw new java.lang.Exception("文件MD5不存在");
		}
		uploadSingleFile(path,md5,file);
		
	}
	
	
	public void uploadSingleFile(String path,String md5filename,MultipartFile file) throws java.lang.Exception {  
	      
	    if (!file.isEmpty()) {  
	    			String fileName = file.getOriginalFilename(); 
	    			String suffixName = fileName.substring(fileName.lastIndexOf("."));
	    			String newnameString=md5filename+suffixName;
	    			
	    			String filePath = path;
	                File serverFile = new File(filePath+newnameString);//createServerFile(path,file.getOriginalFilename());  
	                if (!serverFile.getParentFile().exists()) {
	                	serverFile.getParentFile().mkdirs();
	                }
	                BufferedOutputStream stream = new BufferedOutputStream(  
	                        new FileOutputStream(serverFile));  
	                int length=0;  
	                byte[] buffer = new byte[1024];  
	                InputStream inputStream = file.getInputStream();  
	                while ((length = inputStream.read(buffer)) != -1) {  
	                    stream.write(buffer, 0, length);  
	                }   
	                stream.flush();  
	                stream.close();  
	                FileInfo fileInfo=new FileInfo();
	                fileInfo.setId(md5filename);
	                fileInfo.setPath(filePath);
	                fileInfo.setFname(file.getOriginalFilename());
	                fileDao.insert(fileInfo);
	                logger.info("Server File Location="  
	                        + serverFile.getAbsolutePath());  
	    }else{  
	        throw new java.lang.Exception("上传文本为空");
	    }  
	     
	}  	
	/**
	 * 上传文件
	 * @param file
	 * @param servletContext
	 * @param chunk
	 * @param chunks
	 * @return
	 */
	public JsonResult<UploadStatus> upload(	String fileName,MultipartFile file,int chunk,int chunks){
		JsonResult<UploadStatus> jsonResult = new JsonResult<>();
		try{
			MyLogUtils.getInstance().log("文件名="+fileName+",分片总数="+chunks+",当前分片索引="+chunk);
			//创建临时文件储存目录
			File tempsDir = new File(path+"temps/"+fileName);
			if(!tempsDir.exists()){
				tempsDir.mkdirs();
			}
			//储存为临时文件
			File tempFile = new File(tempsDir.getAbsolutePath(),fileName+".part"+chunk);
			file.transferTo(tempFile);
			//检查分片是否下载完成
			int successChunks = MyFileUtils.getInstance().successChunks(fileName, chunks);
			//构建返回状态信息
			UploadStatus uploadStatus = new UploadStatus();
			if(successChunks==chunks){
				//分片下载完成后合并分片为文件
				MyFileUtils.getInstance().mergeChunks(tempsDir.getAbsolutePath(),
						path+"files", fileName, chunks);
				//返回文件地址
				uploadStatus.setStatus("success");
				uploadStatus.setResult("files/"+fileName);
				jsonResult.setData(uploadStatus);
			}else{
				uploadStatus.setStatus("uploading");
				jsonResult.setData(uploadStatus);
				jsonResult.setErrcode(0);
			}
		}catch (java.lang.Exception e) {
			// TODO: handle exception
			jsonResult.setErrcode(JsonResult.SERVER_ERR);
			jsonResult.setErrmsg(JsonResult.SERVER_ERR_MSG_UPERROR);
			e.printStackTrace();
		}
		return jsonResult;
	}
	
	/**
	 * 上传文件
	 * @param file
	 * @param servletContext
	 * @param chunk
	 * @param chunks
	 * @return
	 */
	public JsonResult<UploadStatus> uploadRandomAccessFile(	String fileName,MultipartFile file,int chunk,int chunks){
		JsonResult<UploadStatus> jsonResult = new JsonResult<>();
		try{
			
		 if(chunks==1)
		 {
			 MyLogUtils.getInstance().log("文件名="+file.getOriginalFilename()+",分片总数="+chunks+",当前分片索引="+chunk);
			 uploadSingleFile(path,fileName,file); 
			 UploadStatus uploadStatus = new UploadStatus();
	         uploadStatus.setStatus("success");
		     uploadStatus.setResult(fileName);
			 jsonResult.setErrcode(0);
			 jsonResult.setData(uploadStatus);
		 }
		 
		 else {
			String  originalFilename=file.getOriginalFilename();
			MyLogUtils.getInstance().log("文件名="+originalFilename+",分片总数="+chunks+",当前分片索引="+chunk);
			String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
	        String tempFileName = fileName + "_tmp";
	        File tmpDir = new File(path);
	        File tmpFile = new File(path, tempFileName);
	        if (!tmpDir.exists()) {
	            tmpDir.mkdirs();
	        }
	        RandomAccessFile accessTmpFile = new RandomAccessFile(tmpFile, "rw");
	        long offset = CHUNK_SIZE * chunk;
	        //定位到该分片的偏移量
	        accessTmpFile.seek(offset);
	        //写入该分片数据
	        accessTmpFile.write(file.getBytes());
	        // 释放
	        accessTmpFile.close();
	        int successChunks = MyFileUtils.getInstance().successChunks(fileName, chunks);
	        //boolean isOk = checkAndSetUploadProgress(param, tempDirPath);
	        UploadStatus uploadStatus = new UploadStatus();
	        if (successChunks==chunks) {
	        	File fileo = new File(path, tempFileName);
	        	boolean flag =fileo .renameTo(new File(path + fileName+suffixName));
	            //boolean flag = renameFile(tmpFile, fileName);
	            System.out.println("upload complete !!" + flag + " name=" + fileName+suffixName);
	            if(flag==true){
	            	
	                FileInfo fileInfo=new FileInfo();
	                fileInfo.setId(fileName);
	                fileInfo.setPath(path);
	                fileInfo.setFname(file.getOriginalFilename());
	                fileDao.insert(fileInfo);	
	            uploadStatus.setStatus("success");
				uploadStatus.setResult("files/"+fileName);
				jsonResult.setErrcode(0);
				jsonResult.setData(uploadStatus);
			    System.out.println("upload complete !!" + flag + " name=" + fileName);
	            }
	            else {
	            	throw new java.lang.Exception("修改文件名错误");	
				}
	        }
		 }
		}catch (java.lang.Exception e) {
			// TODO: handle exception
			jsonResult.setErrcode(JsonResult.SERVER_ERR);
			jsonResult.setErrmsg(JsonResult.SERVER_ERR_MSG_UPERROR);
			e.printStackTrace();
		}
		return jsonResult;
	}


	@Override
	public JsonResult<String> checkFileExistByMd5(String md5str) {
		JsonResult<String> result=new JsonResult<String>();
		try {
			FileInfo tmpFileInfo=fileDao.selectByPrimaryKey(md5str);
			if(tmpFileInfo==null)
			{
				result.setErrcode(0);
				result.setData("NoExist");
			}
			else {
				result.setErrcode(0);
				result.setData("Exist");
			}
		} catch (java.lang.Exception e) {
			result.setErrcode(1);
			result.setData(JsonResult.SERVER_ERR_MSG_BUSY);
		}
		return result;
		
	}
}
