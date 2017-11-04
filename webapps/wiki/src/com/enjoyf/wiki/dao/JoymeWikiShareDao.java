package com.enjoyf.wiki.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.wiki.bean.JoymeWikiShare;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JoymeWikiShareDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-12-21 11:05:02
     *
     * @param joymeShareId
     * @return JoymeWikiShare
     * @author shenqiv0.1
     */
    public JoymeWikiShare queryJoymeWikiSharebyId(Connection conn, Integer joymeShareId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_wiki_share  WHERE joyme_share_id = ?";
            List objectList = new ArrayList();
            objectList.add(joymeShareId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            if (rs.next()) {
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
     * date 2013-12-21 11:05:02
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertJoymeWikiShare(Connection conn, JoymeWikiShare bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_wiki_share(joyme_wiki_key,joyme_share_content,joyme_share_url,joyme_share_pic) VALUES (?,?,?,?)";
            Object[] objects = new Object[]{bean.getJoymeWikiKey(), bean.getJoymeShareContent(), bean.getJoymeShareUrl(), bean.getJoymeSharePic()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-12-21 11:05:02
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateJoymeWikiShare(Connection conn, JoymeWikiShare bean) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_wiki_share SET $updateStr  WHERE joyme_share_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getJoymeShareId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-12-21 11:05:02
     *
     * @param joymeShareId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteJoymeWikiShare(Connection conn, Integer joymeShareId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_wiki_share  WHERE joyme_share_id = ?";
            List objectList = new ArrayList();
            objectList.add(joymeShareId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public JoymeWikiShare queryJoymeWikiShare(Connection conn, String joymeWikiKey) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_wiki_share where joyme_wiki_key= ?";
            List objectList = new ArrayList();
            objectList.add(joymeWikiKey);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
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

     public List<JoymeWikiShare> queryJoymeWikiShare(Connection conn) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_wiki_share";
            List objectList = new ArrayList();
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

    private JoymeWikiShare rsToObject(ResultSet rs) throws SQLException {
        JoymeWikiShare joymeWikiShare = new JoymeWikiShare();
        joymeWikiShare.setJoymeShareId(rs.getInt("joyme_share_id"));
        joymeWikiShare.setJoymeWikiKey(rs.getString("joyme_wiki_key"));
        joymeWikiShare.setJoymeShareContent(rs.getString("joyme_share_content"));
        joymeWikiShare.setJoymeShareUrl(rs.getString("joyme_share_url"));
        joymeWikiShare.setJoymeSharePic(rs.getString("joyme_share_pic"));

        return joymeWikiShare;
    }


}