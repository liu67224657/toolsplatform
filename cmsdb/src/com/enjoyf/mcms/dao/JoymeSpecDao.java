package com.enjoyf.mcms.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.mcms.bean.JoymeSpec;
import com.enjoyf.util.StringUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JoymeSpecDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-08-02 22:11:13
     *
     * @param specId
     * @return JoymeSpec
     * @author shenqiv0.1
     */
    public JoymeSpec queryJoymeSpecbyId(Connection conn, Integer specId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_spec  WHERE spec_id = ? ";
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

    /**
     * 获得所有的专区列表
     *
     * @param
     * @return JoymeSpec
     * @author shenqiv0.1
     */
    public List queryAllJoymeSpec(Connection conn, int maxId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_spec where spec_id > ? ORDER BY spec_id ASC limit 0 ,200";
            List objectList = new ArrayList();
            objectList.add(maxId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            List list = new ArrayList();
            while (rs.next()) {
                JoymeSpec joymeSpec = getJoymeSpec(rs);
                list.add(joymeSpec);
            }
            return list;
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
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public long insertJoymeSpec(Connection conn, JoymeSpec bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_spec(spec_name,spec_type,spec_language,spec_size,spec_version,spec_pic_url,spec_advertise,spec_download_url,archive_id,file_path,last_update_time, is_compress_images) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            Object[] objects = new Object[]{bean.getSpecName(), bean.getSpecType(), bean.getSpecLanguage(), bean.getSpecSize(),
                    bean.getSpecVersion(), bean.getSpecPicUrl(), bean.getSpecAdvertise(), bean.getSpecDownloadUrl(), bean.getArchiveId(),
                    bean.getFilePath(), bean.getLastUpdateTime(), bean.getIsCompressImages()};
            return this.getLongKeyFromExecuteBindingUpdate(conn, sql, objects, true);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-08-03 00:32:36
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
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
     * @param specId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
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

    /**
     * 通过specName获得List<JoymeSpec>
     *
     * @param conn
     * @param
     * @return
     * @throws Exception
     */
    public List<JoymeSpec> queryLikeJoymeSpecBySpecName(Connection conn, String specName) throws JoymeDBException {
        DataBean dbean = null;
        List<JoymeSpec> listJoymeSpec = new ArrayList<JoymeSpec>();
        try {
            StringBuffer str = new StringBuffer();
            str.append(" SELECT spec_id,spec_name,file_path,package_name FROM joyme_spec ");
            if (!StringUtil.isEmpty(specName)) {
                str.append(" WHERE spec_name LIKE  '" + specName + "%'");
            }
            str.append(" GROUP BY file_path ORDER BY file_path ASC ");

            dbean = this.executeBindingQuery(conn, str.toString(), null);
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymeSpec joymeSpec = new JoymeSpec();
                joymeSpec.setSpecId(rs.getInt("spec_id"));
                joymeSpec.setSpecName(rs.getString("spec_name"));
                joymeSpec.setFilePath(rs.getString("file_path"));
                joymeSpec.setPackageName(rs.getString("package_name"));
                listJoymeSpec.add(joymeSpec);
            }
            return listJoymeSpec;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
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
        joymeSpec.setIsCompressImages(rs.getInt("is_compress_images"));
        joymeSpec.setPackageName(rs.getString("package_name"));
        joymeSpec.setIsPackage(rs.getInt("is_package"));
        return joymeSpec;
    }

}
