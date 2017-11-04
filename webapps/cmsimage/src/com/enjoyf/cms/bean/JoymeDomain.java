package com.enjoyf.cms.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhitaoshi on 2015/7/1.
 */
public class JoymeDomain implements Serializable {
    private String domainName;
    private Date buyDate;
    private Date expireDate;
    private String buyMerchant;
    private String buyUser;
    private String domainDesc;

    private Date createDate;
    private String createUser;
    private Date modifyDate;
    private String modifyUser;

    private int removeStatus = 1;//0--删除，1--可用

    private String buyDateStr;
    private String expireDateStr;
    private String createDateStr;
    private String modifyDateStr;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getBuyMerchant() {
        return buyMerchant;
    }

    public void setBuyMerchant(String buyMerchant) {
        this.buyMerchant = buyMerchant;
    }

    public String getBuyUser() {
        return buyUser;
    }

    public void setBuyUser(String buyUser) {
        this.buyUser = buyUser;
    }

    public String getDomainDesc() {
        return domainDesc;
    }

    public void setDomainDesc(String domainDesc) {
        this.domainDesc = domainDesc;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public int getRemoveStatus() {
        return removeStatus;
    }

    public void setRemoveStatus(int removeStatus) {
        this.removeStatus = removeStatus;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getModifyDateStr() {
        return modifyDateStr;
    }

    public void setModifyDateStr(String modifyDateStr) {
        this.modifyDateStr = modifyDateStr;
    }

    public String getBuyDateStr() {
        return buyDateStr;
    }

    public void setBuyDateStr(String buyDateStr) {
        this.buyDateStr = buyDateStr;
    }

    public String getExpireDateStr() {
        return expireDateStr;
    }

    public void setExpireDateStr(String expireDateStr) {
        this.expireDateStr = expireDateStr;
    }
}
