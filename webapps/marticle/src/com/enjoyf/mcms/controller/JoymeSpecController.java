package com.enjoyf.mcms.controller;

import com.enjoyf.mcms.bean.JoymeSpec;
import com.enjoyf.mcms.service.JoymeSpecService;
import com.enjoyf.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-3-6
 * Time: 上午10:39
 * 游戏包名修改
 */
@Controller
public class JoymeSpecController {
    private static JoymeSpecService joymeSpecService = new JoymeSpecService();

    @RequestMapping("/marticle/ac/queryListSpec.do")
    public ModelAndView queryListSpec(@RequestParam(value = "specname", required = false) String specname) throws Exception {
        List<JoymeSpec> listJoymeSpec = new ArrayList<JoymeSpec>();
        //   if (!StringUtil.isEmpty(specname)) {
        listJoymeSpec = joymeSpecService.queryLikeJoymeSpecBySpecName(null, specname);
        //  }
        Map map = new HashMap();
        map.put("listJoymeSpec", listJoymeSpec);
        map.put("specname",specname);
        map.put("size",listJoymeSpec.size());
        return new ModelAndView("/marticle/ac/spec/listspec", map);
    }


    @RequestMapping("/marticle/ac/querybyid.do")
    public ModelAndView querybyid(@RequestParam(value = "specid", required = false) Integer specid, HttpServletRequest request) throws Exception {
        JoymeSpec joymeSpec = joymeSpecService.queryJoymeSpecbyId(null, specid);
        Map map = new HashMap();
        map.put("joymeSpec", joymeSpec);
        String result = request.getParameter("result");

        if (!StringUtil.isEmpty(result) && result.equals("1")) {
            map.put("result", "更新成功");
        } else {

        }

        return new ModelAndView("/marticle/ac/spec/queryspec", map);
    }


    @RequestMapping("/marticle/ac/updateSpecPackage.do")
    public ModelAndView updateSpecPackage(@RequestParam(value = "packageName", required = false) String packageName,
                                          @RequestParam(value = "specid", required = false) Integer specid) throws Exception {
        JoymeSpec bean = new JoymeSpec();
        bean.setSpecId(specid);
        if (StringUtil.isEmpty(packageName)) {
            bean.setIsPackage(0);
            bean.setPackageName("");
        } else {
            bean.setIsPackage(1);
            bean.setPackageName(packageName);
        }
        int result = joymeSpecService.updateJoymeSpec(null, bean);
        return new ModelAndView("redirect:/marticle/ac/querybyid.do?specid=" + specid + "&result=" + result);
    }
}
