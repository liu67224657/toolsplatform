package com.mobcent.codetool.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.bean.ColumnBean;
import com.mobcent.codetool.bean.GenerateBean;
import com.mobcent.codetool.bean.ParameterBean;
import com.mobcent.codetool.bean.ParameterPropertyBean;
import com.mobcent.codetool.bean.WhereClauseBean;
import com.mobcent.codetool.util.NameUtil;
import com.mobcent.codetool.util.StringUtil;

public class Common {
    /**
     * 生成方法中的参数
     * @param bean
     * @return
     */
    public static ParameterBean getMethodParameter(GenerateBean bean) {
        return getMethodParameter(bean, false, false, false);
    }

    public static ParameterBean getMethodParameter(GenerateBean bean, boolean hasBean) {
        return getMethodParameter(bean, false, hasBean, false);
    }

    public static ParameterBean getMethodParameter(GenerateBean bean, boolean hasPage, boolean hasBean, boolean onlyBean) {
        ParameterBean pBean = new ParameterBean();

        List columnList = bean.getColumnList();

        //所有的参数
        List parameterList = new ArrayList();

        String retString = "(Connection conn, ";

        if (hasBean) {
            retString += " " + bean.getClassName() + " bean,";
            ParameterPropertyBean propertyBean = new ParameterPropertyBean();
            propertyBean.setClassName(bean.getClassName());
            propertyBean.setInstanceName("bean");
            parameterList.add(propertyBean);
        }

        //含有list函数的参数，放到这里
        List tempList = new ArrayList();
        //不含list的参数
        List objectList = new ArrayList();

        List whereColumnList = bean.getWhereColumnList();
        for (int i = 0, j = whereColumnList.size(); i < j; i++) {
            WhereClauseBean clauseBean = (WhereClauseBean) whereColumnList.get(i);
            if (clauseBean.getListName() != null) {
                tempList.add(clauseBean.getListName());
            } else {
                objectList.add(clauseBean);
            }
        }

        if (whereColumnList.size() == tempList.size()) { //全完是in
            for (int i = 0, j = tempList.size(); i < j; i++) {
                retString += " List " + tempList.get(i) + ",";
                ParameterPropertyBean propertyBean = new ParameterPropertyBean();
                propertyBean.setClassName("List");
                propertyBean.setInstanceName(tempList.get(i) + "");
                propertyBean.setContainClass("Object<int , String , long>");
                parameterList.add(propertyBean);
            }
        } else { //不完全是in
            //根据where的condition的输入来查
            ParameterPropertyBean propertyBean = new ParameterPropertyBean();
            for (int i = 0, j = objectList.size(); i < j; i++) {
                WhereClauseBean clauseBean = (WhereClauseBean) objectList.get(i);
                for (int k = 0, l = columnList.size(); k < l; k++) {
                    ColumnBean columnBean = (ColumnBean) columnList.get(k);
                    if (clauseBean.getColumnName().equals(columnBean.getColumnName())) {
                        String classType = Constants.getJavaType(columnBean.getTypeName());
                        if (!onlyBean)
                            retString += " " + classType + " " + NameUtil.changeToJavaName(columnBean.getColumnName(), false) + ",";
                        propertyBean = new ParameterPropertyBean();
                        propertyBean.setClassName(classType);
                        propertyBean.setInstanceName(NameUtil.changeToJavaName(columnBean.getColumnName(), false));
                        parameterList.add(propertyBean);
                    }
                }
            }

            for (int i = 0, j = tempList.size(); i < j; i++) {
                if (!onlyBean)
                    retString += " List " + tempList.get(i) + ",";
                propertyBean = new ParameterPropertyBean();
                propertyBean.setClassName("List");
                propertyBean.setInstanceName(tempList.get(i) + "");
                propertyBean.setContainClass(Constants.INPUT_LIST_CONTAIN);
                parameterList.add(propertyBean);
            }
        }

        if (hasPage)
            retString += "int pageNum , int pageCount";

        retString = retString.trim();
        if (retString.endsWith(","))
            retString = retString.substring(0, retString.length() - 1);

        retString += ")";

        pBean.setParameter(retString);
        pBean.setParameterList(parameterList);

        return pBean;
    }

