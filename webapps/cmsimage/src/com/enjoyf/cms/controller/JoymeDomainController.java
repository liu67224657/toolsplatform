package com.enjoyf.cms.controller;

import com.enjoyf.cms.bean.JoymeDomain;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/7/1.
 */
@Controller
@RequestMapping(value = "/ac/joymedomain")
public class JoymeDomainController {

    private static JoymeDomainService server = new JoymeDomainService();
    private static final int PAGE_SIZE = 20;

    @RequestMapping("/list.do")
    public ModelAndView list(@RequestParam(value = "p", required = false, defaultValue = "1") String p,
                             HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        try {
            int cp = Integer.valueOf(p);
            Pagination pagination = new Pagination(PAGE_SIZE * cp, cp, PAGE_SIZE);
            PageRows<JoymeDomain> pageRows = server.queryJoymeDomain(pagination);
            map.put("list", pageRows.getRows());
            map.put("page", pageRows.getPage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/ac/domain/domainlist", map);

    }

    @RequestMapping("/createpage.do")
    public ModelAndView createPage(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/ac/domain/createdomain");
    }

    @RequestMapping("/create.do")
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            String domainName = request.getParameter("domainname");
            String domainDesc = request.getParameter("domaindesc");
            String buyDate = request.getParameter("buydate");
            String expireDate = request.getParameter("expiredate");
            String buyMerchant = request.getParameter("buymerchant");
            String buyUser = request.getParameter("buyuser");

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            JoymeDomain joymeDomain = new JoymeDomain();
            joymeDomain.setDomainDesc(domainDesc);
            joymeDomain.setDomainName(domainName);
            joymeDomain.setModifyUser("");
            if (!StringUtil.isEmpty(buyDate)) {
                joymeDomain.setBuyDate(df.parse(buyDate));
            }
            if (!StringUtil.isEmpty(expireDate)) {
                joymeDomain.setExpireDate(df.parse(expireDate));
            }
            joymeDomain.setBuyMerchant(buyMerchant);
            joymeDomain.setBuyUser(buyUser);
            joymeDomain.setCreateUser("");
            Date date = new Date();
            joymeDomain.setCreateDate(date);
            joymeDomain.setModifyDate(date);
            joymeDomain.setModifyUser("");
            server.insertJoymeDomain(joymeDomain);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/ac/joymedomain/list.do");
    }

    @RequestMapping("/modifypage.do")
    public ModelAndView modifyPage(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        try {
            request.setCharacterEncoding("UTF-8");
            String domainName = request.getParameter("domainname");
            JoymeDomain joymeDomain = server.getJoymeDomain(domainName);
            map.put("joymeDomain", joymeDomain);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/ac/domain/modifydomain", map);
    }

    @RequestMapping("/modify.do")
    public ModelAndView modify(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            String updateName = request.getParameter("updatename");
            String domainName = request.getParameter("domainname");
            String domainDesc = request.getParameter("domaindesc");
            String buyDate = request.getParameter("buydate");
            String expireDate = request.getParameter("expiredate");
            String buyMerchant = request.getParameter("buymerchant");
            String buyUser = request.getParameter("buyuser");

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            JoymeDomain joymeDomain = new JoymeDomain();
            joymeDomain.setDomainDesc(domainDesc);
            joymeDomain.setDomainName(domainName);
            joymeDomain.setModifyUser("");
            if (!StringUtil.isEmpty(buyDate)) {
                joymeDomain.setBuyDate(df.parse(buyDate));
            }
            if (!StringUtil.isEmpty(expireDate)) {
                joymeDomain.setExpireDate(df.parse(expireDate));
            }
            joymeDomain.setBuyMerchant(buyMerchant);
            joymeDomain.setBuyUser(buyUser);
            joymeDomain.setModifyUser("");
            joymeDomain.setModifyDate(new Date());
            server.updateJoymeDomain(updateName, joymeDomain);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/ac/joymedomain/list.do");
    }

    @RequestMapping("/delete.do")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            String updateName = request.getParameter("updatename");
            server.deleteJoymeDomain(updateName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/ac/joymedomain/list.do");
    }
}
