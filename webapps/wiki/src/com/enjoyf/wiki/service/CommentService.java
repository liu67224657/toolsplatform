package com.enjoyf.wiki.service;

import com.enjoyf.util.HttpClientManager;
import com.enjoyf.util.HttpParameter;
import com.enjoyf.util.HttpResult;
import com.enjoyf.wiki.bean.CommentContent;
import com.enjoyf.wiki.container.PropertiesContainer;
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

//	public CommentContent getCid(String fid, String url, String title) throws Exception {
//
//
//		Integer contentId = null;
//		Integer replyNum = null;
//
//		HttpResult result = null;
//		try {
//			result = clientManager.post(PropertiesContainer.getInstance().getJoymeCommentGetcidApi(), new HttpParameter[]{
//					new HttpParameter("fid", fid),
//					new HttpParameter("curl", URLEncoder.encode(url, "UTF-8")),
//					new HttpParameter("title", title),
//					new HttpParameter("cdomain", 1)
//			}, null);
//		} catch (UnsupportedEncodingException e) {
//		}
//
//		if (result.getReponseCode() != 200) {
//			return null;
//		}
//
//		try {
//			JSONObject object = JSONObject.fromObject(result.getResult());
//			if (object.containsKey("result")) {
//				object = (JSONObject) object.get("result");
//				contentId = (Integer) object.get("contentid");
//				if (object.containsKey("replynum")) {
//					replyNum = (Integer) object.get("replynum");
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		CommentContent returnObj = null;
//		if (contentId != null) {
//			returnObj = new CommentContent(contentId, replyNum);
//		}
//
//		return returnObj;
//	}


	public CommentContent getCid2(String unikey, String jsonparam) throws Exception {
		Integer contentId = null;
		Integer replyNum = null;

		HttpResult result = null;
		try {

			result = clientManager.post(PropertiesContainer.getInstance().getJoymeCommentQueryApi2(), new HttpParameter[]{
					new HttpParameter("unikey", unikey),
					new HttpParameter("domain", 1),
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
