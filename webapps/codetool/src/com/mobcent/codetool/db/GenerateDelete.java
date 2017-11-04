package com.mobcent.codetool.db;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.bean.GenerateBean;
import com.mobcent.codetool.bean.OutputBean;
import com.mobcent.codetool.bean.ParameterBean;
import com.mobcent.codetool.util.GenerateBase;

public class GenerateDelete {
    public String generateDelete(String table, String whereClauseString) throws Exception {
        GenerateBean bean = GenerateBase.getBean(table, whereClauseString);

        ParameterBean pBean = Common.getMethodParameter(bean);

        OutputBean outputBean = new OutputBean();
        outputBean.setClassName("int");

        String retString = GenerateJavaDoc.makeJavaDoc(pBean.getParameterList(), outputBean, Constants.UPDATE_RETURN_DOC);
        
        retString += "public int delete" + bean.getClassName() + pBean.getParameter() + "  throws JoymeDBException{" + Constants.NEXT_LINE;
        retString += "   try{" + Constants.NEXT_LINE;
        retString += "      String sql = \"" + getDeleteSql(table, whereClauseString) + "\";" + Constants.NEXT_LINE;
        
        //����in��?
        retString += Common.getSqlInString(bean.getWhereColumnList());
        
        //����objectList;
        retString += "  List objectList = new ArrayList();" + Constants.NEXT_LINE;
        retString += Common.getObjectList(bean.getWhereColumnList());
        retString += "      return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);" + Constants.NEXT_LINE;
        
        retString += " }catch (Exception e) { " + Constants.NEXT_LINE;
        retString += "      throw new JoymeDBException(e);" + Constants.NEXT_LINE;
        retString += " }" + Constants.NEXT_LINE;
        retString += "}" + Constants.NEXT_LINE;
        return retString;
    }
    
    

    private String getDeleteSql(String table, String whereClauseString) {
        String sql = "DELETE FROM " + table +" "+ whereClauseString;
        return sql;
    }



    public static void main(String[] args) throws Exception {
        String table = "sdk_forum_user";
        String whereClauseString = " WHERE forum_user_id =?";
        GenerateDelete delete = new GenerateDelete();
        System.out.println(delete.generateDelete(table, whereClauseString));
        
    }
}
