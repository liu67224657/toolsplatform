package com.enjoyf.mcms.controller;

import com.enjoyf.mcms.bean.JoymePointArchive;
import com.enjoyf.mcms.bean.JoymeShare;
import com.enjoyf.mcms.container.ConfigContainer;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-12-20 上午11:45
 * Description:
 */
@Controller
@RequestMapping("/share")
public class ShareController {

    @ResponseBody
    @RequestMapping("/content.do")
    public String getShare(HttpServletResponse response) {
        JoymeShare joymeShare = ConfigContainer.getShare();

        response.setContentType("text/json; charset=utf-8");
        JSONObject object = new JSONObject();
        object.put("rs", 1);
        object.put("content", joymeShare.getContent());
        object.put("pic", joymeShare.getPic());
        object.put("link", joymeShare.getLink());

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
        return null;
    }
}
