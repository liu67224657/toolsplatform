package com.enjoyf.framework.jdbc.bean;

public class ParameterBean {
    public ParameterBean(int type, Object value) {
        this.type = type;
        this.value = value;
    }

    public ParameterBean(int type, Object value, int length) {
        this.type = type;
        this.value = value;
        this.length = length;
    }

    private int type;
    private Object value;
    private int length;

    public int getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
