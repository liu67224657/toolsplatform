package com.enjoyf.framework.jdbc.impl;

import java.sql.Connection;

import com.enjoyf.framework.log.LogService;

public class JdbcConnection {
    public static boolean closeConnection(Connection conn, boolean isCommit) {
        if (conn != null) {
            try {
                //                if (isCommit)
                conn.commit();

                conn.close();
                conn = null;
                return true;
            } catch (Exception e) {
                LogService.errorBaseDaoLog("关闭Connection出错(cleanup)", e);
                return false;
            }
        }

        return true;
    }
}
