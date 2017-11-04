package com.enjoyf.mcms.factory;

import java.util.List;

import com.enjoyf.mcms.bean.DedeAddonarticle;
import com.enjoyf.mcms.bean.DedeArchives;
import com.enjoyf.mcms.bean.JoymeChannelContent;
import com.enjoyf.mcms.bean.JoymeSpec;

public interface IHtmlParseFactory {
    //生成专区页面
    String makeSpecHtml(JoymeSpec spec, List specList, JoymeChannelContent content, String channel, String localPath) throws Exception;
    //生成文章页面
    String makeArchiveHtml(DedeArchives dedeArchives, DedeAddonarticle dedeAddonarticle, String filePath, String fileName, String channel, String localPath) throws Exception;
    //获得文章缓存的地址
    String getArchiveCachePath(String filePath, String fileName, String channel)  throws Exception;
    //获得专题缓存的地址
    String getGameCachePath(String htmlFile, String channel) throws Exception;
}
