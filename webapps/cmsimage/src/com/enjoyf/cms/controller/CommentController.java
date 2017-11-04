package com.enjoyf.cms.controller;

import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.util.CookieUtil;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.URLUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  14-2-6 上午11:32
 * Description:
 */
@Controller
@RequestMapping("/article/comment")
public class CommentController {
    private static URLUtil urlUtil = new URLUtil();

    @ResponseBody
    @RequestMapping("/hotlist.do")
    public String hostList(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");
        if (StringUtil.isEmpty(cid)) {
            JSONObject object = new JSONObject();
            object.put("rs", 1);
            object.put("msg", "param.is.empty");
            return object.toString();
        }
        long contentId = 0;
        try {
            contentId = Long.parseLong(cid);
        } catch (NumberFormatException e) {
            JSONObject object = new JSONObject();
            object.put("rs", 1);
            object.put("msg", "param.is.empty");
            return object.toString();
        }

        try {
            StringBuffer sb = urlUtil.openURL(PropertiesContainer.getJoymeApiHotList() + "?cid=" + contentId);
            displayHtml(response, sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/list.do")
    public String commentList(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");
        if (StringUtil.isEmpty(cid)) {
            JSONObject object = new JSONObject();
            object.put("rs", 1);
            object.put("msg", "param.is.empty");
            return object.toString();
        }
        long contentId = 0;
        try {
            contentId = Long.parseLong(cid);
        } catch (NumberFormatException e) {
            JSONObject object = new JSONObject();
            object.put("rs", 1);
            object.put("msg", "param.is.empty");
            return object.toString();
        }

        String sort = request.getParameter("sort");
        try {
            StringBuffer sb = urlUtil.openURL(PropertiesContainer.getJoymeCommentApiList() + "?cid=" + contentId + "&sort=" + sort);
            displayHtml(response, sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/agree.do")
    public String agree(HttpServletRequest request, HttpServletResponse response) {
          String rid = request.getParameter("rid");
        if (StringUtil.isEmpty(rid)) {
            JSONObject object = new JSONObject();
            object.put("rs", 0);
            object.put("msg", "param.is.empty");
            return object.toString();
        }

        long replyId = 0;
        try {
            replyId = Long.parseLong(rid);
        } catch (Exception e) {
            JSONObject object = new JSONObject();
            object.put("rs", 0);
            object.put("msg", "param.is.empty");
            try {
                displayHtml(response, object.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }

        String uno = CookieUtil.getCookieValue(request, CookieUtil.UNO);
        if (StringUtil.isEmpty(uno)) {
            JSONObject object = new JSONObject();
            object.put("rs", -1);
            object.put("msg", "user.not.login");
            try {
                displayHtml(response, object.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        try {
            StringBuffer sb = urlUtil.openURL(PropertiesContainer.getJoymeCommentAgreeApi() + "?rid=" + replyId + "&uno=" + uno);
            displayHtml(response, sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void displayHtml(HttpServletResponse response, String html) throws IOException {
        response.setContentType("text/json; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(html);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }
}
