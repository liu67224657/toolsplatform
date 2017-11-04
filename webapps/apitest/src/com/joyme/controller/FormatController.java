package com.joyme.controller;

import com.joyme.parameter.ParamJson;
import com.joyme.parameter.ParamJsonV2;
import com.joyme.servlet.MessagePropertie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-3-18
 * Time: 上午10:41
 * redirect forword
 */
@Controller
public class FormatController {

    @RequestMapping("/format")
    public ModelAndView format() {
        return new ModelAndView("/format/format");
    }

    @RequestMapping("/dtree")
    public ModelAndView dtree() {

        //
        List<ParamJsonV2> listProuct = new ArrayList<ParamJsonV2>();

        Map<String, List<ParamJson>> map = MessagePropertie.getProductMaps();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        int vi = 1;
        for (String key : map.keySet()) {
            returnMap.put(vi+++"", map.get(key));
        }

        int vii=1;
        for (String key : returnMap.keySet()) {
           // System.out.println(key);
            List<ParamJson> list = (List<ParamJson>)returnMap.get(key);
            for (int i = 0; i < list.size(); i++) {
                ParamJsonV2 paramJson = new ParamJsonV2();
                paramJson.setKey(vii+++"");
                paramJson.setParentkey(key);
                paramJson.setValue(list.get(i).getValue());
                listProuct.add(paramJson);
                //System.out.println(list.get(i).getKey() + "---" + );
            }
        }
        Map<String, Object> mapMessage = new HashMap<String, Object>();
        mapMessage.put("list",listProuct);

        for (int i = 0; i < listProuct.size(); i++) {
            System.out.println(listProuct.get(i).getKey()+"---"+listProuct.get(i).getValue());
        }

        return new ModelAndView("/format/dtree",mapMessage);
    }

}
