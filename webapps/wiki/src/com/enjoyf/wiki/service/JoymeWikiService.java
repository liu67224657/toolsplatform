package com.enjoyf.wiki.service;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.wiki.bean.JoymeWiki;
import com.enjoyf.wiki.dao.JoymeWikiDao;

import java.sql.Connection;
import java.util.List;

public class JoymeWikiService {

    private static JoymeWikiDao subDao = new JoymeWikiDao();

    /**
     * date 2013-09-17 11:48:59
     *
     * @param joymeWikiId
     * @return JoymeWiki
     * @author shenqiv0.1
     */
    public JoymeWiki queryJoymeWikibyId(Connection conn, Integer joymeWikiId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeWikibyId(conn, joymeWikiId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }


    /**
     * date 2013-09-17 11:48:59
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertJoymeWiki(Connection conn, JoymeWiki bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertJoymeWiki(conn, bean);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }


    /**
     * date 2013-09-17 11:48:59
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateJoymeWiki(Connection conn, JoymeWiki bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateJoymeWiki(conn, bean);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }


    /**
     * date 2013-09-17 11:48:59
     *
     * @param joymeWikiId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteJoymeWiki(Connection conn, Integer joymeWikiId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteJoymeWiki(conn, joymeWikiId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public List queryJoymeWiki(Connection conn, String contextPath) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeWiki(conn, contextPath);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public List queryJoymeWiki(Connection conn) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeWiki(conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }


    /**
     * date 2013-09-17 12:12:07
     *
     * @param joymeWikiKey
     * @return List <joymeWiki>
     * @author shenqiv0.1
     */
    public List queryJoymeWiki(Connection conn, String joymeWikiKey, String contextPath) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeWiki(conn, joymeWikiKey, contextPath);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

	public List<JoymeWiki> queryJoymeWikiList(Connection conn) throws JoymeDBException, JoymeServiceException {
		boolean isCloseConn = (conn != null) ? false : true;
		try {
			if (conn == null)
				conn = subDao.getConnection();
			return subDao.queryJoymeWikiList(conn);
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