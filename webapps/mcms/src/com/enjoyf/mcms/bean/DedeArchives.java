package com.enjoyf.mcms.bean;

import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

public class DedeArchives {
    private Integer id;
    private Integer typeid;
    private String typeid2;
    private Integer sortrank;
    private Object flag;
    private Integer ismake;
    private Integer channel;
    private Integer arcrank;
    private Object click;
    private Integer money;
    private String title;
    private String shorttitle;
    private String color;
    private String writer;
    private String source;
    private String litpic;
    private Integer pubdate;
    private Integer senddate;
    private Object mid;
    private String keywords;
    private Integer lastpost;
    private Object scores;
    private Object goodpost;
    private Object badpost;
    private Boolean notpost;
    private String description;
    private String filename;
    private Object dutyadmin;
    private Integer tackid;
    private Object mtype;
    private Integer voteid;
    private Integer weight;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getTypeid2() {
        return typeid2;
    }

    public void setTypeid2(String typeid2) {
        this.typeid2 = typeid2;
    }

    public Integer getSortrank() {
        return sortrank;
    }

    public void setSortrank(Integer sortrank) {
        this.sortrank = sortrank;
    }

    public Object getFlag() {
        return flag;
    }

    public void setFlag(Object flag) {
        this.flag = flag;
    }

    public Integer getIsmake() {
        return ismake;
    }

    public void setIsmake(Integer ismake) {
        this.ismake = ismake;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getArcrank() {
        return arcrank;
    }

    public void setArcrank(Integer arcrank) {
        this.arcrank = arcrank;
    }

    public Object getClick() {
        return click;
    }

    public void setClick(Object click) {
        this.click = click;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShorttitle() {
        return shorttitle;
    }

    public void setShorttitle(String shorttitle) {
        this.shorttitle = shorttitle;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }

    public Integer getPubdate() {
        return pubdate;
    }

    public void setPubdate(Integer pubdate) {
        this.pubdate = pubdate;
    }

    public Integer getSenddate() {
        return senddate;
    }

    public void setSenddate(Integer senddate) {
        this.senddate = senddate;
    }

    public Object getMid() {
        return mid;
    }

    public void setMid(Object mid) {
        this.mid = mid;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getLastpost() {
        return lastpost;
    }

    public void setLastpost(Integer lastpost) {
        this.lastpost = lastpost;
    }

    public Object getScores() {
        return scores;
    }

    public void setScores(Object scores) {
        this.scores = scores;
    }

    public Object getGoodpost() {
        return goodpost;
    }

    public void setGoodpost(Object goodpost) {
        this.goodpost = goodpost;
    }

    public Object getBadpost() {
        return badpost;
    }

    public void setBadpost(Object badpost) {
        this.badpost = badpost;
    }

    public Boolean getNotpost() {
        return notpost;
    }

    public void setNotpost(Boolean notpost) {
        this.notpost = notpost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Object getDutyadmin() {
        return dutyadmin;
    }

    public void setDutyadmin(Object dutyadmin) {
        this.dutyadmin = dutyadmin;
    }

    public Integer getTackid() {
        return tackid;
    }

    public void setTackid(Integer tackid) {
        this.tackid = tackid;
    }

    public Object getMtype() {
        return mtype;
    }

    public void setMtype(Object mtype) {
        this.mtype = mtype;
    }

    public Integer getVoteid() {
        return voteid;
    }

    public void setVoteid(Integer voteid) {
        this.voteid = voteid;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (id != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("id");
            bean.setObj(id);
            columnList.add(bean);
        }
        if (typeid != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("typeid");
            bean.setObj(typeid);
            columnList.add(bean);
        }
        if (typeid2 != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("typeid2");
            bean.setObj(typeid2);
            columnList.add(bean);
        }
        if (sortrank != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("sortrank");
            bean.setObj(sortrank);
            columnList.add(bean);
        }
        if (flag != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("flag");
            bean.setObj(flag);
            columnList.add(bean);
        }
        if (ismake != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("ismake");
            bean.setObj(ismake);
            columnList.add(bean);
        }
        if (channel != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("channel");
            bean.setObj(channel);
            columnList.add(bean);
        }
        if (arcrank != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("arcrank");
            bean.setObj(arcrank);
            columnList.add(bean);
        }
        if (click != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("click");
            bean.setObj(click);
            columnList.add(bean);
        }
        if (money != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("money");
            bean.setObj(money);
            columnList.add(bean);
        }
        if (title != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("title");
            bean.setObj(title);
            columnList.add(bean);
        }
        if (shorttitle != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("shorttitle");
            bean.setObj(shorttitle);
            columnList.add(bean);
        }
        if (color != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("color");
            bean.setObj(color);
            columnList.add(bean);
        }
        if (writer != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("writer");
            bean.setObj(writer);
            columnList.add(bean);
        }
        if (source != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("source");
            bean.setObj(source);
            columnList.add(bean);
        }
        if (litpic != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("litpic");
            bean.setObj(litpic);
            columnList.add(bean);
        }
        if (pubdate != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("pubdate");
            bean.setObj(pubdate);
            columnList.add(bean);
        }
        if (senddate != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("senddate");
            bean.setObj(senddate);
            columnList.add(bean);
        }
        if (mid != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("mid");
            bean.setObj(mid);
            columnList.add(bean);
        }
        if (keywords != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("keywords");
            bean.setObj(keywords);
            columnList.add(bean);
        }
        if (lastpost != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("lastpost");
            bean.setObj(lastpost);
            columnList.add(bean);
        }
        if (scores != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("scores");
            bean.setObj(scores);
            columnList.add(bean);
        }
        if (goodpost != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("goodpost");
            bean.setObj(goodpost);
            columnList.add(bean);
        }
        if (badpost != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("badpost");
            bean.setObj(badpost);
            columnList.add(bean);
        }
        if (notpost != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("notpost");
            bean.setObj(notpost);
            columnList.add(bean);
        }
        if (description != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("description");
            bean.setObj(description);
            columnList.add(bean);
        }
        if (filename != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("filename");
            bean.setObj(filename);
            columnList.add(bean);
        }
        if (dutyadmin != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("dutyadmin");
            bean.setObj(dutyadmin);
            columnList.add(bean);
        }
        if (tackid != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("tackid");
            bean.setObj(tackid);
            columnList.add(bean);
        }
        if (mtype != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("mtype");
            bean.setObj(mtype);
            columnList.add(bean);
        }
        if (voteid != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("voteid");
            bean.setObj(voteid);
            columnList.add(bean);
        }
        if (weight != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("weight");
            bean.setObj(weight);
            columnList.add(bean);
        }
        return columnList;
    }
}
