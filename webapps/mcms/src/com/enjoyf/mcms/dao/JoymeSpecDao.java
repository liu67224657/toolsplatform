package com.enjoyf.mcms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.mcms.bean.JoymeSpec;

public class JoymeSpecDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-08-02 22:11:13
     * 
     * @author shenqiv0.1
     * @param specId
     * @return JoymeSpec
     */
    public JoymeSpec queryJoymeSpecbyId(Connection conn, Integer specId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_spec  WHERE spec_id = ?";
            List objectList = new ArrayList();
            objectList.add(specId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymeSpec joymeSpec = getJoymeSpec(rs);
                return joymeSpec;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public JoymeSpec queryJoymeSpecByArchiveId(Connection conn, Integer archiveId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_spec WHERE archive_id = ?";
            List objectList = new ArrayList();
            objectList.add(archiveId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            if (rs.next()) {
                return this.getJoymeSpec(rs);
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    /**
     * 通过file_path获得
     * 
     * @param conn
     * @param filePath
     * @return
     * @throws JoymeDBException
     */
    public JoymeSpec queryJoymeSpecByFilePath(Connection conn, String filePath) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_spec WHERE file_path = ?";
            List objectList = new ArrayList();
            objectList.add(filePath);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymeSpec joymeSpec = this.getJoymeSpec(rs);
                return joymeSpec;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    /**
     * date 2013-08-03 00:32:36
     * 
     * @author shenqiv0.1
     * @param bean
     * @return int 1 success, 0 failed
     */
    public long insertJoymeSpec(Connection conn, JoymeSpec bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_spec(spec_name,spec_type,spec_language,spec_size,spec_version,spec_pic_url,spec_advertise,spec_download_url,archive_id,file_path,last_update_time) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            Object[] objects = new Object[] { bean.getSpecName(), bean.getSpecType(), bean.getSpecLanguage(), bean.getSpecSize(),
                    bean.getSpecVersion(), bean.getSpecPicUrl(), bean.getSpecAdvertise(), bean.getSpecDownloadUrl(), bean.getArchiveId(),
                    bean.getFilePath(), bean.getLastUpdateTime() };
            return this.getLongKeyFromExecuteBindingUpdate(conn, sql, objects, true);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-08-03 00:32:36
     * 
     * @author shenqiv0.1
     * @param bean
     * @param specId
     * @return int 1 success, 0 failed
     */
    public int updateJoymeSpec(Connection conn, JoymeSpec bean) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_spec SET $updateStr  WHERE spec_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getSpecId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-08-03 00:32:36
     * 
     * @author shenqiv0.1
     * @param specId
     * @return int 1 success, 0 failed
     */
    public int deleteJoymeSpec(Connection conn, Integer specId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_spec  WHERE spec_id = ?";
            List objectList = new ArrayList();
            objectList.add(specId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    private JoymeSpec getJoymeSpec(ResultSet rs) throws SQLException {
        JoymeSpec joymeSpec = new JoymeSpec();
        joymeSpec.setSpecId(rs.getInt("spec_id"));
        joymeSpec.setSpecName(rs.getString("spec_name"));
        joymeSpec.setSpecType(rs.getString("spec_type"));
        joymeSpec.setSpecLanguage(rs.getString("spec_language"));
        joymeSpec.setSpecSize(rs.getString("spec_size"));
        joymeSpec.setSpecVersion(rs.getString("spec_version"));
        joymeSpec.setSpecPicUrl(rs.getString("spec_pic_url"));
        joymeSpec.setSpecAdvertise(rs.getString("spec_advertise"));
        joymeSpec.setSpecDownloadUrl(rs.getString("spec_download_url"));
        joymeSpec.setArchiveId(rs.getInt("archive_id"));
        joymeSpec.setFilePath(rs.getString("file_path"));
        joymeSpec.setLastUpdateTime(rs.getTimestamp("last_update_time"));
        return joymeSpec;
    }

}
