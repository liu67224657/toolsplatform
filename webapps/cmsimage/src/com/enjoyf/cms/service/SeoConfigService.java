package com.enjoyf.cms.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.enjoyf.framework.redis.RedisManager;
import net.sf.json.JSONObject;

import com.enjoyf.cms.bean.JoymeRefresh;
import com.enjoyf.cms.bean.SeoConfig;
import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.cms.container.SEOContainer;
import com.enjoyf.cms.dao.SeoConfigDao;
import com.enjoyf.framework.jdbc.bean.ConnectionBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.log.LogService;

public class SeoConfigService {

    private static SeoConfigDao subDao = new SeoConfigDao();
    private static JoymeRefreshService joymeRefreshService = new JoymeRefreshService();
    /**
     * date 2013-11-05 10:10:21
     *
     * @param seoId
     * @return SeoConfig
     * @author shenqiv0.1
     */
    public SeoConfig querySeoConfigbyId(ConnectionBean conn, String seoId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getRWConnection();
            return subDao.querySeoConfigbyId(conn, seoId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public List queryAllSeoConfigList(ConnectionBean conn) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getRWConnection();
            return subDao.queryAllSeoConfigList(conn);
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
     * date 2013-11-05 10:10:21
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertSeoConfig(ConnectionBean conn, SeoConfig bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getRWConnection();
            return subDao.insertSeoConfig(conn, bean);
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
     * date 2013-11-05 10:10:21
     *
     * @param seoId
     * @param bean  @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateSeoConfig(ConnectionBean conn, String seoId, SeoConfig bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getRWConnection();
            return subDao.updateSeoConfig(conn, seoId, bean);
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
     * date 2013-11-05 10:10:21
     *
     * @param seoId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteSeoConfig(ConnectionBean conn, String seoId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getRWConnection();
            return subDao.deleteSeoConfig(conn, seoId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public void loadSeoConfig() throws JoymeDBException, JoymeServiceException {
        LogService.infoSystemLog("############Start to refresh SEO#################");
        SeoConfigService service = new SeoConfigService();
        List seoConfigList = service.queryAllSeoConfigList(null);

        Map transferMap = new HashMap();
        Map originalMap = new HashMap();
        for (Iterator iterator = seoConfigList.iterator(); iterator.hasNext(); ) {
            SeoConfig config = (SeoConfig) iterator.next();
            transferMap.put(config.getSeoTransferPath(), config.getSeoOriginalPath());
            originalMap.put(config.getSeoOriginalPath(), config.getSeoTransferPath());
            System.out.println(config.getSeoTransferPath() + " ---> " + config.getSeoOriginalPath());
        }
        SEOContainer.transferMap = transferMap;
        SEOContainer.originalMap = originalMap;
        LogService.infoSystemLog("############End to refresh SEO#################");
    }

    /**
     * 添加refresh seo的记录
     *
     * @return
     */
    public boolean createRefresh() {
        try {
            LogService.infoSystemLog("##########Begin create to refresh SEO##########", true);

            JSONObject object = new JSONObject();
            object.put("type", "refreshSEO");

            JoymeRefresh refresh = new JoymeRefresh();
            refresh.setFreshContent(object.toString());
            refresh.setMachineId(PropertiesContainer.getMachineId());
            refresh.setOperationTime(new Timestamp(System.currentTimeMillis()));
            refresh.setOperationUser("admin");

            joymeRefreshService.insertJoymeRefresh(null, refresh);

            LogService.infoSystemLog("##########End create to refresh SEO#############", true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}