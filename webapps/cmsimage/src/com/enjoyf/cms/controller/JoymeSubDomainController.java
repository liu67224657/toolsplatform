package com.enjoyf.cms.controller;

import com.enjoyf.cms.bean.JoymeDomain;
import com.enjoyf.cms.bean.JoymeSubDomain;
import com.enjoyf.cms.service.JoymeDomainService;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;
import com.enjoyf.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/7/1.
 */
@Controller
@RequestMapping(value = "/ac/joymedomain/subdomain")
public class JoymeSubDomainController {

    private static JoymeDomainService server = new JoymeDomainService();
    private static final int PAGE_SIZE = 20;

    @RequestMapping("/list.do")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(value = "p", required = false, defaultValue = "1") String p,
                             @RequestParam(value = "maindomain", required = true) String mainDomain,
                             @RequestParam(value = "domainname", required = false) String domainName,
                             @RequestParam(value = "orderby", required = false) String orderBy,
                             @RequestParam(value = "ordertype", required = false) String orderType) {
        Map map = new HashMap();
        map.put("domainName", domainName);
        map.put("orderBy", orderBy);
        map.put("orderType", orderType);
        try {
            int cp = Integer.valueOf(p);
            Pagination pagination = new Pagination(PAGE_SIZE * cp, cp, PAGE_SIZE);

            JoymeDomain joymeDomain = server.getJoymeDomain(mainDomain);
            map.put("joymeDomain", joymeDomain);
            PageRows<JoymeSubDomain> pageRows = server.queryJoymeSubDomain(mainDomain, domainName, orderBy, orderType, pagination);
            map.put("list", pageRows.getRows());
            map.put("page", pageRows.getPage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/ac/domain/subdomainlist", map);

    }

    @RequestMapping("/createpage.do")
    public ModelAndView createPage(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(value = "maindomain", required = true) String mainDomain) {
        Map map = new HashMap();
        map.put("mainDomain", mainDomain);
        return new ModelAndView("/ac/domain/createsubdomain", map);
    }

    @RequestMapping("/create.do")
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) {
        String mainDomain = "";
        try {
            request.setCharacterEncoding("UTF-8");
            mainDomain = request.getParameter("maindomain");
            String domainName = request.getParameter("domainname");
            String indexUrl = request.getParameter("indexurl");
            String department = request.getParameter("usedept");
            String domainDesc = request.getParameter("domaindesc");
            String seoDesc = request.getParameter("seodesc");

            JoymeSubDomain subDomain = new JoymeSubDomain();
            subDomain.setMainDomain(mainDomain);
            subDomain.setDomainName(domainName);
            subDomain.setIndexUrl(indexUrl);
            subDomain.setUseDept(department);
            subDomain.setSeoDesc(seoDesc);
            subDomain.setDomainDesc(domainDesc);
            subDomain.setCreateDate(new Date());
            subDomain.setModifyDate(new Date());
            server.insertJoymeSubDomain(subDomain);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/ac/joymedomain/subdomain/list.do?maindomain=" + mainDomain);
    }

    @RequestMapping("/modifypage.do")
    public ModelAndView modifyPage(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(value = "maindomain", required = true) String mainDomain) {
        Map map = new HashMap();
        try {
            map.put("mainDomain", mainDomain);

            String domainName = request.getParameter("domainname");
            JoymeSubDomain subDomain = server.getJoymeSubDomain(domainName);
            map.put("subDomain", subDomain);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/ac/domain/modifysubdomain", map);
    }

    @RequestMapping("/modify.do")
    public ModelAndView modify(HttpServletRequest request, HttpServletResponse response) {
        String mainDomain = "";
        try {
            request.setCharacterEncoding("UTF-8");
            mainDomain = request.getParameter("maindomain");
            String domainName = request.getParameter("domainname");
            String indexUrl = request.getParameter("indexurl");
            String department = request.getParameter("usedept");
            String domainDesc = request.getParameter("domaindesc");
            String seoDesc = request.getParameter("seodesc");
            String removeStatus = request.getParameter("removestatus");

            JoymeSubDomain subDomain = new JoymeSubDomain();
            subDomain.setDomainName(domainName);
            subDomain.setIndexUrl(indexUrl);
            subDomain.setUseDept(department);
            subDomain.setSeoDesc(seoDesc);
            subDomain.setDomainDesc(domainDesc);
            subDomain.setModifyDate(new Date());
            subDomain.setRemoveStatus(Integer.valueOf(removeStatus));
            server.updateJoymeSubDomain(domainName, subDomain);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/ac/joymedomain/subdomain/list.do?maindomain=" + mainDomain);
    }

    @RequestMapping("/remove.do")
    public ModelAndView remove(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value = "maindomain", required = true) String mainDomain) {
        try {
            String updateName = request.getParameter("domainname");
            String status = request.getParameter("removestatus");
            if (!StringUtil.isEmpty(status)) {
                server.removeJoymeSubDomain(updateName, Integer.valueOf(status));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/ac/joymedomain/subdomain/list.do?maindomain=" + mainDomain);
    }

    @RequestMapping("/delete.do")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value = "maindomain", required = true) String mainDomain) {
        try {
            String updateName = request.getParameter("domainname");
            server.deleteJoymeSubDomain(updateName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/ac/joymedomain/subdomain/list.do?maindomain=" + mainDomain);
    }
}
