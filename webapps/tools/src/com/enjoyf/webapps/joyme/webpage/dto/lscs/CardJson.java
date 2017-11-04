package com.enjoyf.webapps.joyme.webpage.dto.lscs;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-10-16 下午3:35
 * Description:
 */
public class CardJson {
    private String type;
    private CardObject object;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CardObject getObject() {
        return object;
    }

    public void setObject(CardObject object) {
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
