package com.enjoyf.mcms.dao;

import java.sql.Connection;
import java.sql.ResultSet;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

public class DedeMemberDao extends BaseJDBCDAOImpl{
    public boolean checkPassword(Connection conn , String userName , String password) throws JoymeDBException{
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM dede_member WHERE userid = ? AND pwd = ?";
            Object[] objects = new Object[] {userName , password};
            dbean = this.executeBindingQuery(conn, sql, objects);
            ResultSet rs = dbean.getRs();
            return rs.next();
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }
}
