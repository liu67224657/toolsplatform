package com.enjoyf.wiki.facade;

import com.enjoyf.framework.jdbc.bean.PageQueryBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.wiki.bean.JoymeWikiSitemap;
import com.enjoyf.wiki.bean.WikiPage;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.service.JoymeWikiSitemapService;
import com.enjoyf.wiki.service.WikiPageService;
import com.enjoyf.wiki.tools.FileUtil;
import com.enjoyf.wiki.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: ericliu
 * Date: 15-07-18
 * Time: 下午4:38
 * joymeWikiSitemapService
 */
public class WikiSiteMapFacade {
    private static JoymeWikiSitemapService joymeWikiSitemapService = new JoymeWikiSitemapService();

    private static WikiPageService pageService = new WikiPageService();

    private static JoymeWikiSitemapService sitemapService = new JoymeWikiSitemapService();

    private static final int PAGE_STATUS = 1;
    private static final int PAGE_COUNT = 200;

    @Deprecated
    public void createXml(String key) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");
        List<JoymeWikiSitemap> list = new ArrayList<JoymeWikiSitemap>();
        try {
            list = joymeWikiSitemapService.queryJoymeWikiSitemapbyWikiType(null, key);

            for (int i = 0; i < list.size(); i++) {
                Element urlElement = root.addElement("url"); //添加root的子节点
                JoymeWikiSitemap joymeWikiSitemap = list.get(i);
                Element locElement = urlElement.addElement("loc");
                locElement.addText(joymeWikiSitemap.getLoc());
                Element priorityElement = urlElement.addElement("priority");
                priorityElement.addText(joymeWikiSitemap.getPriority());
            }

            String path = getPath(key);
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            String xmlDir = getPath(key);
            // 输出全部原始数据，并用它生成新的我们需要的XML文件
            buildXml(xmlDir, document);
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void createWikiSitemapXml() {
        PropertiesContainer.getInstance().reloadProperties();

        long now = System.currentTimeMillis();

        Document document = DocumentHelper.createDocument();
        Element sitemapindexRoot = document.addElement("sitemapindex");
        Element wiki1Element = sitemapindexRoot.addElement("sitemap"); //添加root的子节点
        Element wiki1locElement = wiki1Element.addElement("loc");
        wiki1locElement.addText(PropertiesContainer.getInstance().getWikiHost() + "/sitemap1/sitemap.xml");
        Element wiki1lastmodElement = wiki1Element.addElement("lastmod");
        wiki1lastmodElement.addText(DateUtil.convert2String(now, DateUtil.DATE_FORMAT));

        //mwiki
        Document mdocument = DocumentHelper.createDocument();
        Element msitemapindexRoot = mdocument.addElement("sitemapindex");
        Element mwiki1Element = msitemapindexRoot.addElement("sitemap"); //添加root的子节点
        Element mwiki1locElement = mwiki1Element.addElement("loc");
        mwiki1locElement.addText(PropertiesContainer.getInstance().getWikiMobileHost() + "/sitemap1/sitemap.xml");
        Element mwiki1lastmodElement = mwiki1Element.addElement("lastmod");
        mwiki1lastmodElement.addText(DateUtil.convert2String(now, DateUtil.DATE_FORMAT));


        //todo 建议从redis取值 俩次循环1.数字站的2.ugc的
        Set<String> sitemapSet = sitemapService.querySitemap(0, -1);
        for (String wikiKey : sitemapSet) {
            Element wikiElement = sitemapindexRoot.addElement("sitemap"); //添加root的子节点
            Element locElement = wikiElement.addElement("loc");
            locElement.addText(PropertiesContainer.getInstance().getWikiHost() + "/" + wikiKey + "/sitemap.xml");

            Element lastmodElement = wikiElement.addElement("lastmod");
            lastmodElement.addText(DateUtil.convert2String(now, DateUtil.DATE_FORMAT));

            //mwiki
            Element mwikiElement = msitemapindexRoot.addElement("sitemap"); //添加root的子节点
            Element mlocElement = mwikiElement.addElement("loc");
            mlocElement.addText(PropertiesContainer.getInstance().getWikiMobileHost() + "/" + wikiKey + "/sitemap.xml");

            Element mlastmodElement = mwikiElement.addElement("lastmod");
            mlastmodElement.addText(DateUtil.convert2String(now, DateUtil.DATE_FORMAT));
        }

        Set<String> ugcSitemapSet = sitemapService.queryUGCSitemap(0, -1);
        for (String wikiKey : ugcSitemapSet) {
            Element wikiElement = sitemapindexRoot.addElement("sitemap"); //添加root的子节点
            Element locElement = wikiElement.addElement("loc");
            locElement.addText(PropertiesContainer.getInstance().getWikiHost() + "/" + wikiKey + "/sitemap.xml");

            Element lastmodElement = wikiElement.addElement("lastmod");
            lastmodElement.addText(DateUtil.convert2String(now, DateUtil.DATE_FORMAT));

            Element mwikiElement = msitemapindexRoot.addElement("sitemap"); //添加root的子节点
            Element mlocElement = mwikiElement.addElement("loc");
            mlocElement.addText(PropertiesContainer.getInstance().getWikiMobileHost() + "/" + wikiKey + "/sitemap.xml");

            Element mlastmodElement = mwikiElement.addElement("lastmod");
            mlastmodElement.addText(DateUtil.convert2String(now, DateUtil.DATE_FORMAT));
        }

        String path = getPath("wiki");
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            buildXml(path, document);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String mpath = getMWikiSitemapPath("wiki");
        File mfile = new File(mpath);
        if (!mfile.exists()) {
            mfile.mkdirs();
        }
        try {
            buildXml(mpath, mdocument);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void createWikiSitemap1Xml() {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");
        Element wikiElement = root.addElement("url"); //添加root的子节点
        Element locElement = wikiElement.addElement("loc");
        locElement.addText(PropertiesContainer.getInstance().getWikiHost());
        Element priorityElement = wikiElement.addElement("priority");
        priorityElement.addText("1.0");

        String path = getPath("sitemap1");
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            buildXml(path, document);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Document mdocument = DocumentHelper.createDocument();

        Element mroot = mdocument.addElement("urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");
        Element mwikiElement = mroot.addElement("url"); //添加root的子节点
        Element mlocElement = mwikiElement.addElement("loc");
        mlocElement.addText(PropertiesContainer.getInstance().getWikiHost());
        Element mpriorityElement = mwikiElement.addElement("priority");
        mpriorityElement.addText("1.0");

        String mpath = getPath("sitemap1");
        File mfile = new File(mpath);
        if (!mfile.exists()) {
            mfile.mkdirs();
        }
        try {
            buildXml(mpath, mdocument);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Document generatorSitemapByWikiKey(String wikiKey) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");
        Element wikiElement = root.addElement("url"); //添加root的子节点
        Element locElement = wikiElement.addElement("loc");
        locElement.addText(PropertiesContainer.getInstance().getWikiHost() + "/" + wikiKey + "/");
        Element priorityElement = wikiElement.addElement("priority");
        priorityElement.addText("0.8");

        Document mdocument = DocumentHelper.createDocument();
        Element mroot = mdocument.addElement("urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");
        Element mwikiElement = mroot.addElement("url"); //添加root的子节点
        Element mlocElement = mwikiElement.addElement("loc");
        mlocElement.addText(PropertiesContainer.getInstance().getWikiMobileHost() + "/" + wikiKey + "/");
        Element mpriorityElement = mwikiElement.addElement("priority");
        mpriorityElement.addText("0.8");

        int pageNum = 1;
        try {
            int total = pageService.countWikiPage(null, wikiKey, PAGE_STATUS);

            int totalPage = (total - 1) / PAGE_COUNT + 1;

            do {
                try {
                    PageQueryBean pageQueryBean = pageService.queryWikiPage(null, wikiKey, PAGE_STATUS, pageNum, PAGE_COUNT, 0);

                    for (Object obj : pageQueryBean.getResultList()) {
                        try {
                            Element pageElement = root.addElement("url"); //添加root的子节点
                            WikiPage wikiPage = (WikiPage) obj;
                            Element pageLocElement = pageElement.addElement("loc");
                            String url = PropertiesContainer.getInstance().getWikiHost();
                            if (!wikiKey.equals("wiki")) {
                                url += "/" + wikiPage.getWikiKey();
                            }
                            url += "/" + wikiPage.getPageId() + ".shtml";
                            pageLocElement.addText(url);
                            Element pagePriorityElement = pageElement.addElement("priority");
                            pagePriorityElement.addText("0.6");

                            //mwiki
                            Element mpageElement = mroot.addElement("url"); //添加root的子节点
                            Element mpageLocElement = mpageElement.addElement("loc");
                            String murl = PropertiesContainer.getInstance().getWikiMobileHost();
                            if (!wikiKey.equals("mwiki")) {
                                murl += "/" + wikiPage.getWikiKey();
                            }
                            murl += "/" + wikiPage.getPageId() + ".shtml";
                            mpageLocElement.addText(murl);
                            Element mpagePriorityElement = mpageElement.addElement("priority");
                            mpagePriorityElement.addText("0.6");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JoymeDBException e) {
                    e.printStackTrace();
                } catch (JoymeServiceException e) {
                    e.printStackTrace();
                }

                pageNum++;
            } while (pageNum < totalPage);
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }

        String path = getPath(wikiKey);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            buildXml(path, document);
        } catch (IOException e) {
            e.printStackTrace();
        }


        String mpath = getMWikiSitemapPath(wikiKey);
        File mfile = new File(mpath);
        if (!mfile.exists()) {
            mfile.mkdirs();
        }
        // 输出全部原始数据，并用它生成新的我们需要的XML文件
        try {
            buildXml(mpath, mdocument);
        } catch (IOException e) {
            e.printStackTrace();
        }



        return document;
    }

    private void buildXml(String xmlDir, Document document) throws IOException {

        //输出全部原始数据，在编译器中显示
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");//根据需要设置编码

        File xmlFile = new File(xmlDir + "/sitemap.xml");
        XMLWriter writer2 = null;
        try {
            writer2 = new XMLWriter(new FileWriter(xmlFile), format);
            writer2.write(document); //输出到文件
        } finally {
            if (writer2 != null) {
                writer2.close();
            }
        }

        File txtFile = new File(xmlDir + "/sitemap.txt");
        XMLWriter writer3 = null;
        try {
            writer3 = new XMLWriter(new FileWriter(txtFile), format);
            writer3.write(document); //输出到文件
        } finally {
            if (writer3 != null) {
                writer3.close();
            }
        }
    }


    public String getPath(String key) {
        return PropertiesContainer.getInstance().getCacheFolder() + "/sitemap/" + key;
    }

    public String getMWikiSitemapPath(String key) {
        return PropertiesContainer.getInstance().getCacheFolder() + "/sitemap_m/" + key;
    }

    public void displayFileXML(HttpServletResponse response, File file, boolean checkFileLength) throws IOException, FileNotFoundException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/xml");
        FileInputStream is = new FileInputStream(file);

        if (checkFileLength && file.length() < 300) {
            String id = file.getName().substring(0, file.getName().lastIndexOf("."));
            WikiPage bean = new WikiPage();
            try {
                bean.setPageId(Long.valueOf(id));
                bean.setPageStatus(0);
                pageService.updateWikiPage(null, bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        OutputStream os = null;
        try {
            os = response.getOutputStream();
            byte[] buff = new byte[8192];
            int len = -1;
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
            }

        } finally {
            if (is != null) {
                is.close();
                is = null;
            }

            if (os != null) {
                os.flush();
                os.close();
                os = null;
            }
        }

    }
}
