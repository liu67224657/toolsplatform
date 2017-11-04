package com.enjoyf.webcache.controller;


import java.net.URLDecoder;
import java.net.URLEncoder;

import com.enjoyf.framework.exception.JoymeException;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.*;
import com.enjoyf.webcache.bean.JoymeWikiInfo;
import com.enjoyf.webcache.bean.JoymeWikiKeyword;
import com.enjoyf.webcache.cache.JoymeWikiCache;
import com.enjoyf.webcache.constant.BaseResultCode;
import com.enjoyf.webcache.constant.WikiResultCode;
import com.enjoyf.webcache.service.JoymeWikiInfoService;
import com.enjoyf.webcache.service.JoymeWikiKeywordService;
import com.enjoyf.webcache.util.GenerateIdUtil;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * wiki添加超链接相关接口
 * @author huazhang
 *
 */
@Controller
@RequestMapping(value="/wiki")
public class JsonJoymeWikiApiController {

	private static JoymeWikiKeywordService joymeWikiKeywordService=new JoymeWikiKeywordService();
	
	private static JoymeWikiInfoService JoymeWikiInfoService=new JoymeWikiInfoService();
	
	private static final int DEFAULT_PAGE_SIZE=10;
	

	/**
	 * wiwi标题查询接口
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/title/query.do")
	public String queryWikiList(HttpServletRequest request,HttpServletResponse response) throws JoymeException{
		String callback = request.getParameter("callback");
		JSONObject jsonObject = new JSONObject();
        try {

            String pageNum = request.getParameter("pnum");
            String pageSize = request.getParameter("psize");
            String name=request.getParameter("name");
            if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize)) {
            	return WikiResultCode.PARAMTER_NULL.getResultJson(callback);
            }

            if (!StringUtil.isNumeric(pageNum) || !StringUtil.isNumeric(pageSize)) {
            	return WikiResultCode.PARAMTER_ERROR.getResultJson(callback);
     
            }
            
            int pnum=Integer.parseInt(pageNum)==0?1:Integer.parseInt(pageNum);
            int psize=Integer.parseInt(pageSize)==0?DEFAULT_PAGE_SIZE:Integer.parseInt(pageSize);
            Pagination page=new Pagination(pnum*psize,pnum,psize);
            if (!StringUtil.isEmpty(name)) {
            	name=URLDecoder.decode(name, "utf-8");
			}
            PageRows<JoymeWikiInfo> pageRows=JoymeWikiInfoService.queryJoymeWikiInfo(page,name);
            for (JoymeWikiInfo JoymeWikiInfo : pageRows.getRows()) {
				String wikiId=JoymeWikiInfo.getWikiId();
				long newKeywordNum=JoymeWikiCache.getNewWikiKeywordNum(wikiId);
				if (newKeywordNum>0) {
					JoymeWikiInfo.setNewKeywordNum((int)newKeywordNum);
				}
			}
            
            jsonObject.put("rs", WikiResultCode.SUCCESS.getCode());
            jsonObject.put("msg", WikiResultCode.SUCCESS.getDesc());
            
            JSONObject retJsonObject=new JSONObject();
            retJsonObject.put("page", pageRows.getPage());
            retJsonObject.put("rows", pageRows.getRows());
            
            jsonObject.put("result", retJsonObject);
            
            return toReponseJsonData(callback, jsonObject);
        } catch (Exception e) {
            LogService.errorSystemLog("/title/query.do",e);
            return WikiResultCode.EXCEPTION.getResultJson(callback);
        }
	}
	
	/**
	 * 增加新的wiki标题
	 * @param request
	 * @return
	 * @throws JoymeException
	 */
	@ResponseBody
	@RequestMapping(value="/title/report.do")
	public String addWiki(HttpServletRequest request,HttpServletResponse response) throws JoymeException{
		String callback = request.getParameter("callback");
		JSONObject jsonObject = new JSONObject();
        try {

            String wikiKey = request.getParameter("wikikey");
            String wikiName = request.getParameter("wikiname");
            
            if (StringUtil.isEmpty(wikiKey) || StringUtil.isEmpty(wikiName)) {
            	return WikiResultCode.PARAMTER_NULL.getResultJson(callback);
            }

            wikiName=URLDecoder.decode(wikiName, "utf-8");
            
            String wikiId=GenerateIdUtil.getInstance().getDataIdByMD5(wikiKey);
            JoymeWikiInfo JoymeWikiInfo=new JoymeWikiInfo();
            JoymeWikiInfo.setWikiId(wikiId);
            JoymeWikiInfo.setWikiName(wikiName);
            JoymeWikiInfo.setKeywordNum(0);
            JoymeWikiInfo.setWikiKey(wikiKey);
            int ret=JoymeWikiInfoService.insertWikiTitle(JoymeWikiInfo);
            if (ret>0) {
                jsonObject.put("rs", WikiResultCode.SUCCESS.getCode());
                jsonObject.put("msg", WikiResultCode.SUCCESS.getDesc());
                
                JSONObject retJsonObject=new JSONObject();
                retJsonObject.put("wikiId", wikiId);
                
                jsonObject.put("result", retJsonObject);
                
                return toReponseJsonData(callback, jsonObject);
			}else {
				return WikiResultCode.CREATE_WIKI_FAILED.getResultJson(callback);
			}

        } catch (Exception e) {
        	LogService.errorSystemLog("/title/report.do", e);
        	return WikiResultCode.EXCEPTION.getResultJson(callback);
        }
	}
	
