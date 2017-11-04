package com.enjoyf.wiki.controller;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.StringUtil;
import com.enjoyf.wiki.bean.JoymeWikiSitemap;
import com.enjoyf.wiki.bean.WikiPage;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.quartz.WikiSiteMapJob;
import com.enjoyf.wiki.service.JoymeWikiSitemapService;
import com.enjoyf.wiki.service.WikiPageService;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-3-3
 * Time: 下午12:37
 * SiteMapController
 */
@Controller
public class WikiAcSiteMapController {
    private JoymeWikiSitemapService joymeWikiSitemapService = new JoymeWikiSitemapService();


    private static WikiPageService pageService = new WikiPageService();


    @RequestMapping(value = "/wiki/ac/st/list.do")
    public ModelAndView list() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("wikiMap", PropertiesContainer.getInstance().getJoymeWikiKetSet());
        return new ModelAndView("/wiki/ac/sitemap/list", map);
    }

    @ResponseBody
    @RequestMapping(value = "/wiki/ac/st/add.do")
    public String add(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("wikiMap", PropertiesContainer.getInstance().getJoymeWikiKetSet());
        String wiki = request.getParameter("wiki");
        String addStr = request.getParameter("addStr");
        String delStr = request.getParameter("delStr");
        try {

            //新增 213___1--13___1--312___1
            if (!StringUtil.isEmpty(addStr)) {
                String addStrArray[] = addStr.split("--");
                for (int i = 0; i < addStrArray.length; i++) {
                    String siteMapArray[] = addStrArray[i].split("___");
                    JoymeWikiSitemap sitemap = new JoymeWikiSitemap();
                    sitemap.setWikiKey(wiki);
                    sitemap.setLoc(siteMapArray[0]);
                    sitemap.setPriority(siteMapArray[1]);
                    joymeWikiSitemapService.insertJoymeWikiSitemap(null, sitemap);
                }
            }

            //删除
            if (!StringUtil.isEmpty(delStr)) {
                String delStrArray[] = delStr.split("___");
                for (int i = 0; i < delStrArray.length; i++) {
                    joymeWikiSitemapService.deleteJoymeWikiSitemap(null, Long.parseLong(delStrArray[i]));
                }
            }

        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }
        return "success";
        // return new ModelAndView("/wiki/ac/sitemap/sitemaplist");
    }

    @RequestMapping(value = "/wiki/ac/st/edit.do")
    public ModelAndView edit(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("wikiMap", PropertiesContainer.getInstance().getJoymeWikiKetSet());
        String wiki = request.getParameter("wiki");
        List<JoymeWikiSitemap> list = null;
        try {
            list = joymeWikiSitemapService.queryJoymeWikiSitemapbyWikiType(null, wiki);
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }
        request.setAttribute("list", list);
        request.setAttribute("wiki", wiki);
        return new ModelAndView("/wiki/ac/sitemap/add", map);
    }


    @RequestMapping(value = "/wiki/ac/st/sitemaplist.do")
    public ModelAndView sitemaplist(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("wikiMap", PropertiesContainer.getInstance().getJoymeWikiKetSet());
        String wiki = request.getParameter("wiki");
        List<JoymeWikiSitemap> list = null;
        try {
            list = joymeWikiSitemapService.queryJoymeWikiSitemapbyWikiType(null, wiki);
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }
        request.setAttribute("list", list);
        map.put("wiki", wiki);
        return new ModelAndView("/wiki/ac/sitemap/sitemaplist", map);
    }

    @RequestMapping(value = "/wiki/ac/st/uploadxml.do")
    public ModelAndView uploadxml(HttpServletRequest request,
                                  @RequestParam(value = "xmlFile", required = false) MultipartFile xmlFile,
                                  @RequestParam(value = "wiki") String wiki) {
        List<JoymeWikiSitemap> joymeWikiSitemapList = new ArrayList<JoymeWikiSitemap>();
        String path = getPath(wiki);


        try {
            //文件保存起来，返回file对象
            File file = this.ReadXml(path, wiki, xmlFile);

            if (file.exists()) {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document document = db.parse(file);
                NodeList list = document.getElementsByTagName("url");
                for (int i = 0; i < list.getLength(); i++) {
                    Element element = (Element) list.item(i);
                    String loc = element.getElementsByTagName("loc").item(0).getFirstChild().getNodeValue();
                    String priority = element.getElementsByTagName("priority").item(0).getFirstChild().getNodeValue();
                    JoymeWikiSitemap joymeWikiSitemap = new JoymeWikiSitemap();
                    joymeWikiSitemap.setLoc(loc);
                    joymeWikiSitemap.setPriority(priority);
                    joymeWikiSitemap.setWikiKey(wiki);
                    joymeWikiSitemapService.insertJoymeWikiSitemap(null, joymeWikiSitemap);
                }

                //文件删除
                file.delete();
                file.getParentFile().delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/wiki/ac/st/edit.do?wiki=" + wiki);
    }

    //手动生成sitemap
    @RequestMapping(value = "/wiki/ac/st/start.do")
    @ResponseBody
    public String start(HttpServletRequest request, @RequestParam(value = "wiki", required = false) String wiki) {
        try {
            WikiSiteMapJob job = new WikiSiteMapJob();
            Set<String> keySet = PropertiesContainer.getInstance().getJoymeWikiKetSet();
            for (String wikiKey : keySet) {
                try {
                    if (wikiKey.equals("default")) {
                        continue;
                    }

                    if (!StringUtil.isEmpty(wiki) && !wiki.equals(wikiKey)) {
                        continue;
                    }

                    boolean isSupportDomain = PropertiesContainer.getInstance().supportSubDomain(wikiKey, "wiki");
                    List<WikiPage> list = pageService.getWikiPageListByWikiKey(null, wikiKey);
                    //File fileXml = new File(getPath(wikiKey) + "/sitemap.xml");

                    job.createXml(list, wikiKey, isSupportDomain);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "success";
    }

    private File ReadXml(String path, String wiki, MultipartFile xmlFile) throws Exception {
        //如果路径不存在
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        if (xmlFile != null && xmlFile.getBytes().length > 0) {
            InputStream inputStream = xmlFile.getInputStream();
            FileOutputStream fs = new FileOutputStream(path + "/" + wiki + ".xml");
            byte[] buffer = new byte[1024 * 1024];
            int bytesum = 0;
            int byteread = 0;
            while ((byteread = inputStream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
                fs.flush();
            }
            fs.close();
            inputStream.close();
        }

        File retutnFile = new File(filePath + "/" + wiki + ".xml");
        return retutnFile;
    }


    private String getPath(String key) {
        return PropertiesContainer.getInstance().getCacheFolder() + "/sitemap/uploadXmlfile/" + key;
    }


    //////////////////////////
    @RequestMapping(value = "/wiki/ac/st/page.do")
    public ModelAndView page(HttpServletRequest request) {
        Map map = new HashMap();

        Set<String> wikiSet = joymeWikiSitemapService.querySitemap(0, -1);
        map.put("wikiSet", wikiSet);

        Set<String> ugcWikiSet = joymeWikiSitemapService.queryUGCSitemap(0, -1);
        map.put("ugcWikiSet", ugcWikiSet);

        return new ModelAndView("/wiki/ac/sitemap/page", map);
    }

    @RequestMapping(value = "/wiki/ac/st/addwiki.do")
    public ModelAndView addWiki(HttpServletRequest request,
                                @RequestParam(value = "wiki") String wiki) {
        joymeWikiSitemapService.addSitemap(wiki);
        return new ModelAndView("redirect:/wiki/ac/st/page.do");
    }

    @RequestMapping(value = "/wiki/ac/st/addugc.do")
    public ModelAndView addUgcWiki(HttpServletRequest request,
                                   @RequestParam(value = "wiki") String wiki) {
        joymeWikiSitemapService.addUGCSitemap(wiki);
        return new ModelAndView("redirect:/wiki/ac/st/page.do");
    }

    @RequestMapping(value = "/wiki/ac/st/delwiki.do")
    public ModelAndView delWiki(HttpServletRequest request,
                                @RequestParam(value = "wiki") String wiki) {
        joymeWikiSitemapService.deleteSitemap(wiki);
        return new ModelAndView("redirect:/wiki/ac/st/page.do");
    }

    @RequestMapping(value = "/wiki/ac/st/delugc.do")
    public ModelAndView delUgcWiki(HttpServletRequest request,
                                   @RequestParam(value = "wiki") String wiki) {
        joymeWikiSitemapService.deleteUGCSitemap(wiki);
        return new ModelAndView("redirect:/wiki/ac/st/page.do");
    }
}
