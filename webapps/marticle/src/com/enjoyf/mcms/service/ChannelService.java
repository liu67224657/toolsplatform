package com.enjoyf.mcms.service;

import com.enjoyf.mcms.joymeapp.JoymeAppClientConstant;
import com.enjoyf.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

public class ChannelService {
	public static String getChannel(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String ch = "default";
		String channel[] = uri.split("/");

		if (channel.length > 2) {
			ch = channel[1];
		}

		if (ch.equals("marticle")) {
			ch = "default";
		}

		String channelByRequest = request.getParameter("channel");
		if (!StringUtil.isEmpty(channelByRequest)) {
			ch = channelByRequest;
		}


		//joymeapp 公共参数
		JoymeAppClientConstant joymeAppClientConstant = JoymeAppCommonParameterService.geAppCommonParameter(request);
		if (joymeAppClientConstant != null && ch.contains("json")) {
			ch = JoymeAppClientConstant.APP_CHANNEL_NAME;
		}

		return ch;


//        String channel = request.getHeader("channel");
//
//        String channelByRequest = request.getParameter("channel");
//        if (!StringUtil.isEmpty(channelByRequest)) {
//            channel = channelByRequest;
//        }
//
//        channel = channel == null ? "default" : channel;
//        if (ConfigContainer.getChannelId() != null)
//            channel = ConfigContainer.getChannelId();
//      return channel;
	}

}
