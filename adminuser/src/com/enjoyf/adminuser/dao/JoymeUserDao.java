package com.enjoyf.adminuser.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.adminuser.bean.JoymeUser;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

public class JoymeUserDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-09-16 15:26:20
     * 
     * @author shenqiv0.1
     * @param userId
     * @return JoymeUser
     */
    public JoymeUser queryJoymeUserbyId(Connection conn, Integer userId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_admin_user.joyme_user WHERE user_id = ?";
            List objectList = new ArrayList();
            objectList.add(userId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymeUser joymeUser = getJoymeUser(rs);
                return joymeUser;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }
    
    public JoymeUser queryJoymeUserByUserName(Connection conn,  String userName, String userPassword)  throws JoymeDBException{
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_admin_user.joyme_user WHERE user_name = ? AND user_password = ?";
            List objectList = new ArrayList();
            objectList.add(userName);
            objectList.add(userPassword);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                 JoymeUser joymeUser = getJoymeUser(rs);
                 return joymeUser;
            }
          return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
      }

    private JoymeUser getJoymeUser(ResultSet rs) throws SQLException {
        JoymeUser joymeUser = new JoymeUser();
         joymeUser.setUserId(rs.getInt("user_id"));
         joymeUser.setUserName(rs.getString("user_name"));
         joymeUser.setUserPassword(rs.getString("user_password"));
         joymeUser.setUserTrueName(rs.getString("user_true_name"));
        return joymeUser;
    }

    /**
     * date 2013-09-16 15:26:20
     * 
     * @author shenqiv0.1
     * @param bean
     * @return int 1 success, 0 failed
     */
    public int insertJoymeUser(Connection conn, JoymeUser bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_admin_user.joyme_user(user_name,user_password,user_true_name) VALUES (?,?,?)";
            Object[] objects = new Object[] { bean.getUserName(), bean.getUserPassword(), bean.getUserTrueName() };
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-09-16 15:26:21
     * 
     * @author shenqiv0.1
     * @param bean
     * @param userId
     * @return int 1 success, 0 failed
     */
    public int updateJoymeUser(Connection conn, JoymeUser bean) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_admin_user.joyme_user SET $updateStr  WHERE user_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getUserId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-09-16 15:26:21
     * 
     * @author shenqiv0.1
     * @param userId
     * @return int 1 success, 0 failed
     */
    public int deleteJoymeUser(Connection conn, Integer userId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_admin_user.joyme_user  WHERE user_id = ?";
            List objectList = new ArrayList();
            objectList.add(userId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }
}