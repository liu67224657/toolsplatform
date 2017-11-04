package com.enjoyf.webcache.controller;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.MD5Util;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.cdn.CdnRefreshFactory;
import com.enjoyf.webcache.bean.WebCacheUrlRule;
import com.enjoyf.webcache.container.PropertiesContainer;
import com.enjoyf.webcache.service.CacheService;
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
 * Created by zhimingli on 2015/8/3.
 */
@Controller
@RequestMapping(value = "/refresh")
public class RefreshIndexController {
    private static CacheService cacheService = new CacheService();
    private static WebCacheUrlRuleService webCacheUrlRuleService = new WebCacheUrlRuleService();
    private static Logger logger = LoggerFactory.getLogger(RefreshIndexController.class);

    @ResponseBody
    @RequestMapping("/index.do")
    public String refreshIndex(HttpServletRequest request) throws Exception {
        String indexURL = "http://www."+ PropertiesContainer.getInstance().getJoymeDomain()+"/";
        logger.info("================= refresh index :"+indexURL);
        cacheService.createCleanPageCache(indexURL,PropertiesContainer.CLEARPAGE_TYPE_FILE);
        //刷新阿里CDN
        clearCDN(indexURL);

        String mIndexURL = "http://m."+ PropertiesContainer.getInstance().getJoymeDomain()+"/";
        logger.info("================= refresh index :"+mIndexURL);
        cacheService.createCleanPageCache(mIndexURL,PropertiesContainer.CLEARPAGE_TYPE_FILE);
        //刷新阿里CDN
        clearCDN(mIndexURL);

        return "success";
    }

    private void clearCDN(String url) throws MalformedURLException, JoymeServiceException, JoymeDBException {
        Map<String, String> map = WebCacheUtil.genSrcRule(url);
        if (map != null) {
            String desRule = map.get("desRule");
            // 匹配到 的 rule
            WebCacheUrlRule webCacheUrlRule = webCacheUrlRuleService.getWebCacheUrlRule(null, MD5Util.Md5(desRule));
            String cdnName = webCacheUrlRule.getCdnType().getDesc();
            CdnRefreshFactory.getFactory(cdnName).clearCDN(url, null);
        }
    }

}


