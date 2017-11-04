package com.enjoyf.activity.bean.sign;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.util.ArrayList;
import java.util.List;

public class Sign {
    private Long signId;
    private Integer point;
    private String gamecode;
    private String signname;
    private String successText;
    private String failedText;

    public Long getSignId() {
        return signId;
    }

    public void setSignId(Long signId) {
        this.signId = signId;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getGamecode() {
        return gamecode;
    }

    public void setGamecode(String gamecode) {
        this.gamecode = gamecode;
    }

    public String getSignname() {
        return signname;
    }

    public void setSignname(String signname) {
        this.signname = signname;
    }

    public String getSuccessText() {
        return successText;
    }

    public void setSuccessText(String successText) {
        this.successText = successText;
    }

    public String getFailedText() {
        return failedText;
    }

    public void setFailedText(String failedText) {
        this.failedText = failedText;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (point != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("point");
            bean.setObj(point);
            columnList.add(bean);
        }
        if (gamecode != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("gamecode");
            bean.setObj(gamecode);
            columnList.add(bean);
        }
        if (signname != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("signname");
            bean.setObj(signname);
            columnList.add(bean);
        }
        if (successText != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("success_text");
            bean.setObj(signname);
            columnList.add(bean);
        }
        if (failedText != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("failed_text");
            bean.setObj(failedText);
            columnList.add(bean);
        }
        return columnList;
    }
}


