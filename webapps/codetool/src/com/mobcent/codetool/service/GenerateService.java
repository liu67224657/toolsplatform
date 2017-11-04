package com.mobcent.codetool.service;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.bean.GenerateBean;
import com.mobcent.codetool.bean.OutputBean;
import com.mobcent.codetool.bean.ParameterBean;
import com.mobcent.codetool.db.Common;
import com.mobcent.codetool.db.GenerateJavaDoc;
import com.mobcent.codetool.util.GenerateBase;

public class GenerateService {
    /**
     * query的service层
     * @param table
     * @param operationType
     * @param whereClause
     * @return
     * @throws Exception
     */
    public String generateQueryService(String table, int operationType, String whereClause) throws Exception {
        boolean hasPage = false;
        if (operationType == Constants.QUERY_PAGE)
            hasPage = true;

        GenerateBean bean = GenerateBase.getBean(table, whereClause);
        ParameterBean pBean = Common.getMethodParameter(bean, hasPage, false , false);

        OutputBean outputBean = new OutputBean();
        outputBean.setClassName(Common.getReturnBean(table, operationType));

        if ("List".equals(Common.getReturnBean(table, operationType)))
            outputBean.setContainClass(bean.getInstanceName());

        //生成javadoc
        String retString = GenerateJavaDoc.makeJavaDoc(pBean.getParameterList(), outputBean, hasPage);

        String methodName = Common.getMethodName(table, operationType);
        String inputMethodParameter = Common.getServiceInputParameter(pBean.getParameterList());

        //query page的service单表中的bean
        String parameter = "";
        if (operationType != Constants.QUERY_PAGE)
            parameter = pBean.getParameter();
        else
            parameter = Common.getQueryPageServiceMethodPara(pBean.getParameter(), hasPage);

        retString += makeServiceBody(bean, table, operationType, methodName, inputMethodParameter, parameter);
        return retString;
    }

    /**
     * 插入的service层
     * @param table
     * @param operationType INSERT/UPDATE/DELETE
     * @return
     * @throws Exception
     */
    public String generateInsertService(String table, String whereClause, int operationType) throws Exception {
        boolean hasBean = true;
        boolean onlyBean = false;
        if (operationType == Constants.INSERT)
            whereClause = null;
        if (operationType == Constants.DELETE)
            hasBean = false;
        if (operationType == Constants.UPDATE_PK)
            onlyBean = true;

        GenerateBean bean = GenerateBase.getBean(table, whereClause);

        ParameterBean pBean = Common.getMethodParameter(bean, false,hasBean ,onlyBean);

        OutputBean outputBean = new OutputBean();
        outputBean.setClassName(Common.getReturnBean(table, operationType));

        //生成javadoc
        String retString = GenerateJavaDoc.makeJavaDoc(pBean.getParameterList(), outputBean, Constants.UPDATE_RETURN_DOC);

        String methodName = Common.getMethodName(table, operationType);
        String inputMethodParameter = Common.getUpdateServiceMethodParameter(pBean.getParameterList(), bean.getClassName() , operationType);
        
        
        retString += makeServiceBody(bean, table, operationType, methodName, inputMethodParameter, pBean.getParameter());

        return retString;
    }

    /**
     * 生成联合查询的service
     * @param loginNameString
     * @param sql
     * @return
     * @throws Exception 
     */
    public String generateJoinQueryService(String logicName, String sql) throws Exception {
        int operationType = Constants.JOIN_QUERY;
        String whereClause = Common.getWhereClause(sql);
        GenerateBean bean = GenerateBase.getJoinBean(logicName, sql, whereClause);
        ParameterBean paramterBean = Common.getJoinMethodParameter(bean);

        OutputBean outputBean = new OutputBean();
        outputBean.setClassName("List");
        outputBean.setContainClass(bean.getClassName() + "OutputBean");

        //生成javadoc
        String retString = GenerateJavaDoc.makeJavaDoc(paramterBean.getParameterList() , outputBean);

//                retString += GenerateJavaDoc.makeJavaDoc(, outputBean);

        String methodName = Common.getMethodName(logicName, operationType);
        String inputMethodParameter = Common.getJoinServiceParameter(bean);
        String parameter = Common.getJoinMethodParameter(bean).getParameter();
        retString += makeServiceBody(bean, logicName, operationType, methodName, inputMethodParameter, parameter);

        return retString;

    }

    private String makeServiceBody(GenerateBean bean, String table, int operationType, String methodName, String inputMethodParameter,
            String parameter) {
        String temp = "";
        if (operationType == Constants.QUERY_PK)
            temp = "byId";

        String retString = "public " + Common.getReturnBean(table, operationType) + " " + methodName + temp + parameter
                + " throws JoymeDBException, JoymeServiceException{" + Constants.NEXT_LINE;

        retString += "  boolean isCloseConn = (conn != null) ? false : true;" + Constants.NEXT_LINE;
        retString += "  try {" + Constants.NEXT_LINE;
        retString += "    if (conn == null)" + Constants.NEXT_LINE;
        retString += "      conn = subDao.getConnection();" + Constants.NEXT_LINE;
        if (operationType == Constants.QUERY_PAGE) { //分页的时候，单做处理
            retString += "    int totalNum = 0;" + Constants.NEXT_LINE;
            retString += "    if (isNeedTotalNum)" + Constants.NEXT_LINE;
            retString += "       totalNum = subDao.count" + bean.getClassName() + inputMethodParameter + ";" + Constants.NEXT_LINE;
            retString += "    PageQueryBean pbean = subDao.query" + bean.getClassName() + Common.getQueryPageServiceInputPara(inputMethodParameter)
                    + ";" + Constants.NEXT_LINE;
            retString += "    pbean.setTotalNum(totalNum);" + Constants.NEXT_LINE;
            retString += "  return pbean;" + Constants.NEXT_LINE;
        } else {
            retString += "      return subDao." + methodName + temp + inputMethodParameter + ";" + Constants.NEXT_LINE;
        }

        retString += "  } catch (JoymeDBException e) { " + Constants.NEXT_LINE;
        retString += "      throw e;" + Constants.NEXT_LINE;
        retString += "  } catch (Exception e) {" + Constants.NEXT_LINE;
        retString += "      throw new JoymeServiceException(e);" + Constants.NEXT_LINE;
        retString += "  } finally {" + Constants.NEXT_LINE;
        retString += "      if (conn != null && isCloseConn) " + Constants.NEXT_LINE;
        retString += "          subDao.closeConnection(conn);" + Constants.NEXT_LINE;
        retString += "  }" + Constants.NEXT_LINE;
        retString += "}" + Constants.NEXT_LINE;
        return retString;
    }

    public static void main(String[] args) throws Exception {
        GenerateService service = new GenerateService();
        //        String str = service.generateQueryService("sdk_topic", Constants.QUERY_COUNT, "where topic_id > ? and forum_id in ($list1)");
        //        System.out.println(str);

        String str = service.generateInsertService("sdk_topic" , "where topic_id = ?" ,  Constants.UPDATE_PK);
        System.out.println(str);
        //                        GenerateService service = new GenerateService();
        //                        String str =  service.generateJoinQueryService("forumUserRole", "SELECT * FROM sdk_user t1 INNER JOIN sdk_forum_user t2 ON t1.user_id = t2.user_id WHERE t1.registed_forum_id > ? AND t2.reply_posts_num = ?");
        //        System.out.println(str);
    }
}
