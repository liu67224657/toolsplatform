package com.mobcent.codetool.bean;

public class ErrorCodeBean {
    private String getErrorString;
    private String insertErrorString;
    private String updateErrorString;
    private String deleteErrorString;

    public String getGetErrorString() {
        return getErrorString;
    }

    public void setGetErrorString(String getErrorString) {
        this.getErrorString = getErrorString;
    }

    public String getInsertErrorString() {
        return insertErrorString;
    }

    public void setInsertErrorString(String insertErrorString) {
        this.insertErrorString = insertErrorString;
    }

    public String getUpdateErrorString() {
        return updateErrorString;
    }

    public void setUpdateErrorString(String updateErrorString) {
        this.updateErrorString = updateErrorString;
    }

    public String getDeleteErrorString() {
        return deleteErrorString;
    }

    public void setDeleteErrorString(String deleteErrorString) {
        this.deleteErrorString = deleteErrorString;
    }
}
