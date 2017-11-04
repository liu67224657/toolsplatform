package com.mobcent.codetool.web;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.interfaces.GenerateInterfacesWeb;

public class GenerateQueryPageInterface extends GenerateWebInterface {

    @Override
    public String doAction(String table, String whereClause) throws Exception {
        GenerateInterfacesWeb interfaces = new GenerateInterfacesWeb();
        String retString = interfaces.makeQueryCountInterface(table, whereClause, false, isDao);
        retString += Constants.NEXT_LINE + Constants.NEXT_LINE;
        retString += interfaces.makeQueryPageInterface(table, whereClause, false, isDao);
        return retString;
    }

}
