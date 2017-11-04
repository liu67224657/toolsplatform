package com.enjoyf.mcms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.mcms.bean.JoymeChannel;

public class JoymeChannelDao extends BaseJDBCDAOImpl {
    public List queryJoymeChannel(Connection conn) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_channel";
            dbean = this.executeBindingQuery(conn, sql, null);
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymeChannel joymeChannel = new JoymeChannel();
                joymeChannel.setChannelId(rs.getInt("channel_id"));
                joymeChannel.setChannelName(rs.getString("channel_name"));
                joymeChannel.setGameTemplate(rs.getString("game_template"));
                joymeChannel.setArchiveTemplate(rs.getString("archive_template"));
                joymeChannel.setMoreLink(rs.getString("more_link"));
                joymeChannel.setHtmlFactory(rs.getString("html_factory"));
                joymeChannel.setTagFactory(rs.getString("tag_factory"));
                joymeChannel.setArticleTypeFactory(rs.getString("articletype_factory"));
                joymeChannel.setTagTemplate(rs.getString("tag_template"));
                joymeChannel.setArticleTypeTemplate(rs.getString("articletype_template"));
                joymeChannel.setArchiveListFactory(rs.getString("archivelist_factory"));
                joymeChannel.setArchiveListTemplate(rs.getString("archivelist_template"));
                joymeChannel.setCategoryFactory(rs.getString("category_factory"));
                retList.add(joymeChannel);
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }
}
