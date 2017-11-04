package com.enjoyf.wiki.factory;

public interface IHtmlParseFactory {

    public String getFileDirByChannel(String channel, String key,boolean isSEO,String wikiType);

    /**
     * /opt/wikihtml/default/mt_seo/ /opt/wikihtml/default/mt_noseo/
     * @param channel
     * @param key
     * @param isSEO
     * @param fileName
     * @return
     */
    public String getFullFileName(String channel, String key, boolean isSEO,String fileName,String wikiType);

    /**
     * @param domain       域名，没有最后的/
     * @param path         /wiki/
     * @param saveFileName 保存页面的名称 index.shtml 1.shtml
     * @param key          mt, mkhx...
     * @param url          http://mt.joyme.com/wiki/****
     * @return
     * @throws Exception
     * @throws Exception
     */
    public String parseURLAndSave(String channel, WikiPraseParam param) throws Exception,
            Exception;

}
