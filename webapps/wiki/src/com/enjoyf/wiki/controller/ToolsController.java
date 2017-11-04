package com.enjoyf.wiki.controller;

import com.enjoyf.util.AppUtil;
import com.enjoyf.wiki.facade.CardSelectFacade;
import com.enjoyf.wiki.service.ChannelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  14-5-13 下午7:51
 * Description:
 */
@Controller
public class ToolsController {

    private CardSelectFacade facade = new CardSelectFacade();

    private ChannelService channelService = new ChannelService();

    @RequestMapping("/mwiki/tools/{wikiCode}/compare.do")
    public ModelAndView selectM(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "wikiCode") String wikiCode) {
        return select(request, response, wikiCode);
    }

    @RequestMapping("/wxwiki/tools/{wikiCode}/compare.do")
    public ModelAndView selectWx(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "wikiCode") String wikiCode) {
        return select(request, response, wikiCode);
    }


    @RequestMapping("/wiki/tools/{wikiCode}/compare.do")
    public ModelAndView select(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "wikiCode") String wikiCode) {
        String wikiType = "wiki";
        if (AppUtil.checkMobile(request)) {
            wikiType = "mwiki";
        }

        String channel = channelService.getChannelByRequest(request);
//        channel = "wx";
//        if ("wx".equals(channel)) {
//            wikiType = "wxwiki";
//        }
        Map<String, Object> map = facade.getCardCompareModel(request, response, wikiCode, wikiType, channel);

        if (map.containsKey("error")) {
            return new ModelAndView("/wiki/tools/error", map);
        } else {
            return new ModelAndView("/wiki/tools/result", map);
        }
    }
}


