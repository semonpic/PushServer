package com.infopush.PushServer.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 分片文件处理工具类
 * @author 许映杰
 *
 */
public class MyFileUtils {
	//记录已经上传成功的分片文件数量，线程安全
	private static ConcurrentHashMap<String,Integer> successChunksCount = new ConcurrentHashMap<>();
	private static MyFileUtils instance = new MyFileUtils();
	
	public static MyFileUtils getInstance(){
		return instance;
	}
	
	/**
	 * 每上传成功一个文件的分片，就在文件名对应的值加1
	 * 对比分片总数，如果上传成功分片数等于分片总数则调用合并文件方法
	 * @param fileName
	 * @param chunks
	 * @return
	 */
	public int successChunks(String fileName,int chunks){
		int chunksNow = successChunksCount.getOrDefault(fileName, 0);
		chunksNow ++;
		if(chunks==chunksNow){
			successChunksCount.remove(fileName);
		}else{
			successChunksCount.put(fileName, chunksNow);
		}
		return chunksNow;
	}
	
	/**
	 * 上传完成后合并分片文件
	 * @param sourceDirPath 分片的目录地址
	 * @param destDirPath	合成后存放的目录地址
	 * @param fileName	文件名
	 * @param chunks	分片总数
	 * @return
	 * @throws FileNotFoundException 
	 */
	public boolean mergeChunks(String sourceDirPath,
			String destDirPath,String fileName,int chunks) throws Exception{
		//创建目标目录
		File destDir = new File(destDirPath);
		if(!destDir.exists()){
			destDir.mkdirs();
		}
		//创建目标文件流
		BufferedOutputStream destOutputStream = 
				new BufferedOutputStream(new FileOutputStream(destDir+"/"+fileName));
		
		//循环将每个分片的数据写入目标文件
		byte[] fileBuffer = new byte[1024];//文件读写缓存
		int readBytesLength = 0;//每次读取字节数
		MyLogUtils.getInstance().log("开始合并文件="+fileName);
		for(int i=0; i<chunks; i++){
			File sourceFile = new File(sourceDirPath+"/"+fileName+".part"+i);
			BufferedInputStream sourceInputStream = 
					new BufferedInputStream(new FileInputStream(sourceFile));
			while((readBytesLength=sourceInputStream.read(fileBuffer))!=-1){
				destOutputStream.write(fileBuffer, 0, readBytesLength);
			}
			sourceInputStream.close();
			//分片用完后删除
			sourceFile.delete();
		}
		//文件合并完成后删除临时文件夹
		File sourceDir = new File(sourceDirPath);
		sourceDir.delete();
		destOutputStream.flush();
		destOutputStream.close();
		MyLogUtils.getInstance().log("文件合并完成="+fileName);
		return false;
	}
}
