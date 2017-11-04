package com.joyme.util;

/**
 * Created by IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-1-23
 * Time: 上午10:12
 * 读取配置路径
 */
public class SystemUtil {

    /**
     * 获取META-INF路径
     * @return
     */
    public String getMetaInfFolderPath(){
        String path = SystemUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        int position = path.indexOf("WEB-INF/classes/");

		if (position >= 0) {
			path = path.substring(0, position);
			return path + "META-INF";
		}
		return null;

    }

    /**
     * 获取WEB-INF路径
     * @return
     */
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
