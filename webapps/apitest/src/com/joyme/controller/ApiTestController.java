package com.joyme.controller;

import com.joyme.parameter.ParamJson;
import com.joyme.parameter.PostParameterDTO;
import com.joyme.parameter.ResultObjectMsg;
import com.joyme.util.*;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-9-25
 * Time: 下午6:26
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("/")
public class ApiTestController {


	@ResponseBody
	@RequestMapping("/api2")
	public String api2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//请求的地址
		String url = request.getParameter("url");
		PostParameter[] params = getPostParam(request);

		url = getUrl(request, url);


		//设置真正的请求参数，方便查找问题
		String post_params = ApiTestParamUtil.getPost_params(url, params);
		response.setHeader("post_params", post_params);
		ResultObjectMsg resultObjectMsg = new ResultObjectMsg();
		resultObjectMsg.setRs(ResultObjectMsg.CODE_S);
		String returnJson = null;
		try {
			returnJson = HttpUtilGetURL.httpGet(post_params);
		} catch (Exception e) {
			returnJson = e.toString();
			resultObjectMsg.setRs(ResultObjectMsg.CODE_E);
		}
		resultObjectMsg.setMsg(post_params);
		resultObjectMsg.setResult(returnJson);


		//请求参数封转成字符串
		resultObjectMsg.setParams(getPostParam(params));

		return JsonBinder.buildNormalBinder().toJson(resultObjectMsg);
	}


	//讲请求参数封装成数组
	private PostParameter[] getPostParam(HttpServletRequest request) {
		PostParameter[] params = null;
		//参数
		String param = request.getParameter("params");

		JSONArray jsonarray = JSONArray.fromObject(param);


		List<ParamJson> list = null;
		if (jsonarray.size() > 0) {
			list = (List<ParamJson>) JSONArray.toCollection(jsonarray, ParamJson.class);
		}

		List<ParamJson> getNewListParm = null;
		String product = request.getParameter("product");
		if (!StringUtils.isEmpty(product)) {
			//环境dev alpha beta com
			String api = request.getParameter("api");
			String env = request.getParameter("env");
			PostParameterDTO dto = ApiTestParamUtil.getMessagePropertes(product, env, api);

			getNewListParm = ApiTestParamUtil.getNewListParmV2(dto.getPostParameter(), list);
			params = new PostParameter[getNewListParm.size()];
			for (int i = 0; i < getNewListParm.size(); i++) {
				ParamJson paramJson = getNewListParm.get(i);
				PostParameter parameter = new PostParameter(paramJson.getKey(), paramJson.getValue());
				params[i] = parameter;
			}
		} else {
			getNewListParm = ApiTestParamUtil.getNewListParmV2(null, list);
		}

		if (getNewListParm != null) {
			if (params == null) {
				params = new PostParameter[getNewListParm.size()];
			}
			for (int i = 0; i < getNewListParm.size(); i++) {
				ParamJson paramJson = getNewListParm.get(i);
				PostParameter parameter = new PostParameter(paramJson.getKey(), paramJson.getValue());
				params[i] = parameter;
			}
		}

		if (params == null) {
			params = new PostParameter[1];
			PostParameter parameter = new PostParameter("1", "1");
			params[0] = parameter;
		}

		return params;
	}


	//讲请求参数封装成字符串
	private String getPostParam(PostParameter[] params) {
		StringBuffer paramStr = new StringBuffer();
		for (int i = 0; i < params.length; i++) {
			PostParameter p = params[i];
			if (i != 0) {
				paramStr.append("&");
			}
			paramStr.append(p.getName() + "=" + p.getValue());
		}
		return paramStr.toString();
	}

	private String getUrl(HttpServletRequest request, String url) {
		String product = request.getParameter("product");
		if (!StringUtils.isEmpty(product)) {
			//环境dev alpha beta com
			String api = request.getParameter("api");
			String env = request.getParameter("env");
			//if(S)
			PostParameterDTO dto = ApiTestParamUtil.getMessagePropertes(product, env, api);

			url = dto.getUrl();
		}
		return url;
	}

}
