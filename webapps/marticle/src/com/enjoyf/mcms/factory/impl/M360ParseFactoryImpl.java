package com.enjoyf.mcms.factory.impl;

import com.enjoyf.mcms.factory.IHtmlParseFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;

public class M360ParseFactoryImpl extends AbstractParseFactoryImpl implements IHtmlParseFactory {

    protected void updateLink(Document document) {
        Elements elements = document.getElementsByTag("a");
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            element.remove();
        }
    }
}
