package com.enjoyf.mcms.factory.impl;

import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.enjoyf.mcms.bean.DedeAddonarticle;
import com.enjoyf.mcms.bean.DedeArchives;
import com.enjoyf.mcms.bean.JoymeChannelContent;
import com.enjoyf.mcms.bean.JoymeSpec;
import com.enjoyf.mcms.factory.IHtmlParseFactory;

public class M360ParseFactoryImpl extends AbstractParseFactoryImpl implements IHtmlParseFactory {
    public String makeSpecHtml(JoymeSpec spec, List specList, JoymeChannelContent content, String channel, String localPath) throws Exception {
        return super.makeSpecHtml(spec, specList, content, channel, localPath);
    }

    public String makeArchiveHtml(DedeArchives dedeArchives, DedeAddonarticle dedeAddonarticle, String filePath, String fileName, String channel,
            String localPath) throws Exception {
        return super.makeArchiveHtml(dedeArchives, dedeAddonarticle, filePath, fileName, channel, localPath);
    }

    protected void updateLink(Document document) {
        Elements elements = document.getElementsByTag("a");
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            element.remove();
        }
    }
}
