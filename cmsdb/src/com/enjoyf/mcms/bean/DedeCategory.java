package com.enjoyf.mcms.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.util.ArrayList;
import java.util.List;

public class DedeCategory {
	private Integer id;
	private String typeName;
	private String typeColor;
	private Integer typeStatus;
	private Integer typeArticle;
	private String url;

	public Integer getTypeArticle() {
		return typeArticle;
	}

	public void setTypeArticle(Integer typeArticle) {
		this.typeArticle = typeArticle;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeColor() {
		return typeColor;
	}

	public void setTypeColor(String typeColor) {
		this.typeColor = typeColor;
	}

	public Integer getTypeStatus() {
		return typeStatus;
	}

	public void setTypeStatus(Integer typeStatus) {
		this.typeStatus = typeStatus;
	}


	public List getNotNullColumnList() {
		List columnList = new ArrayList();
		if (typeName != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("typeName");
			bean.setObj(typeName);
			columnList.add(bean);
		}
		if (typeColor != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("typeColor");
			bean.setObj(typeColor);
			columnList.add(bean);
		}
		if (typeStatus != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("typeStatus");
			bean.setObj(typeStatus);
			columnList.add(bean);
		}
		if (typeArticle != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("typeArticle");
			bean.setObj(typeArticle);
			columnList.add(bean);
		}
		if (url != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("url");
			bean.setObj(url);
			columnList.add(bean);
		}
		return columnList;
	}
}


