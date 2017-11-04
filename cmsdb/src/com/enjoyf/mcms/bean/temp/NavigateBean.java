package com.enjoyf.mcms.bean.temp;

import java.io.Serializable;

public class NavigateBean implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -8400753667884412685L;
    private String level1Str = null;
    private String level2Str = null;
    private String level3Str = null;
    private String level1Add = null;
    private String level2Add = null;
    private String level3Add = null;

    public String getLevel1Str() {
        return level1Str;
    }

    public void setLevel1Str(String level1Str) {
        this.level1Str = level1Str;
    }

    public String getLevel2Str() {
        return level2Str;
    }

    public void setLevel2Str(String level2Str) {
        this.level2Str = level2Str;
    }

    public String getLevel3Str() {
        return level3Str;
    }

    public void setLevel3Str(String level3Str) {
        this.level3Str = level3Str;
    }

    public String getLevel1Add() {
        return level1Add;
    }

    public void setLevel1Add(String level1Add) {
        this.level1Add = level1Add;
    }

    public String getLevel2Add() {
        return level2Add;
    }

    public void setLevel2Add(String level2Add) {
        this.level2Add = level2Add;
    }

    public String getLevel3Add() {
        return level3Add;
    }

    public void setLevel3Add(String level3Add) {
        this.level3Add = level3Add;
    }
}
