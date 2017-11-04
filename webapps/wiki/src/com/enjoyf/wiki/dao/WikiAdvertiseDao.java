package com.enjoyf.wiki.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.wiki.bean.WikiAdvertise;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WikiAdvertiseDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-08-22 16:18:06
     *
     * @param wikiAdvertiseId
     * @return WikiAdvertise
     * @author shenqiv0.1
     */
    public WikiAdvertise queryWikiAdvertisebyId(Connection conn, Integer wikiAdvertiseId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM wiki_advertise  WHERE wiki_advertise_id = ?";
            List objectList = new ArrayList();
            objectList.add(wikiAdvertiseId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                return rsToObject(rs);
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public List queryWikiAdvertise(Connection conn) throws JoymeServiceException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM wiki_advertise ";
            List objectList = new ArrayList();
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {

                retList.add(rsToObject(rs));
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public List queryWikiAdvertise(Connection conn, String wikiAdvertiseKey, String wikiAdvertiseContextPath) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM wiki_advertise  where wiki_advertise_key=? AND  wiki_advertise_context_path=?";
            List objectList = new ArrayList();
            objectList.add(wikiAdvertiseKey);
            objectList.add(wikiAdvertiseContextPath);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {

                retList.add(rsToObject(rs));
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    /**
     * date 2013-08-22 16:18:06
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertWikiAdvertise(Connection conn, WikiAdvertise bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO wiki_advertise(wiki_advertise_key,wiki_advertise_context_path,wiki_advertise_type,wiki_advertise_picurl,wiki_advertise_url,wiki_advertise_desc,wiki_advertise_html) VALUES (?,?,?,?,?,?,?)";
            Object[] objects = new Object[]{bean.getWikiAdvertiseKey(), bean.getWikiAdvertiseContextPath(), bean.getWikiAdvertiseType(), bean.getWikiAdvertisePicUrl(), bean.getWikiAdvertiseUrl(), bean.getWikiAdvertiseDesc(), bean.getWikiAdvertiseHtml()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-08-22 16:18:06
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateWikiAdvertise(Connection conn, WikiAdvertise bean) throws JoymeDBException {
        try {
            String sql = "UPDATE wiki_advertise SET $updateStr  WHERE wiki_advertise_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getWikiAdvertiseId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-08-22 16:18:06
     *
     * @param wikiAdvertiseId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteWikiAdvertise(Connection conn, Integer wikiAdvertiseId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM wiki_advertise  WHERE wiki_advertise_id = ?";
            List objectList = new ArrayList();
            objectList.add(wikiAdvertiseId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-08-23 10:39:36
     *
     * @param wikiAdvertiseKey
     * @param wikiAdvertiseContextPath
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteWikiAdvertise(Connection conn, String wikiAdvertiseKey, String wikiAdvertiseContextPath) throws JoymeDBException {
        try {
            String sql = "DELETE FROM wiki_advertise WHERE wiki_advertise_key=? and wiki_advertise_context_path=? ";
            List objectList = new ArrayList();
            objectList.add(wikiAdvertiseKey);
            objectList.add(wikiAdvertiseContextPath);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    private WikiAdvertise rsToObject(ResultSet rs) throws SQLException {
        WikiAdvertise wikiAdvertise = new WikiAdvertise();
        wikiAdvertise.setWikiAdvertiseId(rs.getInt("wiki_advertise_id"));
        wikiAdvertise.setWikiAdvertiseKey(rs.getString("wiki_advertise_key"));
        wikiAdvertise.setWikiAdvertiseContextPath(rs.getString("wiki_advertise_context_path"));
        wikiAdvertise.setWikiAdvertiseType(rs.getInt("wiki_advertise_type"));
        wikiAdvertise.setWikiAdvertisePicUrl(rs.getString("wiki_advertise_picurl"));
        wikiAdvertise.setWikiAdvertiseUrl(rs.getString("wiki_advertise_url"));
        wikiAdvertise.setWikiAdvertiseDesc(rs.getString("wiki_advertise_desc"));
        wikiAdvertise.setWikiAdvertiseHtml(rs.getString("wiki_advertise_html"));
        return wikiAdvertise;
    }
}