package com.enjoyf.wiki.service;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.wiki.bean.WikiPage;
import com.enjoyf.wiki.bean.WikiRecommend;
import com.enjoyf.wiki.dao.WikiRecommendDao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-7-22
 * Time: 下午3:19
 * To change this template use File | Settings | File Templates.
 */
public class WikiRecommendService {
	private static WikiRecommendDao subDao = new WikiRecommendDao();

	public WikiRecommend queryWikiRecommend(Connection conn, String key, String title) throws JoymeDBException, JoymeServiceException {
		boolean isCloseConn = (conn != null) ? false : true;
		try {
			if (conn == null)
				conn = subDao.getConnection();
			return subDao.queryWikiRecommend(conn, key, title);
		} catch (JoymeDBException e) {
			throw e;
		} catch (Exception e) {
			throw new JoymeServiceException(e);
		} finally {
			if (conn != null && isCloseConn)
				subDao.closeConnection(conn);
		}
	}

	public WikiRecommend queryWikiRecommend(Connection conn, String key, Date start, Date end, String url) throws JoymeDBException, JoymeServiceException {
		boolean isCloseConn = (conn != null) ? false : true;
		try {
			if (conn == null)
				conn = subDao.getConnection();
			return subDao.queryWikiRecommend(conn, key, start, end, url);
		} catch (JoymeDBException e) {
			throw e;
		} catch (Exception e) {
			throw new JoymeServiceException(e);
		} finally {
			if (conn != null && isCloseConn)
				subDao.closeConnection(conn);
		}
	}

	public int insertWikiRecommend(Connection conn, WikiRecommend bean) throws JoymeDBException, JoymeServiceException {
		boolean isCloseConn = (conn != null) ? false : true;
		try {
			if (conn == null)
				conn = subDao.getConnection();
			return subDao.insertWikiRecommend(conn, bean);
		} catch (JoymeDBException e) {
			throw e;
		} catch (Exception e) {
			throw new JoymeServiceException(e);
		} finally {
			if (conn != null && isCloseConn)
				subDao.closeConnection(conn);
		}
	}

	public List<WikiRecommend> queryWikiRecommendList(Connection conn, String joymeWikiKey, int type, Date start, Date end) throws JoymeDBException, JoymeServiceException {
		boolean isCloseConn = (conn != null) ? false : true;
		try {
			if (conn == null)
				conn = subDao.getConnection();
			return subDao.queryWikiRecommend(conn, joymeWikiKey, type, start, end);
		} catch (JoymeDBException e) {
			throw e;
		} catch (Exception e) {
			throw new JoymeServiceException(e);
		} finally {
			if (conn != null && isCloseConn)
				subDao.closeConnection(conn);
		}
	}

	public List<WikiRecommend> queryWikiRecommendList(Connection conn, String joymeWikiKey, int type) throws JoymeDBException, JoymeServiceException {
		boolean isCloseConn = (conn != null) ? false : true;
		try {
			if (conn == null)
				conn = subDao.getConnection();
			return subDao.queryWikiRecommend(conn, joymeWikiKey, type);
		} catch (JoymeDBException e) {
			throw e;
		} catch (Exception e) {
			throw new JoymeServiceException(e);
		} finally {
			if (conn != null && isCloseConn)
				subDao.closeConnection(conn);
		}
	}

}
