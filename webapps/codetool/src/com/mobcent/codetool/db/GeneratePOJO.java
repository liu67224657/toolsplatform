package com.mobcent.codetool.db;

import java.util.ArrayList;
import java.util.List;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.bean.ColumnBean;
import com.mobcent.codetool.bean.GenerateBean;
import com.mobcent.codetool.util.GenerateBase;
import com.mobcent.codetool.util.NameUtil;

public class GeneratePOJO {
    public String generatePOJO(String table) throws Exception {

        GenerateBean bean = GenerateBase.getBean(table, null);
//        String retString = "import java.util.List;" + Constants.NEXT_LINE;
//        retString += "import java.util.ArrayList; " + Constants.NEXT_LINE;
        String retString = "import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;" + Constants.NEXT_LINE;
        retString += "public class " + bean.getClassName() + " {" + Constants.NEXT_LINE;
        retString = makePOJO(bean, retString);

        return retString;
    }

    public String makePOJO(GenerateBean bean, String retString, boolean isNeedNull) {
        retString += this.makePrivateLine(bean.getColumnList()) + Constants.NEXT_LINE;

        //gettersetter
        retString += this.makeGetterSetter(bean.getColumnList()) + Constants.NEXT_LINE;

        //not null method
        if (isNeedNull)
            retString += this.makeNotNullList(bean.getColumnList());
        retString += " }" + Constants.NEXT_LINE;
        return retString;
    }

    public String makePOJO(GenerateBean bean, String retString) {
        return makePOJO(bean, retString, true);
    }

    /**
     * private ��
     * @param columnList
     * @return
     */
    private String makePrivateLine(List columnList) {
        String retString = "";
        List containList = new ArrayList();

        for (int i = 0, j = columnList.size(); i < j; i++) {
            ColumnBean bean = (ColumnBean) columnList.get(i);
            String type = bean.getTypeName();
            String columnName = bean.getColumnName();

            if (containList.contains(columnName)) {
                continue;
            } else {
                String javaType = Constants.getJavaType(type);
                String instanceName = NameUtil.changeToJavaName(columnName, false);
                retString += "    private " + javaType + "  " + instanceName + ";" + Constants.NEXT_LINE;
                containList.add(columnName);
            }
        }

        return retString;
    }

    /**
     * ����getter setter
     * @param columnList
     * @return
     */
    private String makeGetterSetter(List columnList) {
        List containList = new ArrayList();
        String retString = "";
        for (int i = 0, j = columnList.size(); i < j; i++) {
            ColumnBean bean = (ColumnBean) columnList.get(i);
            String type = bean.getTypeName();
            String javaType = Constants.getJavaType(type);
            String columnName = bean.getColumnName();

            if (containList.contains(columnName)) {
                continue;
            } else {
                String instanceName = NameUtil.changeToJavaName(columnName, false);
                String className = NameUtil.changeToJavaName(columnName, true);

                //getter
                retString += " public " + javaType + " get" + className + "(){" + Constants.NEXT_LINE;
                retString += "    return " + instanceName + ";" + Constants.NEXT_LINE;
                retString += "  }" + Constants.NEXT_LINE;
                retString += Constants.NEXT_LINE;
                //setter
                retString += " public void set" + className + "(" + javaType + " " + instanceName + "){" + Constants.NEXT_LINE;
                retString += "    this." + instanceName + " = " + instanceName + ";" + Constants.NEXT_LINE;
                retString += "  }" + Constants.NEXT_LINE;
                retString += Constants.NEXT_LINE;
                containList.add(columnName);
            }
        }
        return retString;
    }

    /**
     * Ϊupdat������notnull
     * @param columnList
     * @return
     */
    private String makeNotNullList(List columnList) {
        String retString = "public List getNotNullColumnList(){" + Constants.NEXT_LINE;
        retString += "  List columnList = new ArrayList();" + Constants.NEXT_LINE;
        List containList = new ArrayList();
        for (int i = 0, j = columnList.size(); i < j; i++) {
            ColumnBean bean = (ColumnBean) columnList.get(i);
            String columnName = bean.getColumnName();
            String instanceName = NameUtil.changeToJavaName(columnName, false);
            if(containList.contains(columnName)){
                continue;
            }else{
                if (!bean.isAutoCreate()) {
                    retString += "   if(" + instanceName + " != null) {" + Constants.NEXT_LINE;
                    retString += "    NotNullColumnBean bean = new NotNullColumnBean();" + Constants.NEXT_LINE;
                    retString += "    bean.setColumnName(\"" + columnName + "\");" + Constants.NEXT_LINE;
                    retString += "    bean.setObj(" + instanceName + ");" + Constants.NEXT_LINE;
                    retString += "    columnList.add(bean);" + Constants.NEXT_LINE;
                    retString += "   }" + Constants.NEXT_LINE;
                    containList.add(columnList);
                }
            }
        }
        retString += "  return columnList;" + Constants.NEXT_LINE;
        retString += "  }" + Constants.NEXT_LINE;
        return retString;
    }

    public static void main(String[] args) throws Exception {
        GeneratePOJO pojo = new GeneratePOJO();
        System.out.println(pojo.generatePOJO("sdk_forum_user"));
    }
}
