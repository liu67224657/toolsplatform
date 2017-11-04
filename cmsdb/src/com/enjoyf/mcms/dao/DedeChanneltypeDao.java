package com.enjoyf.mcms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

public class DedeChanneltypeDao extends BaseJDBCDAOImpl {
    /**
     * date 2013-11-13 09:14:57
     * 
     * @author shenqiv0.1
     * @param id
     * @return DedeChanneltype
     */
    public String queryDedeChanneltypebyId(Connection conn, Integer id) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT addtable FROM dede_channeltype  WHERE id = ?";
            List objectList = new ArrayList();
            objectList.add(id);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            if (rs.next()) {
                return rs.getString("addtable");
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }
}
