package com.mobcent.codetool.web;

import com.mobcent.codetool.daoimpl.GenerateDaoImpl;

public class GenerateQueryConditionDaoImpl extends GenerateWebDaoImpl{
   
    @Override
    public String doAction(String table , String whereClause) throws Exception {
        GenerateDaoImpl web = new GenerateDaoImpl();
        String retString = web.makeQueryConditionWeb(table, whereClause, false) ;
        return retString;
    }
}
