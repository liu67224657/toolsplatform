package com.enjoyf.webcache.constant;

public class WikiResultCode extends BaseResultCode{
	
	public static final WikiResultCode CREATE_WIKI_FAILED=new WikiResultCode(WIKI_BASE_CODE+1, "添加wiki失败");
	public static final WikiResultCode DELETE_WIKI_FAILED=new WikiResultCode(WIKI_BASE_CODE+2, "删除wiki失败");
	public static final WikiResultCode CREATE_KEYWORD_FAILED=new WikiResultCode(WIKI_BASE_CODE+3, "添加keyword失败");
	public static final WikiResultCode DELETE_KEYWORD_FAILED=new WikiResultCode(WIKI_BASE_CODE+4, "删除keyword失败");
	public static final WikiResultCode UPDATE_KEYWORD_FAILED=new WikiResultCode(WIKI_BASE_CODE+5, "更新keyword失败");
	public static final WikiResultCode KEYWORD_IDS_LENGTH_BEYOND=new WikiResultCode(WIKI_BASE_CODE+6, "批量keywordId不能超过200");
	public static final WikiResultCode KEYWORD_EXISTS=new WikiResultCode(WIKI_BASE_CODE+7, "keyword已存在");

	public WikiResultCode(int code, String desc) {
		super(code, desc);
		// TODO Auto-generated constructor stub
	}

}
