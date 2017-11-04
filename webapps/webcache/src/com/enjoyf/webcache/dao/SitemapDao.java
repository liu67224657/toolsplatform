package com.enjoyf.webcache.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;
import com.enjoyf.util.StringUtil;
import com.enjoyf.webcache.bean.ActStatus;
import com.enjoyf.webcache.bean.Sitemap;
import com.enjoyf.webcache.bean.SitemapLog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhitaoshi on 2015/7/1.
 */
public class SitemapDao extends BaseJDBCDAOImpl {

    public PageRows<Sitemap> querySitemapByPage(String domainKey, Integer status, Pagination pagination, Connection conn) throws JoymeDBException {
        List<Sitemap> list = new ArrayList<Sitemap>();
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_sitemap";
            if (!StringUtil.isEmpty(domainKey) || status != null) {
                sql += " WHERE";
                if (!StringUtil.isEmpty(domainKey)) {
                    sql += " domainkey='" + domainKey + "'";
                    if (status != null) {
                        sql += " AND removestatus=" + status;
                    }
                } else {
                    if (status != null) {
                        sql += " removestatus=" + status;
                    }
                }
            }
            sql += " ORDER BY modifydate  DESC LIMIT " + pagination.getStartRowIdx() + "," + pagination.getPageSize();
            dbean = this.executeBindingQuery(conn, sql, null);
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                Sitemap sitemap = rsToSitemap(rs);
                list.add(sitemap);
            }
            String countSql = "SELECT COUNT(*) FROM joyme_sitemap";
            if (!StringUtil.isEmpty(domainKey) || status != null) {
                countSql += " WHERE";
                if (!StringUtil.isEmpty(domainKey)) {
                    countSql += " domainkey='" + domainKey + "'";
                    if (status != null) {
                        countSql += " AND removestatus=" + status;
                    }
                } else {
                    if (status != null) {
                        countSql += " removestatus=" + status;
                    }
                }
            }
            dbean = this.executeBindingQuery(conn, countSql, null);
            ResultSet countRs = dbean.getRs();
            if (countRs.next()) {
                pagination.setTotalRows(countRs.getInt(1));
            }

