package com.enjoyf.webcache.constant;

import net.sf.json.JSONObject;

import com.enjoyf.util.StringUtil;

/**
 * 基础request返回码
 * @author huazhang
 *
 */
public class BaseResultCode {
	
	protected static final int WIKI_BASE_CODE=100;
	
	public static final BaseResultCode FAILED = new BaseResultCode(0, "失败");
	
	public static final BaseResultCode SUCCESS = new BaseResultCode(1, "成功");
	
	public static final BaseResultCode EXCEPTION=new BaseResultCode(2, "系统异常");
	
	public static final BaseResultCode PARAMTER_NULL=new BaseResultCode(3, "参数空");
	
	public static final BaseResultCode PARAMTER_ERROR=new BaseResultCode(4, "参数错误");
	
	public int code;
	
	public String desc;
	
	public BaseResultCode(int code, String desc){
		this.code=code;
		this.desc=desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getResultJson(String callback){
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("rs", this.getCode());
		jsonObject.put("msg", this.getDesc());
        if (StringUtil.isEmpty(callback)) {
            return jsonObject.toString();
        } else {
            return callback + "([" + jsonObject.toString() + "])";
        }
	}
	
}
