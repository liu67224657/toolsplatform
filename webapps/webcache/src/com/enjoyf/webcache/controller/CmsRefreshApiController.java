package com.enjoyf.webcache.controller;

import com.enjoyf.webcache.service.CacheService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String cleanCache() throws Exception {
        JSONObject retunObj = new JSONObject();
        cacheService.createCleanCache();
        retunObj.put("rs", 1);
        retunObj.put("msg", "success");
        return "cleanCache([" + retunObj.toString() + "])";
    }

    /**
     * 测试500页面
     *
     * @param id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/500.do")
    public String refreshIndex(@RequestParam(value = "id", required = false) Integer id) throws Exception {
        throw new Exception();
    }

}
