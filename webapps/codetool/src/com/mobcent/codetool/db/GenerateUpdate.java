package com.mobcent.codetool.db;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.bean.GenerateBean;
import com.mobcent.codetool.bean.OutputBean;
import com.mobcent.codetool.bean.ParameterBean;
import com.mobcent.codetool.util.GenerateBase;

public class GenerateUpdate {
    public String generateUpdate(String table, String whereClauseString) throws Exception {
        GenerateBean bean = GenerateBase.getBean(table, whereClauseString);

//        ParameterBean pBean = Common.getInsertMethodParameter(bean);
        ParameterBean pBean = Common.getMethodParameter(bean, true);

        OutputBean outputBean = new OutputBean();
        outputBean.setClassName("int");

        String retString = GenerateJavaDoc.makeJavaDoc(pBean.getParameterList(), outputBean, Constants.UPDATE_RETURN_DOC);
        retString += "public int update" + bean.getClassName() + pBean.getParameter() + "  throws JoymeDBException{"
                + Constants.NEXT_LINE;
        retString += "  try{" + Constants.NEXT_LINE;
        retString += "        String sql = \"" + getUpdateSql(table, whereClauseString, bean) + "\";" + Constants.NEXT_LINE;
        retString += "        List objectList = new ArrayList();" + Constants.NEXT_LINE;
        retString += "        List columnList = bean.getNotNullColumnList();" + Constants.NEXT_LINE;
        retString += "        sql = sql.replace(\"$updateStr\", this.setUpdate(columnList, objectList));" + Constants.NEXT_LINE;
        //����in��?
        retString += Common.getSqlInString(bean.getWhereColumnList());

        //����objectList;
        retString += Common.getObjectList(bean.getWhereColumnList());

        retString += "      return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);" + Constants.NEXT_LINE;
        retString += "      }catch (Exception e) { " + Constants.NEXT_LINE;
        retString += "        throw new JoymeDBException(e);" + Constants.NEXT_LINE;
        retString += "    }" + Constants.NEXT_LINE;
        retString += "   }" + Constants.NEXT_LINE;
        return retString;
    }


    public String generateUpdateById(String table, String whereClauseString) throws Exception {
        GenerateBean bean = GenerateBase.getBean(table, whereClauseString);

        ParameterBean pBean = Common.getMethodParameter(bean, false, true, true);

        OutputBean outputBean = new OutputBean();
        outputBean.setClassName("int");

        String retString = GenerateJavaDoc.makeJavaDoc(pBean.getParameterList(), outputBean, Constants.UPDATE_RETURN_DOC);
        retString += "public int update" + bean.getClassName() + pBean.getParameter() + "  throws JoymeDBException{"
                + Constants.NEXT_LINE;
        retString += "  try{" + Constants.NEXT_LINE;
        retString += "        String sql = \"" + getUpdateSql(table, whereClauseString, bean) + "\";" + Constants.NEXT_LINE;
        retString += "        List objectList = new ArrayList();" + Constants.NEXT_LINE;
        retString += "        List columnList = bean.getNotNullColumnList();" + Constants.NEXT_LINE;
        retString += "        sql = sql.replace(\"$updateStr\", this.setUpdate(columnList, objectList));" + Constants.NEXT_LINE;
        //����in��?
        retString += Common.getSqlInString(bean.getWhereColumnList());

        //����objectList;
        retString += Common.getObjectList(bean.getWhereColumnList(), Constants.UPDATE_PK);

        retString += "      return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);" + Constants.NEXT_LINE;
        retString += "      }catch (Exception e) { " + Constants.NEXT_LINE;
        retString += "        throw new JoymeDBException(e);" + Constants.NEXT_LINE;
        retString += "    }" + Constants.NEXT_LINE;
        retString += "   }" + Constants.NEXT_LINE;
        return retString;
    }

    public String getUpdateSql(String table, String whereClauseString, GenerateBean bean) {
        String sql = "UPDATE " + table;
        sql += " SET $updateStr " + whereClauseString;
        return sql;
    }

    public static void main(String[] args) throws Exception {
        GenerateUpdate update = new GenerateUpdate();
        String table = "sdk_forum_user";
        String whereClauseString = "where forum_user_id =?";
        System.out.println(update.generateUpdateById(table, whereClauseString));
    }
}
