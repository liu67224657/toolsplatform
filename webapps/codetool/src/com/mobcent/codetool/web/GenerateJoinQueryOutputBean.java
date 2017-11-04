package com.mobcent.codetool.web;

import com.mobcent.codetool.daoimpl.GenerateDaoImpl;

public class GenerateJoinQueryOutputBean extends GenerateCreateFile{
    @Override
    public String doAction(String table, String whereClause) throws Exception {
        this.isNeedAddClassName = false;
        GenerateDaoImpl web = new GenerateDaoImpl();
        String inputBean = web.makeJoinQueryOutputBean(table, whereClause ,false);
        return inputBean;
    }

    @Override
    public void setExtendsFlag() {
    }

    @Override
    public void setClassNameflag() {
        this.classNameflag = "OutputBean";
    }
    
}
