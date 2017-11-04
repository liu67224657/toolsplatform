package com.enjoyf.webcache.controller;

import com.enjoyf.util.StringUtil;
import com.enjoyf.webcache.util.IruiUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ericliu on 16/5/10.
 */
@Controller
@RequestMapping(value = "/action/jump/")
public class JumpController {


    @RequestMapping("/{index}.do")
    public ModelAndView jumpByIndex(HttpServletRequest request, String index) throws Exception {
        String host = request.getHeader("host");
        if (StringUtil.isEmpty(host) || ((!host.contains("xinwen.joyme") && !host.contains("localhost")
                && !host.contains("dev") && !host.contains("alpha")&& !host.contains("beta")))) {
            return null;
        }

        return new ModelAndView("redirect:" + IruiUtil.getRandomUrl());
    }


}
