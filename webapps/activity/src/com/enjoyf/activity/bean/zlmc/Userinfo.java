package com.enjoyf.activity.bean.zlmc;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Userinfo {
    private String profileid;
    private String username;
    private String nickname;
    private String duty;
    private String company;
    private String representativeWork;
    private String telephone;
    private String address;
    private Date createTime;

    public String getProfileid() {
        return profileid;
    }

    public void setProfileid(String profileid) {
        this.profileid = profileid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRepresentativeWork() {
        return representativeWork;
    }

    public void setRepresentativeWork(String representativeWork) {
        this.representativeWork = representativeWork;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (profileid != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("profileid");
            bean.setObj(profileid);
            columnList.add(bean);
        }
        if (username != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("username");
            bean.setObj(username);
            columnList.add(bean);
        }
        if (nickname != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("nickname");
            bean.setObj(nickname);
            columnList.add(bean);
        }
        if (telephone != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("telephone");
            bean.setObj(telephone);
            columnList.add(bean);
        }
        if (address != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("address");
            bean.setObj(address);
            columnList.add(bean);
        }
        if (duty != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("duty");
            bean.setObj(duty);
            columnList.add(bean);
        }
        if (company != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("company");
            bean.setObj(company);
            columnList.add(bean);
        }
        if (representativeWork != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("representative_work");
            bean.setObj(representativeWork);
            columnList.add(bean);
        }
        if (createTime != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("create_time");
            bean.setObj(createTime);
            columnList.add(bean);
        }
        return columnList;
    }
}


