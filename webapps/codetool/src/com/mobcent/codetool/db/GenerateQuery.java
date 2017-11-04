package com.mobcent.codetool.db;

import java.util.List;
import java.util.Map;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.bean.ColumnBean;
import com.mobcent.codetool.bean.GenerateBean;
import com.mobcent.codetool.bean.OutputBean;
import com.mobcent.codetool.bean.ParameterBean;
import com.mobcent.codetool.util.GenerateBase;
import com.mobcent.codetool.util.NameUtil;

public class GenerateQuery {

    /**
     * 通过外键生成
     * @param table
     * @param whereClause
     * @return
     * @throws Exception
     */
    public String generateQueryById(String table , String whereClause) throws Exception{
        GenerateBean bean = GenerateBase.getBean(table, whereClause);
        //得到输入 的方法
        ParameterBean parameterBean = Common.getMethodParameter(bean);
        
      //配置出参
        OutputBean outputBean = new OutputBean();
        outputBean.setClassName(bean.getClassName());
        
        //生成javdaoc
        String retString = GenerateJavaDoc.makeJavaDoc(parameterBean.getParameterList() , outputBean);
        
        retString += "public "+bean.getClassName()+" query" + bean.getClassName() +"byId" + parameterBean.getParameter() + " throws JoymeDBException{" + Constants.NEXT_LINE;
        retString += "  DataBean dbean = null;" + Constants.NEXT_LINE;
        retString += "  try {" + Constants.NEXT_LINE;
        retString += "      String sql = \"" + this.getQueryString(table, whereClause) + "\";" + Constants.NEXT_LINE;
        //生成in的?
        retString += Common.getSqlInString(bean.getWhereColumnList());
        
        //生成objectList;
        retString += "      List objectList = new ArrayList();" + Constants.NEXT_LINE;
        retString += Common.getObjectList(bean.getWhereColumnList());

        //        retString += "      Object[] objects = new Object[]{" + getObjectList(bean.getWhereColumnList()) + "};" + Constants.NEXT_LINE;
        retString += "      dbean = this.executeBindingQuery(conn, sql, objectList.toArray());" + Constants.NEXT_LINE;
        retString += "      List retList = new ArrayList();" + Constants.NEXT_LINE;
        retString += "      ResultSet rs = dbean.getRs();" + Constants.NEXT_LINE;
        retString += "      while (rs.next()) {" + Constants.NEXT_LINE;
        retString += "           " + bean.getClassName() + " " + bean.getInstanceName() + " = new " + bean.getClassName() + "();"
                + Constants.NEXT_LINE;
        retString += this.getRsString(bean.getColumnList() , bean.getInstanceName());
//        retString += "           retList.add(" + bean.getInstanceName() + ");" + Constants.NEXT_LINE;
        retString += "           return "+bean.getInstanceName()+";" + Constants.NEXT_LINE;
        retString += "      }" + Constants.NEXT_LINE;
        retString += "    return null;" + Constants.NEXT_LINE;
        retString += "  } catch (Exception e) {" + Constants.NEXT_LINE;
        retString += "      throw new JoymeDBException(e);" + Constants.NEXT_LINE;
        retString += "  } finally {" + Constants.NEXT_LINE;
        retString += "      this.cleanup(dbean, false);" + Constants.NEXT_LINE;
        retString += "  }" + Constants.NEXT_LINE;
        retString += "}" + Constants.NEXT_LINE;

        return retString;

    }
    
