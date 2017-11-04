package com.enjoyf.cms.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enjoyf.util.MD5Util;
import com.enjoyf.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.enjoyf.cms.bean.SeoConfig;
import com.enjoyf.cms.service.CacheService;
import com.enjoyf.cms.service.SeoConfigService;
import com.mysql.jdbc.StringUtils;

@Controller
public class OpearController {
    private static SeoConfigService seoConfigService = new SeoConfigService();
    private static CacheService cacheService = new CacheService();

    @RequestMapping("/ac/seo.do")
    public ModelAndView seo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List seoList = seoConfigService.queryAllSeoConfigList(null);
        request.setAttribute("seoList", seoList);
        return new ModelAndView("/ac/seo");
    }

    @RequestMapping("/ac/insertSeoConfig.do")
    public ModelAndView insertseo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        String seoTransferPath = request.getParameter("seoTransferPath");
        String seoOriginalPath = request.getParameter("seoOriginalPath");
        if (StringUtils.isNullOrEmpty(seoOriginalPath) || StringUtils.isNullOrEmpty(seoOriginalPath))
            return new ModelAndView("/ac/seo_action");
        //.xml .txt .shtml .html .htm
        if(!seoTransferPath.endsWith("/")){
            seoTransferPath += "/";
        }
        if(!seoOriginalPath.endsWith("/")){
            seoOriginalPath += "/";
        }
        SeoConfig config = new SeoConfig();
        config.setCreateTime(new Timestamp(System.currentTimeMillis()));
        config.setSeoTransferPath(seoTransferPath);
        config.setSeoOriginalPath(seoOriginalPath);
        config.setSeoId(MD5Util.Md5(seoTransferPath));
        //已经有的
        SeoConfig hasConfig = seoConfigService.querySeoConfigbyId(null, MD5Util.Md5(seoTransferPath));
        if(hasConfig != null){
            map.put("errorMsg", "已经存在，不能重复添加");
        }else {
            //cache
            seoConfigService.insertSeoConfig(null, config);
            seoConfigService.createRefresh();
        }
        return new ModelAndView("/ac/seo_action", map);
    }

    @RequestMapping("/ac/updateSeoConfig.do")
    public ModelAndView updateseo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        String seoTransferPath = request.getParameter("seoTransferPath");
        String seoOriginalPath = request.getParameter("seoOriginalPath");
        if (StringUtil.isEmpty(seoOriginalPath) || StringUtil.isEmpty(seoOriginalPath))
            return new ModelAndView("/ac/seo_action");
        if(!seoTransferPath.endsWith("/")){
            seoTransferPath += "/";
        }
        if(!seoOriginalPath.endsWith("/")){
            seoOriginalPath += "/";
        }
        String seoId = request.getParameter("seoId");

        SeoConfig config = new SeoConfig();
        config.setSeoId(MD5Util.Md5(seoTransferPath));
        config.setSeoTransferPath(seoTransferPath);
        config.setSeoOriginalPath(seoOriginalPath);
        SeoConfig hasConfig = seoConfigService.querySeoConfigbyId(null, MD5Util.Md5(seoTransferPath));
        if(hasConfig != null && !hasConfig.getSeoId().equals(seoId)){
            map.put("errorMsg", "已经存在，不能重复添加");
        }else {
            seoConfigService.updateSeoConfig(null, seoId, config);
            seoConfigService.createRefresh();
        }
        return new ModelAndView("/ac/seo_action");
    }

    @RequestMapping("/ac/deleteSeoConfig.do")
    public ModelAndView deleteseo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String seoId = request.getParameter("seoId");
        //cache
        seoConfigService.deleteSeoConfig(null, seoId);
        seoConfigService.createRefresh();
        return new ModelAndView("/ac/seo_action");
    }

    @RequestMapping("/ac/cleanCache.do")
    public ModelAndView cleanCache(HttpServletRequest request, HttpServletResponse response) throws Exception {
        cacheService.createCleanCache();
        return new ModelAndView("/ac/cleancache_action");
    }
}