    /**
     * 获得errorcode
     * @param className
     * @return
     */
    public static String getErrorCodeClassName(String className) {
        return className + "Error";
    }

    public static String getQueryPageServiceMethodPara(String parameterString, boolean isDao) {
        //        String retString = getMethodParameter(bean, hasPage);
        //        retString = StringUtil.removeLastCharacter(retString, ")");
        //        retString += ", boolean isNeedTotalNum)";
        String retString = StringUtil.removeLastCharacter(parameterString, ")");

        if (!isDao)
            retString += ", boolean isNeedTotalNum";

        retString += ")";
        return retString;
    }

    /**
     * service传入的变量
     * @param bean
     * @param hasPage
     * @return
     */
    //    public static String getServiceInputParameter(GenerateBean bean, boolean hasPage) {
    //        String retString = "(conn, ";
    //
    //        List parameterList = new ArrayList();
    //        List whereColumnList = bean.getWhereColumnList();
    //        for (int i = 0, j = whereColumnList.size(); i < j; i++) {
    //            WhereClauseBean clauseBean = (WhereClauseBean) whereColumnList.get(i);
    //            if (clauseBean.getListName() != null) {
    //                parameterList.add(clauseBean.getListName());
    //            }
    //        }
    //
    //        if (whereColumnList.size() == parameterList.size()) { //全完是in
    //            for (int i = 0, j = parameterList.size(); i < j; i++) {
    //                retString += parameterList.get(i) + ",";
    //            }
    //        } else { //不完全是in
    //            retString += " bean,";
    //            for (int i = 0, j = parameterList.size(); i < j; i++) {
    //                retString += parameterList.get(i) + ",";
    //            }
    //        }
    //
    //        if (hasPage)
    //            retString += "pageNum ,pageCount";
    //
    //        retString = retString.trim();
    //        if (retString.endsWith(","))
    //            retString = retString.substring(0, retString.length() - 1);
    //
    //        retString += ")";
    //
    //        return retString;
    //    }

    public static String getServiceInputParameter(List paramterList) {
        String retString = "(conn, ";

        for (int i = 0, j = paramterList.size(); i < j; i++) {
            ParameterPropertyBean propertyBean = (ParameterPropertyBean) paramterList.get(i);
            retString += " " + propertyBean.getInstanceName() + ",";
        }

        retString = StringUtil.removeLastCharacter(retString.trim(), ",");

        retString += ")";
        return retString;
    }

    /**
     * 得到分页的输入参数
     * @param inputParameter
     * @return
     */
    public static String getQueryPageServiceInputPara(String inputParameter) {
        String retString = inputParameter;
        retString = StringUtil.removeLastCharacter(retString, ")");
        retString += ", pageNum ,pageCount)";
        return retString;
    }

