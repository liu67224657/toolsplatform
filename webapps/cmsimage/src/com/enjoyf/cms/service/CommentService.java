package com.enjoyf.cms.service;

import com.enjoyf.cms.bean.CommentContent;
import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.util.*;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  14-2-6 下午1:18
 * Description:
 */
public class CommentService {

    private HttpClientManager clientManager = new HttpClientManager();

    public CommentContent getCid(String fid, String url, String title) throws Exception {


        Integer contentId = null;
        Integer replyNum = null;

        HttpResult result = null;
        try {
            result = clientManager.post(PropertiesContainer.getJoymeCommentApiGetCId(), new HttpParameter[]{
                    new HttpParameter("fid", fid),
                    new HttpParameter("curl", URLEncoder.encode(url, "UTF-8")),
                    new HttpParameter("title", title),
                    new HttpParameter("cdomain", 2)
            }, null);
        } catch (UnsupportedEncodingException e) {
        }

        if (result.getReponseCode() != 200) {
            return null;
        }

        try {
            JSONObject object = JSONObject.fromObject(result.getResult());
            if (object.containsKey("result")) {
                object = (JSONObject) object.get("result");
                contentId = (Integer) object.get("contentid");
                if (object.containsKey("replynum")) {
                    replyNum = (Integer) object.get("replynum");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        CommentContent returnObj = null;
        if (contentId != null) {
            returnObj = new CommentContent(contentId, replyNum);
        }

        return returnObj;
    }

}
