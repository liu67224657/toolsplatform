package com.enjoyf.framework.jdbc.bean;

public class NotNullColumnBean {
    private String columnName;
    private Object obj;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
