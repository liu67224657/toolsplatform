package com.enjoyf.wiki.service;

import com.enjoyf.util.StringUtil;
import com.enjoyf.wiki.container.ChannelContainer;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.factory.IHtmlParseFactory;
import com.enjoyf.wiki.tools.WikiUtil;

import javax.servlet.http.HttpServletRequest;

public class ChannelService {
    public boolean checkDownloadByChannel(HttpServletRequest request, String wikiType) {
        boolean isDownload = PropertiesContainer.getInstance().isDownload();
        String channel = getChannelByRequest(request);
        if (!wikiType.equals(WikiUtil.MOBILE_WIKI)) {
            isDownload = false;
            return isDownload;
        }

        if (channel != null && channel.equals("360")) {
            isDownload = false;
        }
        return isDownload;
    }

    public IHtmlParseFactory getHtmlParseFactory(String code) {
        if (code == null || code.equals("") || !ChannelContainer.channelHtmlFacotryMap.containsKey(code)) {
            return ChannelContainer.channelHtmlFacotryMap.get("default");
        }

        return ChannelContainer.channelHtmlFacotryMap.get(code);
    }


    public static String getChannelByRequest(HttpServletRequest request) {
        if (PropertiesContainer.getInstance().getDefaultChannel() != null) {
            return PropertiesContainer.getInstance().getDefaultChannel();
        }

        String channelCode = request.getHeader("channel");

        String channelByRequest = request.getParameter("channel");
        if (!StringUtil.isEmpty(channelByRequest)) {
            channelCode = channelByRequest;
        }

        if (StringUtil.isEmpty(channelCode) || !ChannelContainer.channelHtmlFacotryMap.containsKey(channelCode)) {
            return "default";
        }

        return channelCode;
    }
}
