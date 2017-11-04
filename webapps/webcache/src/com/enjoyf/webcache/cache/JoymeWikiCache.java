package com.enjoyf.webcache.cache;

import com.enjoyf.webcache.container.PropertiesContainer;

public class JoymeWikiCache {
	
	
	private static final String WIKI_KEYWORD_NEW_ADD="WIKI_KEYWORD_NEW_NUM_ADD_";
	
	public static void addNewWikiKeyword(String wikiId,String keywordId){
		PropertiesContainer.getInstance().getRedisManager().sadd(WIKI_KEYWORD_NEW_ADD+wikiId, keywordId);
	}
	
	public static long getNewWikiKeywordNum(String wikiId){
		return PropertiesContainer.getInstance().getRedisManager().scard(WIKI_KEYWORD_NEW_ADD+wikiId);
	}
	
	public static void removeNewWikiKeyword(String wikiId,String keywordId) {
		if (keywordId==null) {
			PropertiesContainer.getInstance().getRedisManager().remove(WIKI_KEYWORD_NEW_ADD+wikiId);
		}else {
			PropertiesContainer.getInstance().getRedisManager().srem(WIKI_KEYWORD_NEW_ADD+wikiId, keywordId);
		}
	}
}
