package com.mobcent.codetool.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mobcent.codetool.bean.ColumnBean;
import com.mobcent.codetool.bean.ErrorCodeBean;
import com.mobcent.codetool.bean.GenerateBean;
import com.mobcent.codetool.bean.WhereClauseBean;
import com.mobcent.codetool.db.Common;
import com.mobcent.codetool.db.DBColumn;

public class GenerateBase {
    /**
     * 单表中的bean
     * @param table
     * @param whereClause
     * @return
     * @throws Exception
     */
    public static GenerateBean getBean(String table, String whereClause) throws Exception {
        GenerateBean bean = new GenerateBean();
        //获得驼峰写法
        String className = NameUtil.changeToJavaName(table, true);
        String instanceName = NameUtil.changeToJavaName(table, false);
        ErrorCodeBean ebean = NameUtil.getErrorString(table);

        //获得这个表的字段
        DBColumn dbColumn = new DBColumn();
        List columnList = dbColumn.getDBColumns(table);

        //where语句分析
        List whereColumnList = new ArrayList();
        if (whereClause != null && !whereClause.equals("")) {
            NameUtil nameUtil = new NameUtil();
            whereColumnList = nameUtil.parseWhereClause(whereClause);
        }

        bean.setClassName(className);
        bean.setInstanceName(instanceName);
        bean.setColumnList(columnList);
        bean.setWhereColumnList(whereColumnList);
        bean.setErrorCodebean(ebean);
        return bean;
    }

    /**
     * join查询的bean
     * @throws Exception 
     */
    public static GenerateBean getJoinBean(String logicName, String sql, String whereClause) throws Exception {
        GenerateBean bean = new GenerateBean();
        
        //执行前去点where条件
        String tempSql = Common.removeWhereClause(sql);
        
        //获得这个查询取出的的字段
        List columnList = DBColumn.getColumnListFromMeta(tempSql);

        //获得驼峰写法
        String className = NameUtil.changeToJavaName(logicName, true);
        String instanceName = NameUtil.changeToJavaName(logicName, false);
        ErrorCodeBean ebean = NameUtil.getErrorString(logicName);

        //where语句分析
        List whereColumnList = new ArrayList();
        if (whereClause != null && !whereClause.equals("")) {
            NameUtil nameUtil = new NameUtil();
            whereColumnList = nameUtil.parseWhereClause(whereClause);
        }

        bean.setClassName(className);
        bean.setInstanceName(instanceName);
        bean.setColumnList(columnList);
        bean.setWhereColumnList(whereColumnList);
        bean.setErrorCodebean(ebean);
        return bean;
    }
    
    public static void putWhereBeanToColumnBean(String sql , GenerateBean bean) throws FileNotFoundException, SQLException, IOException{
        String allSelectSql = Common.selectAllClause(sql);
        List allColumnList = DBColumn.getColumnListFromMeta(allSelectSql);
        
        List retList = new ArrayList();
        List whereColumnList = bean.getWhereColumnList();
        for (int i = 0, j = whereColumnList.size(); i < j; i++) {
            WhereClauseBean wBean = (WhereClauseBean)whereColumnList.get(i);
            for (int k = 0, l = allColumnList.size(); k < l; k++) {
                ColumnBean cBean = (ColumnBean)allColumnList.get(k);
                if(cBean.getColumnName().equals(wBean.getColumnName())){
                    retList.add(cBean);
                }
            }
        }
        
        bean.setColumnList(retList);
    }
}
