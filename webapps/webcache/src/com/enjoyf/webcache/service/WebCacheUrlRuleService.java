package com.enjoyf.webcache.service;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.webcache.bean.WebCacheUrlRule;
import com.enjoyf.webcache.dao.WebCacheUrlRuleDao;

import java.sql.Connection;

import java.util.List;


public class WebCacheUrlRuleService {

    private static WebCacheUrlRuleDao webCacheUrlRuleDao = new WebCacheUrlRuleDao();

    /**

     * date 2015-08-26 17:27:34

     *

     * @param ruleId

     * @return WebcacheUrlrule

     * @author shenqiv0.1

     */

    public WebCacheUrlRule getWebCacheUrlRule(Connection conn, String ruleId) throws JoymeDBException, JoymeServiceException {

        boolean isCloseConn = (conn != null) ? false : true;

        try {

            if (conn == null)

                conn = webCacheUrlRuleDao.getConnection();

            return webCacheUrlRuleDao.getWebCacheUrlRule(conn, ruleId);

        } catch (JoymeDBException e) {

            throw e;

        } catch (Exception e) {

            throw new JoymeServiceException(e);

        } finally {

            if (conn != null && isCloseConn)

                webCacheUrlRuleDao.closeConnection(conn);

        }

    }





    /**

     * date 2015-08-26 17:27:34

     *

     * @param bean

     * @return int 1 success, 0 failed

     * @author shenqiv0.1

     */

    public int insertWebcacheUrlrule(Connection conn, WebCacheUrlRule bean) throws JoymeDBException, JoymeServiceException {

        boolean isCloseConn = (conn != null) ? false : true;

        try {

            if (conn == null)

                conn = webCacheUrlRuleDao.getConnection();

            return webCacheUrlRuleDao.insertWebcacheUrlrule(conn, bean);

        } catch (JoymeDBException e) {

            throw e;

        } catch (Exception e) {

            throw new JoymeServiceException(e);

        } finally {

            if (conn != null && isCloseConn)

                webCacheUrlRuleDao.closeConnection(conn);

        }

    }





    /**

     * date 2015-08-26 17:27:35

     *

     * @param bean

     * @param ruleId
     * @return int 1 success, 0 failed

     * @author shenqiv0.1

     */

    public int updateWebCacheUrlRule(Connection conn, WebCacheUrlRule bean, String ruleId) throws JoymeDBException, JoymeServiceException {

        boolean isCloseConn = (conn != null) ? false : true;

        try {

            if (conn == null)

                conn = webCacheUrlRuleDao.getConnection();

            return webCacheUrlRuleDao.updateWebcacheUrlrule(conn, bean, ruleId);

        } catch (JoymeDBException e) {

            throw e;

        } catch (Exception e) {

            throw new JoymeServiceException(e);

        } finally {

            if (conn != null && isCloseConn)

                webCacheUrlRuleDao.closeConnection(conn);

        }

    }





    /**

     * date 2015-08-26 17:27:35

     *

     * @param ruleId

     * @return int 1 success, 0 failed

     * @author shenqiv0.1

     */

    public int deleteWebCacheUrlRule(Connection conn, String ruleId) throws JoymeDBException, JoymeServiceException {

        boolean isCloseConn = (conn != null) ? false : true;

        try {

            if (conn == null)

                conn = webCacheUrlRuleDao.getConnection();

            return webCacheUrlRuleDao.deleteWebCacheUrlRule(conn, ruleId);

        } catch (JoymeDBException e) {

            throw e;

        } catch (Exception e) {

            throw new JoymeServiceException(e);

        } finally {

            if (conn != null && isCloseConn)

                webCacheUrlRuleDao.closeConnection(conn);

        }

    }





    public List queryWebCacheUrlRule(Connection conn, int status, Integer srcType, Integer clientType, Integer channel) throws JoymeDBException, JoymeServiceException{

        boolean isCloseConn = (conn != null) ? false : true;

        try {

            if (conn == null)

                conn = webCacheUrlRuleDao.getConnection();

            return webCacheUrlRuleDao.queryWebcacheUrlrule(conn,  status, srcType, clientType, channel);

        } catch (JoymeDBException e) {

            throw e;

        } catch (Exception e) {

            throw new JoymeServiceException(e);

        } finally {

            if (conn != null && isCloseConn)

                webCacheUrlRuleDao.closeConnection(conn);

        }

    }

}