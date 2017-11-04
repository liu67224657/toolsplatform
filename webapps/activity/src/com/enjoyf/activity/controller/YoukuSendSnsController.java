package com.enjoyf.activity.controller;

import com.enjoyf.activity.bean.ResultCodeConstants;
import com.enjoyf.activity.bean.ResultObjectMsg;
import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.enjoyf.util.RandomRange;
import com.enjoyf.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhimingli on 2016/8/9 0009.
 */

@Controller
@RequestMapping("/activity/sns")
public class YoukuSendSnsController extends BaseRestSpringController {

    //产生随机数
    private RandomRange randomRange = new RandomRange(10000, 99999);


    /**
     * 获取验证码
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/getvalidcode")
    public String getvalidcode(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rs", ResultObjectMsg.CODE_E);

        String phone = request.getParameter("phone");
        if (StringUtil.isEmpty(phone)) {
            jsonObject.put("msg", ResultCodeConstants.PARAM_ISEMPTY.getExtMsg());
        }


        return "getvalidcode([" + jsonObject.toString() + "])";
    }
}
