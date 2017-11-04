package com.enjoyf.wiki.controller;

import com.enjoyf.util.StringUtil;
import com.enjoyf.wiki.bean.WikiPage;
import com.enjoyf.wiki.bean.temp.WikiRankBean;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.service.WikiPageService;
import com.enjoyf.wiki.service.WikiRankService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 15/7/14
 * Description: 最新 最热文章的管理类
 */
@Controller
public class WikiAcRankController {

    private WikiRankService wikiRankService = new WikiRankService();
    private WikiPageService pageService = new WikiPageService();


    @RequestMapping("/wiki/ac/rank/page.do")
    public ModelAndView rankPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Set keySet = PropertiesContainer.getInstance().getJoymeWikiKetSet();

        Map map = new HashMap();
        map.put("keySet", keySet);

        return new ModelAndView("/wiki/ac/rank/page", map);
    }

    @RequestMapping("/wiki/ac/rank/query.do")
    public ModelAndView rankSelect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String wikikey = request.getParameter("wikikey");
        List<WikiRankBean> newsList = wikiRankService.getLastModifyList(wikikey);
        List<WikiRankBean> hotList = wikiRankService.getHotRank(wikikey);

        Map map = new HashMap();
        map.put("newsList", newsList);
        map.put("hotList", hotList);
        map.put("wikikey", wikikey);
        map.put("wikihost", PropertiesContainer.getInstance().getWikiHost());

        return new ModelAndView("/wiki/ac/rank/rankresult", map);
    }

    @RequestMapping("/wiki/ac/rank/newsreload.do")
    public ModelAndView newsRankReload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String wikikey = request.getParameter("wikikey");
        wikiRankService.fetchLastModifyList(wikikey);
        return new ModelAndView("/wiki/ac/rank/rank-ok");
    }

    private static final Pattern GET_ID_PATTERN = Pattern.compile("/(\\d+)\\.shtml");

    @RequestMapping("/wiki/ac/rank/addhot.do")
    public ModelAndView addHot(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String wikikey = request.getParameter("wikikey");
        String url = request.getParameter("url");
        String scoreParam = request.getParameter("score");

        Map map = new HashMap();
        if (StringUtil.isEmpty(wikikey) || StringUtil.isEmpty(url) || StringUtil.isEmpty(scoreParam)) {
            map.put("error", "param.empty");
            return new ModelAndView("/wiki/ac/rank/rank-error", map);
        }

        String idStr = "";
        Matcher matcher = GET_ID_PATTERN.matcher(url);
        if (matcher.find()) {
            idStr = matcher.group(1);
        }

        double score = 0;
        long id = 0;
        try {
            score = Double.parseDouble(scoreParam);
            id = Long.parseLong(idStr);

            WikiPage wikiPage = pageService.queryWikiPagebyId(null, id);

            if (wikiPage == null) {
                map.put("error", "wikipage.notexists");
                return new ModelAndView("/wiki/ac/rank/rank-error", map);
            }

            wikiRankService.incrHotRank(wikikey, id, score);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", "param.illegl");
            return new ModelAndView("/wiki/ac/rank/rank-error", map);
        }

        return new ModelAndView("/wiki/ac/rank/rank-ok");
    }
}
