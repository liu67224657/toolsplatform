package com.mobcent.codetool.daoimpl;

import com.mobcent.codetool.bean.ColumnBean;
import com.mobcent.codetool.bean.GenerateBean;
import com.mobcent.codetool.db.Common;
import com.mobcent.codetool.db.GenerateDelete;
import com.mobcent.codetool.db.GenerateInsert;
import com.mobcent.codetool.db.GenerateJoinQuery;
import com.mobcent.codetool.db.GeneratePOJO;
import com.mobcent.codetool.db.GenerateQuery;
import com.mobcent.codetool.db.GenerateUpdate;
import com.mobcent.codetool.util.GenerateBase;

public class GenerateDaoImpl {

    /**
     * ����query
     * @param table
     * @return
     * @throws Exception
     */
    public String makeQueryWeb(String table, boolean needConvert) throws Exception {
        GenerateBean bean = GenerateBase.getBean(table, null);
        ColumnBean columnBean = Common.getPKColumn(bean.getColumnList());
        String whereClause = " WHERE " + columnBean.getColumnName() + " = ?";
        GenerateQuery query = new GenerateQuery();
        String retString = query.generateQuery(table, whereClause);
        if (needConvert)
            retString = Common.replaceToWeb(retString);

        return retString;
    }

    /**
     * ����������ѯ
     * @param table
     * @param needConvert
     * @return
     * @throws Exception
     */
    public String makeQueryPkWeb(String table, boolean needConvert) throws Exception {
        GenerateBean bean = GenerateBase.getBean(table, null);
        ColumnBean columnBean = Common.getPKColumn(bean.getColumnList());
        String whereClause = " WHERE " + columnBean.getColumnName() + " = ?";
        GenerateQuery query = new GenerateQuery();
        String retString = query.generateQueryById(table, whereClause);
        if (needConvert)
            retString = Common.replaceToWeb(retString);

        return retString;
    }

    /**
     * ���ɴ������ĵ����ѯ
     * @param table
     * @param whereClause
     * @return
     * @throws Exception
     */

