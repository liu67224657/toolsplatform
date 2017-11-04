package com.enjoyf.webcache.service;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;
import com.enjoyf.webcache.bean.JoymeWikiInfo;
import com.enjoyf.webcache.dao.JoymeWikiInfoDAO;

/**
 * wiki文章标题
 * @author huazhang
 *
 */
public class JoymeWikiInfoService {

	private static JoymeWikiInfoDAO JoymeWikiInfoDAO = new JoymeWikiInfoDAO();

	public int insertWikiTitle(JoymeWikiInfo JoymeWikiInfo) throws JoymeDBException {
		return JoymeWikiInfoDAO.insertWikiTitle(JoymeWikiInfo);
	}

	public JoymeWikiInfo getJoymeWikiInfoByName(String wikiName) throws JoymeDBException {
		return JoymeWikiInfoDAO.getJoymeWikiInfoByName(wikiName);
	}

	public PageRows<JoymeWikiInfo> queryJoymeWikiInfo(Pagination page,String name) throws JoymeDBException {
		return JoymeWikiInfoDAO.queryJoymeWikiInfo(page,name);
	}

	
	public int updateWikiKeywordNumById(String wikiId,int type) throws JoymeDBException{
		return JoymeWikiInfoDAO.updateWikiKeywordNumById(wikiId, type);
	}
}
