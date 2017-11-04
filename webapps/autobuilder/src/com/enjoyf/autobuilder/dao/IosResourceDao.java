package com.enjoyf.autobuilder.dao;

import com.enjoyf.autobuilder.service.IosResource;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IosResourceDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-09-05 19:11:26
     *
     * @param resourceId
     * @return IosResource
     * @author shenqiv0.1
     */
    public IosResource queryIosResourcebyId(Connection conn, Integer resourceId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM ios_resource  WHERE resource_id = ?";
            List objectList = new ArrayList();
            objectList.add(resourceId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {

                return rsToObject(rs);
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }


    /**
     * date 2013-09-05 19:11:26
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertIosResource(Connection conn, IosResource bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO ios_resource(resource_name,resource_code,resource_version,resource_icons,resource_images,resource_profiles,resource_dir) VALUES (?,?,?,?,?,?,?)";
            Object[] objects = new Object[]{bean.getResourceName(), bean.getResourceCode(), bean.getResourceVersion(), bean.getResourceIcons(), bean.getResourceImages(), bean.getResourceProfiles(), bean.getResourceDir()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-09-05 19:11:26
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateIosResource(Connection conn, IosResource bean) throws JoymeDBException {
        try {
            String sql = "UPDATE ios_resource SET $updateStr  WHERE resource_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getResourceId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-09-05 19:11:26
     *
     * @param resourceId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteIosResource(Connection conn, Integer resourceId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM ios_resource  WHERE resource_id = ?";
            List objectList = new ArrayList();
            objectList.add(resourceId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public List queryResource(Connection conn, String resourceCode, String resourceVersion) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM ios_resource WHERE resource_code=? AND  resource_version=?";
            List objectList = new ArrayList();
            objectList.add(resourceCode);
            objectList.add(resourceVersion);
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


    public List queryResource(Connection conn) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM ios_resource";
            List objectList = new ArrayList();
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

    private IosResource rsToObject(ResultSet rs) throws SQLException {
        IosResource iosResource = new IosResource();
        iosResource.setResourceId(rs.getInt("resource_id"));
        iosResource.setResourceName(rs.getString("resource_name"));
        iosResource.setResourceCode(rs.getString("resource_code"));
        iosResource.setResourceVersion(rs.getString("resource_version"));
        iosResource.setResourceIcons(rs.getString("resource_icons"));
        iosResource.setResourceImages(rs.getString("resource_images"));
        iosResource.setResourceProfiles(rs.getString("resource_profiles"));
        iosResource.setResourceDir(rs.getString("resource_dir"));
        return iosResource;
    }

}