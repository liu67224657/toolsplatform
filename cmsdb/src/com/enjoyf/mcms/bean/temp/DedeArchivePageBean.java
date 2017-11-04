package com.enjoyf.mcms.bean.temp;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-11-19 下午1:45
 * Description:
 */
public class DedeArchivePageBean {
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
