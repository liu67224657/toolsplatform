package com.enjoyf.config;


import com.enjoyf.util.StringUtil;
import com.enjoyf.util.SystemUtil;

import java.io.File;
import java.util.*;

public class AbstractHotdeployConfig {
    protected SystemUtil su = new SystemUtil();
    protected Properties props = new Properties();
    protected String filePath;

    private Map<String, Long> fileLastModifiedTimeMap = new HashMap<String, Long>();

    protected AbstractHotdeployConfig() {
        try {
            reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void init() throws Exception {

    }

    protected void reload(){

    }


    protected List<String> getList(String key){
        String value=props.getProperty(key);

        if(StringUtil.isEmpty(value)){
            return null;
        }

        return Arrays.asList(value.split(" "));
    }

    protected String getString(String key,String defValue){
        String value=props.getProperty(key);

        return value==null?defValue:value;
    }

    public boolean isModified() {
        long curr=new File(filePath).lastModified();

        Long oldLong=fileLastModifiedTimeMap.get(filePath);
        long oldLongValue=0;
        if(oldLong!=null){
            oldLongValue=oldLong.longValue();
        }

        boolean isModifed=curr>oldLongValue;
        if(isModifed){
            fileLastModifiedTimeMap.put(filePath,curr);
        }

        return isModifed;
    }
}