    /**
     * 联合查询的parameter
     * @param bean
     * @return
     */
    public static ParameterBean getJoinMethodParameter(GenerateBean bean) {
        String retString = "(Connection conn, ";

        List parameterList = new ArrayList();

        List retList = new ArrayList();
        List whereColumnList = bean.getWhereColumnList();
        for (int i = 0, j = whereColumnList.size(); i < j; i++) {
            WhereClauseBean clauseBean = (WhereClauseBean) whereColumnList.get(i);
            if (clauseBean.getListName() != null) {
                parameterList.add(clauseBean.getListName());
            }
        }

        if (whereColumnList.size() == parameterList.size()) { //全完是in
            for (int i = 0, j = parameterList.size(); i < j; i++) {
                retString += " List " + parameterList.get(i) + ",";

                ParameterPropertyBean propertyBean = new ParameterPropertyBean();
                propertyBean.setClassName("List");
                propertyBean.setInstanceName(parameterList.get(i) + "");
                propertyBean.setContainClass("Object<int , String , long>");
                retList.add(propertyBean);
            }
        } else { //不完全是in
            retString += " " + bean.getClassName() + "InputBean bean,";
            ParameterPropertyBean propertyBean = new ParameterPropertyBean();
            propertyBean.setClassName(bean.getClassName() + "InputBean");
            propertyBean.setInstanceName(bean.getInstanceName());
            propertyBean.setContainClass(null);
            retList.add(propertyBean);

            for (int i = 0, j = parameterList.size(); i < j; i++) {
                retString += " List " + parameterList.get(i) + ",";
                propertyBean = new ParameterPropertyBean();
                propertyBean.setClassName("List");
                propertyBean.setInstanceName(parameterList.get(i) + "");
                propertyBean.setContainClass("Object<int , String , long>");
                retList.add(propertyBean);
            }
        }

        if (retString.endsWith(","))
            retString = retString.substring(0, retString.length() - 1);

        retString += ")";

        ParameterBean pbean = new ParameterBean();
        pbean.setParameter(retString);
        pbean.setParameterList(retList);

        return pbean;
    }

    /**
     * service的jion query的传入parameter
     * @param bean
     * @return
     */
    public static String getJoinServiceParameter(GenerateBean bean) {
        String retString = "(conn, ";

        List parameterList = new ArrayList();
        List whereColumnList = bean.getWhereColumnList();
        for (int i = 0, j = whereColumnList.size(); i < j; i++) {
            WhereClauseBean clauseBean = (WhereClauseBean) whereColumnList.get(i);
            if (clauseBean.getListName() != null) {
                parameterList.add(clauseBean.getListName());
            }
        }

        if (whereColumnList.size() == parameterList.size()) { //全完是in
            for (int i = 0, j = parameterList.size(); i < j; i++) {
                retString += parameterList.get(i) + ",";
            }
        } else { //不完全是in
            retString += " bean,";
            for (int i = 0, j = parameterList.size(); i < j; i++) {
                retString += parameterList.get(i) + ",";
            }
        }

        if (retString.endsWith(","))
            retString = retString.substring(0, retString.length() - 1);

        retString += ")";

        return retString;
    }

    //    /**
    //     * insert的方法构造
    //     * @param bean
    //     * @return
    //     */
    //    public static ParameterBean getInsertMethodParameter(GenerateBean bean) {
    //        ParameterBean pBean = new ParameterBean();
    //        List paramList = new ArrayList();
    //        
    //        String retString = "(Connection conn, ";
    //        
    //        retString += " " + bean.getClassName() + " bean";
    //        ParameterPropertyBean propertyBean = new ParameterPropertyBean();
    //        propertyBean.setClassName(bean.getClassName());
    //        propertyBean.setInstanceName("bean");
    //        paramList.add(propertyBean);
    //        
    //        List whereColumnList = bean.getWhereColumnList();
    //        for (int i = 0, j = whereColumnList.size(); i < j; i++) {
    //            WhereClauseBean clauseBean = (WhereClauseBean) whereColumnList.get(i);
    //            if (clauseBean.getListName() != null) {
    //                retString += ", List " + clauseBean.getListName();
    //                propertyBean = new ParameterPropertyBean();
    //                propertyBean.setClassName("List");
    //                propertyBean.setInstanceName(clauseBean.getListName());
    //                propertyBean.setContainClass(Constants.INPUT_LIST_CONTAIN);
    //                paramList.add(propertyBean);
    //            }
    //        }
    //
    //        retString += ")";
    //        
    //        pBean.setParameter(retString);
    //        pBean.setParameterList(paramList);
    //        
    //        return pBean;
    //    }