            PageRows pageRows = new PageRows();
            pageRows.setRows(list);
            pageRows.setPage(pagination);
            return pageRows;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public Sitemap insertJoymeSitemap(Sitemap sitemap, Connection conn) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_sitemap(sitemapid,domainkey,sitemapurl," +
                    "mappingurl,expdesc,modifydate,removestatus) VALUES (?,?,?,?,?,?,?)";
            Object[] objects = new Object[]{sitemap.getSitemapId(),
                    sitemap.getDomainKey(),
                    sitemap.getSitemapUrl(),
                    sitemap.getMappingUrl(),
                    sitemap.getExpDesc(),
                    sitemap.getModifyDate() == null ? null : new Timestamp(sitemap.getModifyDate().getTime()),
                    sitemap.getRemoveStatus().getCode()
            };
            int result = this.executeBindingUpdate(conn, sql, objects, false, false);
            if (result > 0) {
                return sitemap;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    public Sitemap getJoymeSitemap(String sitemapId, Connection conn) throws JoymeDBException {
        Sitemap sitemap = null;
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_sitemap WHERE sitemapid=?";
            Object[] objects = new Object[]{sitemapId};
            dbean = this.executeBindingQuery(conn, sql, objects);
            ResultSet rs = dbean.getRs();
            if (rs.next()) {
                sitemap = rsToSitemap(rs);
            }
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
        return sitemap;
    }


    public boolean updateJoymeSitemap(String sitemapId, Sitemap sitemap, Connection conn) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_sitemap SET sitemapid=?,domainkey=?,sitemapurl=?," +
                    "mappingurl=?,expdesc=?,modifydate=?,removestatus=? WHERE sitemapid=?";
            Object[] objects = new Object[]{sitemap.getSitemapId(),
                    sitemap.getDomainKey(),
                    sitemap.getSitemapUrl(),
                    sitemap.getMappingUrl(),
                    sitemap.getExpDesc(),
                    sitemap.getModifyDate() == null ? null : new Timestamp(sitemap.getModifyDate().getTime()),
                    sitemap.getRemoveStatus().getCode(),
                    sitemapId
            };
            return this.executeBindingUpdate(conn, sql, objects, false, false) > 0;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public boolean deleteJoymeSitemap(String sitemapId, Connection conn) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_sitemap WHERE sitemapid=?";
            Object[] objects = new Object[]{
                    sitemapId
            };
            return this.executeBindingUpdate(conn, sql, objects, false, false) > 0;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    private Sitemap rsToSitemap(ResultSet rs) throws SQLException {
        Sitemap sitemap = new Sitemap();
        sitemap.setSitemapId(rs.getString("sitemapid"));
        sitemap.setDomainKey(rs.getString("domainkey"));
        sitemap.setExpDesc(rs.getString("expdesc"));
        sitemap.setMappingUrl(rs.getString("mappingurl"));
        sitemap.setRemoveStatus(ActStatus.getByCode(rs.getInt("removestatus")));
        sitemap.setSitemapUrl(rs.getString("sitemapurl"));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date modifyDate = rs.getTimestamp("modifydate") == null ? null : new Date(rs.getTimestamp("modifydate").getTime());
        sitemap.setModifyDate(modifyDate);
        if (modifyDate != null) {
            sitemap.setModifyDateStr(df.format(modifyDate));
        }
        return sitemap;
    }

    public SitemapLog getJoymeSitemapLog(String logId, Connection conn) throws JoymeDBException {
        SitemapLog sitemapLog = null;
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_sitemap_log WHERE logid=?";
            Object[] objects = new Object[]{logId};
            dbean = this.executeBindingQuery(conn, sql, objects);
            ResultSet rs = dbean.getRs();
            if (rs.next()) {
                sitemapLog = rsToSitemapLog(rs);
            }
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
        return sitemapLog;
    }

    public SitemapLog insertJoymeSitemapLog(SitemapLog sitemapLog, Connection conn) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_sitemap_log(logid,pageurl,domainkey,statusdesc,pushdate,removestatus" +
                    ") VALUES (?,?,?,?,?,?)";
            Object[] objects = new Object[]{sitemapLog.getLogId(),
                    sitemapLog.getPageUrl(),
                    sitemapLog.getDomainKey(),
                    sitemapLog.getStatusDesc(),
                    sitemapLog.getPushDate() == null ? new Date() : new Timestamp(sitemapLog.getPushDate().getTime()),
                    sitemapLog.getStatus().getCode()
            };
            int result = this.executeBindingUpdate(conn, sql, objects, false, false);
            if (result > 0) {
                return sitemapLog;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public PageRows<SitemapLog> querySitemapLogByPage(String domainKey, Pagination pagination, Connection conn) throws JoymeDBException {
        List<SitemapLog> list = new ArrayList<SitemapLog>();
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_sitemap_log";
            if (!StringUtil.isEmpty(domainKey)) {
                sql += " WHERE domainkey='" + domainKey + "'";
            }
            sql += " ORDER BY pushdate DESC LIMIT " + pagination.getStartRowIdx() + "," + pagination.getPageSize();
            dbean = this.executeBindingQuery(conn, sql, null);
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                SitemapLog sitemapLog = rsToSitemapLog(rs);
                list.add(sitemapLog);
            }
            String countSql = "SELECT COUNT(*) FROM joyme_sitemap_log";
            if (!StringUtil.isEmpty(domainKey)) {
                countSql += " WHERE domainkey='" + domainKey + "'";
            }
            dbean = this.executeBindingQuery(conn, countSql, null);
            ResultSet countRs = dbean.getRs();
            if (countRs.next()) {
                pagination.setTotalRows(countRs.getInt(1));
            }

            PageRows pageRows = new PageRows();
            pageRows.setRows(list);
            pageRows.setPage(pagination);
            return pageRows;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    private SitemapLog rsToSitemapLog(ResultSet rs) throws SQLException {
        SitemapLog sitemapLog = new SitemapLog();
        sitemapLog.setDomainKey(rs.getString("domainkey"));
        sitemapLog.setLogId(rs.getString("logid"));
        sitemapLog.setStatusDesc(rs.getString("statusdesc"));
        sitemapLog.setPageUrl(rs.getString("pageurl"));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date pushDate = rs.getTimestamp("pushdate") == null ? null : new Date(rs.getTimestamp("pushdate").getTime());
        sitemapLog.setPushDate(pushDate);
        if (pushDate != null) {
            sitemapLog.setPushDateStr(df.format(pushDate));
        }
        sitemapLog.setStatus(ActStatus.getByCode(rs.getInt("removestatus")));
        return sitemapLog;
    }
}
