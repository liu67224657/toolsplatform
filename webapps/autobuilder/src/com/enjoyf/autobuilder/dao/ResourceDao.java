package com.enjoyf.autobuilder.dao;

import com.enjoyf.autobuilder.bean.Resource;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResourceDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-08-29 12:09:27
     *
     * @param resourceId
     * @return Resource
     * @author shenqiv0.1
     */
    public Resource queryResourcebyId(Connection conn, Integer resourceId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM resource  WHERE resource_id = ?";
            List objectList = new ArrayList();
            objectList.add(resourceId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            if (rs.next()) {
                return rsToObject(rs);
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public List queryResource(Connection conn, String resourceCode, String resourceVersion) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM resource WHERE resource_code=? AND  resource_version=?";
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


    /**
     * date 2013-08-29 12:09:27
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertResource(Connection conn, Resource bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO resource(resource_name,resource_code,resource_version,resource_assets,resource_res) VALUES (?,?,?,?,?)";
            Object[] objects = new Object[]{bean.getResourceName(), bean.getResourceCode(), bean.getResourceVersion(), bean.getResourceAssets(), bean.getResourceRes()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-08-29 12:09:27
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateResource(Connection conn, Resource bean) throws JoymeDBException {
        try {
            String sql = "UPDATE resource SET $updateStr  WHERE resource_id = ?";
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
     * date 2013-08-29 12:09:27
     *
     * @param resourceId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteResource(Connection conn, Integer resourceId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM resource  WHERE resource_id = ?";
            List objectList = new ArrayList();
            objectList.add(resourceId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public List queryResource(Connection conn) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM resource";
            List objectList = new ArrayList();
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                retList.add(rsToObject(rs));
            }
            Collections.reverse(retList);
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    private Resource rsToObject(ResultSet rs) throws SQLException {
        Resource resource = new Resource();
        resource.setResourceId(rs.getInt("resource_id"));
        resource.setResourceName(rs.getString("resource_name"));
        resource.setResourceCode(rs.getString("resource_code"));
        resource.setResourceVersion(rs.getString("resource_version"));
        resource.setResourceAssets(rs.getString("resource_assets"));
        resource.setResourceRes(rs.getString("resource_res"));
        return resource;
    }

}