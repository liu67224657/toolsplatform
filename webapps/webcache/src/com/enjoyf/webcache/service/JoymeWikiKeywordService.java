package com.enjoyf.webcache.service;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;
import com.enjoyf.webcache.bean.JoymeWikiKeyword;
import com.enjoyf.webcache.dao.JoymeWikiKeywordDAO;

public class JoymeWikiKeywordService {
	
	private static JoymeWikiKeywordDAO joymeWikiKeywordDAO=new JoymeWikiKeywordDAO();
	
	public int insertWikiKeyword(JoymeWikiKeyword joymeWikiKeyword) throws JoymeDBException {
		return joymeWikiKeywordDAO.insertWikiKeyword(joymeWikiKeyword);
	}

	public JoymeWikiKeyword getJoymeWikiKeyword(String wikiId,String keyword) throws JoymeDBException {
		return joymeWikiKeywordDAO.getJoymeWikiKeyword(wikiId,keyword);
	}
	
	public int updateJoymeWikiKeyword(JoymeWikiKeyword joymeWikiKeyword) throws JoymeDBException {
		return joymeWikiKeywordDAO.updateKeyword(joymeWikiKeyword);
	}
	
	public JoymeWikiKeyword getJoymeWikiKeywordByKeywordId(String keywordId) throws JoymeDBException {
		return joymeWikiKeywordDAO.getJoymeWikiKeywordByKeywordId(keywordId);
	}

	public PageRows<JoymeWikiKeyword> queryJoymeWikiKeyword(Pagination page,String wikiId,String name) throws JoymeDBException {
		return joymeWikiKeywordDAO.queryJoymeWikiKeyword(page,wikiId,name);
	}

	public int deleteKeyword(String keywordIds) throws JoymeDBException {
		return joymeWikiKeywordDAO.deleteKeyword(keywordIds);
	}

	public int updateKeywordStatus(String keywordIds,int type) throws JoymeDBException {
		return joymeWikiKeywordDAO.updateKeywordStatus(keywordIds,type);
	}
}