    /**
     * insert service层的方法构造
     * @param bean
     * @return
     */
    public static String getUpdateServiceMethodParameter(List parameterList, String className, int operationType) {
        String retString = "(conn ";

        if (operationType == Constants.UPDATE_PK) {
            retString += ", bean)";
        } else{

            for (int i = 0, j = parameterList.size(); i < j; i++) {
                ParameterPropertyBean bean = (ParameterPropertyBean) parameterList.get(i);
                retString += ", " + bean.getInstanceName();
            }
    
            retString += ")";
        }
        return retString;
    }

    /**
     * 封装in的?
     * @param whereColumnList
     * @return
     */
    public static String getSqlInString(List whereColumnList) {
        String retString = "";
        for (int i = 0, j = whereColumnList.size(); i < j; i++) {
            WhereClauseBean bean = (WhereClauseBean) whereColumnList.get(i);
            if (bean.getListName() != null) {
                retString += "      sql = sql.replace(\"$" + bean.getListName() + "\", this.makeInStr(" + bean.getListName() + "));"
                        + Constants.NEXT_LINE;
            }
        }

        return retString;
    }

    /**
     * 封装object
     * @param columnList
     * @return
     */
    /**
     * 封装object
     * @param columnList
     * @return
     */
    public static String getObjectList(List columnList, int operationType) {
        String retString = "";
        for (int i = 0, j = columnList.size(); i < j; i++) {
            WhereClauseBean bean = (WhereClauseBean) columnList.get(i);
            if (bean.getFlag().equals(" in ") || bean.getFlag().equals(" not in ")) {
                retString += "      objectList.addAll(" + bean.getListName() + ");" + Constants.NEXT_LINE;
            } else {
                String column = bean.getColumnName();
                String className = NameUtil.changeToJavaName(column, true);
                //                String temp = "bean.get" + className + "()";
                String instanceName = NameUtil.changeToJavaName(column, false);
                if (operationType == Constants.JOIN_QUERY || operationType == Constants.UPDATE_PK) {
                    retString += "      objectList.add(bean.get" + className + "());" + Constants.NEXT_LINE;
                } else {
                    retString += "      objectList.add(" + instanceName + ");" + Constants.NEXT_LINE;
                }
            }
        }

        return retString;
    }

    public static String getObjectList(List columnList) {
        return getObjectList(columnList, Constants.QUERY);
    }

    /**
     * 分析sql语句中select的字段
     * @param sql
     * @return
     */
    public static String getSelectClause(String sql) {
        String selectFlag = "SELECT ";
        String fromflag = " FROM ";
        int selectPosition = sql.toUpperCase().indexOf(selectFlag);
        int fromPosition = sql.toUpperCase().indexOf(fromflag);
        String selectClause = null;
        if (selectPosition >= 0 && fromPosition >= 0) {
            selectClause = sql.substring(selectPosition + selectFlag.length() + 1, fromPosition);
        }

        return selectClause;
    }

    /**
     * 分析sql中的where
     * @param sql
     * @return
     */
    public static String getWhereClause(String sql) {
        String whereFlag = " WHERE ";
        int wherePosition = sql.toUpperCase().indexOf(whereFlag);
        String whereClause = null;
        if (wherePosition >= 0) {
            whereClause = sql.substring(wherePosition + 1, sql.length());
        }

        return whereClause;
    }

    /**
     * 去掉where
     * @param sql
     * @return
     */
    public static String removeWhereClause(String sql) {
        String whereFlag = " WHERE ";
        int wherePosition = sql.toUpperCase().indexOf(whereFlag);
        String selectClause = null;
        if (wherePosition >= 0) {
            selectClause = sql.substring(0, wherePosition);
        }
        return selectClause + " LIMIT 0 , 1";
    }

    /**
     * 得到查询所有字段的sql
     * @param sql
     * @return
     */
    public static String selectAllClause(String sql) {
        sql = removeWhereClause(sql);
        String selectFlag = "SELECT ";
        String fromflag = " FROM ";
        int selectPosition = sql.toUpperCase().indexOf(selectFlag);
        int fromPosition = sql.toUpperCase().indexOf(fromflag);
        String retString = null;
        if (selectPosition >= 0 && fromPosition >= 0) {
            retString = sql.substring(0, selectPosition + selectFlag.length()) + "*" + sql.substring(fromPosition, sql.length());
        }

        return retString;
    }

