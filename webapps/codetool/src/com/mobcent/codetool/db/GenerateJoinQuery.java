package com.mobcent.codetool.db;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.bean.GenerateBean;
import com.mobcent.codetool.bean.OutputBean;
import com.mobcent.codetool.bean.ParameterBean;
import com.mobcent.codetool.util.GenerateBase;

public class GenerateJoinQuery {

    public String generateJoinQuery(String logicName, String sql) throws Exception {
        String whereClause = Common.getWhereClause(sql);
        GenerateBean bean = GenerateBase.getJoinBean(logicName, sql, whereClause);
        
        ParameterBean paramterBean = Common.getJoinMethodParameter(bean);
        
        //���ó���
        OutputBean outputBean = new OutputBean();
        outputBean.setClassName("List");
        outputBean.setContainClass(bean.getClassName() + "OutputBean");
        
        //����javadoc
        String retString = GenerateJavaDoc.makeJavaDoc(paramterBean.getParameterList() , outputBean);
        
        retString += "public List query" + bean.getClassName() + paramterBean.getParameter()
                + "  throws JoymeDBException{" + Constants.NEXT_LINE;
        retString += "  DataBean dbean = null;" + Constants.NEXT_LINE;
        retString += "  try {" + Constants.NEXT_LINE;
        retString += "      String sql = \"" + sql + "\";" + Constants.NEXT_LINE;

        //����in��?
        retString += Common.getSqlInString(bean.getWhereColumnList());

        //����objectList;
        retString += "  List objectList = new ArrayList();" + Constants.NEXT_LINE;
        retString += Common.getObjectList(bean.getWhereColumnList(), Constants.JOIN_QUERY);
        
        retString += "      dbean = this.executeBindingQuery(conn, sql, objectList.toArray());" + Constants.NEXT_LINE;
        retString += "      List retList = new ArrayList();" + Constants.NEXT_LINE;
        retString += "      ResultSet rs = dbean.getRs();" + Constants.NEXT_LINE;
        retString += "      while (rs.next()) {" + Constants.NEXT_LINE;
        retString += "           " + bean.getClassName() + "OutputBean " + bean.getInstanceName() + " = new " + bean.getClassName() + "OutputBean();"
                + Constants.NEXT_LINE;
        retString += Common.getRsString(bean.getColumnList() , bean.getInstanceName());
        retString += "           retList.add(" + bean.getInstanceName() + ");" + Constants.NEXT_LINE;
        retString += "      }" + Constants.NEXT_LINE;
        retString += "    return retList;" + Constants.NEXT_LINE;
        retString += "  } catch (Exception e) {" + Constants.NEXT_LINE;
        retString += "      throw new JoymeDBException(e);" + Constants.NEXT_LINE;
        retString += "  } finally {" + Constants.NEXT_LINE;
        retString += "      this.cleanup(dbean, false);" + Constants.NEXT_LINE;
        retString += "  }" + Constants.NEXT_LINE;
        retString += "}" + Constants.NEXT_LINE;

        return retString;
    }

    /**
     * ���������bean
     * @param logicName
     * @param sql
     * @return
     * @throws Exception 
     */
    public String getInputBean(String logicName, String sql) throws Exception {
        String whereClause = Common.getWhereClause(sql);
        GenerateBean bean = GenerateBase.getJoinBean(logicName, sql, whereClause);
        GenerateBase.putWhereBeanToColumnBean(sql, bean);

        GeneratePOJO pojo = new GeneratePOJO();
        String retString = "public class " + bean.getClassName() + "InputBean {" + Constants.NEXT_LINE;
        retString = pojo.makePOJO(bean, retString, false);

        return retString;
    }

    /**
     * ���������bean
     * @param logicName
     * @param sql
     * @return
     * @throws Exception
     */
    public String getOutputBean(String logicName, String sql) throws Exception {
        String whereClause = Common.getWhereClause(sql);
        GenerateBean bean = GenerateBase.getJoinBean(logicName, sql, whereClause);
        GeneratePOJO pojo = new GeneratePOJO();
        String retString = "public class " + bean.getClassName() + "OutputBean {" + Constants.NEXT_LINE;
        retString = pojo.makePOJO(bean, retString, false);
        return retString;
    }

    public static void main(String[] args) throws Exception {
        String sql = "SELECT * FROM sdk_user , sdk_forum_user WHERE sdk_user.user_id=sdk_forum_user.user_id AND sdk_user.registed_forum_id>? AND sdk_forum_user.reply_posts_num in ($list1)";
        GenerateJoinQuery j = new GenerateJoinQuery();
//        System.out.println(j.getInputBean("queryDouble", sql));
        System.out.println(j.generateJoinQuery("queryDouble", sql));
    }
}
