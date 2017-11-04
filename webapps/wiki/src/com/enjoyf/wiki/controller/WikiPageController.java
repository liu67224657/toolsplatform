package com.enjoyf.wiki.controller;

import com.enjoyf.util.StringUtil;
import com.enjoyf.wiki.bean.WikiPage;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.service.WikiPageService;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-5-19
 * Time: 下午1:48
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/wiki/page")
public class WikiPageController {


    private WikiPageService wikiPageService = new WikiPageService();

    @ResponseBody
    @RequestMapping("/query.do")
    public String wikipagelist(HttpServletRequest request,
                               HttpServletResponse response
    ) throws Exception {

        String wikikey = request.getParameter("wikikey");
        String key = request.getParameter("key");
        if (wikikey == null || key == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rs", "-1000");
            jsonObject.put("msg", "param.empty");
            return jsonObject.toString();
        }

        try {
            key = URLDecoder.decode(key, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rs", "0");
        jsonObject.put("rs", "1");
        if (!key.equals("首页")) {
            WikiPage wikiPage = wikiPageService.queryWikiPage(null, wikikey, key);
            //wikipage不为空但是状态删除返回错误
            if (wikiPage != null && wikiPage.getPageStatus() == 0) {
                jsonObject.put("rs", "-1");
                jsonObject.put("msg", "wikipage.not.exists");
            } else {
                //wikipage为空insert 返回
                if (wikiPage == null) {
                    wikiPage = new WikiPage();
                    wikiPage.setWikiKey(wikikey);
                    wikiPage.setWikiUrl(key);
                    wikiPage.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    wikiPage.setPageStatus(1);
                    wikiPage.setPageId(wikiPageService.insertWikiPage(null, wikiPage));
                }
                Map map = new HashMap();
                map.put("pageid", wikiPage.getPageId() + ".shtml");
                jsonObject.put("result", map);
            }
        } else {
            Map map = new HashMap();
            map.put("pageid", "index.shtml");
            jsonObject.put("result", map);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        return jsonObject.toString();
    }

}
