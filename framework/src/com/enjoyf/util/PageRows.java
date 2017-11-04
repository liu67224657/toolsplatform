package com.enjoyf.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhitaoshi on 2015/7/6.
 */
public class PageRows<T> implements Serializable {

    private Pagination page;
    private List<T> rows = new ArrayList<T>();

    public Pagination getPage() {
        return page;
    }

    public void setPage(Pagination page) {
        this.page = page;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
