package com.enjoyf.mcms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.mcms.bean.JoymePointArchive;
import com.enjoyf.mcms.bean.temp.PageBean;

public class JoymePointArchiveDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-08-01 16:58:25
     * 
     * @author shenqiv0.1
     * @param pointArchiveId
     * @return JoymePointArchive
     */
    public JoymePointArchive queryJoymePointArchivebyId(Connection conn, Long pointArchiveId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_point_archive  WHERE point_archive_id = ?";
            List objectList = new ArrayList();
            objectList.add(pointArchiveId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymePointArchive joymePointArchive = getJoymePointArchive(rs);
                return joymePointArchive;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    /**
     * date 2013-08-01 18:27:23
     * 
     * @author shenqiv0.1
     * @param pointId
     * @return List <JoymePointArchive>
     */
    public List queryJoymePointArchiveByPointId(Connection conn, Long pointId, int maxId, int limit, int seqId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_point_archive WHERE point_id = ? AND point_archive_id > ? AND seq_id = ? ORDER BY point_archive_id DESC LIMIT 0 , ?";
            List objectList = new ArrayList();
            objectList.add(pointId);
            objectList.add(maxId);
            objectList.add(seqId);
            objectList.add(limit);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymePointArchive joymePointArchive = getJoymePointArchive(rs);
                retList.add(joymePointArchive);
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    /**
     * 通过specArchiveId获得list
     * @param conn
     * @param specArchiveId
     * @param maxId
     * @return
     * @throws JoymeDBException
     */
    public List queryJoymePointArchiveListBySpecArchiveId(Connection conn, long specArchiveId, long maxId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_point_archive WHERE spec_archive_id = ? AND point_archive_id > ? ORDER BY point_archive_id ASC LIMIT 0 , 100";
            List objectList = new ArrayList();
            objectList.add(specArchiveId);
            objectList.add(maxId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymePointArchive joymePointArchive = this.getJoymePointArchive(rs);
                retList.add(joymePointArchive);
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    private JoymePointArchive getJoymePointArchive(ResultSet rs) throws SQLException {
        JoymePointArchive joymePointArchive = new JoymePointArchive();
        joymePointArchive.setPointArchiveId(rs.getLong("point_archive_id"));
        joymePointArchive.setPointId(rs.getLong("point_id"));
        joymePointArchive.setPointName(rs.getString("point_name"));
        joymePointArchive.setSpecFilePath(rs.getString("spec_file_path"));
        joymePointArchive.setTypeId(rs.getInt("type_id"));
        joymePointArchive.setSpecArchiveId(rs.getLong("spec_archive_id"));
        joymePointArchive.setArchiveId(rs.getLong("archive_id"));
        joymePointArchive.setTitle(rs.getString("title"));
        joymePointArchive.setArchivePublishTime(rs.getLong("archive_publish_time"));
        joymePointArchive.setSeqId(rs.getInt("seq_id"));
        joymePointArchive.setIsActive(rs.getInt("is_active"));
        joymePointArchive.setHtmlPath(rs.getString("html_path"));
        joymePointArchive.setHtmlFile(rs.getString("html_file"));
        return joymePointArchive;
    }

    /**
     * date 2013-08-01 16:58:25
     * 
     * @author shenqiv0.1
     * @param bean
     * @return int 1 success, 0 failed
     */
    public int insertJoymePointArchive(Connection conn, JoymePointArchive bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_point_archive(point_id,point_name,spec_file_path,type_id,spec_archive_id,archive_id,title,archive_publish_time,seq_id,is_active,html_path,html_file) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            Object[] objects = new Object[] { bean.getPointId(), bean.getPointName(), bean.getSpecFilePath(), bean.getTypeId(),
                    bean.getSpecArchiveId(), bean.getArchiveId(), bean.getTitle(), bean.getArchivePublishTime(), bean.getSeqId(), bean.getIsActive(),
                    bean.getHtmlPath(), bean.getHtmlFile() };
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-08-01 16:58:25
     * 
     * @author shenqiv0.1
     * @param bean
     * @param pointArchiveId
     * @return int 1 success, 0 failed
     */
    public int updateJoymePointArchive(Connection conn, JoymePointArchive bean) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_point_archive SET $updateStr  WHERE point_archive_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getPointArchiveId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * 更改一批seqId的状态
     * 
     * @param conn
     * @param isActive
     * @param seqId
     * @return
     * @throws JoymeDBException
     */
    public int updateJoymePointArchiveStatus(Connection conn, int isActive, int seqId) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_point_archive SET is_active = ? WHERE seq_id = ?";
            Object[] objects = new Object[] { isActive, seqId };
            return this.executeBindingUpdate(conn, sql, objects, true, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-08-01 16:58:25
     * 
     * @author shenqiv0.1
     * @param pointArchiveId
     * @return int 1 success, 0 failed
     */
    public int deleteJoymePointArchive(Connection conn, Long pointArchiveId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_point_archive  WHERE point_archive_id = ?";
            List objectList = new ArrayList();
            objectList.add(pointArchiveId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * 通过spec_archiveId删除joyme_point_archive
     * 
     * @param conn
     * @param specArchiveId
     * @return
     * @throws JoymeDBException
     */
    public int deleteJoymePointArchiveBySpecArchiveId(Connection conn, int specArchiveId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_point_archive WHERE spec_archive_id = ?";
            List objectList = new ArrayList();
            objectList.add(specArchiveId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), true, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * 删除老数据 date 2013-08-01 16:58:25
     * 
     * @author shenqiv0.1
     * @param pointArchiveId
     * @return int 1 success, 0 failed
     */
    public int deleteOldJoymePointArchive(Connection conn, int newSeqId, long dedeSpecArchiveId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_point_archive WHERE seq_id != ? AND spec_archive_id = ?";
            List objectList = new ArrayList();
            objectList.add(newSeqId);
            objectList.add(dedeSpecArchiveId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), true, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * 通过pointId
     * 
     * @param conn
     * @param pointId
     * @param pageNum
     * @param pageCount
     * @return
     * @throws JoymeDBException
     */
    public PageBean getJoymePointArchivePageList(Connection conn, int pointId, int pageNum, int pageCount) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_point_archive WHERE point_id = ? ORDER BY point_archive_id DESC";
            List objectList = new ArrayList();
            objectList.add(pointId);

            dbean = this.executeBindingQueryWithPage(conn, sql, objectList.toArray(), pageNum, pageCount, true);
            ResultSet rs = dbean.getRs();
            List retList = new ArrayList();
            int a = 0;
            boolean hasNextPage = false;
            while (rs.next()) {
                JoymePointArchive archive = this.getJoymePointArchive(rs);
                retList.add(archive);
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
}