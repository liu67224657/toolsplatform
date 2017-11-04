package com.enjoyf.webcache.controller;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.MD5Util;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.cdn.CdnRefreshFactory;
import com.enjoyf.webcache.bean.PageStat;
import com.enjoyf.webcache.bean.WebCacheSrcType;
import com.enjoyf.webcache.bean.WebCacheUrlRule;
import com.enjoyf.webcache.container.PropertiesContainer;
import com.enjoyf.webcache.service.CacheService;
import com.enjoyf.webcache.service.PageStatService;
import com.enjoyf.webcache.service.WebCacheUrlRuleService;
import com.enjoyf.webcache.util.WebCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.Map;

/**
 * 主站评论数的上报接口，发表评论时异步调用。
 * Created by zhimingli on 2015/8/3.
 */
@Controller
@RequestMapping(value = "/comment/report")
public class CommentReportController {

    private static PageStatService pageStatService = new PageStatService();

    @ResponseBody
    @RequestMapping("/reply.do")
    public String refreshIndex(HttpServletRequest request) throws Exception {
        try {
            String pageId = request.getParameter("pageid");
            String pageType = request.getParameter("pagetype");
            String incNum = request.getParameter("incnum");
            if(StringUtil.isEmpty(pageId) || StringUtil.isEmpty(pageType) || StringUtil.isEmpty(incNum)){
                return "param.empty";
            }
            WebCacheSrcType webCacheSrcType = WebCacheSrcType.getByCode(Integer.valueOf(pageType));
            String statId = PageStat.buildStatId(String.valueOf(pageId), webCacheSrcType);
            PageStat pageStat = pageStatService.getPageStatById(null, statId);
            if (pageStat == null) {
                pageStat = new PageStat();
                pageStat.setStatId(statId);
                pageStat.setPageId(pageId);
                pageStat.setPageType(webCacheSrcType);
                pageStat.setPcPv(0);
                pageStat.setmPv(0);
                pageStat.setWanbaPv(0);
                pageStat.setPvSum(0);
                pageStat.setReplySum(Integer.valueOf(incNum));
                pageStatService.insertPageStat(null, pageStat);
            } else {
                PageStat incStat = new PageStat();
                incStat.setReplySum(Integer.valueOf(incNum));
                incStat.setStatId(statId);
                incStat.setPageId(pageId);
                incStat.setPageType(webCacheSrcType);
                pageStatService.increasePageStat(null, incStat);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "system.error";
        }
        return "success";
    }
}


