package com.enjoyf.search.util;

import com.enjoyf.config.AbstractHotdeployConfig;
import com.enjoyf.search.bean.SolrCore;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoreHotdeployPropsConfig extends AbstractHotdeployConfig {

    private static final String KEY_CORE_LIST="core.list";
    private static final String KEY_FIELD_LIST=".field.list";
    private static final String KEY_FIELD_TYPE=".field.type";

    private  Cache cache;

    public CoreHotdeployPropsConfig() throws Exception {
         super();
    }

    protected void  init() throws Exception {

        InputStream is = null;
        try {
            filePath = su.getMetaInfFolderPath() + "/core.properties";
            is = new FileInputStream(new File(filePath));
            this.props.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
                is = null;
            }
        }
    }

    protected void reload()  {
        System.out.println("start reload.....");
        try {
            init();
            List<String> coreList= getList(KEY_CORE_LIST);

            Map<String,SolrCore> solrCoreMap=new HashMap<String, SolrCore>();
            for(String core:coreList){
                List<String> fieldList=getList(core+KEY_FIELD_LIST);

                if(fieldList==null || fieldList.size()==0){
                    continue;
                }


                Map<String,String> fieldMap=new HashMap<String, String>();
                for(String field:fieldList){
                    String type=getString(core + "." + field + KEY_FIELD_TYPE,null);
                    fieldMap.put(field,type);
                }
                SolrCore solrCore=new SolrCore(core,fieldMap);
                solrCoreMap.put(core,solrCore);
            }

            Cache tempCache=new Cache();
            tempCache.setSolrCoreMap(solrCoreMap);

            cache=tempCache;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end reload....."+cache.getSolrCoreMap());
    }

    public Map<String, SolrCore> getSolrCoreMap() {
        return cache.getSolrCoreMap();
    }


    private class Cache{
        private Map<String,SolrCore> solrCoreMap=new HashMap<String, SolrCore>();

        private Map<String, SolrCore> getSolrCoreMap() {
            return solrCoreMap;
        }

        private void setSolrCoreMap(Map<String, SolrCore> solrCoreMap) {
            this.solrCoreMap = solrCoreMap;
        }
    }


}
