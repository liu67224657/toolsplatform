package com.enjoyf.wiki.bean;

import java.util.List;

public class PageBean {
    private List retList;
    private boolean hasNextPage;
    public List getRetList() {
        return retList;
    }
    public void setRetList(List retList) {
        this.retList = retList;
    }
    public boolean isHasNextPage() {
        return hasNextPage;
    }
    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}
