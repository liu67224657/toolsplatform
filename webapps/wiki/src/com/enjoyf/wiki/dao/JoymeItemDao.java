package com.enjoyf.wiki.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.wiki.bean.JoymeItem;
import com.enjoyf.wiki.bean.PageBean;

public class JoymeItemDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-09-17 12:32:20
     *
     * @param joymeItemId
     * @return JoymeItem
     * @author shenqiv0.1
     */
    public JoymeItem queryJoymeItembyId(Connection conn, Integer joymeItemId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_item  WHERE joyme_item_id = ?";
            List objectList = new ArrayList();
            objectList.add(joymeItemId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymeItem joymeItem = getJoymeItem(rs);
                return joymeItem;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    /**
     * date 2013-09-17 16:10:14
     *
     * @param wiki
     * @param channel
     * @param isIndex
     * @return List <JoymeItem>
     * @author shenqiv0.1
     */
    public JoymeItem queryJoymeItem(Connection conn, String wiki, String channel, Integer isIndex, String itemKey, String contextPath) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_item WHERE wiki = ? AND channel = ? AND is_index = ? AND item_key = ? AND context_path=?";
            List objectList = new ArrayList();
            objectList.add(wiki);
            objectList.add(channel);
            objectList.add(isIndex);
            objectList.add(itemKey);
            objectList.add(contextPath);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                return getJoymeItem(rs);
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    /**
     * 根据条件查找
     *
     * @param conn
     * @param wiki
     * @param channel
     * @param isIndex
     * @param itemKey
     * @return
     * @throws JoymeDBException
     */
    public PageBean queryJoymeItemByCondition(Connection conn, String wiki, String channel, Integer isIndex, String itemKey, int pageNum, String contextPath) throws JoymeDBException {
        int pageCount = 30;
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_item WHERE 1=1 ";
            List objectList = new ArrayList();
            if (wiki != null && !wiki.equals("")) {
                sql += " AND wiki = ?";
                objectList.add(wiki);
            }
            if (channel != null && !channel.equals("")) {
                sql += " AND channel = ?";
                objectList.add(channel);
            }
            if (isIndex != 0) {
                sql += " AND is_index = ?";
                objectList.add(isIndex);
            }
            if (itemKey != null && !itemKey.equals("")) {
                sql += " AND item_key = ?";
                objectList.add(itemKey);
            }
            if (contextPath != null && !contextPath.equals("")) {
                sql += " AND context_path = ?";
                objectList.add(contextPath);
            }


//            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            dbean = this.executeBindingQueryWithPage(conn, sql, objectList.toArray(), pageNum, pageCount, true);
            ResultSet rs = dbean.getRs();
            List retList = new ArrayList();
            int a = 0;
            boolean hasNextPage = false;

            while (rs.next()) {
                JoymeItem joymeItem = getJoymeItem(rs);
                retList.add(joymeItem);
                a++;
                if (a == pageCount)
                    break;
            }

            if (rs.next())
                hasNextPage = true;

            PageBean bean = new PageBean();
            bean.setRetList(retList);
            bean.setHasNextPage(hasNextPage);
            return bean;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    private JoymeItem getJoymeItem(ResultSet rs) throws SQLException {
        JoymeItem joymeItem = new JoymeItem();
        joymeItem.setJoymeItemId(rs.getInt("joyme_item_id"));
        joymeItem.setWiki(rs.getString("wiki"));
        joymeItem.setChannel(rs.getString("channel"));
        joymeItem.setIsIndex(rs.getInt("is_index"));
        joymeItem.setItemKey(rs.getString("item_key"));
        joymeItem.setItemType(rs.getString("item_type"));
        joymeItem.setItemDescription(rs.getString("item_description"));
        joymeItem.setItemProperties(rs.getString("item_properties"));
        joymeItem.setItemContext(rs.getString("item_context"));
        joymeItem.setCreateDate(rs.getTimestamp("create_date"));
        joymeItem.setContextPath(rs.getString("context_path"));
        return joymeItem;
    }

    /**
     * date 2013-09-17 12:32:20
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertJoymeItem(Connection conn, JoymeItem bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_item(wiki,channel,is_index,item_key,item_type,item_description,item_properties,item_context,create_date,context_path) VALUES (?,?,?,?,?,?,?,?,?,?)";
            Object[] objects = new Object[]{bean.getWiki(), bean.getChannel(), bean.getIsIndex(), bean.getItemKey(), bean.getItemType(),
                    bean.getItemDescription(), bean.getItemProperties(), bean.getItemContext(), bean.getCreateDate(),bean.getContextPath()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-09-17 12:32:20
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateJoymeItem(Connection conn, JoymeItem bean) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_item SET $updateStr  WHERE joyme_item_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getJoymeItemId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-09-17 12:32:20
     *
     * @param joymeItemId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteJoymeItem(Connection conn, Integer joymeItemId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_item  WHERE joyme_item_id = ?";
            List objectList = new ArrayList();
            objectList.add(joymeItemId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

}