package com.enjoyf.cms.factory;


public class HtmlFactory {

    public static IHtmlPraser getFactory(String host) {
        if (host.startsWith("www.")) {
            return new DefaultHtmlPraser();
        } else if (host.startsWith("m.")) {
            return new MHtmlPraser();
        } else {
            return new DefaultHtmlPraser();
        }
    }
}
