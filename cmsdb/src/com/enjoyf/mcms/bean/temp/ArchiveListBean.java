package com.enjoyf.mcms.bean.temp;

import java.util.List;

public class ArchiveListBean {
    private boolean hasNextPage;
    private List archiveList;
    public boolean isHasNextPage() {
        return hasNextPage;
    }
    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
    public List getArchiveList() {
        return archiveList;
    }
    public void setArchiveList(List archiveList) {
        this.archiveList = archiveList;
    }
}
