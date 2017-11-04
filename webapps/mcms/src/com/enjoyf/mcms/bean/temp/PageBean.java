package com.enjoyf.mcms.bean.temp;

import java.io.Serializable;
import java.util.List;

public class PageBean implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -9076674144442494430L;
    private List retList;
    private boolean hasNextPage;
    private NavigateBean navigate;

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

    public NavigateBean getNavigate() {
        return navigate;
    }

    public void setNavigate(NavigateBean navigate) {
        this.navigate = navigate;
    }
}
