package com.enjoyf.wiki.controller;

import com.enjoyf.util.*;
import com.enjoyf.wiki.bean.CardOpinion;
import com.enjoyf.wiki.bean.PageBean;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.facade.CardSelectFacade;
import com.enjoyf.wiki.facade.ToolsTemplate;
import com.enjoyf.wiki.service.CardOpinionService;
import com.enjoyf.wiki.service.ChannelService;
import com.enjoyf.wiki.util.FreemarkerTemplateGenerator;
import com.enjoyf.wiki.util.UploadQiniuUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-6-18
 * Time: 下午2:54
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ToolsOpinionController {
    private static URLUtil urlUtil = new URLUtil();
    private CardOpinionService cardOpinionService = new CardOpinionService();
    private ChannelService channelService = new ChannelService();
    private CardSelectFacade facade = new CardSelectFacade();

    @RequestMapping("/wiki/ac/toolsOpinionList.do")
    public ModelAndView itemList(HttpServletRequest request) throws Exception {
        int pageNum = 1;
        String wiki = "";
        String remove_status = "";
        try {
            if (!StringUtil.isEmpty(request.getParameter("pageNum"))) {
                pageNum = Integer.parseInt(request.getParameter("pageNum"));
                if (pageNum <= 0) {
                    pageNum = 1;
                }
            }
            if (!StringUtil.isEmpty(request.getParameter("wiki"))) {
                wiki = request.getParameter("wiki");
            }
            if (!StringUtil.isEmpty(request.getParameter("remove_status"))) {
                remove_status = request.getParameter("remove_status");
            }

            PageBean bean = cardOpinionService.queryCardOpinionByPage(null, pageNum, wiki, StringUtil.isEmpty(remove_status) ? null : Integer.valueOf(remove_status));
            request.setAttribute("result", bean);
            request.setAttribute("wikiMap", PropertiesContainer.getInstance().getJoymeWikiKetSet());
            request.setAttribute("wiki", wiki);
            request.setAttribute("remove_status", remove_status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/wiki/ac/tool/opinionlist");
    }

    @RequestMapping("/wiki/ac/toolsOpinionDelete.do")
    public ModelAndView toolsOpinionDelete(HttpServletRequest request) throws Exception {
        CardOpinion cardOpinion = new CardOpinion();
        Long opinionid = Long.valueOf(request.getParameter("id"));
        cardOpinion.setOpinionId(opinionid);
        if (!StringUtil.isEmpty(request.getParameter("removeState"))) {
            cardOpinion.setRemoveState(Integer.valueOf(request.getParameter("removeState")));
        }
        cardOpinionService.updateJoymeItem(null, cardOpinion);
        return new ModelAndView("redirect:/wiki/ac/toolsOpinionList.do");
    }

    @RequestMapping("/mwiki/tools/{wikiCode}/{pageid}/opinion.do")
    public ModelAndView opinionM(HttpServletRequest request, @PathVariable(value = "wikiCode") String wikiCode,
                                 @PathVariable(value = "pageid") String pageid) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //String openUrl = "http://www.joyme.com/wiki/dtcq/59421.shtml";
            String wikiType = "wiki";
            String openUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/" + wikiType + "/" + wikiCode + "/" + pageid + ".shtml";
            Document cardDoc = Jsoup.parse(urlUtil.openURLWithTimeOut(openUrl));
            Map<String, Object> param = getOpinion(cardDoc, wikiCode, pageid);
            if (param.containsKey("error")) {
                return new ModelAndView("/wiki/tools/error", map);
            }

            String s = "";
            String channel = channelService.getChannelByRequest(request);
            param.put("templateSourceUrl", PropertiesContainer.getInstance().getTemplateSourceUrl());
            param.put("wikitype", "mwiki");
            if ("wx".equals(channel)) {
                s = FreemarkerTemplateGenerator.get().generateTemplate("/ftl/wxmcardopinion.ftl", param);
            } else {
                s = FreemarkerTemplateGenerator.get().generateTemplate("/ftl/mcardopinion.ftl", param);
            }

            String str = ToolsTemplate.CARD_OPINION_MWIKI_DEFAULT.replace("{joyme:wiki_body}", s);
            map.put("result", str);
            map.put("templateSourceUrl", PropertiesContainer.getInstance().getTemplateSourceUrl());
            if (map.containsKey("error")) {
                return new ModelAndView("/wiki/tools/error", map);
            } else {
                return new ModelAndView("/wiki/tools/result", map);
            }
        } catch (Exception e) {
            map.put("error", "card.error.isnull");
            return new ModelAndView("/wiki/tools/error", map);
        }

    }

    @RequestMapping("/wiki/tools/{wikiCode}/{pageid}/opinion.do")
    public ModelAndView opinion(HttpServletRequest request, @PathVariable(value = "wikiCode") String wikiCode,
                                @PathVariable(value = "pageid") String pageid) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String wikiType = "wiki";
            String openUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/" + wikiType + "/" + wikiCode + "/" + pageid + ".shtml";
            // String openUrl = "http://www.joyme.com/wiki/dtcq/59414.shtml";
            Document cardDoc = Jsoup.parse(urlUtil.openURLWithTimeOut(openUrl));
            Map<String, Object> param = getOpinion(cardDoc, wikiCode, pageid);
            if (param.containsKey("error")) {
                return new ModelAndView("/wiki/tools/error", map);
            }

            param.put("wikitype", "wiki");
            param.put("templateSourceUrl", PropertiesContainer.getInstance().getTemplateSourceUrl());
            String s = FreemarkerTemplateGenerator.get().generateTemplate("/ftl/cardopinion.ftl", param);


            Element element = cardDoc.getElementById("wiki-content");
            if (element == null) {
                element = cardDoc.getElementById("mw-content-text");
            }
            element.html(s);
            String template = cardDoc.html();
            map.put("result", template);
            //map.put("templateSourceUrl", PropertiesContainer.getInstance().getTemplateSourceUrl());

        } catch (Exception e) {
            map.put("error", "card.error.isnull");
            return new ModelAndView("/wiki/tools/error", map);
        }
        return new ModelAndView("/wiki/tools/opinion", map);
    }

    @ResponseBody
    @RequestMapping("/mwiki/tools/{wikiCode}/{pageid}/saveq.do")
    public String msaveq(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "wikiCode") String wiki,
                         @PathVariable(value = "pageid") String pageid,
                         @RequestParam(value = "title", required = false) String title,
                         @RequestParam(value = "opinionKey", required = false) String opinionKey,
                         @RequestParam(value = "opinionValue", required = false) String opinionValue,
                         @RequestParam(value = "nickName", required = false) String nickName,
                         @RequestParam(value = "contacts", required = false) String contacts,
                         @RequestParam(value = "filename", required = false) MultipartFile filename
    ) {
        return saveq(request, response, wiki, pageid, title, opinionKey, opinionValue, nickName, contacts, filename);
    }

    @ResponseBody
    @RequestMapping("/wiki/tools/{wikiCode}/{pageid}/saveq.do")
    public String saveq(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "wikiCode") String wiki,
                        @PathVariable(value = "pageid") String pageid,
                        @RequestParam(value = "title", required = false) String title,
                        @RequestParam(value = "opinionKey", required = false) String opinionKey,
                        @RequestParam(value = "opinionValue", required = false) String opinionValue,
                        @RequestParam(value = "nickName", required = false) String nickName,
                        @RequestParam(value = "contacts", required = false) String contacts,
                        @RequestParam(value = "filename", required = false) MultipartFile filename
    ) {
        Map<String, Object> map = new HashMap<String, Object>();
        String json = "{success:true}";
        try {
            if (filename != null) {
                String picurl = UploadQiniuUtil.uploadQiniu(filename);
                if (!StringUtil.isEmpty(picurl)) {
                    opinionKey = "特殊:修改图片";
                    opinionValue = URLUtil.getJoymeDnUrl(picurl);
                } else {
                    json = "{success:false}";
                }
            }

            String wikiType = "wiki";
            String openUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/" + wikiType + "/" + wiki + "/" + pageid + ".shtml";
            CardOpinion page = new CardOpinion();
            page.setWiki(wiki);
            page.setWikiSource(openUrl);
            page.setTitle(title);
            page.setOpinionKey(opinionKey);
            page.setOpinionValue(opinionValue);
            page.setNickName(nickName);
            page.setContacts(contacts);
            page.setCreatetime(new Date());
            cardOpinionService.insertCardOpinion(null, page);
        } catch (Exception e) {
            map.put("error", "card.error.isnull");
        }

        return json;
    }


    private Map<String, Object> getOpinion(Document cardDoc, String wikiCode, String pageid) throws IOException {
        Map<String, Object> param = new HashMap<String, Object>();
        Elements elements = cardDoc.getElementsByAttribute("data-compare-text-opinion");
        try {
            String cardImage = facade.getImage(cardDoc);
            String cardName = facade.getName(cardDoc);
            param.put("cardName", cardName == null ? "" : cardName);
            param.put("cardImage", cardImage == null ? "" : cardImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> cardMap = facade.getCardCompare(elements, CardSelectFacade.DATA_COMPARE_TEXT_OPINION);
        if (CollectionUtil.isEmpty(cardMap)) {
            param.put("error", "card.error.isnull");
            //return new ModelAndView("/wiki/tools/error", map);
            return param;
        }
        for (String key : cardMap.keySet()) {
            if (cardMap.get(key).equals("-")) {
                cardMap.put(key, "");
            }
        }

        param.put("cardMap", cardMap);
        param.put("wikiCode", wikiCode);
        param.put("pageid", pageid);
        return param;
    }

}