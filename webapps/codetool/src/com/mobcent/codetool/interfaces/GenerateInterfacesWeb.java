package com.mobcent.codetool.interfaces;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.bean.ColumnBean;
import com.mobcent.codetool.bean.GenerateBean;
import com.mobcent.codetool.db.Common;
import com.mobcent.codetool.util.GenerateBase;

public class GenerateInterfacesWeb {
    /**
     * 根据主键的接口
     * @param table
     * @param needConvert
     * @param isDao
     * @return
     * @throws Exception
     */
    public String makeQueryPKInterfacesWeb(String table, boolean needConvert, boolean isDao) throws Exception {
        GenerateBean bean = GenerateBase.getBean(table, null);
        ColumnBean columnBean = Common.getPKColumn(bean.getColumnList());
        String whereClause = " WHERE " + columnBean.getColumnName() + " = ?";
        GenerateInterfaces interfaces = new GenerateInterfaces();
        String retString = interfaces.generateQueryInterface(table, Constants.QUERY_PK, whereClause, isDao);
        if (needConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
    
    /**
     * 条件查询的接口
     * @param table
     * @param whereClause
     * @param needConvert
     * @param isDao
     * @return
     * @throws Exception 
     */
    public String makeQueryConditionInterface(String table , String whereClause, boolean needConvert, boolean isDao) throws Exception{
        GenerateInterfaces interfaces = new GenerateInterfaces();
        String retString = interfaces.generateQueryInterface(table, Constants.QUERY, whereClause, isDao);
        if (needConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
    
    /**
     * count的接口
     * @param table
     * @param whereClause
     * @param needConvert
     * @param isDao
     * @return
     * @throws Exception
     */
    public String makeQueryCountInterface(String table , String whereClause, boolean needConvert, boolean isDao) throws Exception{
        GenerateInterfaces interfaces = new GenerateInterfaces();
        String retString = interfaces.generateQueryInterface(table, Constants.QUERY_COUNT, whereClause, isDao);
        if (needConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
    
    /**
     * page的接口
     * @param table
     * @param whereClause
     * @param needConvert
     * @param isDao
     * @return
     * @throws Exception
     */
    public String makeQueryPageInterface(String table , String whereClause, boolean needConvert, boolean isDao) throws Exception{
        GenerateInterfaces interfaces = new GenerateInterfaces();
        String retString = interfaces.generateQueryInterface(table, Constants.QUERY_PAGE, whereClause, isDao);
        if (needConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }

    /**
     * 插入接口
     * @param table
     * @param needConvert
     * @return
     * @throws Exception
     */
    public String makeInsertServiceWeb(String table, boolean needConvert, boolean isDao) throws Exception {
        GenerateInterfaces interfaces = new GenerateInterfaces();
        String retString = interfaces.generateInsertInterface(table, null, Constants.INSERT, isDao);
        if (needConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
    
    /**
     * 插入返回主键接口
     * @param table
     * @param needConvert
     * @param isDao
     * @return
     * @throws Exception
     */
    public String makeInsertReturnServiceWeb(String table, boolean needConvert, boolean isDao) throws Exception {
        GenerateInterfaces interfaces = new GenerateInterfaces();
        String retString = interfaces.generateInsertInterface(table, null, Constants.INSERT_RETURN, isDao);
        if (needConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
    
    /**
     * update 接口
     * @param table
     * @param whereClause
     * @param isPK
     * @param needConvert
     * @return
     * @throws Exception
     */
    public String makeUpdateServiceWeb(String table, String whereClause , boolean isPK , boolean needConvert ,boolean isDao) throws Exception{
        if(isPK){
            GenerateBean bean = GenerateBase.getBean(table, null);
            ColumnBean columnBean = Common.getPKColumn(bean.getColumnList());
            whereClause = " WHERE "+ columnBean.getColumnName() + " = ?";
        }
        
        GenerateInterfaces interfaces = new GenerateInterfaces();
        String retString =  interfaces.generateInsertInterface(table, whereClause, Constants.UPDATE, isDao);
        if(needConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
    
    
    /**
     * update 接口
     * @param table
     * @param whereClause
     * @param isPK
     * @param needConvert
     * @return
     * @throws Exception
     */
    public String makeUpdateByIdServiceWeb(String table, String whereClause , boolean isPK , boolean needConvert ,boolean isDao) throws Exception{
        if(isPK){
            GenerateBean bean = GenerateBase.getBean(table, null);
            ColumnBean columnBean = Common.getPKColumn(bean.getColumnList());
            whereClause = " WHERE "+ columnBean.getColumnName() + " = ?";
        }
        
        GenerateInterfaces interfaces = new GenerateInterfaces();
        String retString =  interfaces.generateInsertInterface(table, whereClause, Constants.UPDATE_PK, isDao);
        if(needConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
    
    /**
     * delete 接口
     * @param table
     * @param whereClause
     * @param isPK
     * @param needConvert
     * @return
     * @throws Exception
     */
    public String makeDeleteServiceWeb(String table, String whereClause , boolean isPK , boolean needConvert ,boolean isDao) throws Exception{
        if(isPK){
            GenerateBean bean = GenerateBase.getBean(table, null);
            ColumnBean columnBean = Common.getPKColumn(bean.getColumnList());
            whereClause = " WHERE "+ columnBean.getColumnName() + " = ?";
        }
        
        GenerateInterfaces interfaces = new GenerateInterfaces();
        String retString =  interfaces.generateInsertInterface(table, whereClause, Constants.DELETE, isDao);
        if(needConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
    
    /**
     * join query的接口
     * @param logicName
     * @param sql
     * @param isDao
     * @param isConvert
     * @return
     * @throws Exception
     */
    public String makeJoinQueryServiceWeb(String logicName, String sql, boolean isDao ,boolean isConvert) throws Exception{
        GenerateInterfaces interfaces = new GenerateInterfaces();
        String retString = interfaces.generateJoinQueryInterface(logicName, sql, isDao);
    
        if(isConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
}
