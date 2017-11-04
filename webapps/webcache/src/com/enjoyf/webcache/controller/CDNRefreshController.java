package com.enjoyf.webcache.controller;

import com.aliyun.api.cdn.cdn20141111.response.RefreshObjectCachesResponse;
import com.enjoyf.util.cdn.AliYunCDNRefresh;
import com.enjoyf.util.cdn.CdnRefreshFactory;
import com.enjoyf.webcache.container.PropertiesContainer;
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
 * 这个借口不需要了
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 15/6/10
 * Description:
 */
@Controller
public class CDNRefreshController {

    private Pattern pattern = Pattern.compile(".*\\.(shtml|html|htm|png|gif|jpeg|jpg|bmp|swf|css|ico|apk|js)$");

    @RequestMapping("/ac/refreshcdn.do")
    public ModelAndView admin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = new HashMap();

        String url = request.getParameter("url");
        url = url.replace("http://", "");

        String cdnName = PropertiesContainer.getInstance().getCdnName();
        CdnRefreshFactory.getFactory(cdnName).clearCDN(url, map);
        return new ModelAndView("/ac/refresh_success", map);
    }
}
