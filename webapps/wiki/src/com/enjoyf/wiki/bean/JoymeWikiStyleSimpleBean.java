package com.enjoyf.wiki.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JoymeWikiStyleSimpleBean {
    private Integer joymeWikiStyleId;
    private Long versionNum;

    public Integer getJoymeWikiStyleId() {
        return joymeWikiStyleId;
    }

    public void setJoymeWikiStyleId(Integer joymeWikiStyleId) {
        this.joymeWikiStyleId = joymeWikiStyleId;
    }

    public Long getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Long versionNum) {
        this.versionNum = versionNum;
    }

    //    public List getNotNullColumnList() {
//        List columnList = new ArrayList();
//
//        if (versionNum != null) {
//            NotNullColumnBean bean = new NotNullColumnBean();
//            bean.setColumnName("version_num");
//            bean.setObj(versionNum);
//            columnList.add(bean);
//        }
//        return columnList;
//    }
}

