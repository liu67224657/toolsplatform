package com.enjoyf.search.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-10-10
 * Time: 上午10:38
 * To change this template use File | Settings | File Templates.
 */
public class SolrCore {
//    public static Map<String,SolrCore> map=new HashMap<String, SolrCore>();
//
//    static{
//        Map<String,String> fieldMap=new HashMap<String, String>();
//        fieldMap.put("uno", "string");
//        fieldMap.put("appkey", "string");
//        fieldMap.put("birthday", "string");
//        fieldMap.put("searchtext", "textIKAnalyze");
//        fieldMap.put("age", "int");
//        fieldMap.put("sex", "string");
//
//        new SolrCore("users",fieldMap);
//    }

    public SolrCore(String coreName, Map<String, String> fieldNames) {
        this.coreName = coreName;
        this.fieldNames = fieldNames;

//        map.put(coreName,this);
    }

    private String coreName;
    private Map<String,String> fieldNames;

    public String getCoreName() {
        return coreName;
    }

    public void setCoreName(String coreName) {
        this.coreName = coreName;
    }

    public Map<String, String> getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(Map<String, String> fieldNames) {
        this.fieldNames = fieldNames;
    }

//    public static SolrCore getByCore(String core){
//        return map.get(core);
//    }

}
