package com.enjoyf.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.cms.bean.SeoConfig;
import com.enjoyf.framework.jdbc.bean.ConnectionBean;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

public class SeoConfigDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-11-05 10:10:20
     * 
     * @author shenqiv0.1
     * @param seoId
     * @return SeoConfig
     */
    public SeoConfig querySeoConfigbyId(ConnectionBean conn, String seoId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM seo_config  WHERE seo_id = ?";
            List objectList = new ArrayList();
            objectList.add(seoId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                SeoConfig seoConfig = getSeoConfig(rs);
                return seoConfig;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }
    
    /**
     * 获得所有的configList
     * @param conn
     * @return
     * @throws JoymeDBException
     */
    public List queryAllSeoConfigList(ConnectionBean conn) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM seo_config ORDER BY create_time DESC";
            List retList = new ArrayList();
            dbean = this.executeBindingQuery(conn, sql, null);
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                SeoConfig seoConfig = this.getSeoConfig(rs);
                retList.add(seoConfig);
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    private SeoConfig getSeoConfig(ResultSet rs) throws SQLException {
        SeoConfig seoConfig = new SeoConfig();
        seoConfig.setSeoId(rs.getString("seo_id"));
        seoConfig.setSeoTransferPath(rs.getString("seo_transfer_path"));
        seoConfig.setSeoOriginalPath(rs.getString("seo_original_path"));
        seoConfig.setCreateTime(rs.getTimestamp("create_time"));
        return seoConfig;
    }

    /**
     * date 2013-11-05 10:10:20
     * 
     * @author shenqiv0.1
     * @param bean
     * @return int 1 success, 0 failed
     */
    public int insertSeoConfig(ConnectionBean conn, SeoConfig bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO seo_config(seo_id,seo_transfer_path,seo_original_path,create_time) VALUES (?,?,?,?)";
            Object[] objects = new Object[] {bean.getSeoId(),  bean.getSeoTransferPath(), bean.getSeoOriginalPath(), bean.getCreateTime() };
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-11-05 10:10:20
     * 
     * @author shenqiv0.1
     * @param seoId
     * @param bean
     * @return int 1 success, 0 failed
     */
    public int updateSeoConfig(ConnectionBean conn, String seoId, SeoConfig bean) throws JoymeDBException {
        try {
            String sql = "UPDATE seo_config SET $updateStr  WHERE seo_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(seoId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-11-05 10:10:20
     * 
     * @author shenqiv0.1
     * @param seoId
     * @return int 1 success, 0 failed
     */
    public int deleteSeoConfig(ConnectionBean conn, String seoId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM seo_config  WHERE seo_id = ?";
            List objectList = new ArrayList();
            objectList.add(seoId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

}