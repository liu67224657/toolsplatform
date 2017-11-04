package com.enjoyf.activity.bean.point;


import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.util.ArrayList;
import java.util.List;

public class Point {
    private String pointId;
    private String profileid;
    private String gamecode;
    private Integer point;

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getProfileid() {
        return profileid;
    }

    public void setProfileid(String profileid) {
        this.profileid = profileid;
    }

    public String getGamecode() {
        return gamecode;
    }

    public void setGamecode(String gamecode) {
        this.gamecode = gamecode;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }


    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (pointId != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("point_id");
            bean.setObj(pointId);
            columnList.add(bean);
        }
        if (profileid != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("profileid");
            bean.setObj(profileid);
            columnList.add(bean);
        }
        if (gamecode != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("gamecode");
            bean.setObj(gamecode);
            columnList.add(bean);
        }
        if (point != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("point");
            bean.setObj(point);
            columnList.add(bean);
        }
        return columnList;
    }
}


