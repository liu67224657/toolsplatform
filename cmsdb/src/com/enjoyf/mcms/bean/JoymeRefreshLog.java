package com.enjoyf.mcms.bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

public class JoymeRefreshLog {
    private Integer freshLogId;
    private Integer machineId;
    private Integer freshId;
    private Timestamp operationTime;

    public Integer getFreshLogId() {
        return freshLogId;
    }

    public void setFreshLogId(Integer freshLogId) {
        this.freshLogId = freshLogId;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public Integer getFreshId() {
        return freshId;
    }

    public void setFreshId(Integer freshId) {
        this.freshId = freshId;
    }

    public Timestamp getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Timestamp operationTime) {
        this.operationTime = operationTime;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (machineId != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("machine_id");
            bean.setObj(machineId);
            columnList.add(bean);
        }
        if (freshId != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("fresh_id");
            bean.setObj(freshId);
            columnList.add(bean);
        }
        if (operationTime != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("operation_time");
            bean.setObj(operationTime);
            columnList.add(bean);
        }
        return columnList;
    }
}