    public String makeQueryConditionWeb(String table, String whereClause, boolean isConvert) throws Exception {
        GenerateQuery query = new GenerateQuery();
        String retString = query.generateQuery(table, whereClause);
        if (isConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }

    /**
     * ���������Ĳ�ѯ
     * @param table
     * @param whereClause
     * @return
     * @throws Exception
     */
    public String makeQueryCountConditionWeb(String table, String whereClause, boolean isConvert) throws Exception {
        GenerateQuery query = new GenerateQuery();
        String retString = query.generateQueryCount(table, whereClause);
        if (isConvert)
            retString = Common.replaceToWeb(retString);

        return retString;
    }

    /**
     * ���ɷ�ҳ�Ĳ�ѯ
     * @param table
     * @param whereClause
     * @return
     * @throws Exception
     */
    public String makeQueryPageConditionWeb(String table, String whereClause, boolean isConvert) throws Exception {
        GenerateQuery query = new GenerateQuery();
        String retString = query.generateQueryPageBean(table, whereClause);
        if (isConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }

    /**
     * ����insert
     * @param table
     * @return
     * @throws Exception
     */
    public String makeInsertWeb(String table, boolean needConvert) throws Exception {
        GenerateInsert insert = new GenerateInsert();
        String retString = insert.generateInsert(table);
        if (needConvert) {
            retString = Common.replaceToWeb(retString);
        }
        return retString;
    }
    
    /**
     * ����insert��������
     * @param table
     * @param needConvert
     * @return
     * @throws Exception 
     */
    public String makeInsertReturnWeb(String table, boolean needConvert) throws Exception{
        GenerateInsert insert = new GenerateInsert();
        String retString = insert.generateInsertReturn(table);
        if (needConvert) {
            retString = Common.replaceToWeb(retString);
        }
        return retString;
    }

    /**
     * ����update�����
     * @param table
     * @return
     * @throws Exception
     */
    public String makeUpdateWeb(String table, boolean needConvert) throws Exception {
        GenerateUpdate update = new GenerateUpdate();
        GenerateBean bean = GenerateBase.getBean(table, null);
        ColumnBean columnBean = Common.getPKColumn(bean.getColumnList());
        String whereClause = " WHERE " + columnBean.getColumnName() + " = ?";
        String retString = update.generateUpdate(table, whereClause);
        if (needConvert) {
            retString = Common.replaceToWeb(retString);
        }
        return retString;
    }
    
    /**
     * ��������update�����
     * @param table
     * @return
     * @throws Exception
     */
    public String makeUpdateByIdWeb(String table, boolean needConvert) throws Exception {
        GenerateUpdate update = new GenerateUpdate();
        GenerateBean bean = GenerateBase.getBean(table, null);
        ColumnBean columnBean = Common.getPKColumn(bean.getColumnList());
        String whereClause = " WHERE " + columnBean.getColumnName() + " = ?";
        String retString = update.generateUpdateById(table, whereClause);
        if (needConvert) {
            retString = Common.replaceToWeb(retString);
        }
        return retString;
    }

    /**
     * 
     * @param table
     * @return
     */
    public String makeUpdateConditionWeb(String table, String whereClause, boolean isConvert) throws Exception {
        GenerateUpdate update = new GenerateUpdate();
        String retString = update.generateUpdate(table, whereClause);
        if (isConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }

    /**
     * ����delete�����
     * @param table
     * @return
     * @throws Exception 
     */
    public String makeDeleteWeb(String table, boolean needConvert) throws Exception {
        GenerateDelete delete = new GenerateDelete();
        GenerateBean bean = GenerateBase.getBean(table, null);
        ColumnBean columnBean = Common.getPKColumn(bean.getColumnList());
        String whereClause = " WHERE " + columnBean.getColumnName() + " = ?";
        String retString = delete.generateDelete(table, whereClause);
        if (needConvert) {
            retString = Common.replaceToWeb(retString);
        }
        return retString;
    }

    /**
     * ���ɴ�������delete�����
     * @param table
     * @return
     * @throws Exception 
     */
    public String makeDeleteConditionWeb(String table, String whereClause, boolean isConvert) throws Exception {
        GenerateDelete delete = new GenerateDelete();
        String retString = delete.generateDelete(table, whereClause);
        if (isConvert)
            retString = Common.replaceToWeb(retString);

        return retString;
    }

    /**
     * ����POJO
     * @param table
     * @return
     * @throws Exception 
     */
    public String makePOJOWeb(String table, boolean needConvert) throws Exception {
        GeneratePOJO pojo = new GeneratePOJO();
        String retString = pojo.generatePOJO(table);
        if (needConvert) {
            retString = Common.replaceToWeb(retString);
        }
        return retString;
    }

    public String makePOJOWeb(String table) throws Exception {
        return makePOJOWeb(table, true);
    }

    /**
     * ���ϲ�ѯ��inputBean
     * @param logicName
     * @param sql
     * @return
     * @throws Exception
     */
    public String makeJoinQueryInputBean(String logicName, String sql, boolean isConvert) throws Exception {
        GenerateJoinQuery query = new GenerateJoinQuery();
        String retString = query.getInputBean(logicName, sql);
        if (isConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }

    /**
     * ���ϲ�ѯ��outputBean
     * @param logicName
     * @param sql
     * @return
     * @throws Exception
     */
    public String makeJoinQueryOutputBean(String logicName, String sql, boolean isConvert) throws Exception {
        GenerateJoinQuery query = new GenerateJoinQuery();
        String retString = query.getOutputBean(logicName, sql);
        if (isConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }

    /**
     * ���ϲ�ѯ���������
     * @param logicName
     * @param sql
     * @return
     * @throws Exception
     */
    public String makeJoinQueryWeb(String logicName, String sql , boolean isConvert) throws Exception {
        GenerateJoinQuery query = new GenerateJoinQuery();
        String retString = query.generateJoinQuery(logicName, sql);
        if(isConvert)
            retString = Common.replaceToWeb(retString);
        return retString;
    }
}