    /**
     * 普通生成query
     * @param table
     * @param whereClause
     * @return
     * @throws Exception
     */
    public String generateQuery(String table, String whereClause) throws Exception {
        GenerateBean bean = GenerateBase.getBean(table, whereClause);
      //获取入参
        ParameterBean parameterBean = Common.getMethodParameter(bean);
        
        //配置出参
        OutputBean outputBean = new OutputBean();
        outputBean.setClassName("List");
        outputBean.setContainClass(bean.getClassName());
        
        //生成javadoc
        String retString = GenerateJavaDoc.makeJavaDoc(parameterBean.getParameterList() , outputBean);
        retString += "public List query" + bean.getClassName() + parameterBean.getParameter() + "  throws JoymeDBException{" + Constants.NEXT_LINE;
        retString += "  DataBean dbean = null;" + Constants.NEXT_LINE;
        retString += "  try {" + Constants.NEXT_LINE;
        retString += "      String sql = \"" + this.getQueryString(table, whereClause) + "\";" + Constants.NEXT_LINE;
        //生成in的?
        retString += Common.getSqlInString(bean.getWhereColumnList());
        
        //生成objectList;
        retString += "      List objectList = new ArrayList();" + Constants.NEXT_LINE;
        retString += Common.getObjectList(bean.getWhereColumnList());

        //        retString += "      Object[] objects = new Object[]{" + getObjectList(bean.getWhereColumnList()) + "};" + Constants.NEXT_LINE;
        retString += "      dbean = this.executeBindingQuery(conn, sql, objectList.toArray());" + Constants.NEXT_LINE;
        retString += "      List retList = new ArrayList();" + Constants.NEXT_LINE;
        retString += "      ResultSet rs = dbean.getRs();" + Constants.NEXT_LINE;
        retString += "      while (rs.next()) {" + Constants.NEXT_LINE;
        retString += "           " + bean.getClassName() + " " + bean.getInstanceName() + " = new " + bean.getClassName() + "();"
                + Constants.NEXT_LINE;
        retString += this.getRsString(bean.getColumnList() , bean.getInstanceName());
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
     * countsql的生成
     * @param table
     * @param whereClause
     * @return
     * @throws Exception 
     */
    public String generateQueryCount(String table, String whereClause) throws Exception {
        GenerateBean bean = GenerateBase.getBean(table, whereClause);
        //获取入参
        ParameterBean parameterBean = Common.getMethodParameter(bean);
        
        //配置出参
        OutputBean outputBean = new OutputBean();
        outputBean.setClassName("int");
        
      //生成javadoc
        String retString = GenerateJavaDoc.makeJavaDoc(parameterBean.getParameterList() , outputBean);
        retString += "public int count" + bean.getClassName() + parameterBean.getParameter() + "  throws JoymeDBException{" + Constants.NEXT_LINE;
        retString += "  DataBean dbean = null;" + Constants.NEXT_LINE;
        retString += "  try {" + Constants.NEXT_LINE;
        retString += "      String sql = \"" + this.getCountString(table, whereClause) + "\";" + Constants.NEXT_LINE;
        
        //生成in的?
        retString += Common.getSqlInString(bean.getWhereColumnList());
        
        //生成objectList;
        retString += "      List objectList = new ArrayList();" + Constants.NEXT_LINE;
        retString += Common.getObjectList(bean.getWhereColumnList());
        
        retString += "      dbean = this.executeBindingQuery(conn, sql, objectList.toArray());" + Constants.NEXT_LINE;
        retString += "      ResultSet rs = dbean.getRs();" + Constants.NEXT_LINE;
        retString += "      if (rs.next()) {" + Constants.NEXT_LINE;
        retString += "          return rs.getInt(1);" + Constants.NEXT_LINE;
        retString += "      }" + Constants.NEXT_LINE;
        retString += "    return 0;" + Constants.NEXT_LINE;
        retString += "  } catch (Exception e) {" + Constants.NEXT_LINE;
        retString += "      throw new JoymeDBException(e);" + Constants.NEXT_LINE;
        retString += "  } finally {" + Constants.NEXT_LINE;
        retString += "      this.cleanup(dbean, false);" + Constants.NEXT_LINE;
        retString += "  }" + Constants.NEXT_LINE;
        retString += "}" + Constants.NEXT_LINE;
        return retString;
    }

    /**
     * 分页函数的代码
     * @param table
     * @param whereClause
     * @return
     * @throws Exception 
     */
    public String generateQueryPageBean(String table, String whereClause) throws Exception {
        GenerateBean bean = GenerateBase.getBean(table, whereClause);

        //获取入参
        ParameterBean parameterBean = Common.getMethodParameter(bean ,true ,false , false);
        
      //配置出参
        OutputBean outputBean = new OutputBean();
        outputBean.setClassName("PageQueryBean");
        
        //生成javadoc
        String retString = GenerateJavaDoc.makeJavaDoc(parameterBean.getParameterList() , outputBean ,true);
        retString += "public PageQueryBean query" + bean.getClassName() + parameterBean.getParameter() + "  throws JoymeDBException{" + Constants.NEXT_LINE;
        retString += "  DataBean dbean = null;" + Constants.NEXT_LINE;
        retString += "  PageQueryBean retBean = new PageQueryBean();" + Constants.NEXT_LINE;
        retString += "  try {" + Constants.NEXT_LINE;
        retString += "      String sql = \"" + this.getQueryString(table, whereClause) + "\";" + Constants.NEXT_LINE;

        //生成in的?
        retString += Common.getSqlInString(bean.getWhereColumnList());
        
        //生成objectList;
        retString += "      List objectList = new ArrayList();" + Constants.NEXT_LINE;
        retString += Common.getObjectList(bean.getWhereColumnList());
        
        retString += "      dbean = this.executeBindingQueryWithPage(conn, sql, objectList.toArray(), pageNum, pageCount , true);" + Constants.NEXT_LINE;
        retString += "      List retList = new ArrayList();" + Constants.NEXT_LINE;
        retString += "      ResultSet rs = dbean.getRs();" + Constants.NEXT_LINE;
        retString += "      int a = 0;" + Constants.NEXT_LINE;
        retString += "      while (rs.next()) {" + Constants.NEXT_LINE;
        retString += "       if (a < pageCount) { " + Constants.NEXT_LINE;
        retString += "           " + bean.getClassName() + " " + bean.getInstanceName() + " = new " + bean.getClassName() + "();"
                + Constants.NEXT_LINE;
        retString += this.getRsString(bean.getColumnList() , bean.getInstanceName());
        retString += "           retList.add(" + bean.getInstanceName() + ");" + Constants.NEXT_LINE;
        retString += "      } else{ " + Constants.NEXT_LINE;
        retString += "          retBean.setHasNextPage(true);" + Constants.NEXT_LINE;
        retString += "      }" + Constants.NEXT_LINE;
        retString += "    a++;" + Constants.NEXT_LINE;   
        retString += "     }" + Constants.NEXT_LINE;
        retString += "      retBean.setResultList(retList); " + Constants.NEXT_LINE;
        retString += "    return retBean;" + Constants.NEXT_LINE;
        retString += "  } catch (Exception e) {" + Constants.NEXT_LINE;
        retString += "      throw new JoymeDBException(e);" + Constants.NEXT_LINE;
        retString += "  } finally {" + Constants.NEXT_LINE;
        retString += "      this.cleanup(dbean, false);" + Constants.NEXT_LINE;
        retString += "  }" + Constants.NEXT_LINE;
        retString += "}" + Constants.NEXT_LINE;

        return retString;
    }

    /**
     * 生成sql
     * @param table
     * @param whereClause
     * @return
     */
    private String getQueryString(String table, String whereClause) {
        if (whereClause == null)
            whereClause = "";
        return "SELECT * FROM " + table + " " + whereClause;
    }

    /**
     * 生成countsql
     * @param table
     * @param whereClause
     * @return
     */
    private String getCountString(String table, String whereClause) {
        if (whereClause == null)
            whereClause = "";
        return "SELECT count('X') FROM " + table + " " + whereClause;
    }

    

    /**
     * 封装rs部分
     * @param columnList
     * @return
     */
    private String getRsString(List columnList , String instanceName) {
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

            retStr += "           "+instanceName+".set" + className + "(rs.get" + rsType + "(\"" + columnName + "\"));";
            retStr += Constants.NEXT_LINE;
        }
        return retStr;
    }
    

    public static void main(String[] args) throws Exception {
        GenerateQuery query = new GenerateQuery();
        String str = query.generateQueryPageBean("sdk_log_guest" , "where imei=? and imsi=?");
//        String str = query.generateQueryPageBean("sdk_topic", "where board_id in ($list1) and forum_id = ?");
        System.out.println(str);
    }

}
