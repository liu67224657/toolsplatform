package com.enjoyf.webcache.service;



import com.enjoyf.framework.jdbc.exception.JoymeDBException;

import com.enjoyf.framework.jdbc.exception.JoymeServiceException;

import com.enjoyf.framework.log.LogService;

import com.enjoyf.util.MD5Util;

import com.enjoyf.webcache.bean.WebCacheUrl;

import com.enjoyf.webcache.dao.WebCacheUrlDao;



import java.sql.Connection;

import java.util.Date;



public class WebCacheUrlService {



    private static WebCacheUrlDao subDao = new WebCacheUrlDao();



    /**

     *

     * @param url

     * @param ruleId

     * @param channel

     * @param key

     * @param urltype

     * @throws Exception

     */

    public void insertOrUpdateWebcacheUrl(String url,String ruleId,String channel,String key,String urltype)throws Exception{

        try {

            String id = MD5Util.Md5(url);

            WebCacheUrl webcacheUrl = queryWebcacheUrlbyId(null, id);

            if (webcacheUrl == null) {

                webcacheUrl = new WebCacheUrl();

                webcacheUrl.setUrlId(id);

                webcacheUrl.setRule_id(ruleId);

                webcacheUrl.setHttpCode(200);

                webcacheUrl.setChannel(channel);

                webcacheUrl.setUrl(url);

                webcacheUrl.setUrlKey(key);

                webcacheUrl.setUrlType(urltype);

                webcacheUrl.setLastModifyTime(new Date());

                insertWebcacheUrl(null, webcacheUrl);

            } else {

                webcacheUrl=new WebCacheUrl();

                webcacheUrl.setUrlId(id);

                webcacheUrl.setLastModifyTime(new Date());

                webcacheUrl.setTags("");

                updateWebcacheUrl(null, webcacheUrl);

            }

        } catch (Exception e) {

            LogService.errorSystemLog("save insertWebCacheUrl", e);

        }



    }



    /**

     * date 2015-08-26 17:25:03

     *

     * @param urlId

     * @return WebcacheUrl

     * @author shenqiv0.1

     */

    public WebCacheUrl queryWebcacheUrlbyId(Connection conn, String urlId) throws JoymeDBException, JoymeServiceException {

        boolean isCloseConn = (conn != null) ? false : true;

        try {

            if (conn == null)

                conn = subDao.getConnection();

            return subDao.queryWebcacheUrlbyId(conn, urlId);

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

     * date 2015-08-26 17:25:04

     *

     * @param bean

     * @return int 1 success, 0 failed

     * @author shenqiv0.1

     */

    public int insertWebcacheUrl(Connection conn, WebCacheUrl bean) throws JoymeDBException, JoymeServiceException {

        boolean isCloseConn = (conn != null) ? false : true;

        try {

            if (conn == null)

                conn = subDao.getConnection();

            return subDao.insertWebcacheUrl(conn, bean);

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

     * date 2015-08-26 17:25:04

     *

     * @param bean

     * @return int 1 success, 0 failed

     * @author shenqiv0.1

     */

    public int updateWebcacheUrl(Connection conn, WebCacheUrl bean) throws JoymeDBException, JoymeServiceException {

        boolean isCloseConn = (conn != null) ? false : true;

        try {

            if (conn == null)

                conn = subDao.getConnection();

            return subDao.updateWebcacheUrl(conn, bean);

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

     * date 2015-08-26 17:25:04

     *

     * @param urlId

     * @return int 1 success, 0 failed

     * @author shenqiv0.1

     */

    public int deleteWebcacheUrl(Connection conn, String urlId) throws JoymeDBException, JoymeServiceException {

        boolean isCloseConn = (conn != null) ? false : true;

        try {

            if (conn == null)

                conn = subDao.getConnection();

            return subDao.deleteWebcacheUrl(conn, urlId);

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