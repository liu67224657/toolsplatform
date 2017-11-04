package com.enjoyf.mcms.facade;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.enjoyf.framework.jdbc.BaseJDBCDAO;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.mcms.bean.DedeArchives;
import com.enjoyf.mcms.bean.JoymeChannelContent;
import com.enjoyf.mcms.bean.JoymePoint;
import com.enjoyf.mcms.bean.JoymePointArchive;
import com.enjoyf.mcms.bean.JoymeSpec;
import com.enjoyf.mcms.bean.JoymeSpecChannel;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.container.MemCachedContainer;
import com.enjoyf.mcms.factory.IHtmlParseFactory;
import com.enjoyf.mcms.factory.impl.DefaultParseFactoryImpl;
import com.enjoyf.mcms.service.DedeArchivesService;
import com.enjoyf.mcms.service.DedeMemberService;
import com.enjoyf.mcms.service.JoymeChannelContentService;
import com.enjoyf.mcms.service.JoymePointArchiveService;
import com.enjoyf.mcms.service.JoymePointService;
import com.enjoyf.mcms.service.JoymeSpecService;
import com.enjoyf.util.MD5Util;

public class AdminConsoleFacade {
    private static DedeMemberService dedeMemberService = new DedeMemberService();
    private static JoymeSpecService joymeSpecService = new JoymeSpecService();
    private static DedeArchivesService dedeArchivesService = new DedeArchivesService();
    private static JoymePointArchiveService joymePointArchiveService = new JoymePointArchiveService();
    private static JoymePointService joymePointService = new JoymePointService();
    private static JoymeChannelContentService joymeChannelContentService = new JoymeChannelContentService();
    private static IHtmlParseFactory factory = new DefaultParseFactoryImpl();
    private static BaseJDBCDAO dao = new BaseJDBCDAOImpl();
    
    
    /**
     * 登陆
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public boolean login(HttpServletRequest request) throws Exception {
        String userName = request.getParameter("userName");
        String password = MD5Util.Md5(request.getParameter("password"));
        boolean isLogin = dedeMemberService.checkPassword(null, userName, password);
        if (isLogin) {
            request.getSession().setAttribute("ac-user", userName);
        } else {
            request.setAttribute("message", "failed");
        }
        return isLogin;
    }

    /**
     * 通过filePath查询
     * 
     * @param request
     * @throws Exception
     */
    public void getJoymeSpecByFilePath(HttpServletRequest request) throws Exception {
        String filePath = request.getParameter("filePath");
        JoymeSpec joymeSpec = joymeSpecService.queryJoymeSpecByFilePath(null, filePath);
        // 封装专区渠道
        JoymeSpecChannel specChannel = generateSpecChannel(joymeSpec);
        request.setAttribute("channelMap", ConfigContainer.channelMap);
        request.setAttribute("joymeSpec", joymeSpec);
        request.setAttribute("specChannel", specChannel);
    }

    private JoymeSpecChannel generateSpecChannel(JoymeSpec joymeSpec) throws JoymeDBException, JoymeServiceException {
        Map channelMap = ConfigContainer.channelMap;
        Set keySet = channelMap.keySet();

        JoymeSpecChannel specChannel = new JoymeSpecChannel();
        if (joymeSpec != null) { //已经有了专区 spec_id
            specChannel.setSpecId(joymeSpec.getSpecId());
            for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
                String channelName = (String) iterator.next();
                JoymeChannelContent content = joymeChannelContentService.queryJoymeChannelContentbySpecChannel(null, joymeSpec.getSpecId(), channelName);
                String url = content == null ? null : content.getDownloadUrl();
                Map urlMap = specChannel.getDownUrlMap();
                if (urlMap == null)
                    urlMap = new HashMap();
                urlMap.put(channelName, url);
                specChannel.setDownUrlMap(urlMap);

                String advertiseContent = content == null ? null : content.getAdvertiseContent();
                Map advertiesMap = specChannel.getAdvertiesMap();
                if (advertiesMap == null)
                    advertiesMap = new HashMap();
                advertiesMap.put(channelName, advertiseContent);
                specChannel.setAdvertiesMap(advertiesMap);
            }
        } 

