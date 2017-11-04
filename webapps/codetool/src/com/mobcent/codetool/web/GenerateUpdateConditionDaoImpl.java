package com.mobcent.codetool.web;

import com.mobcent.codetool.daoimpl.GenerateDaoImpl;

public class GenerateUpdateConditionDaoImpl extends GenerateWebDaoImpl{

    @Override
    public String doAction(String table, String whereClause) throws Exception {
        GenerateDaoImpl web = new GenerateDaoImpl();
        String updateString = web.makeUpdateConditionWeb(table , whereClause,false);
        return updateString;
    }

}
