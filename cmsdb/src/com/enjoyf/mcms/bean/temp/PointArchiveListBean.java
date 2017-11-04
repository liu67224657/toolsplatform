package com.enjoyf.mcms.bean.temp;

import java.util.List;

import com.enjoyf.mcms.bean.JoymePoint;

public class PointArchiveListBean {
    public JoymePoint point = null;
    public List archiveList = null;

    public JoymePoint getPoint() {
        return point;
    }

    public void setPoint(JoymePoint point) {
        this.point = point;
    }

    public List getArchiveList() {
        return archiveList;
    }

    public void setArchiveList(List archiveList) {
        this.archiveList = archiveList;
    }
}
