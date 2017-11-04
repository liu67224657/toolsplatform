package com.enjoyf.wiki.search;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enjoyf.wiki.bean.PageBean;
import com.enjoyf.wiki.bean.WikiPage;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.service.WikiPageService;
import com.enjoyf.wiki.tools.WikiUtil;

import java.net.URL;

public class Search {
    private static WikiPageService service = new WikiPageService();

    public void search(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String searchStr = request.getParameter("search");
        String key = request.getParameter("key");
        int pageNum = 1;
        if (request.getParameter("pageNum") != null)
            pageNum = Integer.parseInt(request.getParameter("pageNum"));

        int pageCount = 10;
        if (request.getParameter("pageCount") != null)
            pageCount = Integer.parseInt(request.getParameter("pageCount"));

        PageBean bean = service.searchWikiPageList(null, key, searchStr, 1, pageNum, pageCount);

        String wikiHost = PropertiesContainer.getInstance().getWikiHost();
        URL url = new URL(request.getRequestURL().toString());
        //if (!WikiUtil.isWWWdomain(url)) {
        for (Object object : bean.getRetList()) {
            WikiPage wikiPage = (WikiPage) object;
            String httpUrl = wikiHost + "/" + key + "/" + wikiPage.getPageId() + ".shtml";
            wikiPage.setHttpUrl(httpUrl);
        }
        // }

        request.setAttribute("pageBean", bean);
    }


    public PageBean search(HttpServletRequest request, String key, String searchStr, int pageNum, int pageCount) throws Exception {

        PageBean bean = service.searchWikiPageList(null, key, searchStr, 1, pageNum, pageCount);

        URL url = new URL(request.getRequestURL().toString());
        String wikiHost = PropertiesContainer.getInstance().getWikiHost();
        //  if (!WikiUtil.isWWWdomain(url)) {
        for (Object object : bean.getRetList()) {
            WikiPage wikiPage = (WikiPage) object;
            String httpUrl = wikiHost + "/" + key + "/" + wikiPage.getPageId() + ".shtml";
            wikiPage.setHttpUrl(httpUrl);
        }
        //   }

        return bean;
    }
}
