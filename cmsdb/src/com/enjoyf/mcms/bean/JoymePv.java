package com.enjoyf.mcms.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

public class JoymePv {
    private Integer id;
    private String joymePvChannel;
    private Date joymePvDate;
    private Long joymePvCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJoymePvChannel() {
        return joymePvChannel;
    }

    public void setJoymePvChannel(String joymePvChannel) {
        this.joymePvChannel = joymePvChannel;
    }

    public Date getJoymePvDate() {
        return joymePvDate;
    }

    public void setJoymePvDate(Date joymePvDate) {
        this.joymePvDate = joymePvDate;
    }

    public Long getJoymePvCount() {
        return joymePvCount;
    }

    public void setJoymePvCount(Long joymePvCount) {
        this.joymePvCount = joymePvCount;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (joymePvChannel != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_pv_channel");
            bean.setObj(joymePvChannel);
            columnList.add(bean);
        }
        if (joymePvDate != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_pv_date");
            bean.setObj(joymePvDate);
            columnList.add(bean);
        }
        if (joymePvCount != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_pv_count");
            bean.setObj(joymePvCount);
            columnList.add(bean);
        }
        return columnList;
    }
}
