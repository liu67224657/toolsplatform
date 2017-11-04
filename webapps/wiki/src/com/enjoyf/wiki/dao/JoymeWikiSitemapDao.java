package com.enjoyf.wiki.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.wiki.bean.JoymeWikiSitemap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JoymeWikiSitemapDao extends BaseJDBCDAOImpl {

    /**
     * date 2014-03-03 12:31:27
     *
     * @param sitemapid
     * @return JoymeWikiSitemap
     * @author shenqiv0.1
     */
    public JoymeWikiSitemap queryJoymeWikiSitemapbyId(Connection conn, Long sitemapid) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_wiki_sitemap  WHERE sitemapid = ?";
            List objectList = new ArrayList();
            objectList.add(sitemapid);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymeWikiSitemap joymeWikiSitemap = new JoymeWikiSitemap();
                joymeWikiSitemap.setSitemapid(rs.getLong("sitemapid"));
                joymeWikiSitemap.setWikiKey(rs.getString("wiki_key"));
                joymeWikiSitemap.setLoc(rs.getString("loc"));
                joymeWikiSitemap.setPriority(rs.getString("priority"));
                return joymeWikiSitemap;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }


/**
     * date 2014-03-03 12:31:27
     *
     * @param sitemapid
     * @return JoymeWikiSitemap
     * @author shenqiv0.1
     */
    public List<JoymeWikiSitemap> queryJoymeWikiSitemapbyWikiType(Connection conn, String wiki) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_wiki_sitemap  WHERE wiki_key = ? order by sitemapid asc";
            List objectList = new ArrayList();
            objectList.add(wiki);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List<JoymeWikiSitemap> retList = new ArrayList<JoymeWikiSitemap>();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymeWikiSitemap joymeWikiSitemap = new JoymeWikiSitemap();
                joymeWikiSitemap.setSitemapid(rs.getLong("sitemapid"));
                joymeWikiSitemap.setWikiKey(rs.getString("wiki_key"));
                joymeWikiSitemap.setLoc(rs.getString("loc"));
                joymeWikiSitemap.setPriority(rs.getString("priority"));
                retList.add(joymeWikiSitemap);
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }


    /**
     * date 2014-03-03 12:31:27
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertJoymeWikiSitemap(Connection conn, JoymeWikiSitemap bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_wiki_sitemap(wiki_key,loc,priority) VALUES (?,?,?)";
            Object[] objects = new Object[]{bean.getWikiKey(), bean.getLoc(), bean.getPriority()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
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
    public int updateJoymeWikiSitemap(Connection conn, JoymeWikiSitemap bean) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_wiki_sitemap SET $updateStr  WHERE sitemapid = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getSitemapid());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2014-03-03 12:31:27
     *
     * @param sitemapid
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteJoymeWikiSitemap(Connection conn, Long sitemapid) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_wiki_sitemap  WHERE sitemapid = ?";
            List objectList = new ArrayList();
            objectList.add(sitemapid);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


}