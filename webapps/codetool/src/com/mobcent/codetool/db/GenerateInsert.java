package com.mobcent.codetool.db;

import java.util.List;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.bean.ColumnBean;
import com.mobcent.codetool.bean.GenerateBean;
import com.mobcent.codetool.bean.OutputBean;
import com.mobcent.codetool.bean.ParameterBean;
import com.mobcent.codetool.util.GenerateBase;
import com.mobcent.codetool.util.NameUtil;
import com.mobcent.codetool.util.StringUtil;

public class GenerateInsert {
    public String generateInsert(String table) throws Exception {
        GenerateBean bean = GenerateBase.getBean(table, null);
        ParameterBean pBean = Common.getMethodParameter(bean, true);
        
        OutputBean outputBean = new OutputBean();
        outputBean.setClassName("int");
        
        String retString = GenerateJavaDoc.makeJavaDoc(pBean.getParameterList(), outputBean , Constants.UPDATE_RETURN_DOC);
        retString += "public int insert" + bean.getClassName() + pBean.getParameter() + "  throws JoymeDBException{"
                + Constants.NEXT_LINE;
        retString += "   try{" + Constants.NEXT_LINE;
        retString += "      String sql = \"" + getInsertSql(table, bean.getColumnList()) + "\";" + Constants.NEXT_LINE;
        retString += "      Object[] objects = new Object[] {" + this.getObjectString(bean) + "}; " + Constants.NEXT_LINE;
        retString += "      return this.executeBindingUpdate(conn, sql, objects, false, false);" + Constants.NEXT_LINE;
        retString += " }catch (Exception e) { " + Constants.NEXT_LINE;
        retString += "      throw new JoymeDBException(e);" + Constants.NEXT_LINE;
        retString += " }" + Constants.NEXT_LINE;
        retString += "}" + Constants.NEXT_LINE;
        return retString;
    }
    
    public String generateInsertReturn(String table) throws Exception {
        GenerateBean bean = GenerateBase.getBean(table, null);
        
        ParameterBean pBean = Common.getMethodParameter(bean , true);
        OutputBean outputBean = new OutputBean();
        outputBean.setClassName("long");
        
        String retString = GenerateJavaDoc.makeJavaDoc(pBean.getParameterList(), outputBean , " auto-increase-key");
        retString += "public long insert" + bean.getClassName() + pBean.getParameter() + "  throws JoymeDBException{"
                + Constants.NEXT_LINE;
        retString += "  DataBean dbean = null;" + Constants.NEXT_LINE;
        retString += "  try{" + Constants.NEXT_LINE;
        retString += "      String sql = \"" + getInsertSql(table, bean.getColumnList()) + "\";" + Constants.NEXT_LINE;
        retString += "      Object[] objects = new Object[] {" + this.getObjectString(bean) + "}; " + Constants.NEXT_LINE;
        retString += "      dbean = this.getKeyFromExecuteBindingUpdate(conn, sql, objects, false);" + Constants.NEXT_LINE;
        retString += "      ResultSet rs = dbean.getRs();" + Constants.NEXT_LINE;
        retString += "      if(rs.next())" + Constants.NEXT_LINE;
        retString += "          return rs.getLong(1);" + Constants.NEXT_LINE;
        retString += "      return 0;" + Constants.NEXT_LINE;
        retString += "  } catch (Exception e) {" + Constants.NEXT_LINE;
        retString += "      throw new JoymeDBException(e);" + Constants.NEXT_LINE;
        retString += "  } finally{" + Constants.NEXT_LINE;
        retString += "      this.cleanup(dbean, false); " + Constants.NEXT_LINE;
        retString += "  }" + Constants.NEXT_LINE;
        retString += "}" + Constants.NEXT_LINE;
        return retString;
    }
    
    private String getInsertSql(String table, List columnList) {
        String sql = "INSERT INTO " + table + "(";
        String temp = "";
        for (int i = 0, j = columnList.size(); i < j; i++) {
            ColumnBean bean = (ColumnBean) columnList.get(i);
            if (bean.isAutoCreate()) {
                continue;
            } else {
                sql += bean.getColumnName() + ",";
                temp += "?,";
            }
        }

        sql = StringUtil.removeLastCharacter(sql, ",");
        temp = StringUtil.removeLastCharacter(temp, ",");

        sql += ") VALUES (";
        sql += temp;
        sql += ")";
        return sql;
    }

    private String getObjectString(GenerateBean bean) {
        List columnList = bean.getColumnList();
        String retString = "";
        for (int i = 0, j = columnList.size(); i < j; i++) {
            ColumnBean columnBean = (ColumnBean) columnList.get(i);
            if (!columnBean.isAutoCreate()) 
                retString += "bean.get" + NameUtil.changeToJavaName(columnBean.getColumnName(), true) + "(),";
        }
        
        retString = StringUtil.removeLastCharacter(retString, ",");
        return retString;
    }

    public static void main(String[] args) throws Exception {
        GenerateInsert insert = new GenerateInsert();
        System.out.println(insert.generateInsert("sdk_topic"));
    }
}
