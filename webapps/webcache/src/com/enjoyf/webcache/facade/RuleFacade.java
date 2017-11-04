package com.enjoyf.webcache.facade;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.webcache.bean.IntStatus;
import com.enjoyf.webcache.bean.WebCacheUrlRule;
import com.enjoyf.webcache.cache.UrlRuleCache;
import com.enjoyf.webcache.service.WebCacheUrlRuleService;

import java.util.*;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 15/8/26
 * Description:
 */
public class RuleFacade {

    private WebCacheUrlRuleService ruleService = new WebCacheUrlRuleService();
    private UrlRuleCache urlRuleCache = new UrlRuleCache();

    public void loadRule() throws JoymeServiceException, JoymeDBException {
        List<WebCacheUrlRule> urlRuleList = ruleService.queryWebCacheUrlRule(null, IntStatus.USED.getCode(), null, null, null);
        for (WebCacheUrlRule urlRule : urlRuleList) {
            urlRuleCache.putUrlRules(urlRule.getDesRule(), urlRule.getSrcRule());
            urlRuleCache.putUrlRuleMemCache(urlRule.getDesRule(), urlRule);
        }
    }


//    public WebCacheUrlRule getRuleByRequest(String requestURL) throws MalformedURLException {
//
//
//
//
//        URL url = new URL(requestURL);
//        String host = url.getHost();
//
//        Set<WebCacheUrlRule> rules = urlRuleCache.getUrlRules(host);
//        for (WebCacheUrlRule urlRule : rules) {
//            if (url.getPath().startsWith(urlRule.getDestPath())) {
//                return urlRule;
//            }
//        }
//
//        return null;
//    }
//
//    public WebCacheUrlRule getRuleById(String reuleId) throws MalformedURLException {
//
//        return urlRuleCache.getUrlRule(reuleId);
//    }
}
