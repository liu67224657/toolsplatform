package com.enjoyf.webapps.joyme.webpage.dto.lscs;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-10-16 下午4:24
 * Description:
 */

/**
 * [{"categoryId":1,"categoryName":"战士","picUrl":"zhanshi"},
 {"categoryId":2,"categoryName":"圣骑士","picUrl":"shengqishi"},
 {"categoryId":3,"categoryName":"萨满","picUrl":"shaman"},
 {"categoryId":4,"categoryName":"猎人","picUrl":"lieren"},
 {"categoryId":5,"categoryName":"盗贼","picUrl":"daozei"},
 {"categoryId":6,"categoryName":"德鲁伊","picUrl":"deluyi"},
 {"categoryId":7,"categoryName":"法师","picUrl":"fashi"},
 {"categoryId":8,"categoryName":"牧师","picUrl":"mushi"},
 {"categoryId":9,"categoryName":"术士","picUrl":"shushi"}]

 */
public class Category {
    private int categoryId;
    private String categoryName;
    private String picUrl;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }
}
