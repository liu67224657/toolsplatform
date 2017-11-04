package com.enjoyf.cms.controller;

import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.cms.filter.URLFilter;
import com.enjoyf.cms.service.CacheService;
import com.enjoyf.cms.service.SyncCacheService;
import com.enjoyf.cms.util.CmsUrlUtil;
import com.enjoyf.cms.util.CmsimageFilePathUtil;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.cdn.AliYunCDNRefresh;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhimingli on 2015/8/3.
 */
@Controller
public class ClearPageController {
    private static CacheService cacheService = new CacheService();

    private static Pattern pattern = Pattern.compile(".*\\.(shtml|html)$");

    @ResponseBody
    @RequestMapping("/ac/clearpage.do")
    public String clearpage(HttpServletRequest request) throws Exception {
        String clearpage = request.getParameter("clearpage");
        if (!StringUtil.isEmpty(clearpage)) {
            cacheService.createCleanPageCache(clearpage);

            //刷新阿里CDN
            clearCDN(clearpage);
        }
        return "success";
    }


    private void clearCDN(String url) {
        String type = AliYunCDNRefresh.CDN_TYPE_DIRECTORY;
        //刷新CDN
        try {
            url = url.substring(0, url.lastIndexOf("/")) + "/";
            AliYunCDNRefresh.refreshCDN(url, type);
        } catch (Exception e) {
            LogService.errorSystemLog("Error when do clearCDN,url=" + url, e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClearPageController syncCacheService = new ClearPageController();
        syncCacheService.clearCDN("http://hs.joyme.com/index.html");
        syncCacheService.clearCDN("http://hs.joyme.com/");

        String s = "http://joyme.youku.com/discoveryvideo/xiaotu/2015/0819/87426.html?sid=YKXFX1";
        s = s.replace("joyme.youku.com", "youku.joyme.com");
        int indexof = s.lastIndexOf("/");
        System.out.println(s.substring(0, indexof) + "/");
    }

}


