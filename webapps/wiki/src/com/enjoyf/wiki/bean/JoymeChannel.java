package com.enjoyf.wiki.bean;


import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.util.ArrayList;
import java.util.List;

public class JoymeChannel {
    private Integer  channelId;
    private String  channelName;
    private String  htmlFactory;

 public Integer getChannelId(){
    return channelId;
  }

 public void setChannelId(Integer channelId){
    this.channelId = channelId;
  }

 public String getChannelName(){
    return channelName;
  }

 public void setChannelName(String channelName){
    this.channelName = channelName;
  }

 public String getHtmlFactory(){
    return htmlFactory;
  }

 public void setHtmlFactory(String htmlFactory){
    this.htmlFactory = htmlFactory;
  }


public List getNotNullColumnList(){
  List columnList = new ArrayList();
   if(channelName != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("channel_name");
    bean.setObj(channelName);
    columnList.add(bean);
   }
   if(htmlFactory != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("html_factory");
    bean.setObj(htmlFactory);
    columnList.add(bean);
   }
  return columnList;
  }
 }


