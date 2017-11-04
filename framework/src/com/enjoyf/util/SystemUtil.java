package com.enjoyf.util;


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

	



	
	
}
