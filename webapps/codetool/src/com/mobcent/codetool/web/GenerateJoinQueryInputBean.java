package com.mobcent.codetool.web;

import com.mobcent.codetool.daoimpl.GenerateDaoImpl;

public class GenerateJoinQueryInputBean extends GenerateCreateFile{
    /**
     * talbe 相当于logicName
     * whereClause相当于sql
     */
    @Override
    public String doAction(String table, String whereClause) throws Exception {
        this.isNeedAddClassName = false;
        GenerateDaoImpl web = new GenerateDaoImpl();
        String inputBean = web.makeJoinQueryInputBean(table, whereClause ,false);
        return inputBean;
    }

    @Override
    public void setExtendsFlag() {
    }

    @Override
    public void setClassNameflag() {
        this.classNameflag = "InputBean";
    }
    
}
