package com.enjoyf.mcms.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-7-15
 * Time: 下午4:33
 * To change this template use File | Settings | File Templates.
 */
public class ClientLineItem  implements Serializable {
	private Long item_id;
	private Long line_id;
	private String title;
	private Long direct_id;

	public Long getItem_id() {
		return item_id;
	}

	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}

	public Long getLine_id() {
		return line_id;
	}

	public void setLine_id(Long line_id) {
		this.line_id = line_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getDirect_id() {
		return direct_id;
	}

	public void setDirect_id(Long direct_id) {
		this.direct_id = direct_id;
	}
}
