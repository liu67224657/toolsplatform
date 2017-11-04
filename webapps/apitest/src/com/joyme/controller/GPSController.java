package com.joyme.controller;

import com.joyme.parameter.ResultObjectMsg;
import com.joyme.util.JsonBinder;
import com.joyme.util.ShortUrlUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhimingli on 2015/2/26 0026.
 */
@Controller
@RequestMapping("/")
public class GPSController {

    @RequestMapping("/gps")
    public ModelAndView shorturl() throws Exception {
        return new ModelAndView("/gps/gps");
    }
}
