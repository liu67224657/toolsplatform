package com.mobcent.codetool.bean;

public class ColumnBean {
    private String columnName;
    private boolean isPK;
    private String typeName;
    private int typelength = 0;
    private String columnType;
    private boolean isAutoCreate = false;
    private String columnComment;
    private String table;
    //别名
    private String columnLabel;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isPK() {
        return isPK;
    }

    public void setPK(boolean isPK) {
        this.isPK = isPK;
    }

    public boolean isAutoCreate() {
        return isAutoCreate;
    }

    public void setAutoCreate(boolean isAutoCreate) {
        this.isAutoCreate = isAutoCreate;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTypelength() {
        return typelength;
    }

    public void setTypelength(int typelength) {
        this.typelength = typelength;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumnLabel() {
        return columnLabel;
    }

    public void setColumnLabel(String columnLabel) {
        this.columnLabel = columnLabel;
    }
}
