package com.enjoyf.mcms.service;

import javax.servlet.http.HttpServletRequest;

public class ChannelService {
    public static String getChannel(HttpServletRequest request){
        String channel = request.getHeader("channel") == null ? "default" : request.getHeader("channel") ;
//        channel = "json";
        return channel;
    }
    
}
