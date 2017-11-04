package com.enjoyf.mcms.controller;

import com.enjoyf.mcms.facade.ArchivePVFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class StatController {
    @RequestMapping("/marticle/ac/querycount.do")
    public ModelAndView addTopic(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ArchivePVFacade facade = new ArchivePVFacade();
        String date = request.getParameter("date");
//        List list = facade.getCount(date);
//        request.setAttribute("countList", list);
        return new ModelAndView("/marticle/ac/count/result");
    }
}
