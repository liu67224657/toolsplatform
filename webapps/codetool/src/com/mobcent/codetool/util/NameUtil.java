package com.mobcent.codetool.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mobcent.codetool.bean.ErrorCodeBean;
import com.mobcent.codetool.bean.WhereClauseBean;

public class NameUtil {
    //    private List clauseList = new ArrayList();
    //    {
    //        clauseList.add("=");
    //        clauseList.add("in");
    //        clauseList.add("like");
    //    }

    private List conditionList = new ArrayList();
    {
        conditionList.add(" and ");
        conditionList.add(" or ");
    }

    /**
     * key 为sql语句中的关键字，value为截取字符串
     * 注意 ，字符串要带前后空格，符号不要带空格
     */
    private Map clauseMap = new HashMap();
    {
        clauseMap.put(" not in ", 1);
        clauseMap.put("=", 1);
        clauseMap.put(" in ", 0);
        clauseMap.put(" like ", 0);
        clauseMap.put(" not like ", 0);
        clauseMap.put("<", 0);
        clauseMap.put(">", 0);
        clauseMap.put("<=", 0);
        clauseMap.put(">=", 0);
        clauseMap.put("<>", 0);
        clauseMap.put("!=", 0);
    }

    public static String changeToJavaName(String name, boolean needFirstUpper) {
        if (name.length() < 2 || name.indexOf("_") < 0) {
            if (needFirstUpper) {
                name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
            }

            return name;
        } else {
            return changeToJavaNameInner(name, needFirstUpper);
        }
    }

    private static String changeToJavaNameInner(String name, boolean needFirstUpper) {
        int position = name.indexOf("_");
        String retStr = "";
        retStr = name.substring(0, position);
        String flag = name.substring(position + 1, position + 2);
        retStr += flag.toUpperCase();

        if (needFirstUpper) {
            retStr = retStr.substring(0, 1).toUpperCase() + retStr.substring(1, retStr.length());
        }

        if (name.length() > position + 2) {
            retStr += name.substring(position + 2, name.length());
        }

        if (retStr.indexOf("_") > 0)
            return changeToJavaNameInner(retStr, needFirstUpper);
        else {
            return retStr;
        }
    }

    public static ErrorCodeBean getErrorString(String name) {
        ErrorCodeBean bean = new ErrorCodeBean();
        String str = "get_" + name + "_db_error";
        bean.setGetErrorString(str.toUpperCase());
        str = "insert_" + name + "_db_error";
        bean.setInsertErrorString(str.toUpperCase());
        str = "update_" + name + "_db_error";
        bean.setUpdateErrorString(str.toUpperCase());
        str = "delete_" + name + "_db_error";
        bean.setDeleteErrorString(str.toUpperCase());
        return bean;
    }

    private List retList = new ArrayList();

    public List parseWhereClause(String whereClause) throws Exception {
        whereClause = whereClause.trim();
        if (whereClause.toUpperCase().startsWith("WHERE")) {
            whereClause = whereClause.substring(5, whereClause.length());
        }

        //取到第一个标记
        WhereClauseBean bean = this.findFirstFlag(whereClause);

        //知道第一个位置
        int position1 = whereClause.toUpperCase().indexOf(bean.getFlag().toUpperCase());
        String columnName = whereClause.substring(0, position1).trim();
        //去掉左括号
        //        columnName = StringUtil.removeStartCharacter(columnName, "(").trim();
        if (columnName.indexOf("(") >= 0) {
            columnName = columnName.replace("(", "");
            columnName = columnName.trim();
        }
        
        int dotPosition = columnName.indexOf(".");
        if(dotPosition > 0){
            String label = columnName.substring(0, dotPosition);
            columnName = columnName.substring(dotPosition + 1, columnName.length());
            bean.setLabel(label);
        }
        
        bean.setColumnName(columnName);
        retList.add(bean);

        //找到最近的and或者or
        String conditionFlag = this.parseConditionFlag(whereClause);
        if (!conditionFlag.equals("")) {
            int index = whereClause.toUpperCase().indexOf(conditionFlag.toUpperCase());

            //取到and or这一小截，必须包含? 或者 $
            String temp1 = whereClause.substring(0 , index);
            int p1 = temp1.indexOf("?");
            int p2 = temp1.indexOf("$");
            if(p1 < 0 && p2 < 0){
                retList.remove(retList.size() - 1);
            }
            
            //截取一下
            String remainString = whereClause.substring(index + conditionFlag.length(), whereClause.length()).trim();

            return parseWhereClause(remainString);
        } else {
            return retList;
        }

    }

    /**
     * 找到截取点
     * @param whereClause
     * @return
     */
    private String parseConditionFlag(String whereClause) {
        //找到最近的and 或者or
        String flag = "";
        int num = 0;
        for (int i = 0, j = conditionList.size(); i < j; i++) {
            String condition = conditionList.get(i).toString();

            int position = whereClause.toUpperCase().indexOf(condition.toUpperCase());
            if (num == 0 && position > 0) {
                num = position;
                flag = condition;
            }

            if (position > 0 && num > position && num != 0) {
                num = position;
                flag = condition;
            }
        }
        return flag;
    }

    /**
     * 找到第一个flag
     * @param whereClause
     * @return
     */
    private WhereClauseBean findFirstFlag(String whereClause) {
        int num = 0;
        String flag = "";
        Set keySet = clauseMap.keySet();
        String listString = "";
        String tempStr = whereClause.toUpperCase();
        WhereClauseBean bean = new WhereClauseBean();
        for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
            String str = (String) iterator.next();

            int position = tempStr.indexOf(str.toUpperCase());

            if (num == 0 && position > 0) {
                num = position;
                flag = str;
            }

            if (position > 0 && num > position && num != 0) {
                num = position;
                flag = str;
            }
        }

        bean.setFlag(flag);

        //分析list
        if (flag.equalsIgnoreCase(" in ") || flag.equalsIgnoreCase(" not in ")) {
            //左括号，切到右括号
            //            int position1 = whereClause.indexOf("(");
            int position2 = whereClause.indexOf(")");
            int position1 = whereClause.lastIndexOf("(", position2);
            listString = whereClause.substring(position1 + 1, position2);
            listString = listString.replace("$", "");
            bean.setListName(listString);
        }
        
        //字母的flag需要trim
//        if (flag.equalsIgnoreCase(" in ") || flag.equalsIgnoreCase(" not in ")
//                || flag.equalsIgnoreCase(" like ") || flag.equalsIgnoreCase( "not like" )) {
//            bean.setFlag(flag.trim());
//        }
        return bean;
    }

    public static void main(String[] args) throws Exception {
        NameUtil nameUtil = new NameUtil();
//        String whereClause = "WHERE forum_user_id IN ($list1) OR        (user_id IN ($list2) AND (( role_num > ? and gold_num = ? ) or gold_num = ?))";
        String whereClause = " t1.user_id = t2.user_id AND t1.registed_forum_id > ? AND t2.reply_posts_num in ($list1) or t3.in_id like '?'";
        List list = nameUtil.parseWhereClause(whereClause);
        for (int i = 0, j = list.size(); i < j; i++) {
            WhereClauseBean bean = (WhereClauseBean) list.get(i);
            System.out.println(bean.getColumnName());
            System.out.println(bean.getFlag());
            System.out.println(bean.getListName());
            System.out.println(bean.getLabel());
            System.out.println("-------------");
        }
    }
}
