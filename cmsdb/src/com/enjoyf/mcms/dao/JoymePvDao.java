package com.enjoyf.mcms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.mcms.bean.JoymePv;

public class JoymePvDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-09-02 15:29:09
     * 
     * @author shenqiv0.1
     * @param id
     * @return JoymePv
     */
    public JoymePv queryJoymePvbyId(Connection conn, Integer id) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_pv  WHERE id = ?";
            List objectList = new ArrayList();
            objectList.add(id);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymePv joymePv = getJoymePV(rs);
                return joymePv;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }
    
    /**
     * date 2013-09-02 15:29:09
     * 
     * @author shenqiv0.1
     * @param id
     * @return JoymePv
     */
    public JoymePv queryJoymePv(Connection conn,  String joymePvChannel, String joymePvDate)  throws JoymeDBException{
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_pv WHERE joyme_pv_channel = ? AND joyme_pv_date = ?";
            List objectList = new ArrayList();
            objectList.add(joymePvChannel);
            objectList.add(joymePvDate);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                 JoymePv joymePv = getJoymePV(rs);
                 return joymePv;
            }
          return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
      }

    private JoymePv getJoymePV(ResultSet rs) throws SQLException {
        JoymePv joymePv = new JoymePv();
         joymePv.setId(rs.getInt("id"));
         joymePv.setJoymePvChannel(rs.getString("joyme_pv_channel"));
         joymePv.setJoymePvDate(rs.getDate("joyme_pv_date"));
         joymePv.setJoymePvCount(rs.getLong("joyme_pv_count"));
        return joymePv;
    }

    /**
     * date 2013-09-02 15:29:09
     * 
     * @author shenqiv0.1
     * @param bean
     * @return int 1 success, 0 failed
     */
    public int insertJoymePv(Connection conn, JoymePv bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_pv(joyme_pv_channel,joyme_pv_date,joyme_pv_count) VALUES (?,?,?)";
            Object[] objects = new Object[] { bean.getJoymePvChannel(), bean.getJoymePvDate(), bean.getJoymePvCount() };
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-09-02 15:29:09
     * 
     * @author shenqiv0.1
     * @param bean
     * @param id
     * @return int 1 success, 0 failed
     */
    public int updateJoymePv(Connection conn, JoymePv bean) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_pv SET $updateStr  WHERE id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-09-02 15:29:09
     * 
     * @author shenqiv0.1
     * @param id
     * @return int 1 success, 0 failed
     */
    public int deleteJoymePv(Connection conn, Integer id) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_pv  WHERE id = ?";
            List objectList = new ArrayList();
            objectList.add(id);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

}