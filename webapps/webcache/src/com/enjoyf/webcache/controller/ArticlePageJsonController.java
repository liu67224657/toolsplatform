package com.enjoyf.webcache.controller;

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
 * m站的加载更多翻页，对页面的结构样式要求很严格，与运营协商好。
 * 1、由于webcache这边没有article的数据，所以，这个是通过在源站的页面中埋入分页的隐藏div，
 * 2、next-page.js中得到当前的页面的url，将url传给接口
 * 3、接口将当前的页面的内容抓出来，渠道class="thisclass"的当前页的下一页的url
 * 4、抓取下一页的内容，解析之后，返回给前端js
 * 5、前端js在将当前也的url，替换成下一页的url
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
        String callback = request.getParameter("callback");
        try {
            retunObj.put("rs", 1);
            retunObj.put("msg", "success");
            // href = "http://joyme.youku.com/globalvideo/index.html";
            if (!StringUtil.isEmpty(href)) {
                href = href.split("\\?")[0];
                if (href.endsWith("/")) {
                    href += "index.html";
                } else {
                    if (!href.contains(".html")) {
                        href += "/index.html";
                    }
                }

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
                        return callback + "([" + retunObj.toString() + "])";
                    }
                }

                //最后一页
                if (curpage == maxPage) {
                    retunObj.put("rs", 2);
                    return callback + "([" + retunObj.toString() + "])";
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
                    return callback + "([" + retunObj.toString() + "])";
                }

                Document nextPage = parseDocumentByHTML(urlUtil.openURLWithTimeOut(jsoupURl.toString()));
                if (href.contains("youku")) {
                    retunObj.put("body", nextPage.getElementsByClass("special-article").html());
                } else {
                    retunObj.put("body", nextPage.getElementById("tuwenwrapper").html());
                }
                retunObj.put("curpage", curpage);
                retunObj.put("href", jsoupURl.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            retunObj.put("rs", 0);
            retunObj.put("msg", "fail");
        }
        return callback + "([" + retunObj.toString() + "])";
    }

    private static Document parseDocumentByHTML(String html) {
        return Jsoup.parse(html);
    }


    /**
     * 单独给图集写的，线上紧急bug
     * asc 页面往上加
     * desc 页数往下减
     */
    @ResponseBody
    @RequestMapping(value = "/tujinextpage.do")
    public String tujinextpage(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(value = "href", required = false) String href,
                               @RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
        JSONObject retunObj = new JSONObject();
        String callback = request.getParameter("callback");
        try {
            retunObj.put("rs", 1);
            retunObj.put("msg", "success");
            // href = "http://m.joyme.com/tags/list_67141_1.html";
            if (!StringUtil.isEmpty(href)) {
                href = href.split("\\?")[0];
                if (href.endsWith("/")) {
                    href += "index.html";
                } else {
                    if (!href.contains(".html")) {
                        href += "/index.html";
                    }
                }

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
                        return callback + "([" + retunObj.toString() + "])";
                    }
                }


                //最后一页判断
                //倒序
                if (curpage == 1 && "desc".equals(order)) {
                    retunObj.put("rs", 2);
                    return callback + "([" + retunObj.toString() + "])";
                } else if (curpage == maxPage && "asc".equals(order)) {
                    retunObj.put("rs", 2);
                    return callback + "([" + retunObj.toString() + "])";
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
                    if ("desc".equals(order)) {
                        curpage = (curpage - 1);
                    } else {
                        curpage = (curpage + 1);
                    }
                    jsoupURl.append(urlArr[0] + "_" + urlArr[1] + "_" + curpage + ".html");
                }

                Document nextPage = parseDocumentByHTML(urlUtil.openURLWithTimeOut(jsoupURl.toString()));
                retunObj.put("body", nextPage.getElementById("tuwenwrapper").html());
                retunObj.put("curpage", curpage);
                retunObj.put("href", jsoupURl.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            retunObj.put("rs", 0);
            retunObj.put("msg", "fail");
        }
        return callback + "([" + retunObj.toString() + "])";
    }


}
