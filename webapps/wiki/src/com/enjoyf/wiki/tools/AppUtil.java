package com.enjoyf.wiki.tools;

import javax.servlet.http.HttpServletRequest;


public class AppUtil {
	private static final String[] MOBILE_SPECIFIC_SUBSTRING = { "iPad", "iPhone", "Android"};
	
	private static final String[] MOBILE_SPECIFIC_SUBSTRING_ANDROID = { "Android"};
	
	private static final String[] MOBILE_SPECIFIC_SUBSTRING_IOS = { "iPad", "iPhone" };
	/**
	 * 检查是否是移动端
	 * @param request
	 * @return
	 */
	public boolean checkMobile(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		for (String mobile : MOBILE_SPECIFIC_SUBSTRING) {
			if (userAgent.contains(mobile)
					|| userAgent.contains(mobile.toUpperCase())
					|| userAgent.contains(mobile.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkIsAndroid(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		if(userAgent != null){
			for (String mobile : MOBILE_SPECIFIC_SUBSTRING_ANDROID) {
				if (userAgent.contains(mobile)
						|| userAgent.contains(mobile.toUpperCase())
						|| userAgent.contains(mobile.toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean checkIsIOS(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		if(userAgent != null){
    		for (String mobile : MOBILE_SPECIFIC_SUBSTRING_IOS) {
    			if (userAgent.contains(mobile)
    					|| userAgent.contains(mobile.toUpperCase())
    					|| userAgent.contains(mobile.toLowerCase())) {
    				return true;
    			}
    		}
		}
		return false;
	}
}
