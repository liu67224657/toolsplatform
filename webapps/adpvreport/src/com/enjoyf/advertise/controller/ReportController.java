package com.enjoyf.advertise.controller;

import com.enjoyf.advertise.weblogic.ReportWebLogic;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  14-3-13 下午2:59
 * Description:
 */
@Controller
public class ReportController {

    private ReportWebLogic reportWebLogic = new ReportWebLogic();

    @RequestMapping("/report/pv.do")
    public ModelAndView report(HttpServletRequest request) {
        reportWebLogic.report(request);

        return new ModelAndView("/result");
    }

    @RequestMapping("/report/close.do")
    public ModelAndView close(HttpServletRequest request) {

        reportWebLogic.bounceRate(request);

        return new ModelAndView("/result");
    }
}
