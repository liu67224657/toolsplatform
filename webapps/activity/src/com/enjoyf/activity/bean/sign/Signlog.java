package com.enjoyf.activity.bean.sign;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Signlog {
    private String  signlogId;
    private String  profileId;
    private Date signDate;
    private String  gamecode;
    private Integer  point;
    private Timestamp createTime;
    private Long  signId;

 public String getSignlogId(){
    return signlogId;
  }

 public void setSignlogId(String signlogId){
    this.signlogId = signlogId;
  }

 public String getProfileId(){
    return profileId;
  }

 public void setProfileId(String profileId){
    this.profileId = profileId;
  }

 public Date getSignDate(){
    return signDate;
  }

 public void setSignDate(Date signDate){
    this.signDate = signDate;
  }

 public String getGamecode(){
    return gamecode;
  }

 public void setGamecode(String gamecode){
    this.gamecode = gamecode;
  }

 public Integer getPoint(){
    return point;
  }

 public void setPoint(Integer point){
    this.point = point;
  }

 public Timestamp getCreateTime(){
    return createTime;
  }

 public void setCreateTime(Timestamp createTime){
    this.createTime = createTime;
  }

 public Long getSignId(){
    return signId;
  }

 public void setSignId(Long signId){
    this.signId = signId;
  }


public List getNotNullColumnList(){
  List columnList = new ArrayList();
   if(signlogId != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("signlog_id");
    bean.setObj(signlogId);
    columnList.add(bean);
   }
   if(profileId != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("profile_id");
    bean.setObj(profileId);
    columnList.add(bean);
   }
   if(signDate != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("sign_date");
    bean.setObj(signDate);
    columnList.add(bean);
   }
   if(gamecode != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("gamecode");
    bean.setObj(gamecode);
    columnList.add(bean);
   }
   if(point != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("point");
    bean.setObj(point);
    columnList.add(bean);
   }
   if(createTime != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("create_time");
    bean.setObj(createTime);
    columnList.add(bean);
   }
   if(signId != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("sign_id");
    bean.setObj(signId);
    columnList.add(bean);
   }
  return columnList;
  }
 }


