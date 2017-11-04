package com.enjoyf.wiki.factory.impl;


import com.enjoyf.wiki.factory.WikiPraseParam;
import org.jsoup.nodes.Document;

import java.io.UnsupportedEncodingException;

public class M360ParseFactoryImpl extends AbstractParseFactoryImpl {

    /**
     * 去掉91去掉外联
     * @param doc
     * @param
     * @return
     * @throws Exception
     * @throws UnsupportedEncodingException
     */
    @Override
    protected Document genHtmlByDocument(Document doc,WikiPraseParam param) throws Exception {
        return super.genHtmlByDocument(doc, param);    //To change body of overridden methods use File | Settings | File Templates.
    }

}
