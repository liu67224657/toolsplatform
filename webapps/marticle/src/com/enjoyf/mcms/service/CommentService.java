package com.enjoyf.mcms.service;

import com.enjoyf.mcms.bean.CommentContent;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.util.HttpClientManager;
import com.enjoyf.util.HttpParameter;
import com.enjoyf.util.HttpResult;
import com.enjoyf.util.MD5Util;
import net.sf.json.JSONObject;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  14-2-6 下午1:18
 * Description:
 */
public class CommentService {

    private HttpClientManager clientManager = new HttpClientManager();


    public CommentContent getCid2(String unikey, String jsonparam) throws Exception {
        String contentId = MD5Util.Md5(unikey + 2);
        Integer replyNum = null;

        HttpResult result = null;
        try {

            result = clientManager.post(ConfigContainer.getJoymeCommentQueryApi(), new HttpParameter[]{
                    new HttpParameter("unikey", unikey),
                    new HttpParameter("domain", 2),
                    new HttpParameter("jsonparam", jsonparam)
            }, null);
        } catch (Exception e) {
        }

        if (result.getReponseCode() != 200) {
            return null;
        }

        try {
            String commonjson = result.getResult().replaceAll("querycallback\\(\\[", "").replaceAll("\\]\\)", "");

            JSONObject object = JSONObject.fromObject(commonjson);
            if (object.containsKey("result")) {
                object = (JSONObject) object.get("result");
                if (object.containsKey("comment_sum")) {
                    replyNum = (Integer) object.get("comment_sum");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        CommentContent returnObj = null;
        if (replyNum != null) {
            returnObj = new CommentContent(contentId, replyNum);
        }


        return returnObj;
    }
}
