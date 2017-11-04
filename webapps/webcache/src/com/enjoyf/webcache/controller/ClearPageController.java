package com.enjoyf.webcache.controller;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.MD5Util;
import com.enjoyf.util.cdn.CdnRefreshFactory;
import com.enjoyf.webcache.bean.WebCacheUrlRule;
import com.enjoyf.webcache.container.PropertiesContainer;
import com.enjoyf.webcache.service.CacheService;
import com.enjoyf.util.StringUtil;
import com.enjoyf.webcache.service.WebCacheUrlRuleService;
import com.enjoyf.webcache.util.WebCacheUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.Map;

/**
 * webcache后台的主动刷新功能
 * Created by zhimingli on 2015/8/3.
 */
@Controller
public class ClearPageController {
    private static CacheService cacheService = new CacheService();
    private static WebCacheUrlRuleService webCacheUrlRuleService = new WebCacheUrlRuleService();

    @ResponseBody
    @RequestMapping("/ac/clearpage.do")
    public String clearpage(HttpServletRequest request) throws Exception {
        String clearpage = request.getParameter("clearpage");
        String clearTypeParam = request.getParameter("cleartype");

        int clearType=PropertiesContainer.CLEARPAGE_TYPE_FILE;
        if(!StringUtil.isEmpty(clearTypeParam)){
            clearType=Integer.parseInt(clearTypeParam);
        }

        if (!StringUtil.isEmpty(clearpage)) {
            if (clearpage.equals("http://www." + PropertiesContainer.getInstance().getJoymeDomain())
                    || clearpage.equals("http://www." + PropertiesContainer.getInstance().getJoymeDomain() + "/")
                    || clearpage.equals("http://m." + PropertiesContainer.getInstance().getJoymeDomain())
                    || clearpage.equals("http://m." + PropertiesContainer.getInstance().getJoymeDomain() + "/")) {
                return "网站首页刷新请到tools后台！";
            }

            cacheService.createCleanPageCache(clearpage,clearType);
            //刷新阿里CDN
            clearCDN(clearpage);
        }
        return "刷新成功";
    }

    @ResponseBody
    @RequestMapping("/ac/clearindexpage.do")
    public String clearindexpage(HttpServletRequest request) throws Exception {
        String clearpage = request.getParameter("clearpage");
        if (!StringUtil.isEmpty(clearpage)) {
            cacheService.createCleanPageCache(clearpage,PropertiesContainer.CLEARPAGE_TYPE_FILE);
            //刷新阿里CDN
            clearCDN(clearpage);
        }
        return "刷新成功";
    }

    private void clearCDN(String url) throws MalformedURLException, JoymeServiceException, JoymeDBException {
        //判断配置中是否有配置cdn需要刷新
        Map<String, String> map = WebCacheUtil.genSrcRule(url);
        if (map != null) {
            String desRule = map.get("desRule");
            // 匹配到 的 rule
            WebCacheUrlRule webCacheUrlRule = webCacheUrlRuleService.getWebCacheUrlRule(null, MD5Util.Md5(desRule));
            String cdnName = webCacheUrlRule.getCdnType().getDesc();
            LogService.infoFreshLog("clear cdn:" + cdnName + "," + url, true);
            CdnRefreshFactory.getFactory(cdnName).clearCDN(url, null);
        }
    }

}


