package com.enjoyf.cms.controller;

import com.aliyun.api.cdn.cdn20141111.response.RefreshObjectCachesResponse;
import com.enjoyf.cms.service.CacheService;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.cdn.AliYunCDNRefresh;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhimingli on 2015/7/28.
 */

@Controller
@RequestMapping("/refresh")
public class CmsRefreshApiController {

    private static CacheService cacheService = new CacheService();


    //www主站调用,刷新cmsimage缓存.
    @ResponseBody
    @RequestMapping("/cleanCache.do")
    public String cleanCache(HttpServletRequest request) throws Exception {
        JSONObject retunObj = new JSONObject();
        String clearpage = request.getParameter("clearpage");
        if (StringUtil.isEmpty(clearpage)) {
            cacheService.createCleanCache();

            clearpage = "http://joyme.youku.com/";
            RefreshObjectCachesResponse rocr = AliYunCDNRefresh.refreshCDN(clearpage, AliYunCDNRefresh.CDN_TYPE_DIRECTORY);
            LogService.infoSystemLog("##########End to cleancdn clearpage is empty##########" + rocr.getRefreshTaskId() + ",cdnurl=" + clearpage, true);

        } else {

            cacheService.createCleanPageCache(clearpage);
            RefreshObjectCachesResponse rocr = AliYunCDNRefresh.refreshCDN(clearpage, AliYunCDNRefresh.CDN_TYPE_DIRECTORY);
            LogService.infoSystemLog("##########End to cleancdn##########" + rocr.getRefreshTaskId() + ",clearpage=" + clearpage, true);
        }
        retunObj.put("rs", 1);
        retunObj.put("msg", "success");
        return "cleanCache([" + retunObj.toString() + "])";
    }
}
