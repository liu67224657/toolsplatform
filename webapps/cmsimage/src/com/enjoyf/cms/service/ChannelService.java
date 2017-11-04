package com.enjoyf.cms.service;

import com.enjoyf.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

public class ChannelService {
	public static String getChannel(HttpServletRequest request) {
		String channel = request.getHeader("channel");

		String channelByRequest = request.getParameter("channel");
		if (!StringUtil.isEmpty(channelByRequest)) {
			channel = channelByRequest;
		}

		if (StringUtil.isEmpty(channel)) {
			channel = "default";
		}
		return channel;
	}

}
