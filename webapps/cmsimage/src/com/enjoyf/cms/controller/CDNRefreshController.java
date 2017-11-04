package com.enjoyf.cms.controller;

import com.aliyun.api.cdn.cdn20141111.response.RefreshObjectCachesResponse;
import com.enjoyf.util.cdn.AliYunCDNRefresh;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 15/6/10
 * Description:
 */
@Controller
public class CDNRefreshController {

    private Pattern pattern = Pattern.compile(".*\\.(shtml|html|htm|png|gif|jpeg|jpg|bmp|swf|css|ico|apk|js)$");

    @RequestMapping("/ac/refreshcdn.do")
    public ModelAndView admin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = request.getParameter("url");
//        String type = request.getParameter("type");

        url = url.replace("http://", "");

        Matcher matcher = pattern.matcher(url);

        //File
        String type = AliYunCDNRefresh.CDN_TYPE_DIRECTORY;
        if (matcher.matches()) {
            type = AliYunCDNRefresh.CDN_TYPE_FILE;
        } else {
            if (!url.endsWith("/")) {
                url = url + "/";
            }
        }
        RefreshObjectCachesResponse rocr = AliYunCDNRefresh.refreshCDN(url, type);

        String taskId = rocr.getRefreshTaskId();

        Map map = new HashMap();
        map.put("taskid", taskId);
        return new ModelAndView("/ac/refresh_success", map);
    }
}
