package com.enjoyf.mcms.factory;


public interface ITagParseFactory {

    String parseTagList(int tagId, int pageNum, String channel,String plat) throws Exception;

    String getTagCachePath(int tagId, int pageNum, String channel, String plat) throws Exception;

}
