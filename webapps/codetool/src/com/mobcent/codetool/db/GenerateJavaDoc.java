package com.mobcent.codetool.db;

import java.sql.ResultSetMetaData;
import java.util.List;

import com.mobcent.codetool.Constants;
import com.mobcent.codetool.bean.OutputBean;
import com.mobcent.codetool.bean.ParameterPropertyBean;
import com.mobcent.codetool.util.DateUtils;

/**
 * java doc的生成类
 * @author new
 *
 */
public class GenerateJavaDoc {
    /**
     * date : 
     * @param list 入参list
     * @param 
     * @return
     */
    public static String makeJavaDoc(List list, OutputBean bean, boolean hasPage, String returnDoc) {
        String retString = Constants.BEGIN_JAVADOC_SPACE + "/**" + Constants.NEXT_LINE;
        retString += Constants.BEGIN_JAVADOC_SPACE + " * date " + DateUtils.getNowDateString() + Constants.NEXT_LINE;
        retString += Constants.BEGIN_JAVADOC_SPACE + " * @author " + Constants.CODE_TOOL_NAME + Constants.CODE_TOOL_VERSION + Constants.NEXT_LINE;

        if (bean.getInputClass() != null) {
            retString += Constants.BEGIN_JAVADOC_SPACE + " * @param "+ bean.getInputClass() + Constants.NEXT_LINE;
        } else {
            for (int i = 0, j = list.size(); i < j; i++) {
                ParameterPropertyBean pbean = (ParameterPropertyBean) list.get(i);
                retString += Constants.BEGIN_JAVADOC_SPACE + " * @param " + pbean.getInstanceName();
                retString += (pbean.getContainClass() != null ? "<" + pbean.getContainClass() + ">" : "");
                retString += Constants.NEXT_LINE;
            }
        }

        if (hasPage) {
            retString += Constants.BEGIN_JAVADOC_SPACE + " * @param pageNum The number of page" + Constants.NEXT_LINE;
            retString += Constants.BEGIN_JAVADOC_SPACE + " * @param pageCount The count of each page" + Constants.NEXT_LINE;
        }

        //设置出参
        retString += Constants.BEGIN_JAVADOC_SPACE + " * @return " + bean.getClassName() + " ";
        retString += returnDoc != null ? returnDoc : "";
        retString += (bean.getContainClass() != null ? "<" + bean.getContainClass() + ">" : "") + Constants.NEXT_LINE;

        retString += Constants.BEGIN_JAVADOC_SPACE + " */" + Constants.NEXT_LINE;
        return retString;
    }

    public static String makeJavaDoc(List list, OutputBean bean) {
        return makeJavaDoc(list, bean, false, "");
    }

    public static String makeJavaDoc(List list, OutputBean bean, boolean hasPage) {
        return makeJavaDoc(list, bean, hasPage, "");
    }

    public static String makeJavaDoc(List list, OutputBean bean, String returnDoc) {
        return makeJavaDoc(list, bean, false, returnDoc);
    }
}
