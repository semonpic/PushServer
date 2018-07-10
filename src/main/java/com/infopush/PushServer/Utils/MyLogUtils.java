package com.infopush.PushServer.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 打印工具
 * @author 许映杰
 *
 */
public class MyLogUtils {
	private static MyLogUtils instance = new MyLogUtils();
	private SimpleDateFormat simpleDateFormat;
	
	public static MyLogUtils getInstance(){
		return instance;
	}
	
	public MyLogUtils() {
		// TODO Auto-generated constructor stub
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 后台打印信息
	 * @param message
	 */
	public void log(String message){
		String time = simpleDateFormat.format(new Date());
		System.out.println("XULovers=>"+time+"=>"+message);
	}
}
