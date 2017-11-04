package com.enjoyf.wiki.bean.temp;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-11-25 下午2:11
 * Description:
 */
public class WikiParamBean {
    private String pageName;
    private String key;

    public WikiParamBean(String pageName, String key) {
        this.pageName = pageName;
        this.key = key;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "WikiParamBean{" +
                "pageName='" + pageName + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
