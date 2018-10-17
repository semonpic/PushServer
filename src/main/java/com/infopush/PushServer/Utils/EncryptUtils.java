package com.infopush.PushServer.Utils;

import static org.hamcrest.CoreMatchers.nullValue;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectRestriction.ReadOnly;

public class EncryptUtils {
	
	private static  Logger log = Logger.getLogger(EncryptUtils.class);	
	/**
     * MD5加密
     *
     * @param password
     * @return
     */
    public static String encryptMD5(String password) {

        return encrypt(password);
    }

    /**
     * MD5 + salt
     *
     * @param password
     * @param salt
     * @return
     */
    public static String encryptMD5(String password, String salt) {

        return encrypt(password + salt);
    }

    private static String encrypt(String password)  {
        byte[] digest = null;
        try {
            //首先生成一个 MessageDigest 类 , 确定计算方法
            MessageDigest md = MessageDigest.getInstance("MD5");
            //添加要进行计算摘要的信息
            md.update((password).getBytes());
            //计算出摘要
            digest = md.digest();

        } catch (NoSuchAlgorithmException e) {
           // System.out.println("非法摘要算法");
            //e.printStackTrace();
        	log.warn("md5 错误", e);
        }
        String result=null;
		try {
			result = Base64Util.encode(digest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.warn("md5 错误", e);
		}
        return result;

    }

    /***
     * 计算字符串MD5值，以小写的16进制字符串返回
     * @param str
     * @return
     */
    public static String HexMd5(String str)
    {
    	
    	 byte[] digest = null;
         try {
             //首先生成一个 MessageDigest 类 , 确定计算方法
             MessageDigest md = MessageDigest.getInstance("MD5");
             //添加要进行计算摘要的信息
             md.update((str).getBytes("utf-8"));
             //计算出摘要
             digest = md.digest();

         } catch (Exception e) {
            // System.out.println("非法摘要算法");
             //e.printStackTrace();
         	log.warn("md5 错误", e);
         }
         String result=null;
 		try {
 			result = byte2hex(digest);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			log.warn("md5 错误", e);
 		}
         return result;
    }
    
    
    //二行制转字符串
    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) hs = hs + "0" + stmp;
            else hs = hs + stmp;
            if (n < b.length - 1) hs = hs + ":";
        }
        return hs.toUpperCase();
    }

//    public static void main(String[] args) {
//        String password = "root123";
//        System.out.println(password + " -- " + EncryptUtils.encryptMD5(password,"asddffgasdghkpiuyy"));
//    }
}
