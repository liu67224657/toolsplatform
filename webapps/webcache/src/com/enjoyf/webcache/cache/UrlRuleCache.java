package com.enjoyf.webcache.cache;

import com.enjoyf.util.MD5Util;
import com.enjoyf.webcache.bean.WebCacheUrl;
import com.enjoyf.webcache.bean.WebCacheUrlRule;
import com.enjoyf.webcache.container.PropertiesContainer;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 15/8/26
 * Description:
 */
public class UrlRuleCache {
    private static String KEY_WEB_CACHE = "webcache_";
    private static String KEY_DES_RULES = "desrule_";
    private static String KEY_SRC_RULES = "srcrule_";
    private static String KEY_URL_RULE_MEM = "url_rule_";
    private static String KEY_URL_RULES = "url_rules";

    public void saddUrlRules(WebCacheUrl webCacheUrl){
        PropertiesContainer.getInstance().getRedisManager().sadd(KEY_WEB_CACHE + KEY_URL_RULES, webCacheUrl.toJson());
    }

    public Set<String> smembersUrlRules(){
        return PropertiesContainer.getInstance().getRedisManager().smembers(KEY_WEB_CACHE + KEY_URL_RULES);
    }

    public void putUrlRules(String desRule, String srcRule) {
        PropertiesContainer.getInstance().getRedisManager().set(KEY_WEB_CACHE + KEY_DES_RULES + desRule, srcRule);
        PropertiesContainer.getInstance().getRedisManager().set(KEY_WEB_CACHE + KEY_SRC_RULES + srcRule, desRule);
    }

    public void putUrlRuleMemCache(String desRule, WebCacheUrlRule urlRule){
        PropertiesContainer.getInstance().getMemCachedManager().put(KEY_WEB_CACHE + KEY_URL_RULE_MEM + MD5Util.Md5(desRule), urlRule, 12l*60l*60l);
    }

    public WebCacheUrlRule getUrlRuleMemCache(String desRule){
        Object rule = PropertiesContainer.getInstance().getMemCachedManager().get(KEY_WEB_CACHE + KEY_URL_RULE_MEM + MD5Util.Md5(desRule));
        if(rule == null){
            return null;
        }
        return (WebCacheUrlRule) rule;
    }

    public void removeUrlRuleMemCache(String desRule){
        PropertiesContainer.getInstance().getMemCachedManager().remove(KEY_WEB_CACHE + KEY_URL_RULE_MEM + MD5Util.Md5(desRule));
    }

    public static String getDesRuleBySrcRule(String srcRule) {
        return PropertiesContainer.getInstance().getRedisManager().get(KEY_WEB_CACHE + KEY_SRC_RULES + srcRule);
    }

    public void delDesRuleSrcRule(String srcRule) {
        PropertiesContainer.getInstance().getRedisManager().remove(KEY_WEB_CACHE + KEY_SRC_RULES + srcRule);
    }

    public static String getSrcRuleByDesRule(String desRule) {
        return PropertiesContainer.getInstance().getRedisManager().get(KEY_WEB_CACHE + KEY_DES_RULES + desRule);
    }

    public void delSrcRuleByDesRule(String desRule) {
        PropertiesContainer.getInstance().getRedisManager().remove(KEY_WEB_CACHE + KEY_DES_RULES + desRule);
    }


}
