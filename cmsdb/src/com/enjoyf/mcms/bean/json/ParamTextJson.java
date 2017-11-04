package com.enjoyf.mcms.bean.json;

import com.enjoyf.util.JsonBinder;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: pengxu
 * Date: 14-2-26
 * Time: 下午1:52
 * To change this template use File | Settings | File Templates.
 */
public class ParamTextJson implements Serializable {
    private String bgcolor;

    //图片主色调
    private String picrgb;

    public ParamTextJson() {

    }

    public ParamTextJson(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public static String toJson(ParamTextJson paramTextJson) {
        return JsonBinder.buildNonDefaultBinder().toJson(paramTextJson);
    }

    public static ParamTextJson fromJson(String jsonString) {
        ParamTextJson resultList = new ParamTextJson();
        try {
            resultList = JsonBinder.buildNonNullBinder().getMapper().readValue(jsonString, new TypeReference<ParamTextJson>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }


    public String toJson() {
        return JsonBinder.buildNonNullBinder().toJson(this);
    }


    @Override
    public String toString() {
        return "ParamTextJson{" +
                "bgcolor='" + bgcolor + '\'' +
                ", picrgb='" + picrgb + '\'' +
                '}';
    }

    public String getPicrgb() {
        return picrgb;
    }

    public void setPicrgb(String picrgb) {
        this.picrgb = picrgb;
    }
}
