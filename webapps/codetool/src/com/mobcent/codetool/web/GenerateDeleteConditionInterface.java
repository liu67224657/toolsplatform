package com.mobcent.codetool.web;

import com.mobcent.codetool.interfaces.GenerateInterfacesWeb;

public class GenerateDeleteConditionInterface extends GenerateWebInterface{

    @Override
    public String doAction(String table, String whereClause) throws Exception {
        GenerateInterfacesWeb interfaces = new GenerateInterfacesWeb();
        String retString = interfaces.makeDeleteServiceWeb(table, whereClause, false, false, isDao);
        return retString;
    }
    
}