    /**
     * 封装rs部分
     * @param columnList
     * @return
     */
    public static String getRsString(List columnList, String instanceName) {
        Map columnMap = Constants.getColumnTypeMap();
        String retStr = "";
        for (int i = 0, j = columnList.size(); i < j; i++) {
            ColumnBean bean = (ColumnBean) columnList.get(i);
            String columnName = bean.getColumnName();
            String className = NameUtil.changeToJavaName(columnName, true);

            String type = bean.getTypeName();
            String rsType = null;
            if (columnMap.get(type) != null) {
                rsType = columnMap.get(type).toString();
            } else {
                rsType = "Object";
            }

            retStr += "           " + instanceName + ".set" + className + "(rs.get" + rsType + "(\"" + columnName + "\"));";
            retStr += Constants.NEXT_LINE;
        }
        return retStr;
    }

    /**
     * 得到主键column
     * @return
     */
    public static ColumnBean getPKColumn(List columnList) {
        for (Iterator iterator = columnList.iterator(); iterator.hasNext();) {
            ColumnBean columnBean = (ColumnBean) iterator.next();
            if (columnBean.isPK() || columnBean.isAutoCreate()) {
                return columnBean;
            }
        }
        return null;
    }

    public static String replaceToWeb(String retString) {
        retString = retString.replaceAll(Constants.NEXT_LINE, Constants.WEB_NEXT_LINE);
        retString = retString.replaceAll(" ", Constants.WEB_SPACE);
        return retString;
    }

    /**
     * 返回类型
     * @param logicName
     * @param operationType
     * @return
     */
    public static String getReturnBean(String logicName, int operationType) {
        String retString = "";
        if (operationType == Constants.QUERY) {
            retString = "List";
        } else if (operationType == Constants.QUERY_PAGE)
            retString = "PageQueryBean";
        else if (operationType == Constants.INSERT || operationType == Constants.UPDATE || operationType == Constants.DELETE
                || operationType == Constants.QUERY_COUNT || operationType == Constants.UPDATE_PK) {
            retString = "int";
        } else if (operationType == Constants.JOIN_QUERY) {
            //            retString = NameUtil.changeToJavaName(logicName, true) + "OutputBean";
            retString = "List";
        } else if (operationType == Constants.QUERY_PK)
            retString = NameUtil.changeToJavaName(logicName, true);
        else if (operationType == Constants.INSERT_RETURN)
            retString = "long";

        return retString;
    }

    /**
     * 方法名
     * @param logicName
     * @param opertionType
     * @return
     */
    public static String getMethodName(String logicName, int operationType) {
        String className = NameUtil.changeToJavaName(logicName, true);
        String retString = "";
        if (operationType == Constants.QUERY || operationType == Constants.QUERY_PAGE || operationType == Constants.JOIN_QUERY
                || operationType == Constants.QUERY_PK)
            retString = "query" + className;
        if (operationType == Constants.QUERY_COUNT)
            retString = "count" + className;
        if (operationType == Constants.INSERT || operationType == Constants.INSERT_RETURN)
            retString = "insert" + className;
        if (operationType == Constants.UPDATE || operationType == Constants.UPDATE_PK)
            retString = "update" + className;
        if (operationType == Constants.DELETE)
            retString = "delete" + className;

        return retString;
    }

    public static void main(String[] args) {
        String sql = "SELECT t1.user_id FROM sdk_user t1 INNER JOIN sdk_forum_user t2 ON t1.user_id = t2.user_id WHERE t1.registed_forum_id > ? AND t2.reply_posts_num = ?";
        System.out.println(Common.selectAllClause(sql));
    }

}
