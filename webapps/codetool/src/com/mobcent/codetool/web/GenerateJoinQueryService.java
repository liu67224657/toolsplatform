package com.mobcent.codetool.web;

import com.mobcent.codetool.service.GenerateServiceWeb;

public class GenerateJoinQueryService extends GenerateWebService {

    @Override
    public String doAction(String table, String whereClause) throws Exception {
        GenerateServiceWeb web = new GenerateServiceWeb();
//        String retString = web.makeQueryServiceWeb(table, whereClause, false);
        String retString = web.makeJoinQueryServiceWeb(table, whereClause, false);
        return retString;
    }

}
