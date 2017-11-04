package com.mobcent.codetool.web;

import com.mobcent.codetool.interfaces.GenerateInterfacesWeb;

public class GenerateInsertReturnInterface extends GenerateWebInterface {

    @Override
    public String doAction(String table, String whereClause) throws Exception {
        GenerateInterfacesWeb interfaces = new GenerateInterfacesWeb();
        String queryString = interfaces.makeInsertReturnServiceWeb(table, false, isDao);
        return queryString;
    }

    
    
}
