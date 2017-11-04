package com.enjoyf.adminuser.bean;

import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

public class JoymeProperties {
    private Integer joymePropertiesId;
    private String joymePropertiesKey;
    private String joymePropertiesKeyName;
    private Integer joymeBelong;
    private Integer joymeType;
    private Integer joymeFatherPropId;
    private Integer joymeDisplayOrder;
    private String joymeLink;

    public Integer getJoymePropertiesId() {
        return joymePropertiesId;
    }

    public void setJoymePropertiesId(Integer joymePropertiesId) {
        this.joymePropertiesId = joymePropertiesId;
    }

    public String getJoymePropertiesKey() {
        return joymePropertiesKey;
    }

    public void setJoymePropertiesKey(String joymePropertiesKey) {
        this.joymePropertiesKey = joymePropertiesKey;
    }

    public String getJoymePropertiesKeyName() {
        return joymePropertiesKeyName;
    }

    public void setJoymePropertiesKeyName(String joymePropertiesKeyName) {
        this.joymePropertiesKeyName = joymePropertiesKeyName;
    }

    public Integer getJoymeBelong() {
        return joymeBelong;
    }

    public void setJoymeBelong(Integer joymeBelong) {
        this.joymeBelong = joymeBelong;
    }

    public Integer getJoymeType() {
        return joymeType;
    }

    public void setJoymeType(Integer joymeType) {
        this.joymeType = joymeType;
    }

    public Integer getJoymeFatherPropId() {
        return joymeFatherPropId;
    }

    public void setJoymeFatherPropId(Integer joymeFatherPropId) {
        this.joymeFatherPropId = joymeFatherPropId;
    }

    public Integer getJoymeDisplayOrder() {
        return joymeDisplayOrder;
    }

    public void setJoymeDisplayOrder(Integer joymeDisplayOrder) {
        this.joymeDisplayOrder = joymeDisplayOrder;
    }

    public String getJoymeLink() {
        return joymeLink;
    }

    public void setJoymeLink(String joymeLink) {
        this.joymeLink = joymeLink;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (joymePropertiesKey != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_properties_key");
            bean.setObj(joymePropertiesKey);
            columnList.add(bean);
        }
        if (joymePropertiesKeyName != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_properties_key_name");
            bean.setObj(joymePropertiesKeyName);
            columnList.add(bean);
        }
        if (joymeBelong != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_belong");
            bean.setObj(joymeBelong);
            columnList.add(bean);
        }
        if (joymeType != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_type");
            bean.setObj(joymeType);
            columnList.add(bean);
        }
        if (joymeFatherPropId != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_father_prop_id");
            bean.setObj(joymeFatherPropId);
            columnList.add(bean);
        }
        if (joymeDisplayOrder != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_display_order");
            bean.setObj(joymeDisplayOrder);
            columnList.add(bean);
        }
        if (joymeLink != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_link");
            bean.setObj(joymeLink);
            columnList.add(bean);
        }
        return columnList;
    }
}
