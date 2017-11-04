package com.mobcent.codetool;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String CODE_TOOL_NAME = "shenqi";
    public static final String CODE_TOOL_VERSION = "v0.1";
    
    public static final String NEXT_LINE = "\r\n";
    public static final String WEB_NEXT_LINE = "<br>";
    public static final String WEB_SPACE = "&nbsp;";
    public static final String SUFFIX=".java";
    public static final String BEGIN_JAVADOC_SPACE = "   ";
    public static final String INPUT_LIST_CONTAIN = "Object <int , String , long..>";
    public static final String UPDATE_RETURN_DOC = "1 success, 0 failed";
    public static final String AUTO_INCREASE_KEY = " auto-increase-key";
    
    public static final int QUERY = 1;
    public static final int QUERY_PAGE = 2;
    public static final int QUERY_COUNT = 3;
    public static final int INSERT = 4;
    public static final int UPDATE = 5;
    public static final int DELETE = 6;
    public static final int JOIN_QUERY = 7;
    public static final int QUERY_PK = 8;
    public static final int INSERT_RETURN = 9;
    public static final int UPDATE_PK = 10;
    
    public static Map getColumnTypeMap(){
        Map columnTypeMap = new HashMap();
        columnTypeMap.put("int", "Int");
        columnTypeMap.put("smallint", "Int");
        columnTypeMap.put("tinyint", "Boolean");
        columnTypeMap.put("bigint", "Long");
        columnTypeMap.put("varchar", "String");
        columnTypeMap.put("nvarchar", "String");
        columnTypeMap.put("text", "String");
        columnTypeMap.put("date", "Date");
        columnTypeMap.put("datetime", "Timestamp");
        columnTypeMap.put("double", "Double");
        columnTypeMap.put("bit", "Boolean");
        columnTypeMap.put("float", "Float");
        columnTypeMap.put("decimal", "BigDecimal");
        columnTypeMap.put("boolean", "Boolean");
        columnTypeMap.put("time", "Time");
        columnTypeMap.put("timestamp", "Timestamp");
        columnTypeMap.put("year", "Date");
        columnTypeMap.put("blob", "Blob");
        columnTypeMap.put("char", "String");
        return columnTypeMap;
    }
    
    public static String getJavaType(String key){
        String type = "Object";
        Map map = Constants.getColumnTypeMap();
        if(map.get(key) != null)
            type = map.get(key).toString();
        
        if(type.equals("Int")){
            type = "Integer";
        }
        return type;
    }
}
