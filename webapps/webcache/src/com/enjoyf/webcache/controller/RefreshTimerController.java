package com.enjoyf.webcache.controller;

import com.enjoyf.util.StringUtil;
import com.enjoyf.util.cdn.CdnRefreshFactory;
import com.enjoyf.webcache.bean.RefreshTimerUrl;
import com.enjoyf.webcache.container.PropertiesContainer;
import com.enjoyf.webcache.service.RefreshTimerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * webcache后台的定时刷新的增删改查维护工具
 *
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 15/6/10
 * Description:
 */
@Controller
public class RefreshTimerController {

    private static RefreshTimerService refreshTimerService = new RefreshTimerService();

    @RequestMapping("/ac/refreshtimer/urllist.do")
    public ModelAndView urlList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<RefreshTimerUrl> list = refreshTimerService.queryRefreshUrl(null);
        map.put("list", list);
        return new ModelAndView("/ac/refresh_url", map);
    }

    @RequestMapping("/ac/refreshtimer/addurl.do")
    public ModelAndView addUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = request.getParameter("refreshurl");
        String type = request.getParameter("refreshtype");
        if(StringUtil.isEmpty(url) || StringUtil.isEmpty(type) || !url.startsWith("http://")){
            return new ModelAndView("redirect:/ac/refreshtimer/urllist.do");
        }
        RefreshTimerUrl refreshTimerUrl = new RefreshTimerUrl();
        refreshTimerUrl.setUrl(url);
        refreshTimerUrl.setType(Integer.valueOf(type));
        refreshTimerService.createRefreshUrl(null, refreshTimerUrl);
        return new ModelAndView("redirect:/ac/refreshtimer/urllist.do");
    }

    @RequestMapping("/ac/refreshtimer/delurl.do")
    public ModelAndView delUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String urlId = request.getParameter("urlid");
        if(StringUtil.isEmpty(urlId)){
            return new ModelAndView("redirect:/ac/refreshtimer/urllist.do");
        }
        refreshTimerService.delRefreshUrl(null, urlId);
        return new ModelAndView("redirect:/ac/refreshtimer/urllist.do");
    }
}
