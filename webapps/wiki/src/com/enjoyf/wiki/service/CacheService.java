package com.enjoyf.wiki.service;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.SystemUtil;
import com.enjoyf.wiki.bean.JoymeRefresh;
import com.enjoyf.wiki.bean.JoymeWiki;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.factory.WikiPraseParam;
import com.enjoyf.wiki.tools.WikiUtil;
import com.enjoyf.wiki.util.RefreshWikiCDNUtil;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class CacheService {
    /**
     * 生成首页
     *
     * @param key
     * @param domain
     * @param path
     * @return
     */
    private static SystemUtil su = new SystemUtil();
    private static JoymeRefreshService joymeRefreshService = new JoymeRefreshService();
    private static ChannelService channelService = new ChannelService();

    private static JoymeWikiService joymeWikiService = new JoymeWikiService();

    public boolean initCreatingIndex(String wikiName, String key, String domain, String path, String url, String androidPath, String iosPath, boolean refreshCss, String wikiType, boolean supportDomain, String pcKeepJscss, String mKeepJscss) throws JoymeDBException, JoymeServiceException {
        try {
            LogService.infoSystemLog("###############Begin to submit creating index which key is : " + key + "#####################", true);

            // 放入同步的信息
            insertInitCreateIndex(wikiName, key, domain, path, url, androidPath, iosPath, refreshCss, wikiType, supportDomain, pcKeepJscss, mKeepJscss);


            LogService.infoSystemLog("###############End to creating index which key is : " + key + "#####################", true);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private int insertInitCreateIndex(String wikiName, String key, String domain, String path, String url, String androidPath, String iosPath, boolean refreshCss, String wikiType, boolean supportDomain, String pcKeepJscss, String mKeepJscss)
            throws JoymeDBException, JoymeServiceException {
        JoymeRefresh bean = new JoymeRefresh();
        bean.setMachineId(PropertiesContainer.getInstance().getMachineId());
        bean.setOperationTime(new Timestamp(System.currentTimeMillis()));
        bean.setOperationUser("admin");

        JSONObject object = new JSONObject();
        object.put("wikiName", wikiName);
        object.put("type", wikiType + "_initIndex");
        object.put("key", key);
        object.put("domain", domain);
        object.put("path", path);
        object.put("url", url);
        object.put("androidPath", androidPath);
        object.put("iosPath", iosPath);
        object.put("refreshCss", refreshCss);
        object.put("wikitype", wikiType);
        object.put("supportDomain", supportDomain);
        object.put("pcKeepJscss", pcKeepJscss);
        object.put("mKeepJscss", mKeepJscss);

        bean.setFreshContent(object.toString());

        // bean.set
        return joymeRefreshService.insertJoymeRefresh(null, bean);
    }

    public boolean initIndex(String wikiName, String key, String domain, String path, String url, String androidPath, String iosPath, boolean refreshCss, String channel, String wikiType, boolean supportDomain, Integer pcKeepJscss, Integer mKeepJscss) {
        try {

            String fileName = getFilePath(key);

            File file = new File(fileName);
            if (file.exists()) {
                FileUtils.forceDelete(file);
            }


            createJoymeWiki(wikiName, key, domain, path, androidPath, iosPath, wikiType, supportDomain, pcKeepJscss, mKeepJscss);

            // reload properties
            SystemService service = new SystemService();
            service.loadJoymeWIki();

            // 删除style.css
            String styleFilePath = su.getWebRootPath() + "css/" + wikiType + "/" + key + "/style.css";
            File styleFile = new File(styleFilePath);
            if (styleFile.exists() && refreshCss) {
                styleFile.delete();
            }

            // 生成首页
//            URLService urlService = new URLService();
            // PatternURLService urlService = new PatternURLService();
//            BaseJDBCDAO dao = new BaseJDBCDAOImpl();
//            Connection conn = dao.getConnection();

//            try {
//                int isIndex = 1;
            WikiPraseParam param = new WikiPraseParam();
            param.setFullUrl(url);
            param.setSaveFileName("index.shtml");
            param.setIndex(1);
            param.setPath(path);
            param.setWikiType(wikiType);
            param.setKey(key);
            param.setDomain(domain);
            param.setPageInfo("首页");
            param.setWikiPageId("index");
            channelService.getHtmlParseFactory(channel).parseURLAndSave(channel, param);

//            } finally {
//                dao.closeConnection(conn);
//            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean creatingIndex(String key, String domain, String path, String url, boolean refreshCss, String wikiType) throws JoymeDBException, JoymeServiceException {
        try {
            LogService.infoSystemLog("###############Begin to submit creating index which key is : " + key + "#####################", true);

            // 放入同步的信息
            insertCreateIndex(key, domain, path, url, refreshCss, wikiType);

            LogService.infoSystemLog("###############End to creating index which key is : " + key + "#####################", true);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private int insertCreateIndex(String key, String domain, String path, String url, boolean refreshCss, String wikiType)
            throws JoymeDBException, JoymeServiceException {
        JoymeRefresh bean = new JoymeRefresh();
        bean.setMachineId(PropertiesContainer.getInstance().getMachineId());
        bean.setOperationTime(new Timestamp(System.currentTimeMillis()));
        bean.setOperationUser("admin");

        JSONObject object = new JSONObject();
        object.put("type", wikiType + "_createIndex");
        object.put("key", key);
        object.put("domain", domain);
        object.put("path", path);
        object.put("url", url);
        object.put("refreshCss", refreshCss);
        object.put("wikitype", wikiType);

        bean.setFreshContent(object.toString());

        return joymeRefreshService.insertJoymeRefresh(null, bean);
    }

    public boolean createIndex(String key, String domain, String path, String url, boolean refreshCss, String channel, String wikiType) {
        try {

            String fileName = getFilePath(key);

            File file = new File(fileName);
            if (file.exists()) {
                FileUtils.forceDelete(file);
            }


            // 删除style.css
            String styleFilePath = su.getWebRootPath() + "css/" + wikiType + "/" + key + "/style.css";
            File styleFile = new File(styleFilePath);
            if (styleFile.exists() && refreshCss) {
                styleFile.delete();
            }

            // 生成首页
//            BaseJDBCDAO dao = new BaseJDBCDAOImpl();
//            Connection conn = dao.getConnection();

//            try {
//                int isIndex = 1;
            WikiPraseParam param = new WikiPraseParam();
            param.setFullUrl(url);
            param.setSaveFileName("index.shtml");
            param.setIndex(1);
            param.setPath(path);
            param.setWikiType(wikiType);
            param.setKey(key);
            param.setDomain(domain);
            param.setPageInfo("首页");
            param.setWikiPageId("index");
            channelService.getHtmlParseFactory(channel).parseURLAndSave(channel, param);

//            try {
//                RefreshWikiCDNUtil.refreshUrl(WikiUtil.getWikiUrl(key, wikiType, "index",channel));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//            } finally {
//                dao.closeConnection(conn);
//            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private boolean createJoymeWiki(String wikiName, String key, String domain, String path, String androidPath, String iosPath, String wikiType, boolean supportDomain, Integer pcKeepJscss, Integer mKeepJscss) throws Exception {

        JoymeWiki joymeWiki = new JoymeWiki();
        joymeWiki.setJoymeAndroidPath(androidPath);
        joymeWiki.setJoymeIosPath(iosPath);
        joymeWiki.setJoymeWikiDomain(domain);
        joymeWiki.setJoymeWikiKey(key);
        joymeWiki.setJoymeWikiPath(path);
        joymeWiki.setContextPath(wikiType);
        joymeWiki.setSupportSubDomain(supportDomain);

        joymeWiki.setJoymeWikiFactory("default");
        joymeWiki.setJoymeWikiName(wikiName);
        joymeWiki.setPcKeepJscss(pcKeepJscss);
        joymeWiki.setmKeepJscss(mKeepJscss);

        List<JoymeWiki> wikiList = joymeWikiService.queryJoymeWiki(null, key, wikiType);
        LogService.infoSystemLog("###############createJoymeWiki which key is : " + key + "#####################" + wikiType + "," + wikiList.size(), true);
        if (wikiList != null && wikiList.size() > 0) {
            joymeWiki.setJoymeWikiId(wikiList.get(0).getJoymeWikiId());
            joymeWikiService.updateJoymeWiki(null, joymeWiki);
        } else {
            joymeWikiService.insertJoymeWiki(null, joymeWiki);
            //wikiset新增一个 key
            PropertiesContainer.getInstance().addJoymeWikiKetSet(joymeWiki.getJoymeWikiKey());
        }
        PropertiesContainer.getInstance().putJoymeWiki(joymeWiki, joymeWiki.getContextPath());

        return true;
    }

    private String getFilePath(String key) {
        // String metaInfoPath = su.getMetaInfFolderPath();
        String cacheFolder = PropertiesContainer.getInstance().getCacheFolder();
        if (!new File(cacheFolder).exists()) {
            new File(cacheFolder).mkdirs();
        }

        String fileName = cacheFolder + "/wiki_" + key + ".properties";
        return fileName;
    }

    /**
     * 提交刷新单页
     *
     * @param key
     * @param wikiPageId
     * @return
     */
    public boolean beginRefreshPage(String key, String wikiPageId, String wikiType) {
        try {
            LogService.infoSystemLog("###############Begin to submit refresh papge which key is : " + key + "#####################", true);

            // 放入同步的信息
            insertRefreshPage(key, wikiPageId, wikiType);
            // boolean isSuccess = this.createIndex(key, domain, path, url,
            // androidPath, iosPath, refreshCss);
            LogService.infoSystemLog("###############End to submit refresh page which key is : " + key + "#####################", true);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void insertRefreshPage(String key, String wikiPageId, String wikiType) throws JoymeDBException, JoymeServiceException {
        JSONObject object = new JSONObject();
        object.put("type", wikiType + "_refreshPage");
        object.put("key", key);
        object.put("wikiPageId", wikiPageId);
        object.put("wikitype", wikiType);

        JoymeRefresh bean = new JoymeRefresh();
        bean.setMachineId(PropertiesContainer.getInstance().getMachineId());
        bean.setOperationTime(new Timestamp(System.currentTimeMillis()));
        bean.setOperationUser("admin");
        bean.setFreshContent(object.toString());

        joymeRefreshService.insertJoymeRefresh(null, bean);
    }

    /**
     * 删一个单页面
     *
     * @param key
     * @param wikiPageId
     * @return
     * @throws IOException
     */
    public boolean refreshPage(String key, String wikiPageId, String channel, String wikiType) throws IOException {
        String saveFilePath = channelService.getHtmlParseFactory(channel).getFileDirByChannel(channel, key, true, wikiType) + wikiPageId + ".shtml";
        File file = new File(saveFilePath);
        if (file.exists())
            FileUtils.forceDelete(file);
        saveFilePath = channelService.getHtmlParseFactory(channel).getFileDirByChannel(channel, key, false, wikiType) + wikiPageId + ".shtml";
        file = new File(saveFilePath);
        if (file.exists())
            FileUtils.forceDelete(file);

//        try {
//            RefreshWikiCDNUtil.refreshUrl(WikiUtil.getWikiUrl(key, wikiType, wikiPageId,channel ));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return true;
    }

    /**
     * 同步删除所有wiki内容
     *
     * @param key
     * @return
     */
    public boolean beginRefreshWiki(String key, String wikiType) {
        try {
            LogService.infoSystemLog("###############Begin to submit refresh all page which key is : " + key + "#####################", true);

            // 放入同步的信息
            insertRefreshWiki(key, wikiType);

            // boolean isSuccess = this.createIndex(key, domain, path, url,
            // androidPath, iosPath, refreshCss);
            LogService.infoSystemLog("###############Begin to submit refresh all page which key is : " + key + "#####################", true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void insertRefreshWiki(String key, String wikiType) throws JoymeDBException, JoymeServiceException {
        JSONObject object = new JSONObject();
        object.put("type", wikiType + "_refreshwiki");
        object.put("key", key);
        object.put("wikitype", wikiType);

        JoymeRefresh bean = new JoymeRefresh();
        bean.setMachineId(PropertiesContainer.getInstance().getMachineId());
        bean.setOperationTime(new Timestamp(System.currentTimeMillis()));
        bean.setOperationUser("admin");
        bean.setFreshContent(object.toString());

        joymeRefreshService.insertJoymeRefresh(null, bean);
    }

    /**
     * refresh wiki
     *
     * @param key
     * @return
     * @throws IOException
     */
    public boolean refreshWiki(String key, String channel, String wikiType) throws IOException {
        forceRefreshWiki(key, channel, true, wikiType);
        forceRefreshWiki(key, channel, false, wikiType);

//        RefreshWikiCDNUtil.refreshWiki("http://" + key + "." + PropertiesContainer.getInstance().getSubDomain());
        return true;
    }

    private boolean forceRefreshWiki(String key, String channel, boolean isSEO, String wikiType) {
        String saveFilePath = channelService.getHtmlParseFactory(channel).getFileDirByChannel(channel, key, isSEO, wikiType);

        LogService.infoSystemLog("Delete file key: " + key + " saveFilePath:" + saveFilePath);

        File file = new File(saveFilePath);
        File[] files = file.listFiles();
        if (files == null) {
            LogService.infoSystemLog("refreshWiki return false key: " + key + " files is null");
            return false;
        }

        for (int i = 0; i < files.length; i++) {
            File itemFile = files[i];
            if (itemFile.getName().equals("index.shtml"))
                continue;
            else {
                try {
                    FileUtils.forceDelete(itemFile);
                } catch (Exception e) {
                    LogService.errorSystemLog("Delete file failed:" + itemFile.getAbsolutePath(), e);
                    continue;
                }
            }
        }
        return true;
    }

    public boolean beginCreatingAllIndex(boolean refreshCss, String wikiType, boolean refreshPage) {
        try {
            LogService.infoSystemLog("###############Begin to submit creating index which refreshCss is : " + refreshCss + "wikiType :" + wikiType + "#####################", true);

            // 放入同步的信息
            insertCreatingAllIndex(refreshCss, wikiType, refreshPage);
            LogService.infoSystemLog("###############End to creating index which key is : " + refreshCss + "wikiType :" + wikiType + "#####################", true);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private int insertCreatingAllIndex(boolean refreshCss, String wikiType, boolean refreshPage) throws JoymeServiceException, JoymeDBException {
        JoymeRefresh bean = new JoymeRefresh();
        bean.setMachineId(PropertiesContainer.getInstance().getMachineId());
        bean.setOperationTime(new Timestamp(System.currentTimeMillis()));
        bean.setOperationUser("admin");

        JSONObject object = new JSONObject();
        object.put("type", wikiType + "_createAllIndex");
        object.put("refreshCss", refreshCss);
        object.put("wikitype", wikiType);
        object.put("refreshPage", refreshPage);
        bean.setFreshContent(object.toString());

        return joymeRefreshService.insertJoymeRefresh(null, bean);
    }


    public int reloadTemplate() throws IOException, JoymeServiceException, JoymeDBException {
        JSONObject object = new JSONObject();
        object.put("type", "reload_joymeTemplate");
        object.put("wikitype", "all");

        JoymeRefresh bean = new JoymeRefresh();
        bean.setMachineId(PropertiesContainer.getInstance().getMachineId());
        bean.setOperationTime(new Timestamp(System.currentTimeMillis()));
        bean.setOperationUser("admin");
        bean.setFreshContent(object.toString());

        return joymeRefreshService.insertJoymeRefresh(null, bean);
    }


    /**
     * 提交刷新网站地图
     *
     * @param key beginRefreshPage
     * @param
     * @return
     */
    public boolean refresitemapcache(String key) throws JoymeServiceException, JoymeDBException {
        try {
            LogService.infoSystemLog("###############Begin to submit refresitemapcache which key is : " + key + "#####################", true);

            // 放入同步的信息
            JSONObject object = new JSONObject();
            object.put("type", key + "_refreshSiteMap");
            object.put("key", key);
            object.put("wikitype", key);

            JoymeRefresh bean = new JoymeRefresh();
            bean.setMachineId(PropertiesContainer.getInstance().getMachineId());
            bean.setOperationTime(new Timestamp(System.currentTimeMillis()));
            bean.setOperationUser("admin");
            bean.setFreshContent(object.toString());

            joymeRefreshService.insertJoymeRefresh(null, bean);

            LogService.infoSystemLog("###############End to submit refresitemapcache which key is : " + key + "#####################", true);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删网站地图
     *
     * @param key
     * @param
     * @return
     * @throws IOException
     */
    public boolean refresSiteMap(String key) throws IOException {
        String saveFilePath = PropertiesContainer.getInstance().getCacheFolder() + "/sitemap/" + key;
        File file = new File(saveFilePath + "/sitemap.xml");
        if (file.exists())
            FileUtils.forceDelete(file);
        file = new File(saveFilePath + "/sitemap.txt");
        if (file.exists())
            FileUtils.forceDelete(file);
        return true;
    }

    /**
     * @param
     * @return
     * @throws IOException
     */
    public void refresAllPage() {
        String saveFilePath = PropertiesContainer.getInstance().getCacheFolder();
        File file = new File(saveFilePath);
        try {
            if (file.exists()) {
                FileUtils.forceDelete(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
