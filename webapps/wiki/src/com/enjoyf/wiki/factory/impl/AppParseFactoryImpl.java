package com.enjoyf.wiki.factory.impl;


import com.enjoyf.wiki.factory.WikiPraseParam;
import org.jsoup.nodes.Document;

import java.io.UnsupportedEncodingException;

public class AppParseFactoryImpl extends AbstractParseFactoryImpl  {

    @Override
    protected Document genHtmlByDocument(Document doc, WikiPraseParam param) throws Exception {
        return super.genHtmlByDocument(doc,param);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
