package com.mobcent.codetool.web;

import com.mobcent.codetool.service.GenerateServiceWeb;

public class GenerateUpdateConditionService extends GenerateWebService{

    @Override
    public String doAction(String table, String whereClause) throws Exception {
        GenerateServiceWeb web = new GenerateServiceWeb();
        String retString = web.makeUpdateServiceWeb(table, whereClause, false, false);
        return retString;
    }

}
