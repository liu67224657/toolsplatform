package com.enjoyf.wiki.controller;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.StringUtil;
import com.enjoyf.wiki.bean.JoymeTemplate;
import com.enjoyf.wiki.bean.PageBean;
import com.enjoyf.wiki.container.ChannelContainer;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.container.TemplateContainer;
import com.enjoyf.wiki.service.CacheService;
import com.enjoyf.wiki.service.JoymeTemplateService;
import com.enjoyf.wiki.service.JoymeWikiStyleService;
import com.enjoyf.wiki.template.TemplateUtil;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-9-17 下午5:33
 * Description:
 */
@Controller
public class TemplateController {

    JoymeTemplateService templateService = new JoymeTemplateService();
    CacheService cacheService=new CacheService();

    @RequestMapping("/wiki/ac/templatepage.do")
    public ModelAndView templatePage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("/wiki/ac/template/templatepage");
    }

    @RequestMapping("/wiki/ac/createtemplate.do")
    public ModelAndView createItem(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "wikitype") String wikiType) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("wikiMap", PropertiesContainer.getInstance().getJoymeWikiKetSet());
        //渠道
        map.put("channelMap", ChannelContainer.channelHtmlFacotryMap);
        map.put("wikitype", wikiType);

        return new ModelAndView("/wiki/ac/template/createtemplate", map);
    }

    @RequestMapping("/wiki/ac/createtemplateAction.do")
    public ModelAndView createItemAction(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @RequestParam(value = "templateName") String templateName,
//                                         @RequestParam(value = "css", required = false) MultipartFile cssFile,
                                         @RequestParam(value = "wiki") String wiki,
                                         @RequestParam(value = "channel") String channel,
                                         @RequestParam(value = "isIndex") Integer isIndex,
                                         @RequestParam(value = "praseFactory") String praseFactory,
                                         @RequestParam(value = "templateContext") String templateContext,
                                         @RequestParam(value = "wikitype") String wikiType,
                                         @RequestParam(value = "templateid")Integer templateid

    ) throws Exception {
        Timestamp createTime = new Timestamp(System.currentTimeMillis());

        if (isIndex == 0) {
            saveIndexTemplate(templateid,templateName, wiki, channel, 1, praseFactory, templateContext, createTime, wikiType);
            saveIndexTemplate(templateid,templateName, wiki, channel, 2, praseFactory, templateContext, createTime, wikiType);
        } else {
            saveIndexTemplate(templateid,templateName, wiki, channel, isIndex, praseFactory, templateContext, createTime, wikiType);
        }
        cacheService.reloadTemplate();

        return new ModelAndView("/wiki/ac/template/createtemplateAction");
    }

    private void saveIndexTemplate(int templateid,String templateName, String wiki, String channel, Integer isIndex, String praseFactory, String templateContext, Timestamp timestamp, String wikiType) throws JoymeServiceException, JoymeDBException {
        JoymeTemplate joymeTemplate = new JoymeTemplate();
        joymeTemplate.setTemplateName(templateName);
        joymeTemplate.setWiki(wiki);
        joymeTemplate.setChannel(channel);
        joymeTemplate.setPraseFactory(praseFactory);
        joymeTemplate.setTemplateContext(templateContext);
        joymeTemplate.setIsEnable(1);
        joymeTemplate.setCreateTime(timestamp);
        joymeTemplate.setIsIndex(isIndex);
        joymeTemplate.setContextPath(wikiType);
        joymeTemplate.setJoymeTemplateId(templateid);
        if (templateid == -1) {
            templateService.insertJoymeTemplate(null, joymeTemplate);
        } else {
            joymeTemplate.setJoymeTemplateId(templateid);
            templateService.updateJoymeTemplate(null, joymeTemplate);
        }


        TemplateContainer.putTemplate(joymeTemplate, wikiType);
    }

    @RequestMapping("/wiki/ac/templatelist.do")
    public ModelAndView itemList(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(value = "templatekey", required = false) String templateKey,
                                 @RequestParam(value = "wiki", required = false) String wiki,
                                 @RequestParam(value = "channel", required = false) String channel,
                                 @RequestParam(value = "isIndex", required = false, defaultValue = "0") String isIndex,
                                 @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                 @RequestParam(value = "wikitype") String wikiType) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("wikiMap", PropertiesContainer.getInstance().getJoymeWikiKetSet());
        map.put("channelMap", ChannelContainer.channelHtmlFacotryMap);

        PageBean bean = templateService.queryJoymeTemplateByCondition(null, wiki, channel, Integer.valueOf(StringUtil.isEmpty(isIndex)?"0":isIndex), templateKey, wikiType, pageNum);
        request.setAttribute("result", bean);

        map.put("param.wiki", wiki);
        map.put("param.channel", channel);
        map.put("param.isIndex", isIndex);
        map.put("param.pageNum", pageNum);
        map.put("param.wikitype", wikiType);
        return new ModelAndView("/wiki/ac/template/list", map);
    }


    @RequestMapping("/wiki/ac/templatemodifypage.do")
    public ModelAndView modifyPage(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(value = "templateid", required = true) Integer templateId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("wikiMap", PropertiesContainer.getInstance().getJoymeWikiKetSet());
        map.put("channelMap", ChannelContainer.channelHtmlFacotryMap);


        JoymeTemplate joymeTemplate = templateService.queryJoymeTemplatebyId(null, templateId);
        map.put("template", joymeTemplate);
        map.put("wikitype", joymeTemplate.getContextPath());
        map.put("templateid", templateId);
        return new ModelAndView("/wiki/ac/template/modify", map);
    }


    @RequestMapping("/wiki/ac/templatepreview.do")
    public ModelAndView createItemAction(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @RequestParam(value = "channel") String channel,
                                         @RequestParam(value = "isIndex") Integer isIndex,
                                         @RequestParam(value = "key") String key,
                                         @RequestParam(value = "templateContext") String templateContext,
                                         @RequestParam(value = "praseFactory", required = false, defaultValue = "default") String praseFactory) throws Exception {

        Document html = TemplateUtil.replaceWikiBody(templateContext, "测试");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("html", html.html());

        return new ModelAndView("/wiki/ac/template/priview", map);
    }

    @RequestMapping("/wiki/ac/preivewtempalte.do")
    public ModelAndView createItemAction(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @RequestParam(value = "templateid") Integer templateid) throws Exception {

        JoymeTemplate joymeTemplate = templateService.queryJoymeTemplatebyId(null, templateid);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("html", joymeTemplate.getTemplateContext());

        return new ModelAndView("/wiki/ac/template/priview", map);
    }
}
