package com.enjoyf.wiki.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.wiki.bean.JoymeWikiStyleSimpleBean;
import com.enjoyf.wiki.bean.JoymeWikiStyle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JoymeWikiStyleDao extends BaseJDBCDAOImpl {
    /**
     * date 2013-10-17 12:30:06
     *
     * @param joymeWikiStyleId
     * @return JoymeWikiStyle
     * @author shenqiv0.1
     */
    public JoymeWikiStyle queryJoymeWikiStylebyId(Connection conn, Integer joymeWikiStyleId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_wiki_style  WHERE joyme_wiki_style_id = ?";
            List objectList = new ArrayList();
            objectList.add(joymeWikiStyleId);
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


    /**
     * date 2013-10-17 12:30:06
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertJoymeWikiStyle(Connection conn, JoymeWikiStyle bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_wiki_style(joyme_wiki_key,joyme_wiki_file,joyme_modify_date,joyme_wiki_isindex,joyme_wiki_channel,context_path) VALUES (?,?,?,?,?,?)";
            Object[] objects = new Object[]{bean.getJoymeWikiKey(), bean.getJoymeWikiFile(), bean.getJoymeModifyDate(), bean.getJoymeWikiIsindex(), bean.getJoymeWikiChannel(), bean.getContextPath()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-10-17 12:30:06
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateJoymeWikiStyle(Connection conn, JoymeWikiStyle bean) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_wiki_style SET $updateStr  WHERE joyme_wiki_style_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getJoymeWikiStyleId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-10-17 12:30:06
     *
     * @param joymeWikiStyleId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteJoymeWikiStyle(Connection conn, Integer joymeWikiStyleId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_wiki_style  WHERE joyme_wiki_style_id = ?";
            List objectList = new ArrayList();
            objectList.add(joymeWikiStyleId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public JoymeWikiStyle queryJoymeWikiStyle(Connection conn, String joymeWikiKey, Integer joymeWikiIsindex, String joymeWikiChannel, String contextPath) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_wiki_style WHERE joyme_wiki_key= ? AND joyme_wiki_isindex=? AND joyme_wiki_channel=? AND context_path=?";
            List objectList = new ArrayList();
            objectList.add(joymeWikiKey);
            objectList.add(joymeWikiIsindex);
            objectList.add(joymeWikiChannel);
            objectList.add(contextPath);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            if (rs.next()) {
                return rsToObject(rs);
            }
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
        return null;
    }


    public JoymeWikiStyleSimpleBean getJoymeWikiStyleVersion(Connection conn, String joymeWikiKey, Integer joymeWikiIsindex, String joymeWikiChannel, String contextPath) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT joyme_wiki_style_id,version_num FROM joyme_wiki_style WHERE joyme_wiki_key= ? AND joyme_wiki_isindex=? AND joyme_wiki_channel=? AND context_path=?";
            List objectList = new ArrayList();
            objectList.add(joymeWikiKey);
            objectList.add(joymeWikiIsindex);
            objectList.add(joymeWikiChannel);
            objectList.add(contextPath);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            if (rs.next()) {
                JoymeWikiStyleSimpleBean entry = new JoymeWikiStyleSimpleBean();
                entry.setJoymeWikiStyleId(rs.getInt("joyme_wiki_style_id"));
                entry.setVersionNum(rs.getLong("version_num"));
                return entry;
            }
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
        return null;
    }


    private JoymeWikiStyle rsToObject(ResultSet rs) throws SQLException {
        JoymeWikiStyle joymeWikiStyle = new JoymeWikiStyle();
        joymeWikiStyle.setJoymeWikiStyleId(rs.getInt("joyme_wiki_style_id"));
        joymeWikiStyle.setJoymeWikiKey(rs.getString("joyme_wiki_key"));
        joymeWikiStyle.setJoymeWikiFile(rs.getBlob("joyme_wiki_file").getBinaryStream());
        joymeWikiStyle.setJoymeModifyDate(rs.getTimestamp("joyme_modify_date"));
        joymeWikiStyle.setJoymeWikiIsindex(rs.getInt("joyme_wiki_isindex"));
        joymeWikiStyle.setJoymeWikiChannel(rs.getString("joyme_wiki_channel"));
        joymeWikiStyle.setContextPath(rs.getString("context_path"));
        joymeWikiStyle.setVersionNum(rs.getLong("version_num"));
        return joymeWikiStyle;
    }

}