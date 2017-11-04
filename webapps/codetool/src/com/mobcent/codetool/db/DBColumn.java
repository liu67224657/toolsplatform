package com.mobcent.codetool.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mobcent.codetool.bean.ColumnBean;
import com.mobcent.codetool.util.OpenConnection;

public class DBColumn {

    /**
     * ͨ�����ݿ��ѯ��Ľṹ
     * @param tableName
     * @return
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List getDBColumns(String tableName) throws SQLException, FileNotFoundException, IOException {
        Connection conn = OpenConnection.openMySqlConnection();
        String db = OpenConnection.getDBName();

        String sql = "SELECT * FROM information_schema.columns WHERE table_schema = '" + db + "' AND table_name = '" + tableName + "'";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            List list = new ArrayList();
            while (rs.next()) {
                ColumnBean bean = new ColumnBean();
                bean.setColumnName(rs.getString("COLUMN_NAME"));
                String type = rs.getString("COLUMN_TYPE");
                bean.setColumnType(rs.getString("COLUMN_TYPE"));
                bean.setTypeName(this.getTypeName(type));
                bean.setTypelength(this.getTypeLength(type));
                String createFlag = rs.getString("EXTRA");
                if (createFlag.equals("auto_increment")) {
                    bean.setAutoCreate(true);
                }
                bean.setColumnComment(rs.getString("COLUMN_COMMENT"));
                bean.setTable(tableName);
                bean.setPK(rs.getString("COLUMN_KEY").equals("PRI"));
                list.add(bean);
            }
            
            if(list.size() == 0)
                throw new Exception("Can not find table");
            
            return list;
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
        }

        return null;
    }

    /**
     * ִ��sql��䣬���ResultSetMetaData
     * @param sql
     * @return
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static ResultSetMetaData getResultSetMetaData(String sql) throws SQLException, FileNotFoundException, IOException  {
        Connection conn = OpenConnection.openMySqlConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            return rsmd;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
        }
        return null;
    }
    
    /**
     * ����sql��䣬����еĶ���
     * @param sql
     * @return
     * @throws IOException 
     * @throws SQLException 
     * @throws FileNotFoundException 
     */
    public static List getColumnListFromMeta(String sql) throws FileNotFoundException, SQLException, IOException{
        ResultSetMetaData rsmd = getResultSetMetaData(sql);
        List list = new ArrayList();
        for (int i = 1, j = rsmd.getColumnCount(); i <= j; i++) {
            ColumnBean bean = new ColumnBean();
            
            String columnName = rsmd.getColumnName(i);
            String columnType = rsmd.getColumnTypeName(i).toLowerCase();
            String table = rsmd.getTableName(i);
            String columnLabel = rsmd.getColumnLabel(i);
            int length = rsmd.getPrecision(i);
            boolean isAutoCreate = rsmd.isAutoIncrement(i);

            bean.setAutoCreate(isAutoCreate);
            bean.setColumnName(columnName);
            bean.setColumnType(columnType);
            bean.setPK(false);
            bean.setTypelength(length);
            bean.setTypeName(columnType);
            bean.setTable(table);
            bean.setColumnLabel(columnLabel);
            list.add(bean);
        }
        return list;
    }
    

    private String getTypeName(String type) {
        int position = type.indexOf("(");
        if (position > 0)
            return type.substring(0, position);
        else
            return type;
    }

    private int getTypeLength(String type) {
        int position = type.indexOf("(");
        int position1 = type.indexOf(")");
        if (position > 0)
            return Integer.parseInt(type.substring(position + 1, position1));
        else {
            return 0;
        }
    }

//    public static void main(String[] args) throws SQLException, FileNotFoundException, IOException {
//        String sql = "SELECT t1.user_id AS t_id , t2.user_id FROM sdk_user t1 INNER JOIN sdk_forum_user t2 ON t1.user_id = t2.user_id;";
//        List list = DBColumn.getColumnListFromMeta(sql);
////        for (int i = 0, j = list.size(); i < j; i++) {
////            System.out.println(bean.);
////        }
//    }
}
