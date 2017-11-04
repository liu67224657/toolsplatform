package com.enjoyf.framework.jdbc.bean;

import java.util.ArrayList;
import java.util.List;

public class PageQueryBean {
    public boolean hasNextPage = false;
    public List resultList = new ArrayList();
    public int totalNum = 0;

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public List getResultList() {
        return resultList;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public void setResultList(List resultList) {
        this.resultList = resultList;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
}
