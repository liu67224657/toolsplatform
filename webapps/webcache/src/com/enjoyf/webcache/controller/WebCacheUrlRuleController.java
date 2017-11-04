package com.enjoyf.webcache.controller;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.MD5Util;
import com.enjoyf.util.StringUtil;
import com.enjoyf.webcache.bean.*;
import com.enjoyf.webcache.cache.UrlRuleCache;
import com.enjoyf.webcache.service.WebCacheUrlRuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * webcache后台规则配置的增删改查维护工具
 * Created by zhitaoshi on 2015/9/24.
 */
@Controller
@RequestMapping(value = "/ac/urlrule")
public class WebCacheUrlRuleController {

    private static WebCacheUrlRuleService urlRuleService = new WebCacheUrlRuleService();
    private UrlRuleCache urlRuleCache = new UrlRuleCache();
    private static List<IntStatus> statusList = new ArrayList<IntStatus>();

    static {
        statusList.add(IntStatus.USED);
        statusList.add(IntStatus.REMOVE);
    }

    @RequestMapping("/list.do")
    public ModelAndView ruleList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Integer qsrc = request.getParameter("qsrc") == null ? null : Integer.parseInt(request.getParameter("qsrc"));
            Integer qclient = request.getParameter("qclient") == null ? null : Integer.parseInt(request.getParameter("qclient"));
            Integer qchannel = request.getParameter("qchannel") == null ? null : Integer.parseInt(request.getParameter("qchannel"));
            int status = request.getParameter("qstatus") == null ? IntStatus.USED.getCode() : Integer.parseInt(request.getParameter("qstatus"));
            List<WebCacheUrlRule> ruleList = urlRuleService.queryWebCacheUrlRule(null, status, qsrc, qclient, qchannel);
            map.put("list", ruleList);
            map.put("statusList", statusList);
            map.put("qstatus", status);
            map.put("qclient", qclient);
            map.put("qsrc", qsrc);
            map.put("qchannel", qchannel);
            map.put("srcTypeList", WebCacheSrcType.getAll());
//            map.put("clientTypeList", WebCacheClientType.getAll());
//            map.put("channelList", WebCacheChannel.getAll());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }
        return new ModelAndView("/ac/urlrule/rulelist", map);
    }

    @RequestMapping("/createpage.do")
    public ModelAndView createPage(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("srcTypeList", WebCacheSrcType.getAll());
            map.put("clientTypeList", WebCacheClientType.getAll());
            map.put("statusList", statusList);
            map.put("cdnList", WebCacheCdnType.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/ac/urlrule/createrule", map);
    }

    @RequestMapping("/create.do")
    public ModelAndView createRule(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String desRule = request.getParameter("desrule");
            if (!desRule.startsWith("http://")) {
                desRule = "http://" + desRule;
            }
            if (!desRule.endsWith("/")) {
                desRule += "/";
            }
            String srcRule = request.getParameter("srcrule");
            if(!StringUtil.isEmpty(srcRule)){
                if (!srcRule.startsWith("http://")) {
                    srcRule = "http://" + srcRule;
                }
                if (!srcRule.endsWith("/")) {
                    srcRule += "/";
                }
            }

            int srcType = Integer.parseInt(request.getParameter("srctype"));
            int clientType = Integer.parseInt(request.getParameter("clienttype"));
            int status = Integer.parseInt(request.getParameter("status"));
            String pvSetting = request.getParameter("pvsetting");
            String tabSetting = request.getParameter("tabsetting");
            String pageKey = request.getParameter("pagekey");
            int cdnType = Integer.parseInt(request.getParameter("cdntype"));

            WebCacheUrlRule urlRule = new WebCacheUrlRule();
            urlRule.setRuleId(MD5Util.Md5(desRule));

            URL url = new URL(desRule);
            urlRule.setDesHost(url.getHost());

            urlRule.setDesRule(desRule);
            urlRule.setSrcRule(srcRule);
            urlRule.setSrcType(WebCacheSrcType.getByCode(srcType));
            urlRule.setClientType(WebCacheClientType.getByCode(clientType));
            urlRule.setStatus(IntStatus.getByCode(status));
            urlRule.setPageKey(pageKey);
            urlRule.setCdnType(WebCacheCdnType.getByCode(cdnType));

            WebCacheGlobalSetting globalSetting = new WebCacheGlobalSetting();
            globalSetting.setPvSetting(pvSetting);
            globalSetting.setPcWapTabSetting(tabSetting);
            urlRule.setGlobalSetting(globalSetting);

            if (urlRuleService.insertWebcacheUrlrule(null, urlRule) > 0) {
                urlRuleCache.putUrlRules(desRule, srcRule);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/ac/urlrule/list.do");
    }

    @RequestMapping("/modifypage.do")
    public ModelAndView modifyPage(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String ruleId = request.getParameter("ruleid");

            map.put("ruleId", ruleId);
            WebCacheUrlRule urlRule = urlRuleService.getWebCacheUrlRule(null, ruleId);
            if (urlRule == null) {
                return new ModelAndView("redirect:/ac/urlrule/list.do");
            }
            map.put("urlRule", urlRule);

            map.put("srcTypeList", WebCacheSrcType.getAll());
            map.put("clientTypeList", WebCacheClientType.getAll());
            map.put("statusList", statusList);
            map.put("cdnList", WebCacheCdnType.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/ac/urlrule/modifyrule", map);
    }

    @RequestMapping("/modify.do")
    public ModelAndView modifyRule(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String oldDesRule = request.getParameter("olddesrule");
            String oldSrcRule = request.getParameter("oldsrcrule");
            String ruleId = request.getParameter("ruleid");

            String desRule = request.getParameter("desrule");
            if (!desRule.startsWith("http://")) {
                desRule = "http://" + desRule;
            }
            if (!desRule.endsWith("/")) {
                desRule += "/";
            }
            String srcRule = request.getParameter("srcrule");
            if(!StringUtil.isEmpty(srcRule)){
                if (!srcRule.startsWith("http://")) {
                    srcRule = "http://" + srcRule;
                }
                if (!srcRule.endsWith("/")) {
                    srcRule += "/";
                }
            }

            int srcType = Integer.parseInt(request.getParameter("srctype"));
            int clientType = Integer.parseInt(request.getParameter("clienttype"));
            int status = Integer.parseInt(request.getParameter("status"));
            String pvSetting = request.getParameter("pvsetting");
            String tabSetting = request.getParameter("tabsetting");
            String pageKey = request.getParameter("pagekey");
            int cdnType = Integer.parseInt(request.getParameter("cdntype"));

            WebCacheUrlRule urlRule = new WebCacheUrlRule();
            urlRule.setRuleId(MD5Util.Md5(desRule));

            URL url = new URL(desRule);
            urlRule.setDesHost(url.getHost());

            urlRule.setDesRule(desRule);
            urlRule.setSrcRule(srcRule);
            urlRule.setSrcType(WebCacheSrcType.getByCode(srcType));
            urlRule.setClientType(WebCacheClientType.getByCode(clientType));
            urlRule.setStatus(IntStatus.getByCode(status));
            urlRule.setPageKey(pageKey);
            urlRule.setCdnType(WebCacheCdnType.getByCode(cdnType));

            WebCacheGlobalSetting globalSetting = new WebCacheGlobalSetting();
            globalSetting.setPvSetting(pvSetting);
            globalSetting.setPcWapTabSetting(tabSetting);
            urlRule.setGlobalSetting(globalSetting);

            if (urlRuleService.updateWebCacheUrlRule(null, urlRule, ruleId) > 0) {
                urlRuleCache.delDesRuleSrcRule(oldSrcRule);
                urlRuleCache.delSrcRuleByDesRule(oldDesRule);
                if(IntStatus.USED.getCode() == status){
                    urlRuleCache.putUrlRules(desRule, srcRule);
                    urlRuleCache.putUrlRuleMemCache(desRule, urlRule);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/ac/urlrule/list.do");
    }

    @RequestMapping("/delete.do")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {
        try {
            String ruleId = request.getParameter("ruleid");

            String oldDesRule = request.getParameter("olddesrule");
            String oldSrcRule = request.getParameter("oldsrcrule");

            if (urlRuleService.deleteWebCacheUrlRule(null, ruleId) > 0) {
                urlRuleCache.delDesRuleSrcRule(oldSrcRule);
                urlRuleCache.delSrcRuleByDesRule(oldDesRule);
                urlRuleCache.removeUrlRuleMemCache(oldDesRule);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/ac/urlrule/list.do");
    }
}
