package com.enjoyf.mcms.factory;

import com.enjoyf.mcms.bean.*;
import com.enjoyf.mcms.bean.temp.DedeArchivePageBean;


public interface IArticleTypeParseFactory {

    String makeArticleTypeHtml(DedeArchivePageBean dedeArchivePageBean, DedeArctype arctype, String channel, String htmlFileName, String localPath, int pageNum) throws Exception;

    String getArticleTypePath(String htmlFile, String channel) throws Exception;

}
