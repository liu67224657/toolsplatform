package com.enjoyf.mcms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.mcms.bean.JoymePoint;

public class JoymePointDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-07-31 18:35:24
     * 
     * @author shenqiv0.1
     * @param specPointId
     * @return JoymePoint
     */
    public JoymePoint queryJoymePointbyId(Connection conn, Integer specPointId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_point  WHERE spec_point_id = ?";
            List objectList = new ArrayList();
            objectList.add(specPointId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymePoint joymePoint = getJoymePoint(rs);
                return joymePoint;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    /**
     * date 2013-07-31 18:35:24
     * 
     * @author shenqiv0.1
     * @param bean
     * @return int 1 success, 0 failed
     */
    public long insertJoymePoint(Connection conn, JoymePoint bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_point(archive_id,spec_point_name,display_row_num,keywords,typeid,create_time,is_able,html_path,html_file,is_active) VALUES (?,?,?,?,?,?,?,?,?,?)";
            Object[] objects = new Object[] { bean.getArchiveId(), bean.getSpecPointName(), bean.getDisplayRowNum(), bean.getKeywords(),
                    bean.getTypeid(), bean.getCreateTime(), bean.getIsAble(), bean.getHtmlPath(), bean.getHtmlFile(), bean.getIsActive() };
            return this.getLongKeyFromExecuteBindingUpdate(conn, sql, objects, true);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-07-31 18:35:24
     * 
     * @author shenqiv0.1
     * @param bean
     * @param specPointId
     * @return int 1 success, 0 failed
     */
    public int updateJoymePoint(Connection conn, JoymePoint bean) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_point SET $updateStr  WHERE spec_point_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getSpecPointId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }
    
    /**
     * date 2013-07-31 18:35:24
     * 
     * @author shenqiv0.1
     * @param bean
     * @param specPointId
     * @return int 1 success, 0 failed
     */
    public int updateJoymePointIsActiveByHtmlFile(Connection conn, String htmlPath) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_point SET is_active = 0, display_order = 0 WHERE html_file = ?";
            return this.executeBindingUpdate(conn, sql, new String[]{htmlPath}, true, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-07-31 18:35:24
     * 
     * @author shenqiv0.1
     * @param specPointId
     * @return int 1 success, 0 failed
     */
    public int deleteJoymePoint(Connection conn, Integer specPointId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_point  WHERE spec_point_id = ?";
            List objectList = new ArrayList();
            objectList.add(specPointId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }
    
    /**
     * 通过archiveId删除
     * @param conn
     * @param specPointId
     * @return
     * @throws JoymeDBException
     */
    public int deleteJoymePointByArchiveId(Connection conn, Integer archiveId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_point WHERE archive_id = ?";
            List objectList = new ArrayList();
            objectList.add(archiveId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), true, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * 通过archiveId和specName查找
     * @param conn
     * @param archiveId
     * @param specPointName
     * @return
     * @throws JoymeDBException
     */
    public JoymePoint queryJoymePointByName(Connection conn, Integer archiveId, String specPointName) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_point WHERE archive_id = ? AND spec_point_name = ?";
            List objectList = new ArrayList();
            objectList.add(archiveId);
            objectList.add(specPointName);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymePoint joymePoint = getJoymePoint(rs);
                return joymePoint;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }
    
    
    /**
     * 通过archive_id查
     * @param conn
     * @param archiveId
     * @return
     * @throws JoymeDBException
     */
    public List queryJoymePointByArchiveId(Connection conn,  Integer archiveId)  throws JoymeDBException{
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_point WHERE archive_id = ?";
            List objectList = new ArrayList();
            objectList.add(archiveId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                 JoymePoint joymePoint = this.getJoymePoint(rs);
                 retList.add(joymePoint);
            }
          return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
      }
    
    /**
     * 通过htmlFile获得pointlist
     * @param conn
     * @param htmlFile
     * @return
     * @throws JoymeDBException
     */
    public List queryJoymePointByHtmlFile(Connection conn,  String htmlFile)  throws JoymeDBException{
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_point WHERE html_file = ? AND is_active = 1 ORDER BY display_order ASC";
            List objectList = new ArrayList();
            objectList.add(htmlFile);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                 JoymePoint joymePoint = this.getJoymePoint(rs);
                 retList.add(joymePoint);
            }
          return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
      }

    private JoymePoint getJoymePoint(ResultSet rs) throws SQLException {
        JoymePoint joymePoint = new JoymePoint();
        joymePoint.setSpecPointId(rs.getLong("spec_point_id"));
        joymePoint.setArchiveId(rs.getInt("archive_id"));
        joymePoint.setSpecPointName(rs.getString("spec_point_name"));
        joymePoint.setDisplayRowNum(rs.getInt("display_row_num"));
        joymePoint.setKeywords(rs.getString("keywords"));
        joymePoint.setTypeid(rs.getString("typeid"));
        joymePoint.setCreateTime(rs.getTimestamp("create_time"));
        joymePoint.setIsAble(rs.getInt("is_able"));
        joymePoint.setHtmlPath(rs.getString("html_path"));
        joymePoint.setHtmlFile(rs.getString("html_file"));
        joymePoint.setIsActive(rs.getInt("is_active"));
        joymePoint.setDisplayOrder(rs.getInt("display_order"));
        return joymePoint;
    }

}
