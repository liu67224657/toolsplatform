package com.enjoyf.webcache.bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

public class JoymeRefresh {
	private Integer freshId;
	private String freshContent;
	private Integer machineId;
	private Timestamp operationTime;
	private String operationUser;

	public Integer getFreshId() {
		return freshId;
	}

	public void setFreshId(Integer freshId) {
		this.freshId = freshId;
	}

	public String getFreshContent() {
		return freshContent;
	}

	public void setFreshContent(String freshContent) {
		this.freshContent = freshContent;
	}

	public Integer getMachineId() {
		return machineId;
	}

	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}

	public Timestamp getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Timestamp operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperationUser() {
		return operationUser;
	}

	public void setOperationUser(String operationUser) {
		this.operationUser = operationUser;
	}

	public List getNotNullColumnList() {
		List columnList = new ArrayList();
		if (freshContent != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("fresh_content");
			bean.setObj(freshContent);
			columnList.add(bean);
		}
		if (machineId != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("machine_id");
			bean.setObj(machineId);
			columnList.add(bean);
		}
		if (operationTime != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("operation_time");
			bean.setObj(operationTime);
			columnList.add(bean);
		}
		if (operationUser != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("operation_user");
			bean.setObj(operationUser);
			columnList.add(bean);
		}
		return columnList;
	}
}
