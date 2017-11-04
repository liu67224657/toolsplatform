package com.enjoyf.mcms.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JoymeSpec {
    private Integer specId;
    private String specName;
    private String specType;
    private String specLanguage;
    private String specSize;
    private String specVersion;
    private String specPicUrl;
    private String specAdvertise;
    private String specDownloadUrl;
    private Integer archiveId;
    private String filePath;
    private Timestamp lastUpdateTime;
    private Integer isCompressImages = 1;
    private String packageName;
    private Integer isPackage;

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSpecType() {
        return specType;
    }

    public void setSpecType(String specType) {
        this.specType = specType;
    }

    public String getSpecLanguage() {
        return specLanguage;
    }

    public void setSpecLanguage(String specLanguage) {
        this.specLanguage = specLanguage;
    }

    public String getSpecSize() {
        return specSize;
    }

    public void setSpecSize(String specSize) {
        this.specSize = specSize;
    }

    public String getSpecVersion() {
        return specVersion;
    }

    public void setSpecVersion(String specVersion) {
        this.specVersion = specVersion;
    }

    public String getSpecPicUrl() {
        return specPicUrl;
    }

    public void setSpecPicUrl(String specPicUrl) {
        this.specPicUrl = specPicUrl;
    }

    public String getSpecAdvertise() {
        return specAdvertise;
    }

    public void setSpecAdvertise(String specAdvertise) {
        this.specAdvertise = specAdvertise;
    }

    public String getSpecDownloadUrl() {
        return specDownloadUrl;
    }

    public void setSpecDownloadUrl(String specDownloadUrl) {
        this.specDownloadUrl = specDownloadUrl;
    }

    public Integer getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(Integer archiveId) {
        this.archiveId = archiveId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getIsCompressImages() {
        return isCompressImages;
    }

    public void setIsCompressImages(Integer isCompressImages) {
        this.isCompressImages = isCompressImages;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getIsPackage() {
        return isPackage;
    }

    public void setIsPackage(Integer aPackage) {
        isPackage = aPackage;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        
        if (specName != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("spec_name");
            bean.setObj(specName);
            columnList.add(bean);
        }
        if (specType != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("spec_type");
            bean.setObj(specType);
            columnList.add(bean);
        }
        if (specLanguage != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("spec_language");
            bean.setObj(specLanguage);
            columnList.add(bean);
        }
        if (specSize != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("spec_size");
            bean.setObj(specSize);
            columnList.add(bean);
        }
        if (specVersion != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("spec_version");
            bean.setObj(specVersion);
            columnList.add(bean);
        }
        if (specPicUrl != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("spec_pic_url");
            bean.setObj(specPicUrl);
            columnList.add(bean);
        }
        if (specAdvertise != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("spec_advertise");
            bean.setObj(specAdvertise);
            columnList.add(bean);
        }
        if (specDownloadUrl != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("spec_download_url");
            bean.setObj(specDownloadUrl);
            columnList.add(bean);
        }
        if (archiveId != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("archive_id");
            bean.setObj(archiveId);
            columnList.add(bean);
        }
        if (filePath != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("file_path");
            bean.setObj(filePath);
            columnList.add(bean);
        }
        if (lastUpdateTime != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("last_update_time");
            bean.setObj(lastUpdateTime);
            columnList.add(bean);
        }
        if (isCompressImages != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("is_compress_images");
            bean.setObj(isCompressImages);
            columnList.add(bean);
        }
        if (packageName != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("package_name");
            bean.setObj(packageName);
            columnList.add(bean);
        }
        if (isPackage != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("is_package");
            bean.setObj(isPackage);
            columnList.add(bean);
        }
        return columnList;
    }
}
