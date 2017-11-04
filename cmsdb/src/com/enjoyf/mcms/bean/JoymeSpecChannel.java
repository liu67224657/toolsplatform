package com.enjoyf.mcms.bean;

import java.util.Map;

public class JoymeSpecChannel {
    private Integer specId;
    private Map downUrlMap;
    private Map advertiesMap;
    public Integer getSpecId() {
        return specId;
    }
    public void setSpecId(Integer specId) {
        this.specId = specId;
    }
    public Map getDownUrlMap() {
        return downUrlMap;
    }
    public void setDownUrlMap(Map downUrlMap) {
        this.downUrlMap = downUrlMap;
    }
    public Map getAdvertiesMap() {
        return advertiesMap;
    }
    public void setAdvertiesMap(Map advertiesMap) {
        this.advertiesMap = advertiesMap;
    }
}
