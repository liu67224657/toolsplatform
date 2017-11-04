package com.enjoyf.mcms.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.util.ArrayList;
import java.util.List;

public class JoymeImage {
    private Integer  imageId;
    private Integer  specid;
    private String  title;
    private String  pic;
    private String  link;
    private Integer  displayorder;
    private Integer  redirectType;
    private Integer  imageType;

 public Integer getImageId(){
    return imageId;
  }

 public void setImageId(Integer imageId){
    this.imageId = imageId;
  }

 public Integer getSpecid(){
    return specid;
  }

 public void setSpecid(Integer specid){
    this.specid = specid;
  }

 public String getTitle(){
    return title;
  }

 public void setTitle(String title){
    this.title = title;
  }

 public String getPic(){
    return pic;
  }

 public void setPic(String pic){
    this.pic = pic;
  }

 public String getLink(){
    return link;
  }

 public void setLink(String link){
    this.link = link;
  }

 public Integer getDisplayorder(){
    return displayorder;
  }

 public void setDisplayorder(Integer displayorder){
    this.displayorder = displayorder;
  }

 public Integer getRedirectType(){
    return redirectType;
  }

 public void setRedirectType(Integer redirectType){
    this.redirectType = redirectType;
  }

 public Integer getImageType(){
    return imageType;
  }

 public void setImageType(Integer imageType){
    this.imageType = imageType;
  }


public List getNotNullColumnList(){
  List columnList = new ArrayList();
   if(specid != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("specid");
    bean.setObj(specid);
    columnList.add(bean);
   }
   if(title != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("title");
    bean.setObj(title);
    columnList.add(bean);
   }
   if(pic != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("pic");
    bean.setObj(pic);
    columnList.add(bean);
   }
   if(link != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("link");
    bean.setObj(link);
    columnList.add(bean);
   }
   if(displayorder != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("displayorder");
    bean.setObj(displayorder);
    columnList.add(bean);
   }
   if(redirectType != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("redirect_type");
    bean.setObj(redirectType);
    columnList.add(bean);
   }
   if(imageType != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("image_type");
    bean.setObj(imageType);
    columnList.add(bean);
   }
  return columnList;
  }
 }


