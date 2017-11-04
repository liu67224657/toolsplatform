package com.enjoyf.adminuser.bean;

import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;
public class JoymeUser {
    private Integer  userId;
    private String  userName;
    private String  userPassword;
    private String  userTrueName;
    private List propertiesList;
 public Integer getUserId(){
    return userId;
  }

 public void setUserId(Integer userId){
    this.userId = userId;
  }

 public String getUserName(){
    return userName;
  }

 public void setUserName(String userName){
    this.userName = userName;
  }

 public String getUserPassword(){
    return userPassword;
  }

 public void setUserPassword(String userPassword){
    this.userPassword = userPassword;
  }

 public String getUserTrueName(){
    return userTrueName;
  }

 public void setUserTrueName(String userTrueName){
    this.userTrueName = userTrueName;
  }


public List getPropertiesList() {
    return propertiesList;
}

public void setPropertiesList(List propertiesList) {
    this.propertiesList = propertiesList;
}

public List getNotNullColumnList(){
  List columnList = new ArrayList();
   if(userName != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("user_name");
    bean.setObj(userName);
    columnList.add(bean);
   }
   if(userPassword != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("user_password");
    bean.setObj(userPassword);
    columnList.add(bean);
   }
   if(userTrueName != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("user_true_name");
    bean.setObj(userTrueName);
    columnList.add(bean);
   }
  return columnList;
  }
 }


