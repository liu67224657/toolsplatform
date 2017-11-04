package com.mobcent.codetool.web;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.service.GenerateServiceWeb;

public class GenerateQueryConditionService extends GenerateWebService {

    public String doAction(String table, String whereClause) throws Exception {
        GenerateServiceWeb web = new GenerateServiceWeb();
        String queryserviceString = web.makeQueryServiceWeb(table, whereClause, false);
        String temp = queryserviceString + Constants.NEXT_LINE + Constants.NEXT_LINE;
        return temp;
    }

}
