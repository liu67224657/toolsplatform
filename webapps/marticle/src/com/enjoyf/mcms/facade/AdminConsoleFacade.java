package com.enjoyf.mcms.facade;

import com.enjoyf.framework.jdbc.BaseJDBCDAO;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.mcms.bean.*;
import com.enjoyf.mcms.bean.temp.CreateItemBean;
import com.enjoyf.mcms.bean.temp.ReplaceChannelBean;
import com.enjoyf.mcms.bean.temp.ReplaceImageBean;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.container.MemCachedContainer;
import com.enjoyf.mcms.factory.IHtmlParseFactory;
import com.enjoyf.mcms.factory.impl.DefaultParseFactoryImpl;
import com.enjoyf.mcms.service.*;
import com.enjoyf.mcms.util.AppUtil;
import com.enjoyf.util.MD5Util;
import com.enjoyf.util.SystemUtil;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;

public class AdminConsoleFacade {
    private static DedeMemberService dedeMemberService = new DedeMemberService();
    private static JoymeSpecService joymeSpecService = new JoymeSpecService();
    private static DedeArchivesService dedeArchivesService = new DedeArchivesService();
    private static JoymePointArchiveService joymePointArchiveService = new JoymePointArchiveService();
    private static JoymePointService joymePointService = new JoymePointService();
    private static JoymeChannelContentService joymeChannelContentService = new JoymeChannelContentService();
    private static IHtmlParseFactory factory = new DefaultParseFactoryImpl();
    private static BaseJDBCDAO dao = new BaseJDBCDAOImpl();
    private static JoymeRefreshService joymeRefreshService = new JoymeRefreshService();
    private static SystemUtil su = new SystemUtil();
    private static JoymeImageService joymeImageService = new JoymeImageService();

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
        if (joymeSpec != null) { // 已经有了专区 spec_id
            specChannel.setSpecId(joymeSpec.getSpecId());
            for (Iterator iterator = keySet.iterator(); iterator.hasNext(); ) {
                String channelName = (String) iterator.next();
                JoymeChannelContent content = joymeChannelContentService.queryJoymeChannelContentbySpecChannel(null, joymeSpec.getSpecId(),
                        channelName);
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

    public boolean createInsertJoymeSpec(CreateItemBean bean) {
        try {
            String infoLog = "##########Begin to insert joyme spec which spec name is:" + bean.getSpecName();
            LogService.infoSystemLog(infoLog, true);

            JSONObject object = new JSONObject();
            object.put("type", "createItem");
            // object.put("channelMap", bean.getChannelMap());
            object.put("filePath", bean.getFilePath());
            object.put("specId", bean.getSpecId());
            object.put("specName", bean.getSpecName());
            object.put("specType", bean.getSpecType());
            object.put("specLanguage", bean.getSpecLanguage());
            object.put("specSize", bean.getSpecSize());
            object.put("specVersion", bean.getSpecVersion());
            object.put("specPicUrl", bean.getSpecPicUrl());
            object.put("localPath", bean.getLocalPath());
            object.put("isCompressImages", bean.getIsCompressImages());

            JoymeRefresh refresh = new JoymeRefresh();
            refresh.setFreshContent(object.toString());
            refresh.setMachineId(ConfigContainer.getMachineId());
            refresh.setOperationTime(new Timestamp(System.currentTimeMillis()));
            refresh.setOperationUser("admin");

            setRedis(refresh);
            joymeRefreshService.insertJoymeRefresh(null, refresh);

            infoLog = "##########end to insert joyme spec which spec name is:" + bean.getSpecName();
            LogService.infoSystemLog(infoLog, true);
            return true;
        } catch (Exception e) {
            LogService.errorBaseDaoLog("Error when createInsertJoymeSpec", e);
            return false;
        }
    }

    /**
     * 插入joyme spec
     *
     * @param
     * @return
     */
    public boolean insertJoymeSpec(CreateItemBean bean) {
        String infoLog = "======begin to execute insert joyme spec which name is : " + bean.getSpecName();
        LogService.infoSystemLog(infoLog, true);
        try {
            JoymeSpec spec = new JoymeSpec();
            spec.setSpecName(bean.getSpecName());
            spec.setSpecType(bean.getSpecType());
            spec.setSpecLanguage(bean.getSpecLanguage());
            spec.setSpecSize(bean.getSpecSize());
            spec.setSpecVersion(bean.getSpecVersion());
            spec.setSpecPicUrl(bean.getSpecPicUrl());
            spec.setIsCompressImages(Integer.parseInt(bean.getIsCompressImages()));

            // spec.setSpecAdvertise(request.getParameter("specAdvertise"));
            // spec.setSpecDownloadUrl(request.getParameter("specDownloadUrl"));

            int archiveId = 0;
            String filePath = bean.getFilePath();
            List list = dedeArchivesService.queryDedeArchivesByFileName(null, 0, filePath);
            if (list != null && list.size() > 0) {
                DedeArchives archives = (DedeArchives) list.get(0);
                archiveId = archives.getId();
            }
            spec.setArchiveId(archiveId);
            spec.setFilePath(filePath);
            spec.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));

            // 新建
            String localPath = bean.getLocalPath();
            // Map channelInfoMap = bean.getChannelMap();
            if (bean.getSpecId() == null || bean.getSpecId().equals("")) {
                int specId = (int) joymeSpecService.insertJoymeSpec(null, spec);
                Connection conn = null;
                // try {
                // conn = dao.getConnection();
                // 生成专区和渠道的对应
                // joymeChannelContentService.replaceJoymeChannelContent(specId,
                // channelInfoMap);
                clearAndGenerate(filePath, (int) specId, conn, localPath);
                // } finally {
                // dao.closeConnection(conn);
                // }
            } else { // 更新
                int specId = Integer.parseInt(bean.getSpecId());
                spec.setSpecId(specId);
                Connection conn = null;
                try {
                    conn = dao.getConnection();
                    joymeSpecService.updateJoymeSpec(conn, spec);
                    // //
//                    joymeChannelContentService.replaceJoymeChannelContent(specId,
//                    channelInfoMap);
                    clearAndGenerate(filePath, (int) specId, null, localPath);
                } finally {
                    dao.closeConnection(conn);
                }
            }

            LogService.infoSystemLog("======end to execute insert joyme spec which name is : " + bean.getSpecName(), true);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 插入广告语的命令
     *
     * @param bean
     * @return
     */
    public boolean createReplaceChannelMap(ReplaceChannelBean bean) {
        try {
            String infoLog = "##########Begin to insert joyme spec which spec id is:" + bean.getSpecId();
            LogService.infoSystemLog(infoLog, true);

            JSONObject object = new JSONObject();
            object.put("type", "replaceChannelMap");
            object.put("channelMap", bean.getChannelMap());
            object.put("filePath", bean.getFilePath());
            object.put("specId", bean.getSpecId());
            object.put("localPath", bean.getLocalPath());

            JoymeRefresh refresh = new JoymeRefresh();
            refresh.setFreshContent(object.toString());
            refresh.setMachineId(ConfigContainer.getMachineId());
            refresh.setOperationTime(new Timestamp(System.currentTimeMillis()));
            refresh.setOperationUser("admin");

            setRedis(refresh);
            joymeRefreshService.insertJoymeRefresh(null, refresh);

            infoLog = "##########end to insert joyme spec which spec id is:" + bean.getSpecId();
            LogService.infoSystemLog(infoLog, true);
            return true;
        } catch (Exception e) {
            LogService.errorBaseDaoLog("Error when createInsertJoymeSpec", e);
            return false;
        }
    }

    /**
     * 修改广告语
     */
    public boolean replaceChannelMap(ReplaceChannelBean bean) {
        Connection conn = null;
        try {
            conn = dao.getConnection();
            // 生成专区和渠道的对应
            int specId = Integer.parseInt(bean.getSpecId());
            joymeChannelContentService.replaceJoymeChannelContent(specId, bean.getChannelMap());
            clearAndGenerate(bean.getFilePath(), specId, conn, bean.getLocalPath());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            dao.closeConnection(conn);
        }
        return true;
    }

    /**
     * @return
     */
    public boolean insertCreateJoymeImage(ReplaceImageBean bean) {

        try {
            joymeImageService.insertJoymeImage(null, bean.getJoymeImage());
            createRefresh(String.valueOf(bean.getJoymeImage().getSpecid()), bean.getFilePath(), bean.getLocalPath());
            return true;
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertDeleteJoymeImage(ReplaceImageBean bean) {

        try {
            joymeImageService.deleteJoymeImage(null, bean.getImageId());
            createRefresh(String.valueOf(bean.getSpecId()), bean.getFilePath(), bean.getLocalPath());
            return true;
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertUpdateJoymeImage(ReplaceImageBean bean) {

        try {
            joymeImageService.updateJoymeImage(null, bean.getJoymeImage());
            createRefresh(String.valueOf(bean.getSpecId()), bean.getFilePath(), bean.getLocalPath());
            return true;
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean replaceJoymeImage(ReplaceImageBean bean) {

        createRefresh(String.valueOf(bean.getJoymeImage().getSpecid()), bean.getFilePath(), bean.getLocalPath());

        return false;
    }

    /**
     * 提交一个更新
     *
     * @param specId
     * @param filePath
     * @param localPath
     * @return
     */
    public boolean createRefresh(String specId, String filePath, String localPath) {
        try {
            LogService.infoSystemLog("##########Begin to clear joyme spec which spec filePath is:" + filePath, true);

            JSONObject object = new JSONObject();
            object.put("type", "refresh");
            object.put("specId", specId);
            object.put("filePath", filePath);
            object.put("localPath", localPath);

            JoymeRefresh refresh = new JoymeRefresh();
            refresh.setFreshContent(object.toString());
            refresh.setMachineId(ConfigContainer.getMachineId());
            refresh.setOperationTime(new Timestamp(System.currentTimeMillis()));
            refresh.setOperationUser("admin");

            setRedis(refresh);
            joymeRefreshService.insertJoymeRefresh(null, refresh);

            LogService.infoSystemLog("##########end to clear joyme spec which spec filePath is:" + filePath, true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 刷新缓存
     *
     * @param
     * @return
     */
    public boolean refresh(String specId, String filePath, String localPath) {
        LogService.infoSystemLog("==============begin to refresh which filePath is: " + filePath + "====================", true);
        if (specId == null || specId.equals("")) {
            return false;
        }

        Connection conn = null;
        try {
            conn = dao.getConnection();

            clearAndGenerate(filePath, Integer.parseInt(specId), conn, localPath);
            LogService.infoSystemLog("==============end to refresh which filePath is: " + filePath + "====================", true);
            return true;
        } catch (Exception e) {
            LogService.errorSystemLog("Error when refresh", e);
            return false;
        } finally {
            dao.closeConnection(conn);
        }
    }

    /**
     * 提交一个单页面的删除
     *
     * @param path
     * @return
     */
    public boolean createCleanSinglePage(String path) {
        try {
            LogService.infoSystemLog("##########Begin to clean the single page which path is : " + path, true);

            JSONObject object = new JSONObject();
            object.put("type", "cleansinglepage");
            object.put("path", path);

            JoymeRefresh refresh = new JoymeRefresh();
            refresh.setFreshContent(object.toString());
            refresh.setMachineId(ConfigContainer.getMachineId());
            refresh.setOperationTime(new Timestamp(System.currentTimeMillis()));
            refresh.setOperationUser("admin");

            setRedis(refresh);
            joymeRefreshService.insertJoymeRefresh(null, refresh);

            LogService.infoSystemLog("##########end to clean the single page which path is : " + path, true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void cleanSinglePage(String path) throws Exception {
        LogService.infoSystemLog("==============end to clean the single page which path is : " + path, true);
        path = path.replaceAll("http://marticle.joyme.com/", "");
        int position = path.indexOf("/");
        if (position > 0)
            path = path.substring(position + 1, path.length());

        String[] paths = path.split("/");
        String type = paths[0].equals("game") ? "game" : "archive";

        String tempPath = "";
        for (int i = 0; i < paths.length; i++) {
            if (i != paths.length - 1)
                tempPath += "/" + paths[i];
        }
        String cacheFolder = ConfigContainer.getCacheFolder() + "/" + type + tempPath;
        String fileName = paths[paths.length - 1];
        try {
            removeSubFileInFolder(new File(cacheFolder), fileName);
        } catch (Exception e) {
            LogService.infoSystemLog("Error when delete single file", true);
        }


        LogService.infoSystemLog("==============end to clean the single page which path is : " + path, true);
    }

    /**
     * 递归删除这个文件夹下面的这个文件
     *
     * @param cacheFolder
     * @param fileName
     */
    private void removeSubFileInFolder(File cacheFolder, String fileName) {
        File[] files = cacheFolder.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                removeSubFileInFolder(files[i], fileName);
            } else {
                if (files[i].getName().equals(fileName))
                    files[i].delete();
            }
        }
    }

    public void allRefresh(String localPath) throws JoymeDBException, JoymeServiceException {
        int maxId = 0;
        int all = 0;
        for (int i = 0; ; i++) {

            LogService.infoSystemLog("====获得第" + i + "批专区====", true);
            List joymeSpecList = joymeSpecService.queryAllJoymeSpec(null, maxId);
            if (joymeSpecList == null || joymeSpecList.size() == 0)
                break;

            for (Iterator iterator = joymeSpecList.iterator(); iterator.hasNext(); ) {
                all++;
                JoymeSpec spec = (JoymeSpec) iterator.next();
                String specId = spec.getSpecId() + "";
                String filePath = spec.getFilePath();
                this.createRefresh(specId, filePath, localPath);

                if (spec.getSpecId() > maxId)
                    maxId = spec.getSpecId();
            }
        }
        LogService.infoSystemLog("一共" + all + "个专区", true);
    }

    /**
     * 删除所有的缓存
     */
    public boolean createCleanAllCache() {
        try {
            LogService.infoSystemLog("##########Begin to create all clean##############", true);

            JSONObject object = new JSONObject();
            object.put("type", "cleanallcache");

            JoymeRefresh refresh = new JoymeRefresh();
            refresh.setFreshContent(object.toString());
            refresh.setMachineId(ConfigContainer.getMachineId());
            refresh.setOperationTime(new Timestamp(System.currentTimeMillis()));
            refresh.setOperationUser("admin");

            setRedis(refresh);
            joymeRefreshService.insertJoymeRefresh(null, refresh);

            LogService.infoSystemLog("##########Begin to create all clean##############", false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void cleanAllCache() throws Exception {
        LogService.infoSystemLog("##########Begin to cleanCache##########", true);
        String folder = ConfigContainer.getCacheFolder();
        File cacheFile = new File(folder);
        File[] files = cacheFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            try {
                if (files[i].isDirectory())
                    FileUtils.deleteDirectory(files[i]);
                else
                    files[i].delete();
            } catch (Exception e) {
                LogService.warnSystemLog("Warning when delete the file :" + files[i].getName());
            }
        }
        LogService.infoSystemLog("##########End to cleanCache##########", true);
    }

    /**
     * 删除所有的缓存
     */
    public boolean createCleanAllTag() {
        try {
            LogService.infoSystemLog("##########Begin to create all tag##############", true);

            JSONObject object = new JSONObject();
            object.put("type", "cleanalltag");

            JoymeRefresh refresh = new JoymeRefresh();
            refresh.setFreshContent(object.toString());
            refresh.setMachineId(ConfigContainer.getMachineId());
            refresh.setOperationTime(new Timestamp(System.currentTimeMillis()));
            refresh.setOperationUser("admin");

            setRedis(refresh);
            joymeRefreshService.insertJoymeRefresh(null, refresh);

            LogService.infoSystemLog("##########Begin to create all tag##############", false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 手游画报标签
     */
    public boolean createAllCategorytags() {
        try {
            LogService.infoSystemLog("##########Begin to create all createAllCategorytags##############", true);

            JSONObject object = new JSONObject();
            object.put("type", "categorytags");

            JoymeRefresh refresh = new JoymeRefresh();
            refresh.setFreshContent(object.toString());
            refresh.setMachineId(ConfigContainer.getMachineId());
            refresh.setOperationTime(new Timestamp(System.currentTimeMillis()));
            refresh.setOperationUser("admin");


            setRedis(refresh);
            joymeRefreshService.insertJoymeRefresh(null, refresh);

            LogService.infoSystemLog("##########Begin to create all createAllCategorytags##############", false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 手游画报标签列表页
     */
    public boolean createAllCategoryarchivelist() {
        try {
            LogService.infoSystemLog("##########Begin to create all createAllCategoryarchivelist##############", true);

            JSONObject object = new JSONObject();
            object.put("type", "categoryarchivelist");

            JoymeRefresh refresh = new JoymeRefresh();
            refresh.setFreshContent(object.toString());
            refresh.setMachineId(ConfigContainer.getMachineId());
            refresh.setOperationTime(new Timestamp(System.currentTimeMillis()));
            refresh.setOperationUser("admin");

            setRedis(refresh);
            joymeRefreshService.insertJoymeRefresh(null, refresh);

            LogService.infoSystemLog("##########Begin to create all createAllCategoryarchivelist##############", false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public void cleanAllTag() throws Exception {
        LogService.infoSystemLog("##########Begin to cleanAllTag##########", true);
        String folder = ConfigContainer.getCacheFolder() + "/tag/";
        cleanFolder(folder);
        LogService.infoSystemLog("##########End to cleanAllTag##########", true);
    }


    public boolean createCleanShare() {
        try {
            LogService.infoSystemLog("##########Begin to create all tag##############", true);

            JSONObject object = new JSONObject();
            object.put("type", "cleanShare");

            JoymeRefresh refresh = new JoymeRefresh();
            refresh.setFreshContent(object.toString());
            refresh.setMachineId(ConfigContainer.getMachineId());
            refresh.setOperationTime(new Timestamp(System.currentTimeMillis()));
            refresh.setOperationUser("admin");

            setRedis(refresh);
            joymeRefreshService.insertJoymeRefresh(null, refresh);

            LogService.infoSystemLog("##########Begin to create all tag##############", false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void cleanShare() throws Exception {
        LogService.infoSystemLog("##########Begin to cleanAllTag##########", true);

        ConfigContainer.cleanShare();

        LogService.infoSystemLog("##########End to cleanAllTag##########", true);
    }

    /**
     * 删除所有的缓存
     */
    public boolean createCleanAllArchiveType() {
        try {
            LogService.infoSystemLog("##########Begin to create all archvietype##############", true);

            JSONObject object = new JSONObject();
            object.put("type", "cleanallarchivetype");

            JoymeRefresh refresh = new JoymeRefresh();
            refresh.setFreshContent(object.toString());
            refresh.setMachineId(ConfigContainer.getMachineId());
            refresh.setOperationTime(new Timestamp(System.currentTimeMillis()));
            refresh.setOperationUser("admin");

            setRedis(refresh);
            joymeRefreshService.insertJoymeRefresh(null, refresh);

            LogService.infoSystemLog("##########Begin to create all archvietype##############", false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void cleanAllArchiveType() throws Exception {
        LogService.infoSystemLog("##########Begin to cleanArchiveType##########", true);
        String folder = ConfigContainer.getCacheFolder() + "/archive/";
        cleanFolder(folder);
        LogService.infoSystemLog("##########End to cleanArchiveType##########", true);

    }

    /**
     * 删除所有的缓存
     */
    public boolean createCleanAllArchiveList() {
        try {
            LogService.infoSystemLog("##########Begin to create all archivelist##############", true);

            JSONObject object = new JSONObject();
            object.put("type", "cleanallarchivelist");

            JoymeRefresh refresh = new JoymeRefresh();
            refresh.setFreshContent(object.toString());
            refresh.setMachineId(ConfigContainer.getMachineId());
            refresh.setOperationTime(new Timestamp(System.currentTimeMillis()));
            refresh.setOperationUser("admin");

            setRedis(refresh);
            joymeRefreshService.insertJoymeRefresh(null, refresh);

            LogService.infoSystemLog("##########Begin to create all archivelist##############", false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void cleanAllArchiveList() throws Exception {
        LogService.infoSystemLog("##########Begin to cleanArchiveType##########", true);
        String folder = ConfigContainer.getCacheFolder() + "/archivelist/";
        cleanFolder(folder);
        LogService.infoSystemLog("##########End to cleanArchiveType##########", true);
    }

    private void cleanFolder(String folder) {
        File cacheFile = new File(folder);
        File[] files = cacheFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            try {
                if (files[i].isDirectory())
                    FileUtils.deleteDirectory(files[i]);
                else
                    files[i].delete();
            } catch (Exception e) {
                LogService.warnSystemLog("Warning when delete the file :" + files[i].getName());
            }
        }
    }

    // TODO:删除单个子页
    public void refreshSinglePage(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String pageName = request.getParameter("pageName");
        int position = pageName.indexOf(contextPath);
        if (position >= 0) {
            String path = pageName.substring(position + contextPath.length(), pageName.length());
            System.out.println(path);
        }
        // request.setAttribute("message", "Error , no file :" + pageName +
        // " found");
    }

    private void clearAndGenerate(String filePath, int specId, Connection conn, String localPath) throws JoymeDBException, JoymeServiceException,
            Exception, IOException {
        JoymeSpec spec = joymeSpecService.queryJoymeSpecbyId(conn, specId);
        int archiveId = spec.getArchiveId();
        // 清理spec_cache_file
        List joymePointList = joymePointService.queryJoymePointByArchiveId(conn, archiveId);
        clearGameCache(joymePointList);

        // 清理joyme_point_archive
        clearArchiveList(conn, archiveId);

        // 清理more archivelist
        clearMoreArchiveList(joymePointList);

        // 清理memcached
//        List pointList = joymePointService.queryJoymePointByArchiveId(conn, archiveId);
//        for (Iterator iterator = pointList.iterator(); iterator.hasNext(); ) {
//            JoymePoint point = (JoymePoint) iterator.next();
//            long pointId = point.getSpecPointId();
//            this.clearMemcached(pointId);
//        }

        // 清理joyme_point
        // joymePointService.deleteJoymePointByArchiveId(conn, archiveId);
        // 清理joyme_point_archive
        joymePointArchiveService.deleteJoymePointArchiveBySpecArchiveId(conn, archiveId);

        // 重新生成
        ArchiveFacade facade = new ArchiveFacade();
        int status = facade.generateTempData(filePath);
        // 生成临时数据失败
        if (status == 0) {
            throw new Exception("generate temp data failed which filename is :" + filePath);
        }

        Set keySet = ConfigContainer.channelMap.keySet();
        String[] channels = new String[keySet.size()];
        int i = 0;
        for (Iterator iterator = keySet.iterator(); iterator.hasNext(); ) {
            String channel = (String) iterator.next();
            channels[i] = channel;
            i++;
        }

        // 生成临时文件
        facade.generateSpecHtml(conn, filePath, channels, localPath);
    }

    /**
     * 清理一个专区的more link
     *
     * @param joymePointList
     * @throws Exception
     */
    private void clearMoreArchiveList(List joymePointList) throws Exception {
        String cachePath = ConfigContainer.getCacheFolder() + "/archivelist";
        File file = new File(cachePath);
        File[] rootFiles = file.listFiles();
        if (joymePointList != null && joymePointList.size() > 0) {
            for (int a = 0; a < joymePointList.size(); a++) {
                JoymePoint point = (JoymePoint) joymePointList.get(a);
                long id = point.getSpecPointId();
                if (rootFiles != null) {
                    for (int i = 0; i < rootFiles.length; i++) {
                        File[] subFiles = rootFiles[i].listFiles();
                        for (int j = 0; j < subFiles.length; j++) {
                            if (subFiles[j].getName().startsWith(id + "_")) {
                                subFiles[j].delete();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 删除文件夹中的文件
     *
     * @param conn
     * @param archiveId
     * @param
     * @throws Exception
     * @throws IOException
     */
    private void clearArchiveList(Connection conn, int archiveId) throws Exception, IOException {
        long maxId = 0;
        while (true) {
            List joymePointArchiveList = joymePointArchiveService.queryJoymePointArchiveListBySpecArchiveId(conn, archiveId, maxId);
            for (Iterator iterator = joymePointArchiveList.iterator(); iterator.hasNext(); ) {
                JoymePointArchive archive = (JoymePointArchive) iterator.next();
                // 清理图片
                String targetPath = su.getWebRootPath() + "images/" + archive.getArchiveId() + "/";
                FileUtils.deleteDirectory(new File(targetPath));

                String filePath = archive.getHtmlPath();
                String fileName = archive.getHtmlFile();

                // 删除多渠道的缓存
                Set keySet = ConfigContainer.channelMap.keySet();
                String[] plats = new String[]{AppUtil.ANDROID, AppUtil.IPHONE, AppUtil.IPAD};
                for (Iterator iterator2 = keySet.iterator(); iterator2.hasNext(); ) {
                    String channel = (String) iterator2.next();
                    for (int i = 0; i < plats.length; i++) {
                        String plat = plats[i];
                        String archivePath = factory.getArchiveCachePath(filePath, fileName, channel, plat);

                        File file = new File(archivePath);
                        if (file.exists())
                            file.delete();
                    }

                }

                if (archive.getPointArchiveId() > maxId)
                    maxId = archive.getPointArchiveId();
            }

            if (joymePointArchiveList == null || joymePointArchiveList.size() == 0)
                break;
        }
    }

    private void clearGameCache(List joymePointList) throws Exception {
        if (joymePointList != null && joymePointList.size() > 0) {
            JoymePoint point = (JoymePoint) joymePointList.get(0);

            // 如要清理所有的channel的数据
            Set keySet = ConfigContainer.channelMap.keySet();
            for (Iterator iterator2 = keySet.iterator(); iterator2.hasNext(); ) {
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


    public void cleanAllCategorytags() throws Exception {
        LogService.infoSystemLog("##########Begin to cleanAllCategorytags##########", true);
        String folder = ConfigContainer.getCacheFolder() + "/categorytags/";
        cleanFolder(folder);
        LogService.infoSystemLog("##########End to cleanAllCategorytags##########", true);
    }

    public void cleanAllCategoryarchivelist() throws Exception {
        LogService.infoSystemLog("##########Begin to cleanAllCategoryarchivelist##########", true);
        String folder = ConfigContainer.getCacheFolder() + "/categoryarticles/";
        cleanFolder(folder);
        LogService.infoSystemLog("##########End to cleanAllCategoryarchivelist##########", true);
    }


    private static void setRedis(JoymeRefresh refresh) {
        //添加打redis中
        ConfigContainer.getNamingService().pushAllServQueue(ConfigContainer.getService().getServiceType(), refresh.getFreshContent());
    }
}
