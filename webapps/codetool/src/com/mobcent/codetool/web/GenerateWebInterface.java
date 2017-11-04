package com.mobcent.codetool.web;

public abstract class GenerateWebInterface extends GenerateCreateFile{
    protected boolean isDao = false;

    public void setDao(boolean isDao) {
        this.isDao = isDao;
    }
    
    @Override
    public boolean isClass() {
        this.isClass = false;
        return false;
    }
    
    @Override
    public void setClassNameflag() {
        if(isDao)
            this.classNameflag = "Dao";
        else
            this.classNameflag = "ServiceInterface";
    }
    
    @Override
    public void setExtendsFlag() {
        if(isDao)
            this.extendsFlag = "extends BaseJDBCDAO";
        else 
            this.extendsFlag = "";
    }

}
