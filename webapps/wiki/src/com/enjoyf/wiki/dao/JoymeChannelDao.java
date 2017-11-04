package com.enjoyf.wiki.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.wiki.bean.JoymeChannel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JoymeChannelDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-08-22 16:04:32
     *
     * @param channelId
     * @return JoymeChannel
     * @author shenqiv0.1
     */
    public JoymeChannel queryJoymeChannelbyId(Connection conn, Integer channelId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_channel  WHERE channel_id = ?";
            List objectList = new ArrayList();
            objectList.add(channelId);
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
     * date 2013-08-22 16:04:32
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertJoymeChannel(Connection conn, JoymeChannel bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_channel(channel_name,html_factory) VALUES (?,?)";
            Object[] objects = new Object[]{bean.getChannelName(), bean.getHtmlFactory()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-08-22 16:04:32
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateJoymeChannel(Connection conn, JoymeChannel bean) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_channel SET $updateStr  WHERE channel_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getChannelId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-08-22 16:04:32
     *
     * @param channelId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteJoymeChannel(Connection conn, Integer channelId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_channel  WHERE channel_id = ?";
            List objectList = new ArrayList();
            objectList.add(channelId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    public List queryJoymeChannel(Connection conn) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_channel ";
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

    private JoymeChannel rsToObject(ResultSet rs) throws SQLException {
        JoymeChannel joymeChannel = new JoymeChannel();
        joymeChannel.setChannelId(rs.getInt("channel_id"));
        joymeChannel.setChannelName(rs.getString("channel_name"));
        joymeChannel.setHtmlFactory(rs.getString("html_factory"));

        return joymeChannel;
    }
}


