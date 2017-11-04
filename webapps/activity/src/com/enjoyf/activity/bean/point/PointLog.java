package com.enjoyf.activity.bean.point;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PointLog {
    private Long  pointlogId;
    private String  profileid;
    private String  gamecode;
    private Integer  reason;
    private Integer  point;
    private Timestamp createTime;

 public Long getPointlogId(){
    return pointlogId;
  }

 public void setPointlogId(Long pointlogId){
    this.pointlogId = pointlogId;
  }

 public String getProfileid(){
    return profileid;
  }

 public void setProfileid(String profileid){
    this.profileid = profileid;
  }

 public String getGamecode(){
    return gamecode;
  }

 public void setGamecode(String gamecode){
    this.gamecode = gamecode;
  }

 public Integer getReason(){
    return reason;
  }

 public void setReason(Integer reason){
    this.reason = reason;
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


public List getNotNullColumnList(){
  List columnList = new ArrayList();
   if(pointlogId != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("pointlog_id");
    bean.setObj(pointlogId);
    columnList.add(bean);
   }
   if(profileid != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("profileid");
    bean.setObj(profileid);
    columnList.add(bean);
   }
   if(gamecode != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("gamecode");
    bean.setObj(gamecode);
    columnList.add(bean);
   }
   if(reason != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("reason");
    bean.setObj(reason);
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
  return columnList;
  }
 }


