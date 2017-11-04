package com.enjoyf.webcache.util;

import com.enjoyf.util.MD5Util;

/**
 * 数据id生成工具类
 * @author huazhang
 *
 */
public class GenerateIdUtil {

	public static GenerateIdUtil instance=null;
	
	private static Object lock=new Object();
	
	private GenerateIdUtil(){
		
	}
	
	public static GenerateIdUtil getInstance(){
		synchronized (lock) {
			if (null == instance) {
				instance=new GenerateIdUtil();
			}
		}
		return instance;
	}
	
	public  String getDataIdByMD5(String key){
		return MD5Util.Md5(key);
	}
	
	public  String getDataIdByMD5(String key,String ext){
		return MD5Util.Md5(key+ext);
	}
}
