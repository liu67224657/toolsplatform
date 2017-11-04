package com.enjoyf.mcms.service;

import java.sql.Connection;
import java.util.List;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.mcms.bean.JoymePointArchive;
import com.enjoyf.mcms.bean.temp.PageBean;
import com.enjoyf.mcms.dao.JoymePointArchiveDao;

public class JoymePointArchiveService {
    private static JoymePointArchiveDao subDao = new JoymePointArchiveDao();

    /**
     * date 2013-08-01 12:47:46
     *
     * @param pointArchiveId
     * @return JoymePointArchive
     * @author shenqiv0.1
     */
    public JoymePointArchive queryJoymePointArchivebyId(Connection conn, Long pointArchiveId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymePointArchivebyId(conn, pointArchiveId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public JoymePointArchive queryJoymePointArchivebyArchiveId(Connection conn, Integer archiveId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymePointArchivebyArchiveId(conn, archiveId);
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
     * 查找archive是否存在
     *
     * @param conn
     * @param pointId
     * @param archiveId
     * @param seqId
     * @return
     */
    public boolean isJoymePointArchiveExisted(Connection conn, long pointId, int archiveId, int seqId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.isJoymePointArchiveExisted(conn, pointId, archiveId, seqId);
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
     * 根据专区获得文章列表
     *
     * @param conn
     * @return
     * @throws JoymeServiceException
     * @throws JoymeDBException
     */
    public List queryJoymePointArchiveByPointId(Connection conn, Long pointId, int maxId, int limit, int seqId) throws JoymeServiceException, JoymeDBException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymePointArchiveByPointId(conn, pointId, maxId, limit, seqId);
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
     * 通过specArchiveId获得joymepointarchive
     *
     * @param conn
     * @param specArchiveId
     * @param maxId
     * @return
     * @throws Exception
     */
    public List queryJoymePointArchiveListBySpecArchiveId(Connection conn, long specArchiveId, long maxId) throws Exception {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymePointArchiveListBySpecArchiveId(conn, specArchiveId, maxId);
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
     * date 2013-08-01 12:47:46
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertJoymePointArchive(Connection conn, JoymePointArchive bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertJoymePointArchive(conn, bean);
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
     * date 2013-08-01 12:47:46
     *
     * @param bean
     * @param pointArchiveId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateJoymePointArchive(Connection conn, JoymePointArchive bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateJoymePointArchive(conn, bean);
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
     * 通过aid update
     *
     * @param conn
     * @param bean
     * @return
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public int updateJoymePointArchiveImageUrlByAId(Connection conn, JoymePointArchive bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateJoymePointArchiveImageUrlByAId(conn, bean);
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
     * 更改一批seqId的状态
     *
     * @param conn
     * @param isActive
     * @param seqId
     * @return
     * @throws JoymeDBException
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public int updateJoymePointArchiveStatus(Connection conn, int isActive, int seqId, int specArchiveId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateJoymePointArchiveStatus(conn, isActive, seqId, specArchiveId);
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
     * date 2013-08-01 12:47:46
     *
     * @param pointArchiveId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteJoymePointArchive(Connection conn, Long pointArchiveId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteJoymePointArchive(conn, pointArchiveId);
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
     * 通过archiveId删除joyme_point_archive
     *
     * @param conn
     * @param archiveId
     * @return
     * @throws Exception
     */
    public int deleteJoymePointArchiveBySpecArchiveId(Connection conn, int archiveId) throws Exception {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteJoymePointArchiveBySpecArchiveId(conn, archiveId);
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
     * 删除老的joyme_point_archive的数据
     *
     * @param conn
     * @param newSeqId
     * @param dedeSpecArticleId
     * @return
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public int deleteOldJoymePointArchive(Connection conn, int newSeqId, long dedeSpecArticleId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteOldJoymePointArchive(conn, newSeqId, dedeSpecArticleId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }


    public PageBean getJoymePointArchivePageList(Connection conn, int pointId, int pageNum, int pageCount) throws Exception {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.getJoymePointArchivePageList(conn, pointId, pageNum, pageCount);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public boolean isCompressImages(Connection conn, int aid) throws Exception {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.isCompressImages(conn, aid);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    /**
     * 根据archiveId 查找pointId
     *
     * @param conn
     * @param archiveId
     * @return
     * @throws Exception
     */
    public JoymePointArchive getPointArchiveIdByArchive(Connection conn, int archiveId) throws Exception {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.getPointArchiveIdByArchive(conn, archiveId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public JoymePointArchive getJoymePointArchive(Connection conn, Long pointId, Long pointArchiveId, String fx) throws Exception {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.getJoymePointArchive(conn, pointId, pointArchiveId, fx);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

}
