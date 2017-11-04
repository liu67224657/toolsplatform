package com.enjoyf.wiki.controller;

import com.enjoyf.util.CookieUtil;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.URLUtil;
import com.enjoyf.wiki.container.PropertiesContainer;
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
@RequestMapping("/wiki/comment")
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
            String outPut = "commentlistcallback([" + object.toString() + "])";
            return outPut;
        }
        long contentId = 0;
        try {
            contentId = Long.parseLong(cid);
        } catch (NumberFormatException e) {
            JSONObject object = new JSONObject();
            object.put("rs", 1);
            object.put("msg", "param.is.empty");
            String outPut = "commentlistcallback([" + object.toString() + "])  ";
            return outPut;
        }

        try {
            StringBuffer sb = urlUtil.openURL(PropertiesContainer.getInstance().getJoymeApiHotList() + "?cid=" + contentId);
            String outPut = "commentlistcallback([" + sb.toString() + "])  ";
            displayHtml(response, outPut);
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
            String outPut = "commentlistcallback([" + object.toString() + "])  ";
            return outPut;
        }
        long contentId = 0;
        try {
            contentId = Long.parseLong(cid);
        } catch (NumberFormatException e) {
            JSONObject object = new JSONObject();
            object.put("rs", 1);
            object.put("msg", "param.is.empty");
            String outPut = "commentlistcallback([" + object.toString() + "])  ";
            return outPut;
        }

        String sort = request.getParameter("sort");
        try {
            StringBuffer sb = urlUtil.openURL(PropertiesContainer.getInstance().getJoymeApiCommentList() + "?cid=" + contentId + "&sort=" + sort);
            //System.out.println(sb.toString());
            String outPut = "commentlistcallback([" + sb.toString() + "])  ";
            //System.out.println(outPut);
            displayHtml(response, outPut);
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
            String outPut = "agreecallback([" + object.toString() + "])  ";
            return outPut;
        }

        long replyId = 0;
        try {
            replyId = Long.parseLong(rid);
        } catch (Exception e) {
            JSONObject object = new JSONObject();
            object.put("rs", 0);
            object.put("msg", "param.is.empty");
            String outPut = "agreecallback([" + object.toString() + "])";
            try {
                displayHtml(response, outPut);
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
            String outPut = "agreecallback([" + object.toString() + "])";
            try {
                displayHtml(response, outPut);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        try {
            StringBuffer sb = urlUtil.openURL(PropertiesContainer.getInstance().getJoymeCommentAgreeApi() + "?rid=" + replyId + "&uno=" + uno);
            String outPut = "agreecallback([" + sb.toString() + "])";
            displayHtml(response, outPut);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/remove.do")
    public String remove(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("rid");
        if (StringUtil.isEmpty(cid)) {
            JSONObject object = new JSONObject();
            object.put("rs", 0);
            object.put("msg", "param.is.empty");
            return object.toString();
        }
        long replyId = 0;
        try {
            replyId = Long.parseLong(cid);
        } catch (NumberFormatException e) {
            JSONObject object = new JSONObject();
            object.put("rs", 0);
            object.put("msg", "param.is.empty");
            return object.toString();
        }

        String uno = CookieUtil.getCookieValue(request, CookieUtil.UNO);
        if (StringUtil.isEmpty(uno)) {
            JSONObject object = new JSONObject();
            object.put("rs", -1);
            object.put("msg", "user.not.login");
            return object.toString();
        }

        try {
            StringBuffer sb = urlUtil.openURL(PropertiesContainer.getInstance().getJoymeCommentRemoveApi() + "?rid=" + replyId + "&uno=" + uno);
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
