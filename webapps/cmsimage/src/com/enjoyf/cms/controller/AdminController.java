package com.enjoyf.cms.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.enjoyf.adminuser.bean.JoymeUser;
import com.enjoyf.adminuser.bean.JoymeUserProperties;
import com.enjoyf.adminuser.service.JoymeAdminService;

@Controller
public class AdminController {
    private static JoymeAdminService service = new JoymeAdminService();

    @RequestMapping("/ac/login.do")
    public ModelAndView admin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        JoymeUser user = service.queryUserbyUserName(userName, password);
        if (user != null) {
            List list = user.getPropertiesList();
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                JoymeUserProperties prop = (JoymeUserProperties) iterator.next();
                if (prop.getUserPropertiesKey().equals("cms_operator") && prop.getUserPropertiesValue().equals("1")) {
                    request.getSession().setAttribute("adminUser", user);
                    return new ModelAndView("/ac/main");
                }
            }
        }
        request.setAttribute("failed", true);
        return new ModelAndView("/ac/login");
    }
}
