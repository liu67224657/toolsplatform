package com.enjoyf.activity.util;


import com.enjoyf.util.StringUtil;

public class SystemUtil {
	
	
	public String getMetaInfFolderPath() {
		String path = SystemUtil.class.getProtectionDomain().getCodeSource()
				.getLocation().getFile();
		int position = path.indexOf("WEB-INF/classes/");

		if (position >= 0) {
			path = path.substring(0, position);
			return path + "META-INF";
		}
		return null;
	}

	public String getWebRootPath() {
		String path = SystemUtil.class.getProtectionDomain().getCodeSource()
				.getLocation().getFile();
		int position = path.indexOf("WEB-INF/classes/");

		if (position >= 0) {
			path = path.substring(0, position);
			return path;
		}
		return null;
	}
	public static String getSubNickname(String nickname,int length){
		return (!StringUtil.isEmpty(nickname) && nickname.length()>length)?nickname.substring(0,length):nickname;
	}
	



	
	
}
