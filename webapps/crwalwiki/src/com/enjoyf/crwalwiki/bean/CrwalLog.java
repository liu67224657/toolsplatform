package com.enjoyf.crwalwiki.bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;
public class CrwalLog {
    private Integer  id;
    private String  crwalKey;
    private Timestamp  createTime;
    private Integer  isRunning;
    private Integer  isFinish;
    private String  downloadUrl;
    private Integer  crwalType;
    private String  pageUrls;

 public Integer getId(){
    return id;
  }

 public void setId(Integer id){
    this.id = id;
  }

 public String getCrwalKey(){
    return crwalKey;
  }

 public void setCrwalKey(String crwalKey){
    this.crwalKey = crwalKey;
  }

 public Timestamp getCreateTime(){
    return createTime;
  }

 public void setCreateTime(Timestamp createTime){
    this.createTime = createTime;
  }

 public Integer getIsRunning(){
    return isRunning;
  }

 public void setIsRunning(Integer isRunning){
    this.isRunning = isRunning;
  }

 public Integer getIsFinish(){
    return isFinish;
  }

 public void setIsFinish(Integer isFinish){
    this.isFinish = isFinish;
  }

 public String getDownloadUrl(){
    return downloadUrl;
  }

 public void setDownloadUrl(String downloadUrl){
    this.downloadUrl = downloadUrl;
  }

 public Integer getCrwalType(){
    return crwalType;
  }

 public void setCrwalType(Integer crwalType){
    this.crwalType = crwalType;
  }

 public String getPageUrls(){
    return pageUrls;
  }

 public void setPageUrls(String pageUrls){
    this.pageUrls = pageUrls;
  }


public List getNotNullColumnList(){
  List columnList = new ArrayList();
   if(crwalKey != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("crwal_key");
    bean.setObj(crwalKey);
    columnList.add(bean);
   }
   if(createTime != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("create_time");
    bean.setObj(createTime);
    columnList.add(bean);
   }
   if(isRunning != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("is_running");
    bean.setObj(isRunning);
    columnList.add(bean);
   }
   if(isFinish != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("is_finish");
    bean.setObj(isFinish);
    columnList.add(bean);
   }
   if(downloadUrl != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("download_url");
    bean.setObj(downloadUrl);
    columnList.add(bean);
   }
   if(crwalType != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("crwal_type");
    bean.setObj(crwalType);
    columnList.add(bean);
   }
   if(pageUrls != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("page_urls");
    bean.setObj(pageUrls);
    columnList.add(bean);
   }
  return columnList;
  }
 }

