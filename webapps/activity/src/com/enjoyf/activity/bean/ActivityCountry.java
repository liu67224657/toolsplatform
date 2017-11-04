package com.enjoyf.activity.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhimingli on 2016/8/1 0001.
 */
public class ActivityCountry implements Serializable {
    private Integer id;
    private String profileid;
    private String activity_code;
    private Integer score;
    private String country;
    private ValidStatus valid_status = ValidStatus.VALID;
    private Date create_date;
    private String create_ip;

    private String nickname;
    private String headimgurl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfileid() {
        return profileid;
    }

    public void setProfileid(String profileid) {
        this.profileid = profileid;
    }

    public String getActivity_code() {
        return activity_code;
    }

    public void setActivity_code(String activity_code) {
        this.activity_code = activity_code;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ValidStatus getValid_status() {
        return valid_status;
    }

    public void setValid_status(ValidStatus valid_status) {
        this.valid_status = valid_status;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getCreate_ip() {
        return create_ip;
    }

    public void setCreate_ip(String create_ip) {
        this.create_ip = create_ip;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();

        if (this.id != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("id");
            bean.setObj(this.id);
            columnList.add(bean);
        }

        if (this.profileid != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("profileid");
            bean.setObj(this.profileid);
            columnList.add(bean);
        }
        if (this.activity_code != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("activity_code");
            bean.setObj(this.activity_code);
            columnList.add(bean);
        }

        if (this.score != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("score");
            bean.setObj(this.score);
            columnList.add(bean);
        }

        if (this.country != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("country");
            bean.setObj(this.country);
            columnList.add(bean);
        }

        if (this.valid_status != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("valid_status");
            bean.setObj(valid_status.getCode());
            columnList.add(bean);
        }

        if (this.create_date != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("create_date");
            bean.setObj(this.create_date);
            columnList.add(bean);
        }

        if (this.create_ip != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("create_ip");
            bean.setObj(this.create_ip);
            columnList.add(bean);
        }

        if (this.nickname != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("nickname");
            bean.setObj(this.nickname);
            columnList.add(bean);
        }

        if (this.headimgurl != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("headimgurl");
            bean.setObj(this.headimgurl);
            columnList.add(bean);
        }
        return columnList;
    }

    
}
