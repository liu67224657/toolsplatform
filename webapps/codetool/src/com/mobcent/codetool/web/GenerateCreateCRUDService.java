package com.mobcent.codetool.web;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.service.GenerateServiceWeb;

public class GenerateCreateCRUDService extends GenerateWebService {

    @Override
    public String doAction(String table, String whereClause) throws Exception {
        // TODO Auto-generated method stub
        

        GenerateServiceWeb web = new GenerateServiceWeb();
        String queryserviceString = web.makeQueryPKServiceWeb(table, false);
        String insertServiceString = web.makeInsertServiceWeb(table, false);
        String updateServiceString = web.makeUpdateServiceByIdWeb(table, null, true, false);
        String deleteServiceString = web.makeDeleteServiceWeb(table, null, true, false);
        String temp = queryserviceString + Constants.NEXT_LINE + Constants.NEXT_LINE;
               temp += insertServiceString + Constants.NEXT_LINE + Constants.NEXT_LINE;
               temp += updateServiceString + Constants.NEXT_LINE + Constants.NEXT_LINE;
               temp += deleteServiceString + Constants.NEXT_LINE + Constants.NEXT_LINE;
        return temp;
    }

   

}
