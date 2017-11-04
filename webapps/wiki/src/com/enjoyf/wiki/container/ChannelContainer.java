package com.enjoyf.wiki.container;

import com.enjoyf.wiki.factory.IHtmlParseFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-8-26 上午9:14
 * Description:
 */
public class ChannelContainer {

    //优酷渠道
    public static String YOUKU_CHANNEL = "youku";

    //微信渠道
    public static String WX_CHANNEL = "wx";

    public static Map<String, IHtmlParseFactory> channelHtmlFacotryMap = new HashMap<String, IHtmlParseFactory>();

}
