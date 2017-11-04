package com.enjoyf.autobuilder.dao;

import com.enjoyf.autobuilder.bean.IosSrc;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IosSrcDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-09-05 18:34:52
     *
     * @param srcId
     * @return IosSrc
     * @author shenqiv0.1
     */
    public IosSrc queryIosSrcbyId(Connection conn, Integer srcId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM ios_src  WHERE src_id = ?";
            List objectList = new ArrayList();
            objectList.add(srcId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                IosSrc iosSrc = new IosSrc();
                iosSrc.setSrcId(rs.getInt("src_id"));
                iosSrc.setSrcVersion(rs.getString("src_version"));
                iosSrc.setSrcType(rs.getString("src_type"));
                iosSrc.setSrcUrl(rs.getString("src_url"));
                return iosSrc;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }


    /**
     * date 2013-09-05 18:34:52
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertIosSrc(Connection conn, IosSrc bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO ios_src(src_version,src_type,src_url) VALUES (?,?,?)";
            Object[] objects = new Object[]{bean.getSrcVersion(), bean.getSrcType(), bean.getSrcUrl()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-09-05 18:34:52
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateIosSrc(Connection conn, IosSrc bean) throws JoymeDBException {
        try {
            String sql = "UPDATE ios_src SET $updateStr  WHERE src_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getSrcId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-09-05 18:34:52
     *
     * @param srcId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteIosSrc(Connection conn, Integer srcId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM ios_src  WHERE src_id = ?";
            List objectList = new ArrayList();
            objectList.add(srcId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public List querySrc(Connection conn) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM ios_src";
            dbean = this.executeBindingQuery(conn, sql, null);
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

    public List querySrc(Connection conn, String srcVersion, String srcType) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM src where src_version=? AND  src_type=?";
            List objectList = new ArrayList();
            objectList.add(srcVersion);
            objectList.add(srcType);
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

    private Object rsToObject(ResultSet rs) throws SQLException {
        IosSrc iosSrc = new IosSrc();
        iosSrc.setSrcId(rs.getInt("src_id"));
        iosSrc.setSrcVersion(rs.getString("src_version"));
        iosSrc.setSrcType(rs.getString("src_type"));
        iosSrc.setSrcUrl(rs.getString("src_url"));
        return iosSrc;
    }


}