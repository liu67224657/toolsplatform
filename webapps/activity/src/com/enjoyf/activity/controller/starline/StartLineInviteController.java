package com.enjoyf.activity.controller.starline;

import com.enjoyf.activity.bean.GoodsResultCodeConstants;
import com.enjoyf.activity.bean.ResultCodeConstants;
import com.enjoyf.activity.bean.goods.GoodsItem;
import com.enjoyf.activity.bean.point.GoodsItemResult;
import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.enjoyf.activity.facade.GoodsItemFacade;
import com.enjoyf.activity.facade.GoodsItemPointFacade;
import com.enjoyf.activity.facade.InviteFacade;
import com.enjoyf.activity.util.Contants;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.util.Constants;
import com.enjoyf.util.Pagination;
import com.enjoyf.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wengangsai on 2016/8/01 0029.
 */

@RequestMapping("/activity/starline/invite")
@Controller
public class StartLineInviteController extends BaseRestSpringController {
    private static String APPID = "wx2621eecc821ca71a";
    private static String SECRET = "ef5c57e630726e64f39b8b16b380bb90";
    private static final String ACTIVITY_NAME = "starlineinvite";
    private static final long GOODS_ID = 5l;


    private InviteFacade inviteFacade = new InviteFacade();
    private GoodsItemPointFacade goodsItemFacade = new GoodsItemPointFacade();

    @RequestMapping("/create")
    @ResponseBody
    public String create(HttpServletRequest request, HttpServletResponse response) {

        String srcOpenId = request.getParameter("srcopenid");
//        String destOpenId = request.getParameter("openid");
//        String destHead = request.getParameter("head");
//        String destNick = request.getParameter("nick");
        String jsonCallback = request.getParameter("callback");

        if (StringUtil.isEmpty(srcOpenId)) {
            return ResultCodeConstants.PARAM_ISEMPTY.getJsonString(jsonCallback);
        }

        return inviteFacade.buildInvite(srcOpenId, ACTIVITY_NAME, jsonCallback, APPID, SECRET, request, response);
    }

    @RequestMapping("/list")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response) {
        String jsonCallback = request.getParameter("callback");
        return inviteFacade.queryInviteInfo(APPID, SECRET, ACTIVITY_NAME, new Pagination(5, 1, 5), jsonCallback, request, response);
    }

    @RequestMapping("/sendcode")
    @ResponseBody
    public String counter(HttpServletRequest request, HttpServletResponse response) {
        String jsonCallback = request.getParameter("callback");
        String srcOpenId = request.getParameter("openid");
        long goodsId = GOODS_ID;
        if (request.getParameter("goodsid") != null) {
            goodsId = Long.parseLong(request.getParameter("goodsid"));
        }
        long count = inviteFacade.getInviteListCount(srcOpenId, ACTIVITY_NAME);
        if (count >= 1) {
            GoodsItemResult goodsItem = goodsItemFacade.getWeixinItemText(goodsId, srcOpenId);

            JSONObject jsonObject = ResultCodeConstants.SUCCESS.getJsonObject();
            if (goodsItem != null && goodsItem.getGoods_item()!=null) {
                jsonObject.put("result", goodsItem.getGoods_item());
                return ResultCodeConstants.resultCheckCallback(jsonObject, jsonCallback);
            } else {
                return GoodsResultCodeConstants.ITEM_EMPTY.getJsonString(jsonCallback);
            }
        } else {
            JSONObject jsonObject = GoodsResultCodeConstants.NOT_MATCH_CONDITION.getJsonObject();
            jsonObject.put("result", count);
            return ResultCodeConstants.resultCheckCallback(jsonObject, jsonCallback);
        }

    }


}
