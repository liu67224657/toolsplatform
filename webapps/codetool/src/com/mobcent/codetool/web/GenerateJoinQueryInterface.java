package com.mobcent.codetool.web;

import com.mobcent.codetool.interfaces.GenerateInterfacesWeb;

public class GenerateJoinQueryInterface extends GenerateWebInterface{

    @Override
    public String doAction(String table, String whereClause) throws Exception {
        GenerateInterfacesWeb interfaces = new GenerateInterfacesWeb();
        String queryString = interfaces.makeJoinQueryServiceWeb(table, whereClause, isDao, false);
        return queryString;
    }

}
