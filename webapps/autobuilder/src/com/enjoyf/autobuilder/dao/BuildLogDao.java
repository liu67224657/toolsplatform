package com.enjoyf.autobuilder.dao;

import com.enjoyf.autobuilder.bean.BuildLog;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuildLogDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-08-29 14:44:31
     *
     * @param buildLogId
     * @return BuildLog
     * @author shenqiv0.1
     */
    public BuildLog queryBuildLogbyId(Connection conn, Long buildLogId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM build_log  WHERE build_log_id = ?";
            List objectList = new ArrayList();
            objectList.add(buildLogId);
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


    /**
     * date 2013-08-29 14:44:31
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public long insertBuildLog(Connection conn, BuildLog bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO build_log(build_log_code,jsonstring,build_platform,build_type) VALUES (?,?,?,?)";
            Object[] objects = new Object[]{bean.getBuildLogCode(), bean.getJsonstring(), bean.getBuildPlatform(), bean.getBuildType()};
            return this.getLongKeyFromExecuteBindingUpdate(conn, sql, objects, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-08-29 14:44:31
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateBuildLog(Connection conn, BuildLog bean) throws JoymeDBException {
        try {
            String sql = "UPDATE build_log SET $updateStr  WHERE build_log_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getBuildLogId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-08-29 14:44:31
     *
     * @param buildLogId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteBuildLog(Connection conn, Integer buildLogId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM build_log  WHERE build_log_id = ?";
            List objectList = new ArrayList();
            objectList.add(buildLogId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public BuildLog getRecentBuildLog(Connection conn, String code, Integer buildPlatform, String buildType) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM build_log WHERE build_log_code=? AND build_platform=? AND build_type=? ORDER BY build_log_id DESC LIMIT 0,1";
            List objectList = new ArrayList();
            objectList.add(code);
            objectList.add(buildPlatform);
            objectList.add(buildType);
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

    private BuildLog rsToObject(ResultSet rs) throws SQLException {
        BuildLog buildLog = new BuildLog();
        buildLog.setBuildLogId(rs.getLong("build_log_id"));
        buildLog.setBuildLogCode(rs.getString("build_log_code"));
        buildLog.setJsonstring(rs.getString("jsonstring"));
        buildLog.setBuildPlatform(rs.getInt("build_platform"));
        buildLog.setBuildType(rs.getString("build_type"));
        return buildLog;
    }
}

