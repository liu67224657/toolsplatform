package com.mobcent.codetool.web;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.interfaces.GenerateInterfacesWeb;

public class GenerateCreateCRUDInterface extends GenerateWebInterface {
    
    public String doAction(String table, String whereClause) throws Exception {
        GenerateInterfacesWeb interfaces = new GenerateInterfacesWeb();
        String queryString = interfaces.makeQueryPKInterfacesWeb(table, false, isDao);
        String insertString = interfaces.makeInsertServiceWeb(table, false, isDao);
        String updateString = interfaces.makeUpdateByIdServiceWeb(table, null, true, false, isDao);
        String deleteString = interfaces.makeDeleteServiceWeb(table, null, true, false, isDao);
        String temp = queryString + Constants.NEXT_LINE + Constants.NEXT_LINE + insertString + Constants.NEXT_LINE + Constants.NEXT_LINE
                + updateString + Constants.NEXT_LINE + Constants.NEXT_LINE + deleteString + Constants.NEXT_LINE + Constants.NEXT_LINE;
        return temp;
    }
    

}
