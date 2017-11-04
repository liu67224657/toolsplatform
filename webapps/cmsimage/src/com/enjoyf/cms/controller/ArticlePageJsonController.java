package com.enjoyf.cms.controller;

import com.enjoyf.util.StringUtil;
import com.enjoyf.util.URLUtil;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhimingli on 2015/5/27.
 */
@Controller
@RequestMapping("/article/page")
public class ArticlePageJsonController {

    private static URLUtil urlUtil = new URLUtil();

    @ResponseBody
    @RequestMapping(value = "/nextpage.do")
    public String nextpage(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam(value = "href", required = false) String href) {
        JSONObject retunObj = new JSONObject();
        try {
            retunObj.put("rs", 1);
            retunObj.put("msg", "success");
            // href = "http://joyme.youku.com/globalvideo/index.html";
            if (!StringUtil.isEmpty(href)) {
                href = href.split("\\?")[0];

                Document doc = parseDocumentByHTML(urlUtil.openURLWithTimeOut(href));
                Elements elements = doc.getElementsByClass("pageinfo");
                int maxPage = 1;

                if (elements != null && elements.select("strong") != null) {
                    maxPage = Integer.valueOf(elements.select("strong").get(0).html());
                }


                int curpage = 1;
                if (!href.contains("index.html")) {
                    if (href.split("_").length >= 2) {
                        curpage = Integer.valueOf(href.split("_")[2].replace(".html", ""));
                    } else {
                        retunObj.put("rs", 0);
                        retunObj.put("msg", "fail");
                        return "nextpage([" + retunObj.toString() + "])";
                    }
                }

                //最后一页
                if (curpage == maxPage) {
                    retunObj.put("rs", 2);
                    return "nextpage([" + retunObj.toString() + "])";
                }

                StringBuffer jsoupURl = new StringBuffer();
                String urlArr[] = href.split("_");
                if (href.contains("index.html")) {
                    curpage = (maxPage - 1);
                    String listHref = "";
                    Elements elements1 = doc.getElementById("pageNav").select("li");
                    for (Element e : elements1) {
                        if (e.attr("class").equals("thisclass") || e.html().equals("首页")) {
                            continue;
                        }
                        listHref = e.select("a").get(0).attr("href");
                        break;
                    }
                    String listhrefarr[] = listHref.split("_");
                    if (href.startsWith("http://")) {
                        jsoupURl.append(listHref);
                    } else {
                        jsoupURl.append(href.substring(0, href.lastIndexOf("/")) + "/" + listhrefarr[0] + "_" + listhrefarr[1] + "_" + curpage + ".html");
                    }
                } else {
                    if (curpage != 1) {
                        curpage = (curpage - 1);
                    }
                    jsoupURl.append(urlArr[0] + "_" + urlArr[1] + "_" + curpage + ".html");
                }

                if (href.equals(jsoupURl.toString())) {
                    retunObj.put("rs", 2);
                    return "nextpage([" + retunObj.toString() + "])";
                }

                Document nextPage = parseDocumentByHTML(urlUtil.openURLWithTimeOut(jsoupURl.toString()));
                retunObj.put("body", nextPage.getElementsByClass("special-article").html());
                retunObj.put("curpage", curpage);
                retunObj.put("href", jsoupURl.toString());
            }
        } catch (Exception e) {
            retunObj.put("rs", 0);
            retunObj.put("msg", "fail");
        }
        return "nextpage([" + retunObj.toString() + "])";
    }

    private static Document parseDocumentByHTML(String html) {
        return Jsoup.parse(html);
    }


}
