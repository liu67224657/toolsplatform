package com.joyme.controller;

import com.joyme.util.http.HttpClientManager;
import com.joyme.util.http.HttpParameter;
import com.joyme.util.http.HttpResult;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhimingli on 2016/11/10 0010.
 * 官网地址：https://luosimao.com/
 */
@Controller
@RequestMapping("/luosimao")
public class LuosimaoDemoController {

    @RequestMapping("/demo")
    public ModelAndView demo() {
        return new ModelAndView("/luosimao/demo");
    }

    @ResponseBody
    @RequestMapping("/api")
    public String api(@RequestParam(value = "username", required = false) String username,
                      @RequestParam(value = "passsword", required = false) String passsword,
                      @RequestParam(value = "luotest_response", required = false) String luotest_response) {
        String result = verify(luotest_response);
        return result;
    }

    private static String verify(String response) {
        HttpClientManager httpClient = new HttpClientManager();
        HttpResult httpResult = httpClient.post("https://captcha.luosimao.com/api/site_verify", new HttpParameter[]{
                new HttpParameter("api_key", "9bf3e865c94d1bc4a88c5e85f553264a"),
                new HttpParameter("response", response)
        }, null);
        JSONObject jsonObject = JSONObject.fromObject(httpResult.getResult());
        return jsonObject.toString();
    }
}
