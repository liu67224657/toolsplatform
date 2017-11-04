package com.enjoyf.mcms.bean;

import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;
public class DedeTaglist {
    private Integer  tid;
    private Integer  aid;
    private Integer  arcrank;
    private Integer  typeid;
    private String  tag;

 public Integer getTid(){
    return tid;
  }

 public void setTid(Integer tid){
    this.tid = tid;
  }

 public Integer getAid(){
    return aid;
  }

 public void setAid(Integer aid){
    this.aid = aid;
  }

 public Integer getArcrank(){
    return arcrank;
  }

 public void setArcrank(Integer arcrank){
    this.arcrank = arcrank;
  }

 public Integer getTypeid(){
    return typeid;
  }

 public void setTypeid(Integer typeid){
    this.typeid = typeid;
  }

 public String getTag(){
    return tag;
  }

 public void setTag(String tag){
    this.tag = tag;
  }


public List getNotNullColumnList(){
  List columnList = new ArrayList();
   if(tid != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("tid");
    bean.setObj(tid);
    columnList.add(bean);
   }
   if(aid != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("aid");
    bean.setObj(aid);
    columnList.add(bean);
   }
   if(arcrank != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("arcrank");
    bean.setObj(arcrank);
    columnList.add(bean);
   }
   if(typeid != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("typeid");
    bean.setObj(typeid);
    columnList.add(bean);
   }
   if(tag != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("tag");
    bean.setObj(tag);
    columnList.add(bean);
   }
  return columnList;
  }
 }


