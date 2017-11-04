package com.enjoyf.activity.controller;

import com.enjoyf.activity.weixin.WeixinUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by zhimingli on 2016/9/5 0005.
 * 微信js示例
 */
@RequestMapping("/activity/example")
@Controller
public class ExampleController {
    //着迷测试服务号
//    private static String APPID = "wx758f0b2d30620771";
//    private static String SECRET = "b58cd348c7f5908055e5e691eed45c39";


    //着迷测试服务号
    private static String APPID = "wx529ba0bd21499c1a";
    private static String SECRET = "c54c523397a9a2330a8d6856dceadf2c";

    @RequestMapping("/page")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> mapMessage = WeixinUtil.getMapMessage(APPID, SECRET, request, response);
        return new ModelAndView("/jsp/example", mapMessage);
    }
}
