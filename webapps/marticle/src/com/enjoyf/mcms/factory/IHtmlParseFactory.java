package com.enjoyf.mcms.factory;

import com.enjoyf.mcms.bean.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface IHtmlParseFactory {
    //生成专区页面
    String makeSpecHtml(Connection conn, JoymeSpec spec, List specList, JoymeChannelContent content, String channel, String localPath) throws Exception;

    //生成文章页面
    String makeArchiveHtml(DedeArchives dedeArchives, DedeAddonarticle dedeAddonarticle, CmsAddress cmsAddress, String filePath, String fileName, String channel, String localPath, String plat, String uri) throws Exception;

    //获得文章缓存的地址
    String getArchiveCachePath(String filePath, String fileName, String channel, String plat) throws Exception;

    //获得专题缓存的地址
    String getGameCachePath(String htmlFile, String channel) throws Exception;
}
