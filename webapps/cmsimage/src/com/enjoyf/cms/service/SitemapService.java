package com.enjoyf.cms.service;

import com.enjoyf.cms.bean.JoymeDomain;
import com.enjoyf.cms.bean.JoymeSubDomain;
import com.enjoyf.cms.bean.Sitemap;
import com.enjoyf.cms.bean.SitemapLog;
import com.enjoyf.cms.dao.JoymeDomainDao;
import com.enjoyf.cms.dao.SitemapDao;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;

import java.sql.Connection;

/**
 * Created by zhitaoshi on 2015/7/1.
 */
public class SitemapService {

    private static SitemapDao sitemapDao = new SitemapDao();

    public PageRows<Sitemap> querySitemapByPage(String domainKey, Integer status, Pagination pagination) throws JoymeDBException, JoymeServiceException {
        Connection conn = null;
        try {
            conn = sitemapDao.getConnection();
            return sitemapDao.querySitemapByPage(domainKey, status, pagination, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                sitemapDao.closeConnection(conn);
            }
        }
    }

    public Sitemap insertJoymeSitemap(Sitemap sitemap) throws JoymeServiceException, JoymeDBException {
        Connection conn = null;
        try {
            conn = sitemapDao.getConnection();
            return sitemapDao.insertJoymeSitemap(sitemap, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                sitemapDao.closeConnection(conn);
            }
        }
    }

    public Sitemap getJoymeSitemap(String sitemapId) throws JoymeServiceException, JoymeDBException {
        Connection conn = null;
        try {
            conn = sitemapDao.getConnection();
            return sitemapDao.getJoymeSitemap(sitemapId, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                sitemapDao.closeConnection(conn);
            }
        }
    }

    public boolean updateJoymeSitemap(String sitemapId, Sitemap sitemap) throws JoymeDBException, JoymeServiceException {
        Connection conn = null;
        try {
            conn = sitemapDao.getConnection();
            return sitemapDao.updateJoymeSitemap(sitemapId, sitemap, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                sitemapDao.closeConnection(conn);
            }
        }
    }

    public boolean deleteJoymeSitemap(String sitemapId) throws JoymeDBException, JoymeServiceException {
        Connection conn = null;
        try {
            conn = sitemapDao.getConnection();
            return sitemapDao.deleteJoymeSitemap(sitemapId, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                sitemapDao.closeConnection(conn);
            }
        }
    }

    public SitemapLog getJoymeSitemapLog(String logId) throws JoymeDBException, JoymeServiceException {
        Connection conn = null;
        try {
            conn = sitemapDao.getConnection();
            return sitemapDao.getJoymeSitemapLog(logId, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                sitemapDao.closeConnection(conn);
            }
        }
    }

    public SitemapLog insertJoymeSitemapLog(SitemapLog sitemapLog) throws JoymeDBException, JoymeServiceException {
        Connection conn = null;
        try {
            conn = sitemapDao.getConnection();
            return sitemapDao.insertJoymeSitemapLog(sitemapLog, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                sitemapDao.closeConnection(conn);
            }
        }
    }

    public PageRows<SitemapLog> querySitemapLogByPage(String domainKey, Pagination pagination) throws JoymeDBException, JoymeServiceException {
        Connection conn = null;
        try {
            conn = sitemapDao.getConnection();
            return sitemapDao.querySitemapLogByPage(domainKey, pagination, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                sitemapDao.closeConnection(conn);
            }
        }
    }
}
