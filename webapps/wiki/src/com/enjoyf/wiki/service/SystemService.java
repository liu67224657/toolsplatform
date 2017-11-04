package com.enjoyf.wiki.service;

import com.enjoyf.framework.jdbc.BaseJDBCDAO;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.util.SystemUtil;
import com.enjoyf.wiki.bean.JoymeChannel;
import com.enjoyf.wiki.bean.JoymeTemplate;
import com.enjoyf.wiki.bean.JoymeWiki;
import com.enjoyf.wiki.container.ChannelContainer;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.container.TemplateContainer;
import com.enjoyf.wiki.factory.IHtmlParseFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author liangtang
 */
public class SystemService {
    private static SystemUtil util = new SystemUtil();

    private final static String START_FLAG = "wiki_";
    private final static String END_FLAG = ".properties";


    JoymeChannelService joymeChannelService = new JoymeChannelService();
    JoymeWikiService joymeWikiService = new JoymeWikiService();
    JoymeTemplateService joymeTemplateService = new JoymeTemplateService();

    private static BaseJDBCDAO dao = new BaseJDBCDAOImpl();

    private WikiMemcachedService wikiMemcachedService = new WikiMemcachedService();


    /**
     * 加载全部
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
//    public void loadProperties() throws FileNotFoundException, IOException {
//        // String metaInfPath = util.getMetaInfFolderPath();
//        String path = PropertiesContainer.cacheFolder;
//        File file = new File(path);
//        File[] files = file.listFiles();
//        if (files != null && files.length > 0) {
//            for (int i = 0; i < files.length; i++) {
//                String name = files[i].getName();
//                if (name.startsWith(START_FLAG) && name.endsWith(END_FLAG)) {
//                    Properties prop = new Properties();
//                    int position = name.indexOf(END_FLAG);
//                    String key = name.substring(START_FLAG.length(), position);
//                    InputStream is = new FileInputStream(files[i]);
//                    try {
//                        prop.load(is);
//                        PropertiesContainer.joymeWikiMap.put(key, prop);
//                    } finally {
//                        if (is != null)
//                            is.close();
//                    }
//                }
//            }
//            System.out.println(PropertiesContainer.joymeWikiMap);
//        }
//    }
    public void loadJoymeWIki() throws Exception {
        PropertiesContainer.getInstance().getJoymeWikiKetSet().clear();
        //初始化先清一下缓存
        PropertiesContainer.getInstance().addJoymeWikiKetSet(PropertiesContainer.DEFAULE_WIKISET_WIKI);

        List<JoymeWiki> joymeWikiList = joymeWikiService.queryJoymeWiki(null);

        PropertiesContainer.getInstance().getJoymeWikiKetSet().add(PropertiesContainer.DEFAULE_WIKISET_WIKI);
        for (JoymeWiki joymeWiki : joymeWikiList) {
            PropertiesContainer.getInstance().putJoymeWiki(joymeWiki, joymeWiki.getContextPath());
            PropertiesContainer.getInstance().getJoymeWikiKetSet().add(joymeWiki.getJoymeWikiKey());
        }
    }


//    public void loadCacheProperties() throws IOException {
//        String metaInfPath = util.getMetaInfFolderPath();
//        String path = metaInfPath + "/cache.properties";
//        InputStream is = new FileInputStream(new File(path));
//        try {
//            Properties prop = new Properties();
//            prop.load(is);
//            PropertiesContainer.cacheFolder = prop.getProperty("cache_folder");
//            PropertiesContainer.cacheCSSZIPFolder = prop.getProperty("cache_csszip_folder");
//            PropertiesContainer.isDownload = Boolean.parseBoolean(prop.getProperty("is_download"));
//            PropertiesContainer.machineId = Integer.parseInt(prop.getProperty("machine_id"));
//            PropertiesContainer.sourceUrl = prop.getProperty("source_url");
//            PropertiesContainer.templateSourceUrl = prop.getProperty("template_source_url");
//
//
//            PropertiesContainer.joymeCommentJsSrc= prop.getProperty("joyme_comment_js");
//            PropertiesContainer.joymeApiHotList = prop.getProperty("joyme_comment_hotlist_api");
//            PropertiesContainer.joymeApiCommentList = prop.getProperty("joyme_comment_commentlist_api");
//            PropertiesContainer.joymeCommentApiGetByContentId = prop.getProperty("joyme_comment_getcontentid_api");
//            PropertiesContainer.joymeCommentAgreeApi = prop.getProperty("joyme_comment_agree_api");
//            PropertiesContainer.joymeCommentPostApi = prop.getProperty("joyme_comment_post_api");
//            PropertiesContainer.joymeCommentListUrl = prop.getProperty("joyme_comment_list_url");
//            PropertiesContainer.joymeCommentGetcidApi = prop.getProperty("joyme_comment_getcid_api");
//           PropertiesContainer.joymeCommentRemoveApi = prop.getProperty("joyme_comment_remove_api");
//
//
//
//            PropertiesContainer.wikiHost = prop.getProperty("wiki_host");
//
//            PropertiesContainer.isSavePv = Boolean.parseBoolean(prop.getProperty("is_save_pv"));
//
//            PropertiesContainer.mongoDbMaxConns = Integer.parseInt(prop.getProperty("mongo_db_max_conns"));
//            PropertiesContainer.mongoDBPort = Integer.parseInt(prop.getProperty("mongo_db_port"));
//            PropertiesContainer.mongoDbName = prop.getProperty("mongo_db_name");
//            PropertiesContainer.mongoDbIp = prop.getProperty("mongo_db_ip");
//            PropertiesContainer.defaultChannel = prop.getProperty("defautl_channel");
//
//        } finally {
//            if (is != null)
//                is.close();
//        }
//    }
//

    /**
     * 加载一个
     *
     * @param key
     * @throws Exception
     */
    public JoymeWiki loadOneJoymeWiki(String key, String wikiType) throws Exception {
        List<JoymeWiki> joymeWikiList = joymeWikiService.queryJoymeWiki(null, key, wikiType);

        if (joymeWikiList == null || joymeWikiList.size() == 0) {
            return null;
        }

        return joymeWikiList.get(0);
    }


    //    /**
//     * 加载一个
//     *
//     * @param key
//     * @throws Exception
//     */
//    public Properties loadOneProperties(String key) throws Exception {
//        String path = PropertiesContainer.cacheFolder + "/wiki_" + key
//                + ".properties";
//        File file = new File(path);
//        if (file.exists()) {
//            Properties prop = new Properties();
//            InputStream is = new FileInputStream(new File(path));
//            try {
//                prop.load(is);
//                PropertiesContainer.joymeWikiMap.put(key, prop);
//            } finally {
//                if (is != null)
//                    is.close();
//            }
//            return prop;
//        } else {
//            throw new Exception("properties " + key + " not found");
//        }
//    }
    public void loadChannelConfigure() throws Exception {
        List<JoymeChannel> channelList = joymeChannelService.queryJoymeChannel(null);

        for (JoymeChannel joymeChannel : channelList) {
            IHtmlParseFactory factory = (IHtmlParseFactory) Class.forName(joymeChannel.getHtmlFactory()).newInstance();
            ChannelContainer.channelHtmlFacotryMap.put(joymeChannel.getChannelName(), factory);
        }
    }

    public void loadTemplate() throws Exception {
        List<JoymeTemplate> joymeTemplateList = joymeTemplateService.queryJoymeTemplate(null);
        for (JoymeTemplate joymeTemplate : joymeTemplateList) {
            TemplateContainer.putTemplate(joymeTemplate, joymeTemplate.getContextPath());
        }
    }


}
