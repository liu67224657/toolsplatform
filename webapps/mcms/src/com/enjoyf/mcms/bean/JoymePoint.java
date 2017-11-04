package com.enjoyf.mcms.bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

public class JoymePoint {
    private Long specPointId;
    private Integer archiveId;
    private String specPointName;
    private Integer displayRowNum;
    private String keywords;
    private String typeid;
    private Timestamp createTime;
    private Integer isAble;
    private String htmlPath;
    private String htmlFile;
    private Integer isActive;
    private Integer displayOrder;

    public Long getSpecPointId() {
        return specPointId;
    }

    public void setSpecPointId(Long specPointId) {
        this.specPointId = specPointId;
    }

    public Integer getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(Integer archiveId) {
        this.archiveId = archiveId;
    }

    public String getSpecPointName() {
        return specPointName;
    }

    public void setSpecPointName(String specPointName) {
        this.specPointName = specPointName;
    }

    public Integer getDisplayRowNum() {
        return displayRowNum;
    }

    public void setDisplayRowNum(Integer displayRowNum) {
        this.displayRowNum = displayRowNum;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getIsAble() {
        return isAble;
    }

    public void setIsAble(Integer isAble) {
        this.isAble = isAble;
    }

    public String getHtmlPath() {
        return htmlPath;
    }

    public void setHtmlPath(String htmlPath) {
        this.htmlPath = htmlPath;
    }

    public String getHtmlFile() {
        return htmlFile;
    }

    public void setHtmlFile(String htmlFile) {
        this.htmlFile = htmlFile;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (archiveId != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("archive_id");
            bean.setObj(archiveId);
            columnList.add(bean);
        }
        if (specPointName != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("spec_point_name");
            bean.setObj(specPointName);
            columnList.add(bean);
        }
        if (displayRowNum != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("display_row_num");
            bean.setObj(displayRowNum);
            columnList.add(bean);
        }
        if (keywords != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("keywords");
            bean.setObj(keywords);
            columnList.add(bean);
        }
        if (typeid != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("typeid");
            bean.setObj(typeid);
            columnList.add(bean);
        }
        if (createTime != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("create_time");
            bean.setObj(createTime);
            columnList.add(bean);
        }
        if (isAble != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("is_able");
            bean.setObj(isAble);
            columnList.add(bean);
        }
        if (htmlPath != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("html_path");
            bean.setObj(htmlPath);
            columnList.add(bean);
        }
        if (htmlFile != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("html_file");
            bean.setObj(htmlFile);
            columnList.add(bean);
        }
        if (isActive != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("is_active");
            bean.setObj(isActive);
            columnList.add(bean);
        }
        if (displayOrder != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("display_order");
            bean.setObj(displayOrder);
            columnList.add(bean);
        }
        return columnList;
    }
}
