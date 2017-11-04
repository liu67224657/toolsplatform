package com.mobcent.codetool.web;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.daoimpl.GenerateDaoImpl;

public class GenerateQueryPageDaoImpl extends GenerateWebDaoImpl{

    @Override
    public String doAction(String table, String whereClause) throws Exception {
        GenerateDaoImpl web = new GenerateDaoImpl();
        //count
        String retString = web.makeQueryCountConditionWeb(table, whereClause, false) ;
        retString += Constants.NEXT_LINE + Constants.NEXT_LINE;
        //分页
        retString += web.makeQueryPageConditionWeb(table, whereClause, false);
        
        return retString;
    }
    
}
