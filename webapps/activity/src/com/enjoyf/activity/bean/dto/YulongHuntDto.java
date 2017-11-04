package com.enjoyf.activity.bean.dto;

import com.enjoyf.util.StringUtil;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhimingli on 2016/8/24 0024.
 * 御龙寻宝DTO
 */

public class YulongHuntDto implements Serializable {
    private String yizhou = ""; //益州
    private String yangzhou = "";//杨州
    private String yuzhou = "";//豫州
    private String jingzhou = "";//荆州
    private String qingzhou = "";//青州
    private String end = "";//终极

    public String getYizhou() {
        return yizhou;
    }

    public void setYizhou(String yizhou) {
        this.yizhou = yizhou;
    }

    public String getYangzhou() {
        return yangzhou;
    }

    public void setYangzhou(String yangzhou) {
        this.yangzhou = yangzhou;
    }

    public String getYuzhou() {
        return yuzhou;
    }

    public void setYuzhou(String yuzhou) {
        this.yuzhou = yuzhou;
    }

    public String getJingzhou() {
        return jingzhou;
    }

    public void setJingzhou(String jingzhou) {
        this.jingzhou = jingzhou;
    }

    public String getQingzhou() {
        return qingzhou;
    }

    public void setQingzhou(String qingzhou) {
        this.qingzhou = qingzhou;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static YulongHuntDto fromJson(String jsonStr) {
        return new Gson().fromJson(jsonStr, YulongHuntDto.class);
    }

    public static Map<String, String> fromMap(String jsonStr) {
        YulongHuntDto dto = fromJson(jsonStr);
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("yizhou", StringUtil.isEmpty(dto.getYizhou()) ? "" : dto.getYizhou());
        returnMap.put("yangzhou", StringUtil.isEmpty(dto.getYangzhou()) ? "" : dto.getYangzhou());
        returnMap.put("yuzhou", StringUtil.isEmpty(dto.getYuzhou()) ? "" : dto.getYuzhou());
        returnMap.put("jingzhou", StringUtil.isEmpty(dto.getJingzhou()) ? "" : dto.getJingzhou());
        returnMap.put("qingzhou", StringUtil.isEmpty(dto.getQingzhou()) ? "" : dto.getQingzhou());
        returnMap.put("end", StringUtil.isEmpty(dto.getEnd()) ? "" : dto.getEnd());
        return returnMap;
    }


}
