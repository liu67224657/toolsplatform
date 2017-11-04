package com.enjoyf.activity.controller.weixinop.point;

import com.enjoyf.activity.bean.point.GoodsItemResult;
import com.enjoyf.activity.bean.point.GoodsItemResultType;
import com.enjoyf.activity.facade.GoodsItemPointFacade;
import com.enjoyf.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhimingli on 2016/10/13 0013.
 */

@RequestMapping("/weixinop/point")
@Controller
public class GoodItemPointController {

    private GoodsItemPointFacade goodsItemPointFacade = new GoodsItemPointFacade();

    @ResponseBody
    @RequestMapping("{goodsid}/getcode")
    public String getcode(HttpServletRequest request,
                          @RequestParam(value = "openid", required = false) String openid,
                          @PathVariable String goodsid) {
        //返回的值
        JSONObject jsonObject = new JSONObject();
        int rs = 0;
        String msg = "";
        String result = "";
        if (StringUtil.isEmpty(openid)) {
            rs = GoodsItemResultType.PARAM_EMPTY.getCode();
            msg = "openid.is.null";
        } else {
            Long gameidInteger = 0l;
            try {
                gameidInteger = Long.valueOf(goodsid);

                GoodsItemResult goodItemResult = goodsItemPointFacade.getWeixinItemText(gameidInteger, openid);
                if (goodItemResult != null) {
                    rs = goodItemResult.getGoodsItemResultType().getCode();
                    result = goodItemResult.getGoods_item();

                    if (!StringUtil.isEmpty(result)) {
                        msg = "success";
                    } else {
                        msg = "fail";
                    }

                }
            } catch (NumberFormatException e) {
                rs = GoodsItemResultType.SYSTEM_ERROR.getCode();
                msg = "NumberFormatException";
            }
        }
        jsonObject.put("rs", rs);
        jsonObject.put("msg", msg);
        jsonObject.put("result", result);
        return "getcode([" + jsonObject.toString() + "])";
    }
}
