package com.mobcent.codetool.web;

import com.mobcent.codetool.Constants;

public abstract class GenerateWebService extends GenerateCreateFile {
    @Override
    public void setClassNameflag() {
        this.classNameflag = "ServiceImpl";
    }
    @Override
    public void setExtendsFlag() {
        this.extendsFlag = "implements " + className + "ServiceInterface";
    }
    
    @Override
    protected String getPropertiesString(String className) {
        String retString = "   private static "+className+"Dao subDao = new "+className+"DaoImpl();" + Constants.NEXT_LINE;
        return retString;
    }
}
