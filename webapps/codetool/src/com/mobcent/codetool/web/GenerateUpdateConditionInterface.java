package com.mobcent.codetool.web;

import com.mobcent.codetool.interfaces.GenerateInterfacesWeb;

public class GenerateUpdateConditionInterface extends GenerateWebInterface{
    public String doAction(String table, String whereClause) throws Exception {
        GenerateInterfacesWeb interfaces = new GenerateInterfacesWeb();
        String retString = interfaces.makeUpdateServiceWeb(table, whereClause, false, false, isDao);
        return retString;
    }
}
