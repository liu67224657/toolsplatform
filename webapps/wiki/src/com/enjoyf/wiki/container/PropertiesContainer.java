package com.enjoyf.wiki.container;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.memcached.MemCachedConfig;
import com.enjoyf.framework.memcached.MemCachedManager;
import com.enjoyf.framework.redis.RedisConfig;
import com.enjoyf.framework.redis.RedisManager;
import com.enjoyf.service.NamingService;
import com.enjoyf.service.Service;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.SystemUtil;
import com.enjoyf.wiki.bean.JoymeWiki;
import com.enjoyf.wiki.service.JoymeWikiService;
import com.enjoyf.wiki.service.SystemService;
import com.enjoyf.wiki.service.WikiMemcachedService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class PropertiesContainer {

    private static PropertiesContainer instance = new PropertiesContainer();

    //contextPath+key
//    public Map<String, JoymeWiki> joymeWikiMap = new HashMap<String, JoymeWiki>();
    private WikiMemcachedService wikiMemcachedService = new WikiMemcachedService();
    private JoymeWikiService joymeWikiService = new JoymeWikiService();

    public Set<String> joymeWikiKetSet = new HashSet<String>();
    public String cacheFolder = null;
    public int machineId = 0;

    private Service service;

    public boolean isDownload = false;
    public String sourceUrl = "http://www.joyme.com";
    public String wikiHost = "http://wiki.joyme.com";
    public String wikiMobileHost = "http://m.wiki.joyme.com";
    public String templateSourceUrl = "";

    public String joymePcCommentJsSrc = "";
    public String joymeMobileCommentJsSrc = "";
    public String joymeApiHotList = "";
    public String joymeApiCommentList = "";
    public String joymeCommentApiGetByContentId = "";
    public String joymeCommentAgreeApi = "";
    public String joymeCommentPostApi = "";
    public String joymeCommentListUrl = "";
    public String joymeMobileCommentListUrl = "";
    public String joymeCommentGetcidApi = "";

    public String joymeCommentQueryApi2 = "";
    public String joymeCommentListUrl2 = "";

    public String joymeCommentRemoveApi = "";


    public boolean isSavePv = true;
    public int mongoDbMaxConns;
    public int mongoDBPort;
    public String mongoDbName;
    public String mongoDbIp;
    public String cacheCSSZIPFolder;
    public String defaultChannel;

    public boolean isWikiRanking = false;


    public String searchSaveUrl = "";
    public String searchDeleteUrl = "";

    public String subDomain = "joyme.com";

    private MemCachedManager memCachedManager;

    private String wikiLoginPage;
    private String wikiMainPage;
    private String wikiLoginAction;
    private String wikiLogoutAction;

    private RedisManager redisManager;

    private List<String> supportWikiKeyList = new ArrayList<String>();

    //wikiset需要添加一个默认的default
    public static String DEFAULE_WIKISET_WIKI = "default";

    private PropertiesContainer() {
        try {
            loadCacheProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PropertiesContainer getInstance() {
        return instance;
    }

    public void loadCacheProperties() throws IOException {
        SystemUtil util = new SystemUtil();
        String metaInfPath = util.getMetaInfFolderPath();
        String path = metaInfPath + "/cache.properties";
        InputStream is = new FileInputStream(new File(path));
        try {
            Properties prop = new Properties();
            prop.load(is);
            cacheFolder = prop.getProperty("cache_folder");
            cacheCSSZIPFolder = prop.getProperty("cache_csszip_folder");
            isDownload = Boolean.parseBoolean(prop.getProperty("is_download"));
            machineId = Integer.parseInt(prop.getProperty("machine_id"));
            sourceUrl = prop.getProperty("source_url");
            templateSourceUrl = prop.getProperty("template_source_url");


            String service_idString = prop.getProperty("service_id");
            if (service_idString != null) {
                service = Service.getServiceByString(service_idString);
            }


            joymePcCommentJsSrc = prop.getProperty("joyme_comment_js");
            joymeMobileCommentJsSrc = prop.getProperty("joyme_mobile_comment_js");
            joymeApiHotList = prop.getProperty("joyme_comment_hotlist_api");
            joymeApiCommentList = prop.getProperty("joyme_comment_commentlist_api");
            joymeCommentApiGetByContentId = prop.getProperty("joyme_comment_getcontentid_api");
            joymeCommentAgreeApi = prop.getProperty("joyme_comment_agree_api");
            joymeCommentPostApi = prop.getProperty("joyme_comment_post_api");
            joymeCommentListUrl = prop.getProperty("joyme_comment_list_url");
            joymeMobileCommentListUrl = prop.getProperty("joyme_mobile_comment_list_url");
            joymeCommentGetcidApi = prop.getProperty("joyme_comment_getcid_api");
            joymeCommentGetcidApi = prop.getProperty("joyme_comment_getcid_api");
            joymeCommentQueryApi2 = prop.getProperty("joyme_comment_query_api2");
            joymeCommentListUrl2 = prop.getProperty("joyme_comment_list_url2");
            joymeCommentRemoveApi = prop.getProperty("joyme_comment_remove_api");

            searchSaveUrl = prop.getProperty("search_save_url");
            searchDeleteUrl = prop.getProperty("search_delete_url");

            wikiHost = prop.getProperty("wiki_host");

            wikiMobileHost = prop.getProperty("wiki_mobile_host");

            isSavePv = Boolean.parseBoolean(prop.getProperty("is_save_pv"));

            isWikiRanking = Boolean.parseBoolean(prop.getProperty("is_wiki_ranking"));

            mongoDbMaxConns = Integer.parseInt(prop.getProperty("mongo_db_max_conns"));
            mongoDBPort = Integer.parseInt(prop.getProperty("mongo_db_port"));
            mongoDbName = prop.getProperty("mongo_db_name");
            mongoDbIp = prop.getProperty("mongo_db_ip");
            defaultChannel = prop.getProperty("defautl_channel");


            subDomain = prop.getProperty("sub_domain");

            memCachedManager = new MemCachedManager(new MemCachedConfig(prop));

            wikiLoginPage = prop.getProperty("wiki_login_jsp");
            wikiMainPage = prop.getProperty("wiki_main_jsp");
            wikiLoginAction = prop.getProperty("tools_login_url") + "?reurl=" + prop.getProperty("wiki_main_jsp");
            wikiLogoutAction = prop.getProperty("tools_loginout_url") + "?reurl=" + prop.getProperty("wiki_login_jsp");


            if (redisManager == null) {
                synchronized (PropertiesContainer.class) {
                    if (redisManager == null) {
                        String host = prop.getProperty("redis_host");
                        int maxActivity = Integer.parseInt(prop.getProperty("redis_maxactivity"));
                        int maxWait = Integer.parseInt(prop.getProperty("redis_maxwait"));
                        int maxIdel = Integer.parseInt(prop.getProperty("redis_maxidel"));
                        String passowrd = prop.getProperty("redis_password");
                        RedisConfig redisConfig = new RedisConfig(host, maxActivity, maxWait, maxIdel,passowrd);
                        redisManager = new RedisManager(redisConfig);
                    }
                }
            }

            String wikiKeyString = prop.getProperty("support.wikijoymecom.wikikey");
            if (!StringUtil.isEmpty(wikiKeyString)) {
                String[] keyArray = wikiKeyString.split(" ");
                for (String s : keyArray) {
                    supportWikiKeyList.add(s);
                }
            }
        } finally {
            if (is != null)
                is.close();
        }
    }

    public void reloadProperties() {
        PropertiesContainer temp = new PropertiesContainer();
        instance = temp;
    }


    public String getDomain(String key, String wikiType) throws Exception {
        JoymeWiki joymeWiki = getJoymeWiki(key, wikiType);
        if (joymeWiki == null) {
            return null;
        }
        return joymeWiki.getJoymeWikiDomain();
    }

    public boolean supportSubDomain(String key, String wikiType) throws Exception {
        JoymeWiki joymeWiki = getJoymeWiki(key, wikiType);
        return joymeWiki.getSupportSubDomain();
    }

    public String getSubDomain() {
        return subDomain;
    }

    public String getPath(String key, String wikiType) throws Exception {
        JoymeWiki joymeWiki = getJoymeWiki(key, wikiType);
        return joymeWiki.getJoymeWikiPath();
    }


    public JoymeWiki getJoymeWiki(String key, String wikiType) throws Exception {
        JoymeWiki joymeWiki = wikiMemcachedService.getJoymeWiki(key, wikiType);
        if (joymeWiki == null) {
            SystemService ss = new SystemService();
            joymeWiki = ss.loadOneJoymeWiki(key, wikiType);
            if (joymeWiki != null) {
//                joymeWikiMap.put(joymeWiki.getJoymeWikiKey() + wikiType, joymeWiki);
                putJoymeWiki(joymeWiki, wikiType);
//                joymeWikiKetSet.add(joymeWiki.getJoymeWikiKey());
            } else {
                System.out.println("joyme wiki is null.key:" + key);
            }

        }
        return joymeWiki;
    }

    public String getAndroidPath(String key, String wikiType) throws Exception {
        JoymeWiki joymeWiki = getJoymeWiki(key, wikiType);
        return joymeWiki.getJoymeAndroidPath();
    }

    public String getIOSPath(String key, String wikiType) throws Exception {
        JoymeWiki joymeWiki = getJoymeWiki(key, wikiType);
        return joymeWiki.getJoymeIosPath();
    }

    public JoymeWiki getJoymeWikiByKeyAndWikiType(String joymeWiki, String wikiType) {
        return wikiMemcachedService.getJoymeWiki(joymeWiki, wikiType);
    }

    public Set<String> getJoymeWikiKetSet() {
        Set<String> set = wikiMemcachedService.getJoymeWikiSet();
        if (set == null) {
            try {
                List<JoymeWiki> joymeWikiList = joymeWikiService.queryJoymeWiki(null, "wiki");
                set = new HashSet<String>();

                //添加默认的default
                set.add(DEFAULE_WIKISET_WIKI);

                for (JoymeWiki joymeWiki : joymeWikiList) {
                    set.add(joymeWiki.getJoymeWikiKey());
                }
                wikiMemcachedService.putJoymeWikiSet(set);
            } catch (JoymeDBException e) {
                e.printStackTrace();
            } catch (JoymeServiceException e) {
                e.printStackTrace();
            }
        }
        return set;
    }

    public Map<String, List<String>> getWikKeySetByFisrstLetter() {
        Set<String> set = getJoymeWikiKetSet();

        Map<String, List<String>> firstLetterMap = new TreeMap<String, List<String>>();
        for (String s : set) {
            String firstLetter = s.substring(0, 1);
            if (!firstLetterMap.containsKey(firstLetter)) {
                firstLetterMap.put(firstLetter, new ArrayList<String>());
            }
            firstLetterMap.get(firstLetter).add(s);
        }
        return firstLetterMap;
    }

    public void addJoymeWikiKetSet(String wikikey) {
        Set<String> set = getJoymeWikiKetSet();
        set.add(wikikey);
        wikiMemcachedService.putJoymeWikiSet(set);
    }

    public void putJoymeWiki(JoymeWiki joymeWiki, String wikiType) {
        wikiMemcachedService.putJoymeWiki(joymeWiki, wikiType);
    }

    public String getCacheFolder() {
        return cacheFolder;
    }

    public int getMachineId() {
        return machineId;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getWikiHost() {
        return wikiHost;
    }

    public String getTemplateSourceUrl() {
        return templateSourceUrl;
    }

    public String getJoymePcCommentJsSrc() {
        return joymePcCommentJsSrc;
    }

    public String getJoymeMobileCommentJsSrc() {
        return joymeMobileCommentJsSrc;
    }

    public String getJoymeApiHotList() {
        return joymeApiHotList;
    }

    public String getJoymeApiCommentList() {
        return joymeApiCommentList;
    }

    public String getJoymeCommentApiGetByContentId() {
        return joymeCommentApiGetByContentId;
    }

    public String getJoymeCommentAgreeApi() {
        return joymeCommentAgreeApi;
    }

    public String getJoymeCommentPostApi() {
        return joymeCommentPostApi;
    }

    public String getJoymeCommentListUrl() {
        return joymeCommentListUrl;
    }

    public String getJoymeMobileCommentListUrl() {
        return joymeMobileCommentListUrl;
    }

    public void setJoymeMobileCommentListUrl(String joymeMobileCommentListUrl) {
        this.joymeMobileCommentListUrl = joymeMobileCommentListUrl;
    }

//    public String getJoymeCommentGetcidApi() {
//        return joymeCommentGetcidApi;
//    }

    public String getJoymeCommentQueryApi2() {
        return joymeCommentQueryApi2;
    }

    public String getJoymeCommentRemoveApi() {
        return joymeCommentRemoveApi;
    }

    public boolean isSavePv() {
        return isSavePv;
    }

    public int getMongoDbMaxConns() {
        return mongoDbMaxConns;
    }

    public int getMongoDBPort() {
        return mongoDBPort;
    }

    public String getMongoDbName() {
        return mongoDbName;
    }

    public String getMongoDbIp() {
        return mongoDbIp;
    }

    public String getCacheCSSZIPFolder() {
        return cacheCSSZIPFolder;
    }


    public String getJoymeCommentListUrl2() {
        return joymeCommentListUrl2;
    }

    public String getDefaultChannel() {
        return defaultChannel;
    }

    public MemCachedManager getMemCachedManager() {
        return memCachedManager;
    }

    public String getWikiLoginPage() {
        return wikiLoginPage;
    }

    public String getWikiMainPage() {
        return wikiMainPage;
    }

    public String getWikiLoginAction() {
        return wikiLoginAction;
    }

    public String getWikiLogoutAction() {
        return wikiLogoutAction;
    }

    public String getWikiMobileHost() {
        return wikiMobileHost;
    }

    public RedisManager getRedisManager() {

        return redisManager;
    }

    public List<String> getSupportWikiKeyList() {
        return supportWikiKeyList;
    }

    public Service getService() {
        return service;
    }

    public NamingService getNamingService() {
        return NamingService.getInstance(getRedisManager());
    }



}
