package com.mobcent.codetool.interfaces;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.bean.GenerateBean;
import com.mobcent.codetool.bean.OutputBean;
import com.mobcent.codetool.bean.ParameterBean;
import com.mobcent.codetool.db.Common;
import com.mobcent.codetool.db.GenerateJavaDoc;
import com.mobcent.codetool.util.GenerateBase;

public class GenerateInterfaces {
    /**
     * 封装查询的接口层
     * @param table
     * @param operationType
     * @param whereClause
     * @param isDao 是否是dao层
     * @return
     * @throws Exception
     */
    public String generateQueryInterface(String table, int operationType, String whereClause, boolean isDao) throws Exception {
        boolean hasPage = false;
        if (operationType == Constants.QUERY_PAGE)
            hasPage = true;

        GenerateBean bean = GenerateBase.getBean(table, whereClause);

        ParameterBean pBean = Common.getMethodParameter(bean, hasPage, false ,false);

        OutputBean outputBean = new OutputBean();
        outputBean.setClassName(Common.getReturnBean(table, operationType));
        outputBean.setContainClass(bean.getInstanceName());
        //生成javadoc
        String retString = GenerateJavaDoc.makeJavaDoc(pBean.getParameterList(), outputBean, hasPage);

        String methodName = Common.getMethodName(table, operationType);
        String parameter = "";
        if (operationType == Constants.QUERY_PAGE)
            parameter = Common.getQueryPageServiceMethodPara(pBean.getParameter(),isDao);
        else
            parameter = pBean.getParameter();
        retString += this.makeServiceBody(table, operationType, methodName, parameter, isDao);
        return retString;
    }

    /**
     * insert, update ,delete的接口
     * @param table
     * @param whereClause
     * @param operationType
     * @param isDao
     * @return
     * @throws Exception
     */
    public String generateInsertInterface(String table, String whereClause, int operationType, boolean isDao) throws Exception {
        boolean hasBean = true;
        boolean onlyBean = false;
        if (operationType == Constants.INSERT)
            whereClause = null;
        if (operationType == Constants.DELETE)
            hasBean = false;
        if (operationType == Constants.UPDATE_PK)
            onlyBean = true;

        GenerateBean bean = GenerateBase.getBean(table, whereClause);

        ParameterBean pBean = Common.getMethodParameter(bean, false , hasBean ,onlyBean);

        OutputBean outputBean = new OutputBean();
        outputBean.setClassName(Common.getReturnBean(table, operationType));
        outputBean.setContainClass(bean.getInstanceName());
        //生成javadoc
        String retString = GenerateJavaDoc.makeJavaDoc(pBean.getParameterList(), outputBean);

        String methodName = Common.getMethodName(table, operationType);
        String parameter = pBean.getParameter();
        retString += makeServiceBody(table, operationType, methodName, parameter, isDao);

        return retString;
    }

    /**
     * 生成联合查询的service
     * @param loginNameString
     * @param sql
     * @return
     * @throws Exception 
     */
    public String generateJoinQueryInterface(String logicName, String sql, boolean isDao) throws Exception {
        int operationType = Constants.JOIN_QUERY;
        String whereClause = Common.getWhereClause(sql);

        GenerateBean bean = GenerateBase.getJoinBean(logicName, sql, whereClause);

        ParameterBean pBean = Common.getMethodParameter(bean);

        OutputBean outputBean = new OutputBean();
        outputBean.setClassName(Common.getReturnBean(logicName, operationType));
        outputBean.setContainClass(bean.getClassName() + "OutputBean");
        outputBean.setInputClass(bean.getClassName() + "InputBean");
        //生成javadoc
        String retString = GenerateJavaDoc.makeJavaDoc(pBean.getParameterList(), outputBean);

        String methodName = Common.getMethodName(logicName, operationType);
        String parameter = Common.getJoinMethodParameter(bean).getParameter();
        retString += makeServiceBody(logicName, operationType, methodName, parameter, isDao);

        return retString;

    }

    /**
     * 制作主题程序
     * @param table
     * @param operationType
     * @param methodName
     * @param parameter
     * @param isDao
     * @return
     */
    private String makeServiceBody(String table, int operationType, String methodName, String parameter, boolean isDao) {
        String temp = "";
        if (operationType == Constants.QUERY_PK)
            temp = "byId";

        String retString = "    " + Common.getReturnBean(table, operationType) + " " + methodName + temp + parameter + " throws JoymeDBException ";
        if (!isDao)
            retString += " ,JoymeServiceException ";
        retString += ";" + Constants.NEXT_LINE;

        return retString;
    }

    public static void main(String[] args) throws Exception {
        GenerateInterfaces i = new GenerateInterfaces();

        String str = i.generateQueryInterface("sdk_topic", Constants.QUERY_PAGE, "where topic_id > ?", true);
        System.out.println(str);

        //        String table = "sdk_topic";
        //        String whereClause = "where topic_id in ($list1) and topic_num<?";
        //        //        String str = i.generateQueryInterface(table, Constants.QUERY_PAGE, whereClause, true);
        //        String str = i.generateInsertInterface(table, whereClause, Constants.DELETE, false);
        //        System.out.println(str);

        //        String str = i
        //                .generateJoinQueryInterface(
        //                        "forumUserRole",
        //                        "SELECT * FROM sdk_user t1 INNER JOIN sdk_forum_user t2 ON t1.user_id = t2.user_id WHERE t1.registed_forum_id > ? AND t2.reply_posts_num = ?",
        //                        true);
        //        System.out.println(str);
    }
}
