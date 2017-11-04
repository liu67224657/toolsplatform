package com.joyme.controller;

import com.joyme.parameter.ResultObjectMsg;
import com.joyme.util.JsonBinder;
import com.joyme.util.ShortUrlUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhimingli on 2015/2/26 0026.
 */
@Controller
@RequestMapping("/")
public class ShortURLController {

	@ResponseBody
	@RequestMapping("/shorturl")
	public String shorturl(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "url", required = false) String url) throws Exception {
		ResultObjectMsg resultObjectMsg = new ResultObjectMsg();
		resultObjectMsg.setRs(ResultObjectMsg.CODE_S);
		String shorturl = "";
		try {
			shorturl = ShortUrlUtils.getSinaURL(url);
		} catch (Exception e) {
			resultObjectMsg.setRs(ResultObjectMsg.CODE_E);
		}
		resultObjectMsg.setResult(shorturl);
		return JsonBinder.buildNormalBinder().toJson(resultObjectMsg);
	}
}
