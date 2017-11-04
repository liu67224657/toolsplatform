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
public class CategoryJson {
    private String type;
    private Category object;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Category getObject() {
        return object;
    }

    public void setObject(Category object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "CategoryJson{" +
                "type='" + type + '\'' +
                ", object=" + object +
                '}';
    }
}
