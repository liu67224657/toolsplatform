package com.mobcent.codetool.bean;

import java.util.List;

public class GenerateBean {
    private String className;
    private String instanceName;
    private List columnList;
    private List whereColumnList;
    private ErrorCodeBean errorCodebean;


    public String getClassName() {
        return className;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public List getColumnList() {
        return columnList;
    }

    public List getWhereColumnList() {
        return whereColumnList;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public void setColumnList(List columnList) {
        this.columnList = columnList;
    }

    public void setWhereColumnList(List whereColumnList) {
        this.whereColumnList = whereColumnList;
    }

    public ErrorCodeBean getErrorCodebean() {
        return errorCodebean;
    }

    public void setErrorCodebean(ErrorCodeBean errorCodebean) {
        this.errorCodebean = errorCodebean;
    }
}
