package com.enjoyf.wiki.service;

import com.enjoyf.framework.jdbc.bean.PageQueryBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.HttpClientManager;
import com.enjoyf.util.HttpParameter;
import com.enjoyf.util.StringUtil;
import com.enjoyf.wiki.bean.PageBean;
import com.enjoyf.wiki.bean.WikiPage;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.dao.WikiPageDao;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

public class WikiPageService {
    private static WikiPageDao subDao = new WikiPageDao();
    private HttpClientManager manager = new HttpClientManager();

    public WikiPage queryWikiPagebyId(Connection conn, Long pageId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryWikiPagebyId(conn, pageId);
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
     * date 2013-06-19 13:53:07
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public long insertWikiPage(Connection conn, WikiPage bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertWikiPage(conn, bean);
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
     * date 2013-06-19 13:53:07
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateWikiPage(Connection conn, WikiPage bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateWikiPage(conn, bean);
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
     * date 2013-06-19 13:53:07
     *
     * @param pageId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteWikiPage(Connection conn, Long pageId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteWikiPage(conn, pageId);
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
     * 通过key,url获得实体
     *
     * @param conn
     * @param key
     * @param url
     * @return
     * @throws JoymeDBException
     * @throws
     */
    public WikiPage queryWikiPage(Connection conn, String key, String url) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryWikiPage(conn, key, url);
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
     * 通过中文的url查询wiki_page_id，如果没有，创建一个
     *
     * @param url
     * @return
     * @throws JoymeServiceException
     * @throws JoymeDBException
     */
    public long getWikiPageIdByChineseURL(Connection conn, String key, String url) throws JoymeDBException, JoymeServiceException {
        WikiPage wp = this.queryWikiPage(conn, key, url);
        if (wp == null) {
            //TODO url空格替换成_
            url = url.trim().replaceAll(" ", "_");
            wp = new WikiPage();
            wp.setWikiKey(key);
            wp.setWikiUrl(url);
            wp.setCreateTime(new Timestamp(System.currentTimeMillis()));
            wp.setPageStatus(1);


            wp.setPageId(insertWikiPage(conn, wp));

            //call search engine generator search
            if (wp.getPageId() > 0 && !StringUtil.isEmpty(PropertiesContainer.getInstance().searchSaveUrl)) {
                HttpParameter[] saveParams = new HttpParameter[]{
                        new HttpParameter("c", "wiki-page"),
                        new HttpParameter("field", "id=" + wp.getPageId() + ",wikikey=" + wp.getWikiKey() + ",title=" + wp.getWikiUrl()),
                };
                manager.post(PropertiesContainer.getInstance().searchSaveUrl, saveParams, null);
            }
        }

        return wp.getPageId();
    }

    /**
     * 搜索服务
     *
     * @param conn
     * @param key
     * @param searchStr
     * @param pageNum
     * @param pageCount
     * @return
     * @throws Exception
     */
    public PageBean searchWikiPageList(Connection conn, String key, String searchStr, int pageStatus, int pageNum, int pageCount) throws Exception {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.searchWikiPageList(conn, key, searchStr, pageStatus, pageNum, pageCount);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public List<WikiPage> getWikiPageListByWikiKey(Connection conn, String key) throws Exception {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.getWikiPageListByWikiKey(conn, key);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public List<WikiPage> getWikiPageCount(Connection conn) throws Exception {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.getWikiPageCount(conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }


    public PageQueryBean queryWikiPage(Connection conn, String wikiKey, Integer pageStatus, int pageNum, int pageCount, int totalNum) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            PageQueryBean pbean = subDao.queryWikiPage(conn, wikiKey, pageStatus, pageNum, pageCount);
            pbean.setTotalNum(totalNum);
            return pbean;
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public int countWikiPage(Connection conn, String wikiKey, Integer pageStatus) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.countWikiPage(conn, wikiKey, pageStatus);
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
