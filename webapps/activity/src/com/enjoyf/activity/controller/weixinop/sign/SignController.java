package com.enjoyf.activity.controller.weixinop.sign;/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.activity.bean.ResultCodeConstants;
import com.enjoyf.activity.bean.sign.Sign;
import com.enjoyf.activity.facade.SignFacade;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
@RequestMapping("/weixinop/sign")
@Controller
public class SignController {

    private SignFacade signFacade = new SignFacade();

    @ResponseBody
    @RequestMapping("/{gamecode}")
    public String sign(HttpServletRequest request, HttpServletResponse response, @PathVariable String gamecode) {
        String profileId = request.getParameter("openid");
        if (StringUtil.isEmpty(profileId)) {
            return ResultCodeConstants.PARAM_ISEMPTY.getJsonString();
        }

        try {
            Sign sign = signFacade.getSignByGame(gamecode);
            if(sign==null){
                return ResultCodeConstants.PARAM_ISEMPTY.getJsonString();
            }

            boolean bval = signFacade.sign(sign, profileId,sign.getPoint());
            JSONObject jsonObj = null;
            jsonObj = ResultCodeConstants.SUCCESS.getJsonObject();
            jsonObj.put("result", sign);
            jsonObj.put("signflag", bval);
            return jsonObj.toString();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        } catch (JoymeDBException e) {
            e.printStackTrace();
        }
        return "error";
    }
}
