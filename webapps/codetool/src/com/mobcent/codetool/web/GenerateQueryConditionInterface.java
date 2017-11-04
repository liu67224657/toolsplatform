package com.mobcent.codetool.web;

import com.mobcent.codetool.interfaces.GenerateInterfacesWeb;


public class GenerateQueryConditionInterface  extends GenerateWebInterface {

    public String doAction(String table, String whereClause) throws Exception {
        GenerateInterfacesWeb interfaces = new GenerateInterfacesWeb();
        String queryString = interfaces.makeQueryConditionInterface(table, whereClause, false, isDao);
        return queryString;
    }
    
}
