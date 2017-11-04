package com.enjoyf.activity.controller.weixinop.qyz;
/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
@RequestMapping("/weixinop/uploadIndex")
@Controller
public class QyzUploadIndexController extends BaseRestSpringController {

    /**
     * 跳转到生成商品页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/page")
    public ModelAndView showUploadPage(HttpServletRequest request,
                                     HttpServletResponse response) {
        Map<String, Object> mapMessage = Maps.newHashMap();

        return new ModelAndView("jsp/qyz/goodsUploadIndex", mapMessage);
    }

}