	/**
	 * 查询词条列表接口
	 * @param request
	 * @return
	 * @throws JoymeException
	 */
	@ResponseBody
	@RequestMapping(value="/keyword/page/query.do")
	public String queryWikiKeywordList(HttpServletRequest request,HttpServletResponse response) throws JoymeException{
		String callback = request.getParameter("callback");
		JSONObject jsonObject = new JSONObject();
        try {

            String pageNum = request.getParameter("pnum");
            String pageSize = request.getParameter("psize");
            String wikiId = request.getParameter("wikiid");
            String name = request.getParameter("name");
            
            
            if (StringUtil.isEmpty(wikiId)) {
            	return WikiResultCode.PARAMTER_NULL.getResultJson(callback);
            }
            if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize)) {
            	return WikiResultCode.PARAMTER_NULL.getResultJson(callback);
            }
            if (!StringUtil.isNumeric(pageNum) || !StringUtil.isNumeric(pageSize)) {
            	return WikiResultCode.PARAMTER_ERROR.getResultJson(callback);
            }
            
            int pnum=Integer.parseInt(pageNum)==0?1:Integer.parseInt(pageNum);
            int psize=Integer.parseInt(pageSize)==0?DEFAULT_PAGE_SIZE:Integer.parseInt(pageSize);
            Pagination page=new Pagination(pnum*psize,pnum,psize);
            if (!StringUtil.isEmpty(name)) {
            	name=URLDecoder.decode(name, "utf-8");
			}
            PageRows<JoymeWikiKeyword> pageRows=joymeWikiKeywordService.queryJoymeWikiKeyword(page, wikiId,name);
            
            jsonObject.put("rs", WikiResultCode.SUCCESS.getCode());
            jsonObject.put("msg", WikiResultCode.SUCCESS.getDesc());
            
            JSONObject retJsonObject=new JSONObject();
            retJsonObject.put("page", pageRows.getPage());
            retJsonObject.put("rows", pageRows.getRows());
            
            jsonObject.put("result", retJsonObject);
            
