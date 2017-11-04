package com.enjoyf.mcms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.mcms.bean.JoymeChannelContent;

public class JoymeChannelContentDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-08-07 11:22:14
     * 
     * @author shenqiv0.1
     * @param channelContentId
     * @return JoymeChannelContent
     */
    public JoymeChannelContent queryJoymeChannelContentbyId(Connection conn, Integer channelContentId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_channel_content  WHERE channel_content_id = ?";
            List objectList = new ArrayList();
            objectList.add(channelContentId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                return getJoymeChannelContent(rs);
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }
    
    /**
     * 通过渠道和专区获得
     * @param conn
     * @param specId
     * @param channelName
     * @return
     * @throws JoymeDBException
     */
    public JoymeChannelContent queryJoymeChannelContentbySpecChannel(Connection conn, int specId , String channelName) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_channel_content  WHERE spec_id = ? AND channel_name = ?";
            List objectList = new ArrayList();
            objectList.add(specId);
            objectList.add(channelName);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                return getJoymeChannelContent(rs);
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    private JoymeChannelContent getJoymeChannelContent(ResultSet rs) throws SQLException {
        JoymeChannelContent joymeChannelContent = new JoymeChannelContent();
        joymeChannelContent.setChannelContentId(rs.getInt("channel_content_id"));
        joymeChannelContent.setChannelId(rs.getInt("channel_id"));
        joymeChannelContent.setSpecId(rs.getInt("spec_id"));
        joymeChannelContent.setChannelName(rs.getString("channel_name"));
        joymeChannelContent.setDownloadUrl(rs.getString("download_url"));
        joymeChannelContent.setAdvertiseContent(rs.getString("advertise_content"));
        return joymeChannelContent;
    }

    /**
     * date 2013-08-07 11:22:14
     * 
     * @author shenqiv0.1
     * @param bean
     * @return int 1 success, 0 failed
     */
    public int insertJoymeChannelContent(Connection conn, JoymeChannelContent bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_channel_content(channel_id,spec_id,channel_name,download_url,advertise_content) VALUES (?,?,?,?,?)";
            Object[] objects = new Object[] { bean.getChannelId(), bean.getSpecId(), bean.getChannelName(), bean.getDownloadUrl(),
                    bean.getAdvertiseContent() };
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-08-07 11:22:14
     * 
     * @author shenqiv0.1
     * @param bean
     * @param channelContentId
     * @return int 1 success, 0 failed
     */
    public int updateJoymeChannelContent(Connection conn, JoymeChannelContent bean) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_channel_content SET $updateStr  WHERE channel_content_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getChannelContentId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-08-07 11:22:14
     * 
     * @author shenqiv0.1
     * @param channelContentId
     * @return int 1 success, 0 failed
     */
    public int deleteJoymeChannelContent(Connection conn, Integer channelContentId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_channel_content  WHERE channel_content_id = ?";
            List objectList = new ArrayList();
            objectList.add(channelContentId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

}