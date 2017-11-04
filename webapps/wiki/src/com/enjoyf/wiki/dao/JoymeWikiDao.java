package com.enjoyf.wiki.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.wiki.bean.JoymeWiki;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JoymeWikiDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-09-17 11:48:58
     *
     * @param conn
     * @param joymeWikiId
     * @return JoymeWiki
     * @throws com.enjoyf.framework.jdbc.exception.JoymeDBException
     * @author shenqiv0.1
     */
    public JoymeWiki queryJoymeWikibyId(Connection conn, Integer joymeWikiId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_wiki  WHERE joyme_wiki_id = ?";
            List objectList = new ArrayList();
            objectList.add(joymeWikiId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                return roToObject(rs);
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }


    /**
     * date 2013-09-17 11:48:58
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertJoymeWiki(Connection conn, JoymeWiki bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_wiki(joyme_wiki_key,joyme_wiki_domain,joyme_wiki_path,joyme_wiki_factory,joyme_wiki_name,joyme_android_path,joyme_ios_path,context_path,support_subdomain,pc_keep_jscss,m_keep_jscss) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            Object[] objects = new Object[]{bean.getJoymeWikiKey(), bean.getJoymeWikiDomain(), bean.getJoymeWikiPath(), bean.getJoymeWikiFactory(), bean.getJoymeWikiName(), bean.getJoymeAndroidPath(), bean.getJoymeIosPath(), bean.getContextPath(), bean.getSupportSubDomain(), bean.getPcKeepJscss(), bean.getmKeepJscss()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
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
    public int updateJoymeWiki(Connection conn, JoymeWiki bean) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_wiki SET $updateStr  WHERE joyme_wiki_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getJoymeWikiId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-09-17 11:48:59
     *
     * @param joymeWikiId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteJoymeWiki(Connection conn, Integer joymeWikiId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_wiki  WHERE joyme_wiki_id = ?";
            List objectList = new ArrayList();
            objectList.add(joymeWikiId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-09-17 12:10:06
     *
     * @return List <JoymeWiki>
     * @author shenqiv0.1
     */
    public List queryJoymeWiki(Connection conn, String contextPath) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_wiki WHERE context_path=?";

            List objectList = new ArrayList();
            objectList.add(contextPath);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                retList.add(roToObject(rs));
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public List queryJoymeWiki(Connection conn) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_wiki";

            List objectList = new ArrayList();
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                retList.add(roToObject(rs));
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    /**
     * date 2013-09-17 12:12:07
     *
     * @param joymeWikiKey
     * @return List <JoymeWiki>
     * @author shenqiv0.1
     */
    public List queryJoymeWiki(Connection conn, String joymeWikiKey, String contextPath) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_wiki WHERE joyme_wiki_key=? AND context_path=?";
            List objectList = new ArrayList();
            objectList.add(joymeWikiKey);
            objectList.add(contextPath);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                retList.add(roToObject(rs));
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }


    /**
     * date 2013-09-17 12:12:07
     *
     * @return List <JoymeWiki>
     * @author shenqiv0.1
     */
    public List<JoymeWiki> queryJoymeWikiList(Connection conn) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT joyme_wiki_key FROM joyme_wiki GROUP BY joyme_wiki_key";
            List objectList = new ArrayList();
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List<JoymeWiki> retList = new ArrayList<JoymeWiki>();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymeWiki joymeWiki = new JoymeWiki();
                joymeWiki.setJoymeWikiKey(rs.getString("joyme_wiki_key"));
                retList.add(joymeWiki);
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }


    private JoymeWiki roToObject(ResultSet rs) throws SQLException {
        JoymeWiki joymeWiki = new JoymeWiki();
        joymeWiki.setJoymeWikiId(rs.getInt("joyme_wiki_id"));
        joymeWiki.setJoymeWikiKey(rs.getString("joyme_wiki_key"));
        joymeWiki.setJoymeWikiDomain(rs.getString("joyme_wiki_domain"));
        joymeWiki.setJoymeWikiPath(rs.getString("joyme_wiki_path"));
        joymeWiki.setJoymeWikiFactory(rs.getString("joyme_wiki_factory"));
        joymeWiki.setJoymeWikiName(rs.getString("joyme_wiki_name"));
        joymeWiki.setJoymeAndroidPath(rs.getString("joyme_android_path"));
        joymeWiki.setJoymeIosPath(rs.getString("joyme_ios_path"));
        joymeWiki.setContextPath(rs.getString("context_path"));
        joymeWiki.setSupportSubDomain(rs.getInt("support_subdomain") > 0);
        joymeWiki.setPcKeepJscss(rs.getInt("pc_keep_jscss"));
        joymeWiki.setmKeepJscss(rs.getInt("m_keep_jscss"));
        return joymeWiki;
    }

}