            return toReponseJsonData(callback, jsonObject);
        } catch (Exception e) {
        	LogService.errorSystemLog("/keyword/page/query.do", e);
        	return WikiResultCode.EXCEPTION.getResultJson(callback);
        }
	}
	
	/**
	 * 添加新的词条
	 * @param request
	 * @return
	 * @throws JoymeException
	 */
	@ResponseBody
	@RequestMapping(value="/keyword/report.do")
	public String addWikiKeyword(HttpServletRequest request,HttpServletResponse response) throws JoymeException{
		String callback = request.getParameter("callback");
		JSONObject jsonObject = new JSONObject();
        try {
            String wikiId = request.getParameter("wikiid");
            String keyword = request.getParameter("keyword");
            String url=request.getParameter("url");
            LogService.infoSystemLog("/keyword/report.do reques data,wikiId:"+wikiId+",keyword:"+keyword+",url:"+url);
            if (StringUtil.isEmpty(wikiId) ||StringUtil.isEmpty(keyword)||StringUtil.isEmpty(url)) {
            	return WikiResultCode.PARAMTER_NULL.getResultJson(callback);
            }
            keyword=URLDecoder.decode(keyword, "utf-8");
            url=URLDecoder.decode(url, "utf-8");
            JoymeWikiKeyword oldJoymeWikiKeyword=joymeWikiKeywordService.getJoymeWikiKeyword(wikiId,keyword);
            if (oldJoymeWikiKeyword!=null) {
            	oldJoymeWikiKeyword.setUrl(url);
            	joymeWikiKeywordService.updateJoymeWikiKeyword(oldJoymeWikiKeyword);
                JoymeWikiCache.addNewWikiKeyword(wikiId, oldJoymeWikiKeyword.getKeywordId());
            	JoymeWikiInfoService.updateWikiKeywordNumById(wikiId,1);
				return WikiResultCode.SUCCESS.getResultJson(callback);
			}
            String keywordId=GenerateIdUtil.getInstance().getDataIdByMD5(wikiId, keyword);

            JoymeWikiKeyword joymeWikiKeyword=new JoymeWikiKeyword();
            joymeWikiKeyword.setKeywordId(keywordId);
            joymeWikiKeyword.setWikiId(wikiId);
            joymeWikiKeyword.setKeyword(keyword);
            joymeWikiKeyword.setStatus(0);
            joymeWikiKeyword.setUrl(url);
            int ret=joymeWikiKeywordService.insertWikiKeyword(joymeWikiKeyword);
            if (ret>0) {
            	JoymeWikiCache.addNewWikiKeyword(wikiId, keywordId);
            	
            	JoymeWikiInfoService.updateWikiKeywordNumById(wikiId,1);
            	
                jsonObject.put("rs", WikiResultCode.SUCCESS.getCode());
                jsonObject.put("msg", WikiResultCode.SUCCESS.getDesc());
                
                JSONObject retJsonObject=new JSONObject();
                retJsonObject.put("keywordId", keywordId);
                
                jsonObject.put("result", retJsonObject);
                
                return toReponseJsonData(callback, jsonObject);
			}else {
                return WikiResultCode.CREATE_KEYWORD_FAILED.getResultJson(callback);
			}

        } catch (Exception e) {
            LogService.errorSystemLog("/keyword/report.do",e);
            return WikiResultCode.EXCEPTION.getResultJson(callback);
        }
	}
	
	/**
	 * 删除词条
	 * @param request
	 * @return
	 * @throws JoymeException
	 */
	@ResponseBody
	@RequestMapping(value="/keyword/delete.do")
	public String deleteWikiKeyword(HttpServletRequest request,HttpServletResponse response) throws JoymeException{
		String callback = request.getParameter("callback");
        try {

            String keywordIds = request.getParameter("keywordIds");
            String wikiId = request.getParameter("wikiid");

            LogService.infoSystemLog("/keyword/delete.do reques data,keywordIds:"+keywordIds);
            if (StringUtil.isEmpty(keywordIds)) {
            	return WikiResultCode.PARAMTER_NULL.getResultJson(callback);
            }
            String[] keywordIdArray=keywordIds.split(",");
            if (!CollectionUtil.isEmpty(keywordIdArray)) {
            	 StringBuilder sb=new StringBuilder();
                 for (int i=0 ; i<keywordIdArray.length;i++) {
                 	JoymeWikiCache.removeNewWikiKeyword(wikiId, keywordIdArray[i]);
     				sb.append("'").append(keywordIdArray[i]).append("'");
     				if(i!=keywordIdArray.length-1){
     					sb.append(",");
     				}
     			}

                 int ret=joymeWikiKeywordService.deleteKeyword(sb.toString());
                 if (ret>0) {
                	 JoymeWikiInfoService.updateWikiKeywordNumById(wikiId,2);
                	 return WikiResultCode.SUCCESS.getResultJson(callback);
     			}else {
     				return WikiResultCode.DELETE_KEYWORD_FAILED.getResultJson(callback);
     			}

			}else {
                return WikiResultCode.PARAMTER_ERROR.getResultJson(callback);
			}
           
        } catch (Exception e) {
            LogService.errorSystemLog("/keyword/delete.do", e);
            return WikiResultCode.EXCEPTION.getResultJson(callback);
        }
	}
	
	/**
	 * 修改词条状态
	 * @param request
	 * @return
	 * @throws JoymeException
	 */
	@ResponseBody
	@RequestMapping(value="/keyword/updatestatus.do")
	public String modifyWikiKeywordStatus(HttpServletRequest request,HttpServletResponse response) throws JoymeException{
		String callback = request.getParameter("callback");
		JSONObject jsonObject = new JSONObject();
        try {

            String wikiId = request.getParameter("wikiid");
            String keywordIds = request.getParameter("keywordids");
            String commandType = request.getParameter("type");
            LogService.infoSystemLog("/keyword/updatastatus.do reques data,keywordids:"+keywordIds+",type:"+commandType+",wikiid:"+wikiId);
            if (StringUtil.isEmpty(commandType) || StringUtil.isEmpty(wikiId)) {
                return WikiResultCode.PARAMTER_NULL.getResultJson(callback);
            }

            int type=Integer.parseInt(commandType);
            int ret=0;
            if(type==1){
            	if(StringUtil.isEmpty(keywordIds)){
                    return WikiResultCode.PARAMTER_NULL.getResultJson(callback);
            	}
            	String[] keywordIdArray=keywordIds.split(",");
                StringBuilder sb=new StringBuilder();


                if (keywordIdArray.length>200) {
                    jsonObject.put("rs", WikiResultCode.KEYWORD_IDS_LENGTH_BEYOND.getCode());
                    jsonObject.put("msg", WikiResultCode.KEYWORD_IDS_LENGTH_BEYOND.getDesc());
                    return toReponseJsonData(callback, jsonObject);
				}
                
                for (int i=0 ; i<keywordIdArray.length;i++) {
                	JoymeWikiCache.removeNewWikiKeyword(wikiId, keywordIdArray[i]);
    				sb.append("'").append(keywordIdArray[i]).append("'");
    				if(i!=keywordIdArray.length-1){
    					sb.append(",");
    				}
    			}
            	
            	ret=joymeWikiKeywordService.updateKeywordStatus(sb.toString(), type);
            }else if (type==2) {
				JoymeWikiCache.removeNewWikiKeyword(wikiId,null);
				ret=joymeWikiKeywordService.updateKeywordStatus(null, type);
			}
            
            if (ret>0) {
                return WikiResultCode.SUCCESS.getResultJson(callback);
			}else {
                
                return WikiResultCode.UPDATE_KEYWORD_FAILED.getResultJson(callback);
			}

        } catch (Exception e) {
            LogService.errorSystemLog(""+e.getMessage());
            return WikiResultCode.EXCEPTION.getResultJson(callback);
        }
	}
	
	private String toReponseJsonData(String callback,JSONObject jsonObject){
        if (StringUtil.isEmpty(callback)) {
            return jsonObject.toString();
        } else {
            return callback + "([" + jsonObject.toString() + "])";
        }
	}
	  

}
