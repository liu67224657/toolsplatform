package com.enjoyf.activity.service;

import com.enjoyf.activity.bean.goods.ExchangeLog;
import com.enjoyf.activity.dao.ExchangeLogDao;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;

import java.sql.Connection;
import java.util.List;

public class ExchangeLogService {

    private static ExchangeLogDao subDao = new ExchangeLogDao();

    /**
     * date 2016-10-11 14:02:45
     *
     * @param id
     * @return ExchangeLog
     * @author shenqiv0.1
     */
    public ExchangeLog queryExchangeLogbyId(Connection conn, Long id) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryExchangeLogbyId(conn, id);
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
     * date 2016-10-11 14:02:45
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertExchangeLog(Connection conn, ExchangeLog bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertExchangeLog(conn, bean);
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
     * date 2016-10-11 14:02:46
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateExchangeLog(Connection conn, ExchangeLog bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateExchangeLog(conn, bean);
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
     * date 2016-10-11 14:02:46
     *
     * @param id
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteExchangeLog(Connection conn, Long id) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteExchangeLog(conn, id);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }


    public List<ExchangeLog> getExchangeGoodsPage(Connection conn, String openid, String goodsCategoryExchange, int pSize, int pNum) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;

        try {

            if (conn == null)

                conn = subDao.getConnection();

            return subDao.getExchangeGoodsPage(conn, openid, goodsCategoryExchange, pSize, pNum);

        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public Long getTotalExchangeLog(Connection conn,  String openid, String goodsCategoryExchange) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;

        try {

            if (conn == null)

                conn = subDao.getConnection();

            return subDao.getTotalExchangeLog(conn, openid, goodsCategoryExchange);

        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public ExchangeLog queryExchangeLogbyParam(Connection conn, String openid, Long goodsId) throws JoymeDBException, JoymeServiceException{
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryExchangeLogbyParam(conn, openid,goodsId);
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