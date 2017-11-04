package com.enjoyf.autobuilder.service;

import com.enjoyf.autobuilder.bean.BuildLog;
import com.enjoyf.autobuilder.dao.BuildLogDao;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;

import java.sql.Connection;

public class BuildLogService {
    public static final String BUILD_TYPE_BUILD = "build";
    public static final String BUILD_TYPE_PAKCAGE = "package";

    public static final String BUILD_TYPE_BATCHBUILD = "batchbuild";
    public static final String BUILD_TYPE_BATCHPACKAGE= "batchpkg";

    public static final int BUILD_PLATFORM_IOS = 1;
    public static final int BUILD_PLATFORM_ANDROID = 0;

    private static BuildLogDao subDao = new BuildLogDao();

    /**
     * date 2013-08-29 14:44:31
     *
     * @param buildLogId
     * @return BuildLog
     * @author shenqiv0.1
     */
    public BuildLog queryBuildLogbyId(Connection conn, Long buildLogId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryBuildLogbyId(conn, buildLogId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    /**
     * date 2013-08-29 14:44:31
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public BuildLog insertBuildLog(Connection conn, BuildLog bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            long logId = subDao.insertBuildLog(conn, bean);

            bean.setBuildLogId(logId);
            return bean;
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
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
    public int updateBuildLog(Connection conn, BuildLog bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateBuildLog(conn, bean);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }


    /**
     * date 2013-08-29 14:44:31
     *
     * @param buildLogId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteBuildLog(Connection conn, Integer buildLogId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteBuildLog(conn, buildLogId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public BuildLog getRecentBuildLog(Connection conn, String code, Integer buildPlatform, String buildType) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.getRecentBuildLog(conn, code, buildPlatform, buildType);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

}