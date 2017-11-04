package com.enjoyf.webcache.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * wiki标题信息
 *
 * @author huazhang
 */
public class JoymeWikiInfo implements Serializable {

    private String wikiId;
    private String wikiName;
    private int keywordNum;
    private Date createTime;
    private int newKeywordNum;
    private String wikiKey;

    public String getWikiId() {
        return wikiId;
    }

    public void setWikiId(String wikiId) {
        this.wikiId = wikiId;
    }

    public String getWikiName() {
        return wikiName;
    }

    public void setWikiName(String wikiName) {
        this.wikiName = wikiName;
    }

    public int getKeywordNum() {
        return keywordNum;
    }

    public void setKeywordNum(int keywordNum) {
        this.keywordNum = keywordNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getNewKeywordNum() {
        return newKeywordNum;
    }

    public void setNewKeywordNum(int newKeywordNum) {
        this.newKeywordNum = newKeywordNum;
    }

	public String getWikiKey() {
		return wikiKey;
	}

	public void setWikiKey(String wikiKey) {
		this.wikiKey = wikiKey;
	}

}
