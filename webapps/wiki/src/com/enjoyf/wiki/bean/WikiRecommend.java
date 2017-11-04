package com.enjoyf.wiki.bean;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-7-22
 * Time: 下午12:17
 * To change this template use File | Settings | File Templates.
 */
public class WikiRecommend {
	private Long id;
	private String wiki_key;
	private String title;
	private Long pageid;
	private String php_url;
	private Integer type; //1为访问量 2最近修改
	private Integer pv_count;
	private Date create_time;
	private Integer page_status;

	public Long getPageid() {
		return pageid;
	}

	public void setPageid(Long pageid) {
		this.pageid = pageid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWiki_key() {
		return wiki_key;
	}

	public void setWiki_key(String wiki_key) {
		this.wiki_key = wiki_key;
	}

	public String getPhp_url() {
		return php_url;
	}

	public void setPhp_url(String php_url) {
		this.php_url = php_url;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Integer getPage_status() {
		return page_status;
	}

	public void setPage_status(Integer page_status) {
		this.page_status = page_status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPv_count() {
		return pv_count;
	}

	public void setPv_count(Integer pv_count) {
		this.pv_count = pv_count;
	}

	@Override
	public int hashCode() {
		return title.hashCode();
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
