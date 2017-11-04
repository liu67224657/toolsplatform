package com.enjoyf.wiki.controller;

import com.enjoyf.util.StringUtil;
import com.enjoyf.wiki.bean.WikiPage;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.service.WikiPageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-8-6
 * Time: 下午6:13
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class WikiRandomController {


    private WikiPageService wikiPageService = new WikiPageService();

    @RequestMapping("/{wiki}/{code}/random.do")
    public ModelAndView wikipagelist(@PathVariable(value = "code") String code,
                                     @PathVariable(value = "wiki") String wiki,
                                     HttpServletRequest request) throws Exception {
        List<WikiPage> wikiPageList = wikiPageService.getWikiPageListByWikiKey(null, code);
        StringBuffer url = new StringBuffer();
        int randomNum = new Random().nextInt(wikiPageList.size());
        String channel = StringUtil.isEmpty(request.getParameter("channel")) ?
                request.getHeader("channel") : request.getParameter("channel");

        if (!channel.equals("default")) {
            channel = channel + "wiki";
        } else {
            channel = wiki.equals("wiki") ? "wiki" : "mwiki";
        }


        if (wiki.equals("mwiki")) {
            url.append("http://www.joyme.com/" + channel + "/" + code + "/" + wikiPageList.get(randomNum).getPageId() + ".shtml");
        } else {
            boolean isSupportDomain = PropertiesContainer.getInstance().supportSubDomain(code, "wiki");
            if (isSupportDomain) {
                url.append("http://" + code + ".joyme.com/" + wikiPageList.get(randomNum).getPageId() + ".shtml");
            } else {
                url.append("http://www.joyme.com/" + channel + "/" + code + "/" + wikiPageList.get(randomNum).getPageId() + ".shtml");
            }
        }
        return new ModelAndView("redirect:" + url.toString());
    }
}
