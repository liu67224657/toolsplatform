package com.enjoyf.cms.bean;

import net.sf.json.JSONObject;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/7/24.
 */
public class SitemapOutRule implements Serializable {

    private static Map<Integer, SitemapOutRule> map = new LinkedHashMap<Integer, SitemapOutRule>();

    public static SitemapOutRule DEFAULT = new SitemapOutRule(0, "", "不需要转换");
    public static SitemapOutRule ARTICLE = new SitemapOutRule(1, "article.joyme.com/article", "article二级目录");
    public static SitemapOutRule ARTICLE_VIP = new SitemapOutRule(2, "article.joyme.com/article/vip", "article二级域名");
    public static SitemapOutRule WIKI = new SitemapOutRule(3, "wiki.joyme.com", "wiki域名输出");
    public static SitemapOutRule M_WIKI = new SitemapOutRule(4, "m.wiki.joyme.com", "mwiki域名输出");
    public static SitemapOutRule M_CMS = new SitemapOutRule(5, "article.joyme.com/article", "m站CMS二级目录");

    private int code;
    private String rule;
    private String desc;

    public SitemapOutRule(int c, String r, String d) {
        this.code = c;
        this.rule = r;
        this.desc = d;
        map.put(c, this);
    }

    public int getCode() {
        return code;
    }

    public String getRule() {
        return rule;
    }

    public String getDesc() {
        return desc;
    }

    public static SitemapOutRule getByCode(int c) {
        return map.get(c);
    }

    public static Collection<SitemapOutRule> getAll() {
        return map.values();
    }

    @Override
    public int hashCode() {
        return code;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (code != ((SitemapOutRule) o).code) return false;
        return true;
    }

}
