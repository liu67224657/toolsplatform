package com.mobcent.codetool.service;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.bean.ColumnBean;
import com.mobcent.codetool.bean.GenerateBean;
import com.mobcent.codetool.db.Common;
import com.mobcent.codetool.util.GenerateBase;

public class GenerateServiceWeb {
    /**
     * 根据主键生成service
     * @param table
     * @param whereClause
     * @return
     * @throws Exception 
     */
    public String makeQueryPKServiceWeb(String table ,boolean needConvert) throws Exception{
        GenerateBean bean = GenerateBase.getBean(table, null);
        ColumnBean columnBean = Common.getPKColumn(bean.getColumnList());
        String whereClause = " WHERE "+ columnBean.getColumnName() + " = ?";
        GenerateService service = new GenerateService();
        String retString = service.generateQueryService(table, Constants.QUERY_PK, whereClause);
        if(needConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
    
    /**
     * 条件查询的service实现
     * @param table
     * @param whereClause
     * @param needConvert
     * @return
     * @throws Exception
     */
    public String makeQueryServiceWeb(String table , String whereClause, boolean needConvert) throws Exception{
        GenerateService service = new GenerateService();
        String retString = service.generateQueryService(table, Constants.QUERY, whereClause);
        if(needConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
    
    /**
     * join query的service层
     * @param logicName
     * @param sql
     * @return
     * @throws Exception 
     */
    public String makeJoinQueryServiceWeb(String logicName, String sql ,boolean isConvert) throws Exception{
        GenerateService service = new GenerateService();
        String retString = service.generateJoinQueryService(logicName, sql);
        if(isConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
    
    
    /**
     * count查询的service实现
     * @param table
     * @param whereClause
     * @param needConvert
     * @return
     * @throws Exception
     */
    public String makeQueryCountServiceWeb(String table , String whereClause, boolean needConvert) throws Exception{
        GenerateService service = new GenerateService();
        String retString = service.generateQueryService(table, Constants.QUERY_COUNT, whereClause);
        if(needConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
    
    /**
     * count查询的service实现
     * @param table
     * @param whereClause
     * @param needConvert
     * @return
     * @throws Exception
     */
    public String makeQueryPageServiceWeb(String table , String whereClause, boolean needConvert) throws Exception{
        GenerateService service = new GenerateService();
        String retString = service.generateQueryService(table, Constants.QUERY_PAGE, whereClause);
        if(needConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
    
    /**
     * 生成insert
     * @param table
     * @return
     * @throws Exception 
     */
    public String makeInsertServiceWeb(String table ,boolean needConvert) throws Exception{
        GenerateService service = new GenerateService();
        String retString =  service.generateInsertService(table, null, Constants.INSERT);
        if(needConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
    
    /**
     * 生成insert return 
     * @param table
     * @param needConvert
     * @return
     * @throws Exception
     */
    public String makeInsertReturnServiceWeb(String table ,boolean needConvert) throws Exception{
        GenerateService service = new GenerateService();
        String retString =  service.generateInsertService(table, null, Constants.INSERT_RETURN);
        if(needConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
    
    /**
     * update的service生成
     * @param table
     * @param whereClause
     * @param isPK
     * @param needConvert
     * @return
     * @throws Exception
     */
    public String makeUpdateServiceWeb(String table, String whereClause , boolean isPK , boolean needConvert) throws Exception{
        if(isPK){
            GenerateBean bean = GenerateBase.getBean(table, null);
            ColumnBean columnBean = Common.getPKColumn(bean.getColumnList());
            whereClause = " WHERE "+ columnBean.getColumnName() + " = ?";
        }
        
        GenerateService service = new GenerateService();
        String retString =  service.generateInsertService(table, whereClause, Constants.UPDATE);
        if(needConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
    
    /**
     * update的主键修改service生成
     * @param table
     * @param whereClause
     * @param isPK
     * @param needConvert
     * @return
     * @throws Exception
     */
    public String makeUpdateServiceByIdWeb(String table, String whereClause , boolean isPK , boolean needConvert) throws Exception{
        if(isPK){
            GenerateBean bean = GenerateBase.getBean(table, null);
            ColumnBean columnBean = Common.getPKColumn(bean.getColumnList());
            whereClause = " WHERE "+ columnBean.getColumnName() + " = ?";
        }
        
        GenerateService service = new GenerateService();
        String retString =  service.generateInsertService(table, whereClause, Constants.UPDATE_PK);
        if(needConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
    
    /**
     * delete的service
     * @param table
     * @param whereClause
     * @param isPK
     * @param needConvert
     * @return
     * @throws Exception
     */
    public String makeDeleteServiceWeb(String table, String whereClause , boolean isPK , boolean needConvert) throws Exception{
        if(isPK){
            GenerateBean bean = GenerateBase.getBean(table, null);
            ColumnBean columnBean = Common.getPKColumn(bean.getColumnList());
            whereClause = " WHERE "+ columnBean.getColumnName() + " = ?";
        }
        
        GenerateService service = new GenerateService();
        String retString =  service.generateInsertService(table, whereClause, Constants.DELETE);
        if(needConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
}
