package com.enjoyf.util;

import com.enjoyf.util.bean.PageBean;

public class SQLUtil {
    public static PageBean getPageString(String sql, int start, int length) {
        PageBean bean = new PageBean();
        String postsql = sql + " LIMIT ? , ?";
        bean.setSql(postsql);
        bean.setStart(start + 1);
        bean.setEnd(start + length);
        return bean;
    }

    public static PageBean getPageStringByPage(String sql, int pageNum, int pageCount) {
        int start = (pageNum - 1) * pageCount;
        int end = pageNum * pageCount;
        return getPageString(sql, start, end);
    }
}
