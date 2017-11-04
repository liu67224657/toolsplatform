package com.enjoyf.mcms.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.mcms.bean.JoymeChannel;
import com.enjoyf.mcms.bean.JoymeChannelContent;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.dao.JoymeChannelContentDao;

public class JoymeChannelContentService {

    private static JoymeChannelContentDao subDao = new JoymeChannelContentDao();

    /**
     * date 2013-08-07 11:12:48
     * 
     * @author shenqiv0.1
     * @param channelContentId
     * @return JoymeChannelContent
     */
    public JoymeChannelContent queryJoymeChannelContentbyId(Connection conn, Integer channelContentId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeChannelContentbyId(conn, channelContentId);
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
     * 通过渠道和专区获得
     * 
     * @param conn
     * @param channelContentId
     * @return
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public JoymeChannelContent queryJoymeChannelContentbySpecChannel(Connection conn, int specId, String channelName) throws JoymeDBException,
            JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeChannelContentbySpecChannel(conn, specId, channelName);
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
     * date 2013-08-07 11:12:48
     * 
     * @author shenqiv0.1
     * @param bean
     * @return int 1 success, 0 failed
     */
    public int insertJoymeChannelContent(Connection conn, JoymeChannelContent bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertJoymeChannelContent(conn, bean);
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
     * date 2013-08-07 11:12:48
     * 
     * @author shenqiv0.1
     * @param bean
     * @param channelContentId
     * @return int 1 success, 0 failed
     */
    public int updateJoymeChannelContent(Connection conn, JoymeChannelContent bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateJoymeChannelContent(conn, bean);
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
     * date 2013-08-07 11:12:48
     * 
     * @author shenqiv0.1
     * @param channelContentId
     * @return int 1 success, 0 failed
     */
    public int deleteJoymeChannelContent(Connection conn, Integer channelContentId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteJoymeChannelContent(conn, channelContentId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public int replaceJoymeChannelContent(int specId, Map channelInfoMap) throws JoymeDBException, JoymeServiceException {
        Connection conn = null;
        try {
            conn = subDao.getConnection();

            Map channelMap = ConfigContainer.channelMap;
            Set entrySet = channelMap.entrySet();
            for (Iterator iterator = entrySet.iterator(); iterator.hasNext();) {
                Entry entry = (Entry) iterator.next();
                
                String channelName = (String)entry.getKey();
                JoymeChannel channel = (JoymeChannel)entry.getValue();
                String downUrl = channelInfoMap.get(channelName + "_specDownloadUrl") == null ? null : channelInfoMap.get(channelName + "_specDownloadUrl").toString();
                String advertise = channelInfoMap.get(channelName + "_specAdvertise") == null ? null : channelInfoMap.get(channelName + "_specAdvertise").toString();
                JoymeChannelContent content = this.queryJoymeChannelContentbySpecChannel(conn, specId, channelName);
                // 新增一个
                if (content == null) {
                    content = new JoymeChannelContent();
                    content.setSpecId(specId);
                    content.setDownloadUrl(downUrl);
                    content.setAdvertiseContent(advertise);
                    content.setChannelName(channelName);
                    content.setChannelId(channel.getChannelId());
                    this.insertJoymeChannelContent(conn, content);
                } else { //更改
                    content.setSpecId(specId);
                    content.setDownloadUrl(downUrl);
                    content.setAdvertiseContent(advertise);
                    content.setChannelName(channelName);
                    content.setChannelId(channel.getChannelId());
                    this.updateJoymeChannelContent(conn, content);
                }
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            subDao.closeConnection(conn);
        }
    }
    
    
    /**
     * 获得渠道的数据
     * @param request
     * @return
     */
    public Map getChannelMap(HttpServletRequest request){
        Map retMap = new HashMap();
        Map channelMap = ConfigContainer.channelMap;
        Set entrySet = channelMap.entrySet();
        
        for (Iterator iterator = entrySet.iterator(); iterator.hasNext();) {
            Entry entry = (Entry) iterator.next();
            
            String channelName = (String)entry.getKey();
            String downUrl = request.getParameter(channelName + "_specDownloadUrl");
            String advertise = request.getParameter(channelName + "_specAdvertise");
            retMap.put(channelName + "_specDownloadUrl", downUrl);
            retMap.put(channelName + "_specAdvertise", advertise);
        }
        
        return retMap;
    }
}