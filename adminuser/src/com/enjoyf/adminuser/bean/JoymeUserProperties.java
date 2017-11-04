package com.enjoyf.adminuser.bean;

import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;
public class JoymeUserProperties {
    private Integer  userPropertiesId;
    private Integer  userId;
    private String  userPropertiesKey;
    private String  userPropertiesValue;

 public Integer getUserPropertiesId(){
    return userPropertiesId;
  }

 public void setUserPropertiesId(Integer userPropertiesId){
    this.userPropertiesId = userPropertiesId;
  }

 public Integer getUserId(){
    return userId;
  }

 public void setUserId(Integer userId){
    this.userId = userId;
  }

 public String getUserPropertiesKey(){
    return userPropertiesKey;
  }

 public void setUserPropertiesKey(String userPropertiesKey){
    this.userPropertiesKey = userPropertiesKey;
  }

 public String getUserPropertiesValue(){
    return userPropertiesValue;
  }

 public void setUserPropertiesValue(String userPropertiesValue){
    this.userPropertiesValue = userPropertiesValue;
  }


public List getNotNullColumnList(){
  List columnList = new ArrayList();
   if(userId != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("user_id");
    bean.setObj(userId);
    columnList.add(bean);
   }
   if(userPropertiesKey != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("user_properties_key");
    bean.setObj(userPropertiesKey);
    columnList.add(bean);
   }
   if(userPropertiesValue != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("user_properties_value");
    bean.setObj(userPropertiesValue);
    columnList.add(bean);
   }
  return columnList;
  }
 }


