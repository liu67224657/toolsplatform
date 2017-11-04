package com.joyme.controller;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhimingli on 2016/11/28 0028.
 */
@Controller
@RequestMapping("/patch")
public class JoymePatchController {


    @RequestMapping("/page")
    public ModelAndView demo() {
        return new ModelAndView("/patch/patch");
    }

    @ResponseBody
    @RequestMapping("/format")
    public String format(@RequestParam(value = "content", required = false) String content) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rs", "1");
        jsonObject.put("result", formatUtil(content));
        return jsonObject.toString();
    }

    private static String formatUtil(String content) {
        String strline[] = content.split("\\n");
        StringBuffer returnStr = new StringBuffer();
        for (String line : strline) {
            if (StringUtils.isEmpty(line)) {
                continue;
            }

            if (!line.contains(".java")) {
                continue;
            }
            int comindexof = line.indexOf("com/");
            line = line.substring(comindexof, line.length() - 1);

            int javaindexOf = line.indexOf(".java");
            line = line.substring(0, javaindexOf);

            line = line.replaceAll("/", ".");
            if (!StringUtils.isEmpty(line)) {
                returnStr.append(line + " ");
            }
        }
        return returnStr.toString().trim();
    }


    public static void main(String[] args) {

        String str = "//online/platform/main/framework/src/main/java/com/enjoyf/platform/serv/ask/AskLogic.java#123\n" +
                "//online/platform/main/webapps/tools/htdocs/view/jsp/wanba/admenu/createtopmenu.jsp#5\n" +
                "//online/platform/main/webapps/tools/java/com/enjoyf/webapps/tools/webpage/controller/wanba/AskRecommentAdMenuController.java#7";


        System.out.println(formatUtil(str));

    }
}
