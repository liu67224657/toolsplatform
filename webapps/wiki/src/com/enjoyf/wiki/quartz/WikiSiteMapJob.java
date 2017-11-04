package com.enjoyf.wiki.quartz;

import com.enjoyf.wiki.bean.WikiPage;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.facade.WikiSiteMapFacade;
import com.enjoyf.wiki.service.JoymeWikiSitemapService;
import com.enjoyf.wiki.service.WikiPageService;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Set;

/**
 * Created by zhimingli on 2015/3/17.
 */
public class WikiSiteMapJob extends AbstractQuartzCronTrigger implements Job {

    private static WikiPageService pageService = new WikiPageService();

    private static WikiSiteMapFacade siteMapFacade = new WikiSiteMapFacade();

    private static JoymeWikiSitemapService sitemapService = new JoymeWikiSitemapService();

    public WikiSiteMapJob() throws SchedulerException {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(System.currentTimeMillis() + " WikiSiteMapJob start");

        PropertiesContainer.getInstance().reloadProperties();
        Set<String> supportKeyList = sitemapService.querySitemap(0,-1);
        for(String wikiKey:supportKeyList){
            siteMapFacade.generatorSitemapByWikiKey(wikiKey);
        }
        siteMapFacade.createWikiSitemap1Xml();
        siteMapFacade.createWikiSitemapXml();

        //todo 不用的逻辑 兼容迁移前的
        Set<String> keySet = PropertiesContainer.getInstance().getJoymeWikiKetSet();
        for (String wikiKey : keySet) {
            if(supportKeyList.contains(wikiKey)){
                 continue;
            }

            if (wikiKey.equals("default")) {
                continue;
            }
            if (wikiKey.equals("wiki")) {
                continue;
            }

            try {
                boolean isSupportDomain = PropertiesContainer.getInstance().supportSubDomain(wikiKey, "wiki");
                List<WikiPage> list = pageService.getWikiPageListByWikiKey(null, wikiKey);
                createXml(list, wikiKey, isSupportDomain);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(System.currentTimeMillis() + " WikiSiteMapJob end");
    }

    @Deprecated
    public void createXml(List<WikiPage> list, String key, boolean isSupportDomain) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");
        // List<JoymeWikiSitemap> list = new ArrayList<JoymeWikiSitemap>();
        try {
            // list = joymeWikiSitemapService.queryJoymeWikiSitemapbyWikiType(null, key);
            Element rootElement = root.addElement("url"); //添加root的子节点
            Element rootLocElement = rootElement.addElement("loc");
            if (isSupportDomain) {
                rootLocElement.addText("http://" + key + ".joyme.com/index.shtml");
            } else {
                rootLocElement.addText("http://www.joyme.com/wiki/" + key + "/index.shtml");
            }
            Element rootpriorityElement = rootElement.addElement("priority");
            rootpriorityElement.addText("1.0");


            for (int i = 0; i < list.size(); i++) {
                Element urlElement = root.addElement("url"); //添加root的子节点
                WikiPage wikiPage = list.get(i);
                Element locElement = urlElement.addElement("loc");
                if (isSupportDomain) {
                    locElement.addText("http://" + key + ".joyme.com/" + wikiPage.getPageId() + ".shtml");
                } else {
                    locElement.addText("http://www.joyme.com/wiki/" + key + "/" + wikiPage.getPageId() + ".shtml");
                }
                Element priorityElement = urlElement.addElement("priority");
                priorityElement.addText("0.9");
            }


            //输出全部原始数据，在编译器中显示
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");//根据需要设置编码

            String path = getPath(key);
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            String xmlPath = getPath(key);
            // 输出全部原始数据，并用它生成新的我们需要的XML文件
            File xmlFile = new File(xmlPath + "/sitemap.xml");
            XMLWriter writer2 = new XMLWriter(new FileWriter(xmlFile), format);
            writer2.write(document); //输出到文件
            writer2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws SchedulerException {
        //每分钟执行一次
//        addTriggerJob("*/30 * * * * ?", WikiSiteMapJob.class);

        //每天5点执行一次
        addTriggerJob(" 0 0 5 ? * *", WikiSiteMapJob.class);
    }

    public String getPath(String key) {
        return PropertiesContainer.getInstance().getCacheFolder() + "/sitemap/" + key;
    }
}
