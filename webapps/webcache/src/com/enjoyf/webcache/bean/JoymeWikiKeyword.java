package com.enjoyf.webcache.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * wiki词条
 * 
 * @author huazhang
 *
 */
public class JoymeWikiKeyword implements Serializable {

	private String keywordId;
	private String keyword;
	private String wikiId;
	private int status;
	private Date createTime;
	private String url;

	public String getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(String keywordId) {
		this.keywordId = keywordId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getWikiId() {
		return wikiId;
	}

	public void setWikiId(String wikiId) {
		this.wikiId = wikiId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
