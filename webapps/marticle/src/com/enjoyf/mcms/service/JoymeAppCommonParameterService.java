package com.enjoyf.mcms.service;

import com.enjoyf.mcms.joymeapp.JoymeAppClientConstant;
import com.enjoyf.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-9-25
 * Time: 下午3:12
 * To change this template use File | Settings | File Templates.
 */
public class JoymeAppCommonParameterService {
	public static JoymeAppClientConstant geAppCommonParameter(HttpServletRequest request) {
		String platform = request.getParameter("platform");
		if (StringUtil.isEmpty(platform)) {
			return null;
		}
		String version = request.getParameter("version");
		if (StringUtil.isEmpty(version)) {
			return null;
		}
		String appkey = request.getParameter("appkey");
		if (StringUtil.isEmpty(appkey)) {
			return null;
		}
		String clientid = request.getParameter("clientid");
		if (StringUtil.isEmpty(clientid)) {
			return null;
		}
		String channelid = request.getParameter("channelid");
		if (StringUtil.isEmpty(channelid)) {
			return null;
		}
		JoymeAppClientConstant constant = new JoymeAppClientConstant();
		try {
			constant.setPlatform(Integer.valueOf(platform));
			constant.setVersion(version);
			constant.setAppkey(appkey);
			constant.setClientid(clientid);
			constant.setChannelid(channelid);
		} catch (Exception e) {
			return null;
		}
		return constant;
	}
}
