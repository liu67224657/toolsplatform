package com.mobcent.codetool.web;

public abstract class GenerateWebDaoImpl extends GenerateCreateFile{
    @Override
    public void setExtendsFlag() {
        this.extendsFlag = "extends BaseJDBCDAOImpl implements "+ className+ "Dao";
    }
    
    @Override
    public void setClassNameflag() {
        this.classNameflag = "DaoImpl";
    }
}