        return specChannel;
    }

    /**
     * 插入joyme spec
     * 
     * @param request
     * @return
     */
    public boolean insertJoymeSpec(HttpServletRequest request) {
        try {
            JoymeSpec spec = new JoymeSpec();
            spec.setSpecName(request.getParameter("specName"));
            spec.setSpecType(request.getParameter("specType"));
            spec.setSpecLanguage(request.getParameter("specLanguage"));
            spec.setSpecSize(request.getParameter("specSize"));
            spec.setSpecVersion(request.getParameter("specVersion"));
            spec.setSpecPicUrl(request.getParameter("specPicUrl"));

            // spec.setSpecAdvertise(request.getParameter("specAdvertise"));
            // spec.setSpecDownloadUrl(request.getParameter("specDownloadUrl"));

            int archiveId = 0;
            List list = dedeArchivesService.queryDedeArchivesByFileName(null, 0, request.getParameter("filePath"));
            if (list != null && list.size() > 0) {
                DedeArchives archives = (DedeArchives) list.get(0);
                archiveId = archives.getId();
            }
            spec.setArchiveId(archiveId);
            String filePath = request.getParameter("filePath");
            spec.setFilePath(filePath);
            spec.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));

            // 新建
            if (request.getParameter("specId") == null || request.getParameter("specId").equals("")) {
                int specId = (int) joymeSpecService.insertJoymeSpec(null, spec);
                Connection conn = null;
                try {
                    conn = dao.getConnection();
                    // 生成专区和渠道的对应
                    joymeChannelContentService.replaceJoymeChannelContent(specId, request);

                    clearAndGenerate(filePath, (int) specId, conn, request);
                } finally {
                    dao.closeConnection(conn);
                }
            } else { // 更新
                int specId = Integer.parseInt(request.getParameter("specId"));
                spec.setSpecId(specId);
                Connection conn = null;
                try {
                    conn = dao.getConnection();
                    joymeSpecService.updateJoymeSpec(conn, spec);
                    joymeChannelContentService.replaceJoymeChannelContent(specId, request);
                    clearAndGenerate(filePath, (int) specId, conn, request);
                } finally {
                    dao.closeConnection(conn);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新缓存
     * 
     * @param request
     * @return
     */
    public boolean refresh(HttpServletRequest request) {
        if (request.getParameter("specId") == null || request.getParameter("specId").equals("")) {
            return false;
        }

        String filePath = request.getParameter("filePath");
        int specId = Integer.parseInt(request.getParameter("specId"));
        Connection conn = null;
        try {
            conn = dao.getConnection();
            clearAndGenerate(filePath, specId, conn, request);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            dao.closeConnection(conn);
        }
    }

    private void clearAndGenerate(String filePath, int specId, Connection conn, HttpServletRequest request) throws JoymeDBException,
            JoymeServiceException, Exception, IOException {
        JoymeSpec spec = joymeSpecService.queryJoymeSpecbyId(conn, specId);
        int archiveId = spec.getArchiveId();
        // 清理spec_cache_file
        List joymePointList = joymePointService.queryJoymePointByArchiveId(conn, archiveId);
        clearGameCache(joymePointList, request);

        // 清理joyme_point_archive
        clearArchiveList(conn, archiveId, request);

        // 清理memcached
        List pointList = joymePointService.queryJoymePointByArchiveId(conn, archiveId);
        for (Iterator iterator = pointList.iterator(); iterator.hasNext();) {
            JoymePoint point = (JoymePoint) iterator.next();
            long pointId = point.getSpecPointId();
            this.clearMemcached(pointId);
        }

        // 清理joyme_point
        // joymePointService.deleteJoymePointByArchiveId(conn, archiveId);
        // 清理joyme_point_archive
        joymePointArchiveService.deleteJoymePointArchiveBySpecArchiveId(conn, archiveId);

        // 重新生成
        ArchiveFacade facade = new ArchiveFacade();
        int status = facade.generateTempData(filePath);
        //生成临时数据失败
        if(status == 0){
            throw new Exception("generate temp data failed which filename is :" + filePath);
        }
        
        Set keySet = ConfigContainer.channelMap.keySet();
        String[] channels = new String[keySet.size()];
        int i = 0;
        for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
            String channel = (String) iterator.next();
            channels[i] = channel;
            i++;
        }
        
        //生成临时文件
        facade.generateSpecHtml(conn, filePath, request, channels);
    }

    /**
     * 删除文件夹中的文件
     * 
     * @param conn
     * @param archiveId
     * @param request
     * @throws Exception
     * @throws IOException
     */
    private void clearArchiveList(Connection conn, int archiveId, HttpServletRequest request) throws Exception, IOException {
        long maxId = 0;
        for (int i = 0;; i++) {
            List joymePointArchiveList = joymePointArchiveService.queryJoymePointArchiveListBySpecArchiveId(conn, archiveId, maxId);
            for (Iterator iterator = joymePointArchiveList.iterator(); iterator.hasNext();) {
                JoymePointArchive archive = (JoymePointArchive) iterator.next();
                String filePath = archive.getHtmlPath();
                String fileName = archive.getHtmlFile();

                //删除多渠道的缓存
                Set keySet = ConfigContainer.channelMap.keySet();
                for (Iterator iterator2 = keySet.iterator(); iterator2.hasNext();) {
                    String channel = (String) iterator2.next();
                    String archivePath = factory.getArchiveCachePath(filePath, fileName, channel);
                    
                    File file = new File(archivePath);
                    if (file.exists())
                        file.delete();
                }

                if (archive.getPointArchiveId() > maxId)
                    maxId = archive.getPointArchiveId();
            }

            if (joymePointArchiveList == null || joymePointArchiveList.size() == 0)
                break;
        }
    }

    private void clearGameCache(List joymePointList, HttpServletRequest request) throws Exception {
        if(joymePointList != null && joymePointList.size() > 0){
            JoymePoint point = (JoymePoint) joymePointList.get(0);
            
            //如要清理所有的channel的数据
            Set keySet = ConfigContainer.channelMap.keySet();
            for (Iterator iterator2 = keySet.iterator(); iterator2.hasNext();) {
                String channel = (String) iterator2.next();
                String path = factory.getGameCachePath(point.getHtmlFile(), channel);
                
                File file = new File(path);
                if (file.exists())
                    file.delete();
            }
        }
    }

    private void clearMemcached(long pointId) {
        int pageNum = 1;
        while (true) {
            String key = ArchiveFacade.ARTICLE_LIST_FLAG + "_" + pointId + "_" + pageNum;
            if (MemCachedContainer.mcc.get(key) != null) {
                MemCachedContainer.mcc.delete(key);
                pageNum++;
            } else {
                break;
            }
        }

    }
}
