package com.enjoyf.util.cdn;

/**
 * Created by zhitaoshi on 2015/10/12.
 */
public class CdnRefreshFactory {

    private static final String ALY = "aly";
    private static final String QN = "qn";

    public static ICdnRefresh getFactory(String cdnName){
        if(ALY.equalsIgnoreCase(cdnName)){
            return new AliYunCDNRefresh();
        }else if(QN.equalsIgnoreCase("qn")){
            return new QiNiuCDNRefresh();
        }else {
            return new DefaultCDNRefresh();
        }

    }
}
