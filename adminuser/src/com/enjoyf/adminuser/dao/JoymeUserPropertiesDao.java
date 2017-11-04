package com.enjoyf.adminuser.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.adminuser.bean.JoymeUserProperties;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

public class JoymeUserPropertiesDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-09-16 17:50:02
     * 
     * @author shenqiv0.1
     * @param userPropertiesId
     * @return JoymeUserProperties
     */
    public JoymeUserProperties queryJoymeUserPropertiesbyId(Connection conn, Integer userPropertiesId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_admin_user.joyme_user_properties  WHERE user_properties_id = ?";
            List objectList = new ArrayList();
            objectList.add(userPropertiesId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymeUserProperties joymeUserProperties = getUserProperties(rs);
                return joymeUserProperties;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public List queryJoymeUserPropertiesByUserId(Connection conn, Integer userId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_admin_user.joyme_user_properties WHERE user_id = ?";
            List objectList = new ArrayList();
            objectList.add(userId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymeUserProperties joymeUserProperties = getUserProperties(rs);
                retList.add(joymeUserProperties);
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    private JoymeUserProperties getUserProperties(ResultSet rs) throws SQLException {
        JoymeUserProperties joymeUserProperties = new JoymeUserProperties();
        joymeUserProperties.setUserPropertiesId(rs.getInt("user_properties_id"));
        joymeUserProperties.setUserId(rs.getInt("user_id"));
        joymeUserProperties.setUserPropertiesKey(rs.getString("user_properties_key"));
        joymeUserProperties.setUserPropertiesValue(rs.getString("user_properties_value"));
        return joymeUserProperties;
    }

    /**
     * date 2013-09-16 17:50:02
     * 
     * @author shenqiv0.1
     * @param bean
     * @return int 1 success, 0 failed
     */
    public int insertJoymeUserProperties(Connection conn, JoymeUserProperties bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_admin_user.joyme_user_properties(user_id,user_properties_key,user_properties_value) VALUES (?,?,?)";
            Object[] objects = new Object[] { bean.getUserId(), bean.getUserPropertiesKey(), bean.getUserPropertiesValue() };
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-09-16 17:50:02
     * 
     * @author shenqiv0.1
     * @param bean
     * @param userPropertiesId
     * @return int 1 success, 0 failed
     */
    public int updateJoymeUserProperties(Connection conn, JoymeUserProperties bean) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_admin_user.joyme_user_properties SET $updateStr  WHERE user_properties_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getUserPropertiesId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-09-16 17:50:02
     * 
     * @author shenqiv0.1
     * @param userPropertiesId
     * @return int 1 success, 0 failed
     */
    public int deleteJoymeUserProperties(Connection conn, Integer userPropertiesId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_admin_user.joyme_user_properties  WHERE user_properties_id = ?";
            List objectList = new ArrayList();
            objectList.add(userPropertiesId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }
}