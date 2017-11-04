package com.mobcent.codetool.web;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.daoimpl.GenerateDaoImpl;

public class GenerateCreatePOJOFile extends GenerateCreateFile{

    @Override
    public String doAction(String table, String whereClause) throws Exception {
        this.isNeedAddClassName = false;
        
        GenerateDaoImpl web = new GenerateDaoImpl();
        String pojoString = web.makePOJOWeb(table,false);
        String temp=pojoString+Constants.NEXT_LINE+Constants.NEXT_LINE;
        return temp;
    }

    @Override
    public void setClassNameflag() {
        // TODO Auto-generated method stub
    }

    @Override
    public void setExtendsFlag() {
        // TODO Auto-generated method stub
    }
}
