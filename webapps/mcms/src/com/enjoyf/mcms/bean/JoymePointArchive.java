package com.enjoyf.mcms.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

public class JoymePointArchive implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -6072686569194231002L;
    private Long pointArchiveId;
    private Long pointId;
    private String pointName;
    private String specFilePath;
    private Long specArchiveId;
    private Long archiveId;
    private String title;
    private Long archivePublishTime;
    private Integer seqId;
    private Integer isActive;
    private String htmlPath;
    private String htmlFile;
    private Integer typeId;

    public Long getPointArchiveId() {
        return pointArchiveId;
    }

    public void setPointArchiveId(Long pointArchiveId) {
        this.pointArchiveId = pointArchiveId;
    }

    public Long getPointId() {
        return pointId;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    public void setSpecArchiveId(Long specArchiveId) {
        this.specArchiveId = specArchiveId;
    }

    public Long getSpecArchiveId() {
        return specArchiveId;
    }

    public Long getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(Long archiveId) {
        this.archiveId = archiveId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getArchivePublishTime() {
        return archivePublishTime;
    }

    public void setArchivePublishTime(Long archivePublishTime) {
        this.archivePublishTime = archivePublishTime;
    }

    public Integer getSeqId() {
        return seqId;
    }

    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
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

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setSpecFilePath(String specFilePath) {
        this.specFilePath = specFilePath;
    }

    public String getSpecFilePath() {
        return specFilePath;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (pointId != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("point_id");
            bean.setObj(pointId);
            columnList.add(bean);
        }
        if (archiveId != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("archive_id");
            bean.setObj(archiveId);
            columnList.add(bean);
        }
        if (pointName != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("point_name");
            bean.setObj(pointName);
            columnList.add(bean);
        }
        if (title != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("title");
            bean.setObj(title);
            columnList.add(bean);
        }
        if (specArchiveId != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("spec_archive_id");
            bean.setObj(specArchiveId);
            columnList.add(bean);
        }
        if (archivePublishTime != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("archive_publish_time");
            bean.setObj(archivePublishTime);
            columnList.add(bean);
        }
        if (seqId != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("seq_id");
            bean.setObj(seqId);
            columnList.add(bean);
        }
        if (isActive != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("is_active");
            bean.setObj(isActive);
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
        if (specFilePath != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("spec_file_path");
            bean.setObj(specFilePath);
            columnList.add(bean);
        }
        return columnList;
    }
}
