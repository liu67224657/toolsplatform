package com.enjoyf.cms.controller;

import com.enjoyf.cms.bean.IntRemoveStatus;
import com.enjoyf.cms.bean.Sitemap;
import com.enjoyf.cms.bean.SitemapLog;
import com.enjoyf.cms.bean.SitemapOutRule;
import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.cms.quartz.SiteMapJob;
import com.enjoyf.cms.service.SitemapFetcherService;
import com.enjoyf.cms.service.SitemapService;
import com.enjoyf.framework.redis.RedisManager;
import com.enjoyf.util.MD5Util;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;
import com.enjoyf.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by zhitaoshi on 2015/8/21.
 */
@Controller
@RequestMapping
public class SitemapController {

    private static final String JOYME_SITEMAP_HAS_PUSH_URLS = "joyme_sitemap_has_push_urls_";

    private static List<IntRemoveStatus> statusList = new ArrayList<IntRemoveStatus>();

    static {
        statusList.add(IntRemoveStatus.USED);
        statusList.add(IntRemoveStatus.REMOVE);
    }

    private static SitemapService service = new SitemapService();
    private static final int PAGE_SIZE = 20;
    private static final int LOG_PAGE_SIZE = 100;

    @RequestMapping(value = "/ac/sitemap/list.do")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(value = "p", required = false, defaultValue = "1") String p) {
        Map map = new HashMap();
        try {
            map.put("statusList", statusList);
            map.put("evnDomain", PropertiesContainer.getEnvDomain());

            request.setCharacterEncoding("UTF-8");
            String qdkey = request.getParameter("qdkey");
            String qstatus = request.getParameter("qstatus");
            map.put("qdkey", qdkey);
            map.put("qstatus", qstatus);

            int cp = Integer.valueOf(p);
            Pagination pagination = new Pagination(PAGE_SIZE * cp, cp, PAGE_SIZE);
            PageRows<Sitemap> pageRows = service.querySitemapByPage(qdkey, StringUtil.isEmpty(qstatus) ? null : Integer.valueOf(qstatus), pagination);
            if (pageRows != null) {
                map.put("list", pageRows.getRows());
                map.put("page", pageRows.getPage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/ac/sitemap/sitemaplist", map);
    }

    @RequestMapping(value = "/ac/sitemap/createpage.do")
    public ModelAndView createPage(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        String qdkey = request.getParameter("qdkey");
        String qstatus = request.getParameter("qstatus");
        map.put("qdkey", qdkey);
        map.put("qstatus", qstatus);
        map.put("evnDomain", PropertiesContainer.getEnvDomain());

        map.put("ruleList", SitemapOutRule.getAll());
        return new ModelAndView("/ac/sitemap/createsitemap", map);
    }

    @RequestMapping(value = "/ac/sitemap/create.do")
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) {
        String qdkey = "";
        String qstatus = "";
        try {
            request.setCharacterEncoding("UTF-8");
            qdkey = request.getParameter("qdkey");
            qstatus = request.getParameter("qstatus");

            String domainKey = request.getParameter("domainkey");
            String contextPath = request.getParameter("contextpath");
            String ruleCode = request.getParameter("rulecode");
            String expDesc = request.getParameter("expdesc");

            Sitemap sitemap = new Sitemap();
            sitemap.setDomainKey(domainKey);
            sitemap.setContextPath(contextPath);
            sitemap.setSitemapId(Sitemap.buildSitemapId(domainKey, contextPath));

            SitemapOutRule outRule = SitemapOutRule.getByCode(Integer.valueOf(ruleCode));
            sitemap.setOutRule(outRule);
            String sitemapUrl = SitemapFetcherService.buildSitemapUrl(domainKey, contextPath);
            String mappingUrl = SitemapFetcherService.buildMappingUrl(domainKey, contextPath, outRule);
            sitemap.setSitemapUrl(sitemapUrl);
            sitemap.setMappingUrl(mappingUrl);


            sitemap.setExpDesc(expDesc);
//            sitemap.setModifyDate(new Date());
            sitemap.setRemoveStatus(IntRemoveStatus.USED);

            sitemap = service.insertJoymeSitemap(sitemap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/ac/sitemap/list.do?qdkey=" + qdkey + "&qstatus=" + qstatus);
    }

    @RequestMapping(value = "/ac/sitemap/modifypage.do")
    public ModelAndView modifyPage(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        try {
            request.setCharacterEncoding("UTF-8");
            String qdkey = request.getParameter("qdkey");
            String qstatus = request.getParameter("qstatus");
            map.put("qdkey", qdkey);
            map.put("qstatus", qstatus);
            map.put("evnDomain", PropertiesContainer.getEnvDomain());

            String sitemapId = request.getParameter("sid");
            Sitemap sitemap = service.getJoymeSitemap(sitemapId);
            map.put("sitemap", sitemap);
            map.put("ruleList", SitemapOutRule.getAll());
            map.put("statusList", statusList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/ac/sitemap/modifysitemap", map);
    }

    @RequestMapping(value = "/ac/sitemap/modify.do")
    public ModelAndView modify(HttpServletRequest request, HttpServletResponse response) {
        String qdkey = "";
        String qstatus = "";
        try {
            request.setCharacterEncoding("UTF-8");
            qdkey = request.getParameter("qdkey");
            qstatus = request.getParameter("qstatus");

            String sitemapId = request.getParameter("sid");
            String domainKey = request.getParameter("domainkey");
            String contextPath = request.getParameter("contextpath");
            String ruleCode = request.getParameter("rulecode");
            String expDesc = request.getParameter("expdesc");
            String status = request.getParameter("status");

            Sitemap sitemap = new Sitemap();
            sitemap.setDomainKey(domainKey);
            sitemap.setContextPath(contextPath);
            sitemap.setSitemapId(Sitemap.buildSitemapId(domainKey, contextPath));

            SitemapOutRule outRule = SitemapOutRule.getByCode(Integer.valueOf(ruleCode));
            sitemap.setOutRule(outRule);
            String sitemapUrl = SitemapFetcherService.buildSitemapUrl(domainKey, contextPath);
            String mappingUrl = SitemapFetcherService.buildMappingUrl(domainKey, contextPath, outRule);
            sitemap.setSitemapUrl(sitemapUrl);
            sitemap.setMappingUrl(mappingUrl);

            sitemap.setExpDesc(expDesc);
//            sitemap.setModifyDate(new Date());
            sitemap.setRemoveStatus(IntRemoveStatus.getByCode(Integer.valueOf(status)));

            service.updateJoymeSitemap(sitemapId, sitemap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/ac/sitemap/list.do?qdkey=" + qdkey + "&qstatus=" + qstatus);
    }

    @RequestMapping(value = "/ac/sitemap/delete.do")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {
        String qdkey = "";
        String qstatus = "";
        try {
            request.setCharacterEncoding("UTF-8");
            qdkey = request.getParameter("qdkey");
            qstatus = request.getParameter("qstatus");

            String sitemapId = request.getParameter("sid");
            service.deleteJoymeSitemap(sitemapId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/ac/sitemap/list.do?qdkey=" + qdkey + "&qstatus=" + qstatus);
    }

    @RequestMapping(value = "/ac/sitemap/cleancache.do")
    public ModelAndView cleanCache(HttpServletRequest request, HttpServletResponse response) {
        try {
            SiteMapJob siteMapJob = new SiteMapJob();
            siteMapJob.execute(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/ac/sitemap/list.do");
    }

    @ResponseBody
    @RequestMapping(value = "/sitemap/insertlog.do")
    public String insertLog(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(value = "key", required = false) String key,
                                  @RequestParam(value = "pushurl", required = false) String pushUrl,
                                  @RequestParam(value = "result", required = false) String result) {
        try {
            RedisManager redisManager = PropertiesContainer.getRedisManager();
            key = key + ".joyme.com";

            JSONObject jsonObject = JSONObject.fromObject(result);
            SitemapLog sitemapLog = new SitemapLog();
            sitemapLog.setPushDate(new Date());
            sitemapLog.setPageUrl(pushUrl);
            if (jsonObject.containsKey("not_same_site")) {
                sitemapLog.setStatusDesc("不是" + key + "的url而未处理");
                sitemapLog.setStatus(IntRemoveStatus.ERROR);
            }else if (jsonObject.containsKey("not_valid")) {
                sitemapLog.setStatusDesc("不合法的url");
                sitemapLog.setStatus(IntRemoveStatus.ERROR);
            }else {
                sitemapLog.setStatusDesc("推送成功");
                sitemapLog.setStatus(IntRemoveStatus.USED);
            }
            sitemapLog.setDomainKey(key);
            sitemapLog.setLogId(MD5Util.Md5(pushUrl));

            sitemapLog = service.insertJoymeSitemapLog(sitemapLog);
            if (sitemapLog != null) {
                //写入 db 就去掉 缓存
                redisManager.srem(JOYME_SITEMAP_HAS_PUSH_URLS + key, pushUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/ac/sitemap/loglist.do")
    public ModelAndView logList(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "p", required = false, defaultValue = "1") String p) {
        Map map = new HashMap();
        try {
            map.put("evnDomain", PropertiesContainer.getEnvDomain());

            request.setCharacterEncoding("UTF-8");
            String qdkey = request.getParameter("qdkey");
            map.put("qdkey", qdkey);

            if(!StringUtil.isEmpty(qdkey)){
                qdkey += ("."+PropertiesContainer.getEnvDomain());
            }

            int cp = Integer.valueOf(p);
            Pagination pagination = new Pagination(LOG_PAGE_SIZE * cp, cp, LOG_PAGE_SIZE);
            PageRows<SitemapLog> pageRows = service.querySitemapLogByPage(qdkey, pagination);
            if (pageRows != null) {
                map.put("list", pageRows.getRows());
                map.put("page", pageRows.getPage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/ac/sitemap/sitemaploglist", map);
    }
}
