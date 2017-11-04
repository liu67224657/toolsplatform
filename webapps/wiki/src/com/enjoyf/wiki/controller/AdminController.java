package com.enjoyf.wiki.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.enjoyf.adminuser.bean.JoymeUser;
import com.enjoyf.adminuser.bean.JoymeUserProperties;
import com.enjoyf.adminuser.service.JoymeAdminService;

@Controller
public class AdminController {

    private static JoymeAdminService service = new JoymeAdminService();

    @RequestMapping("/wiki/ac/login.do")
    public ModelAndView admin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        JoymeUser user = service.queryUserbyUserName(userName, password);
        if (user != null) {
            List list = user.getPropertiesList();
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                JoymeUserProperties prop = (JoymeUserProperties) iterator.next();
                if (prop.getUserPropertiesKey().equals("wiki_operator") && prop.getUserPropertiesValue().equals("1")) {
                    request.getSession().setAttribute("adminUser", user);
                    return new ModelAndView("/wiki/ac/main");
                }
            }
        }
        request.setAttribute("failed", true);
        return new ModelAndView("/wiki/ac/login");
    }

    @RequestMapping("/wiki/ac/updatePassword.do")
    public ModelAndView updatePassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        JoymeUser user = (JoymeUser) request.getSession().getAttribute("adminUser");
        boolean isSuccess = service.updateUserPassword(user.getUserName(), oldPassword, newPassword);
        request.setAttribute("result", isSuccess);
        return new ModelAndView("/wiki/ac/updatePassword");
    }


    /**
     * 测试500页面
     *
     * @param id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/wiki/ac/500.do")
    public String refreshIndex(@RequestParam(value = "id", required = false) Integer id) throws Exception {
        throw new Exception();
    }
}
