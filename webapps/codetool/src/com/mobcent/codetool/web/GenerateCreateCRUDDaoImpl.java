package com.mobcent.codetool.web;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.daoimpl.GenerateDaoImpl;

public class GenerateCreateCRUDDaoImpl extends GenerateWebDaoImpl{

    @Override
    public String doAction(String table, String whereClause) throws Exception {
        
        GenerateDaoImpl web = new GenerateDaoImpl();
        String queryString = web.makeQueryPkWeb(table,false);
        String insertString = web.makeInsertWeb(table,false);
        String updateString = web.makeUpdateByIdWeb(table, false);
        String deleteString = web.makeDeleteWeb(table,false);
        String temp=queryString+Constants.NEXT_LINE+Constants.NEXT_LINE
                +insertString+Constants.NEXT_LINE+Constants.NEXT_LINE
                +updateString+Constants.NEXT_LINE+Constants.NEXT_LINE
                +deleteString+Constants.NEXT_LINE+Constants.NEXT_LINE;
        return temp;
    }

}
