package com.enjoyf.wiki.service;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.redis.RedisManager;
import com.enjoyf.wiki.bean.JoymeWikiSitemap;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.dao.JoymeWikiSitemapDao;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

public class JoymeWikiSitemapService {

    private static JoymeWikiSitemapDao subDao = new JoymeWikiSitemapDao();

    private static final String KEY_WIKI_SITEMAP = "wikipage_stmap";
    private static final String KEY_UGCWIKI_SITEMAP = "wikipage_ugcstmap";

    /**
     * date 2014-03-03 12:31:27
     *
     * @param sitemapid
     * @return JoymeWikiSitemap
     * @author shenqiv0.1
     */
    public JoymeWikiSitemap queryJoymeWikiSitemapbyId(Connection conn, Long sitemapid) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeWikiSitemapbyId(conn, sitemapid);
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
     * date 2014-03-03 12:31:27
     *
     * @param sitemapid
     * @return JoymeWikiSitemap
     * @author shenqiv0.1
     */
    public List<JoymeWikiSitemap> queryJoymeWikiSitemapbyWikiType(Connection conn, String wiki) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeWikiSitemapbyWikiType(conn, wiki);
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
     * date 2014-03-03 12:31:27
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertJoymeWikiSitemap(Connection conn, JoymeWikiSitemap bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertJoymeWikiSitemap(conn, bean);
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
     * date 2014-03-03 12:31:27
     *
     * @param bean
     * @param sitemapid
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateJoymeWikiSitemap(Connection conn, JoymeWikiSitemap bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateJoymeWikiSitemap(conn, bean);
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
     * date 2014-03-03 12:31:27
     *
     * @param sitemapid
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteJoymeWikiSitemap(Connection conn, Long sitemapid) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteJoymeWikiSitemap(conn, sitemapid);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }


    public Set<String> querySitemap(int startIndex, int endIndex) {
        return PropertiesContainer.getInstance().getRedisManager().zrange(KEY_WIKI_SITEMAP, startIndex, endIndex, RedisManager.RANGE_ORDERBY_DESC);
    }

    public void addSitemap(String wikiKey) {
        PropertiesContainer.getInstance().getRedisManager().zadd(KEY_WIKI_SITEMAP, System.currentTimeMillis(), wikiKey, -1);
    }

    public void deleteSitemap(String wikiKey) {
        PropertiesContainer.getInstance().getRedisManager().zrem(KEY_WIKI_SITEMAP, wikiKey);
    }

    public Set<String> queryUGCSitemap(int startIndex, int endIndex) {
        return PropertiesContainer.getInstance().getRedisManager().zrange(KEY_UGCWIKI_SITEMAP, startIndex, endIndex, RedisManager.RANGE_ORDERBY_DESC);
    }

    public void addUGCSitemap(String wikiKey) {
        PropertiesContainer.getInstance().getRedisManager().zadd(KEY_UGCWIKI_SITEMAP, System.currentTimeMillis(), wikiKey, -1);
    }

    public void deleteUGCSitemap(String wikiKey) {
        PropertiesContainer.getInstance().getRedisManager().zrem(KEY_UGCWIKI_SITEMAP, wikiKey);
    }
}