package com.enjoyf.mcms.factory.impl;

import com.enjoyf.mcms.bean.DedeArctype;
import com.enjoyf.mcms.bean.temp.DedeArchivePageBean;
import com.enjoyf.mcms.factory.IArticleTypeParseFactory;

public class DefaultArticleTypeParseFactoryImpl extends AbstractArticleTypeParseFactoryImpl implements IArticleTypeParseFactory {

    @Override
    public String makeArticleTypeHtml(DedeArchivePageBean dedeArchivePageBean, DedeArctype arctype, String channel, String htmlFile, String localPath, int pageNum) throws Exception {
        return super.makeArticleTypeHtml(dedeArchivePageBean, arctype, channel, htmlFile, localPath, pageNum);
    }

    @Override
    public String getArticleTypePath(String htmlFile, String channel) throws Exception {
        return super.getArticleTypePath(htmlFile, channel);
    }
}
