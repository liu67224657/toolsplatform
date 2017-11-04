package com.enjoyf.framework.jdbc.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.enjoyf.framework.jdbc.BaseJDBCDAO;
import com.enjoyf.framework.jdbc.bean.ConnectionBean;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;
import com.enjoyf.framework.jdbc.bean.ParameterBean;
import com.enjoyf.framework.jdbc.errorcode.BaseJDBCDAOErrorCode;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.StringUtil;

public class BaseJDBCDAOImpl implements BaseJDBCDAO {
    /* 当前读取的数据源 */
    public static String currentReadDataSource = BaseJDBCDAO.mainReadJndiName;
    public static boolean isMainReadDataSource = true;

    /* 是否有备用的读取源 */
    public static boolean hasBackupDataSource = false;

    /* 读取数据源 */
    public static DataSource mainReadDs = null;
    public static DataSource backupReadDs = null;

    /* 插入数据源 */
    public static DataSource uds = null;

    public final static String NEXT_LINE = "\r\n";

    /**
     * 初始化读取的data source
     * 
     * @param jndiName
     * @throws JoymeDBException
     */
    private void initMainReadDS(String jndiName) throws JoymeDBException {
        if (mainReadDs == null) {
            jndiName = (jndiName == null) ? BaseJDBCDAO.mainReadJndiName : jndiName;
            mainReadDs = this.getDataSource(jndiName);
        }
    }

    /**
     * 初始化备份读取的datasource
     * 
     * @param jndiName
     * @throws JoymeDBException
     */
    private void initBackupReadDS(String jndiName) throws JoymeDBException {
        if (backupReadDs == null) {
            jndiName = (jndiName == null) ? BaseJDBCDAO.backupReadJndiName : jndiName;
            backupReadDs = this.getDataSource(jndiName);
        }
    }

    /**
     * 初始化更新的data source
     * 
     * @param jndiName
     * @throws JoymeDBException
     */
    private void initUpdateDs(String jndiName) throws JoymeDBException {
        if (uds == null) {
            jndiName = (jndiName == null) ? BaseJDBCDAO.updateJndiName : jndiName;
            uds = this.getDataSource(jndiName);
        }
    }

    /**
     * 初始化data source
     * 
     * @throws JoymeDBException
     */
    public void initDs() throws JoymeDBException {
        this.initMainReadDS(null);
        try {
            this.initBackupReadDS(null);
            hasBackupDataSource = true;
        } catch (JoymeDBException e) {
            hasBackupDataSource = false;
        }
        this.initUpdateDs(null);
    }

    /**
     * 切换回主读取源
     */
    public void switchToMainDs() {
        if (!isMainReadDataSource) {// 如果不是主读取源，切换回来
            try {
                this.initMainReadDS(null);
                isMainReadDataSource = true;
                currentReadDataSource = BaseJDBCDAO.mainReadJndiName;
                System.out.println("============switch to main read data source successfully================");
            } catch (JoymeDBException e) {
                LogService.errorSystemLog("Switch to main read data source failed", e);
            }
        }
    }

    /**
     * 初始化datasource
     * 
     * @param jndiName
     * @return
     * @throws JoymeDBException
     */
    public DataSource getDataSource(String jndiName) throws JoymeDBException {
        Context ctx = null;

        DataSource ds = null;
        try {
            ctx = new InitialContext();
            ds = (DataSource) ctx.lookup(jndiName);
        } catch (NamingException e) {
            try {
                if(ctx != null)
                    ds = (DataSource) ctx.lookup("java:comp/env/" + jndiName);
            } catch (NamingException e1) {
                throw new JoymeDBException("Can not init datasource which name is :" + jndiName);
            }
        } finally {
            if (ctx != null)
                try {
                    ctx.close();
                } catch (Exception ignore) {
                }
        }

        return ds;
    }

    /**
     * 打开读取的连接
     */
    public Connection getConnection() throws JoymeDBException {
        Connection conn = null;
        DataSource ds = null;
        try {
            // 初始化data source
            boolean isEntered = false;
            if (isMainReadDataSource) {
                this.initMainReadDS(mainReadJndiName);
                ds = mainReadDs;
                isEntered = true;
            } 

            if(hasBackupDataSource && !isEntered){
                this.initBackupReadDS(backupReadJndiName);
                ds = backupReadDs;
            }

//            LogService.infoBaseDaoLog("Time:"+ new Date() , true);
//            ProxoolDataSource pds = (ProxoolDataSource)ds;
//            LogService.infoBaseDaoLog("UserName:"+ pds.getUser(), true);
//            LogService.infoBaseDaoLog("Password:"+ pds.getPassword(), true);
            
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            return conn;
        } catch (Exception e) {
            if (hasBackupDataSource) { // 如果有备份，走备份
                try {
                    conn = null;
                    // 如果从main里面读取出问题,自动切向另外一个尝试
                    String dsName = currentReadDataSource.equals(BaseJDBCDAO.mainReadJndiName) ? backupReadJndiName : mainReadJndiName;

                    // 初始化data source
                    if (isMainReadDataSource) {
                        this.initMainReadDS(backupReadJndiName);
                        ds = backupReadDs;
                    } else {
                        this.initBackupReadDS(mainReadJndiName);
                        ds = mainReadDs;
                    }

                    conn = ds.getConnection();
                    conn.setAutoCommit(false);
                    // 切换当前的datasource
                    currentReadDataSource = dsName;
                    // 切换当前状态
                    isMainReadDataSource = !isMainReadDataSource;
                    System.out.println("============switch to back up read data source successfully================");
                    return conn;

                } catch (Exception e1) {
                    e1.printStackTrace();
                    // 两个都有问题的时候，抛出一场
                    throw new JoymeDBException(e, BaseJDBCDAOErrorCode.OPEN_CONNECTION_ERROR);
                }
            } else {
                throw new JoymeDBException(e, BaseJDBCDAOErrorCode.OPEN_CONNECTION_ERROR);
            }
        }
    }

    public Connection getUpdateConnection() throws JoymeDBException {
        try {
            this.initUpdateDs(null);
            Connection conn = uds.getConnection();
            conn.setAutoCommit(false);
            return conn;
        } catch (Exception e) {
            throw new JoymeDBException(e, BaseJDBCDAOErrorCode.OPEN_CONNECTION_ERROR);
        }
    }

    public ConnectionBean getRWConnection() throws JoymeDBException {
        ConnectionBean bean = new ConnectionBean();
        bean.setReadConnection(this.getConnection());
        bean.setWriteConnection(this.getUpdateConnection());
        return bean;
    }

    public Connection getBackupReadConnnection() throws SQLException {
        return backupReadDs.getConnection();
    }

    public void cleanup(Connection conn, Statement stmt, ResultSet rs) throws JoymeDBException {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException e) {
                // LogService.errorBaseDaoLog("关闭ResultSet出错(cleanup)",BaseJDBCDAOErrorCode.CLOSE_JDBC_ELEMENT_ERROR
                // , e);
                throw new JoymeDBException(e, BaseJDBCDAOErrorCode.CLOSE_JDBC_ELEMENT_ERROR);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
                stmt = null;
            } catch (SQLException e) {
                // LogService.errorBaseDaoLog("关闭Statement出错(cleanup)",BaseJDBCDAOErrorCode.CLOSE_JDBC_ELEMENT_ERROR,
                // e);
                throw new JoymeDBException(e, BaseJDBCDAOErrorCode.CLOSE_JDBC_ELEMENT_ERROR);
            }
        }
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
                conn = null;
            } catch (SQLException e) {
                // LogService.errorBaseDaoLog("关闭Connection出错(cleanup)",BaseJDBCDAOErrorCode.CLOSE_JDBC_ELEMENT_ERROR,
                // e);
                throw new JoymeDBException(e, BaseJDBCDAOErrorCode.CLOSE_JDBC_ELEMENT_ERROR);
            }
        }
    }

    /**
     * 关闭databean
     * 
     * @param dbean
     * @param isCloseConn
     *            是否关闭connection
     */
    public void cleanup(DataBean dbean, boolean isCloseConn) {
        if (dbean != null) {
            if (dbean.getRs() != null) {
                try {
                    dbean.getRs().close();
                    dbean.setRs(null);
                } catch (SQLException e) {
                    LogService.errorBaseDaoLog("关闭ResultSet出错(cleanup)", e);
                }
            }
            if (dbean.getStmt() != null) {
                try {
                    dbean.getStmt().close();
                    dbean.setStmt(null);
                } catch (SQLException e) {
                    LogService.errorBaseDaoLog("关闭Statement出错(cleanup)", e);
                }
            }
            if (dbean.getConn() != null && isCloseConn) {
                try {
                    dbean.getConn().close();
                    dbean.setConn(null);
                } catch (SQLException e) {
                    LogService.errorBaseDaoLog("关闭Connection出错(cleanup)", e);
                }
            }
            dbean = null;
        }
    }

    public void cleanup(DataBean dbean) {
        cleanup(dbean, true);
    }

    public void closeConnection(Connection conn, boolean isCommit) {
        if (conn != null) {
            try {
                if (isCommit && !conn.isClosed())
                    conn.commit();

                if (!conn.isClosed())
                    conn.close();
                conn = null;
            } catch (Exception e) {
                LogService.errorBaseDaoLog("关闭Connection出错(cleanup)", e);
            }
        }
    }

    public void closeConnection(Connection conn) {
        this.closeConnection(conn, true);
    }

    public int executeBindingUpdate(ConnectionBean bean, String sql, Object[] objects, boolean isCommit, boolean isCloseConn) throws JoymeDBException {
        return this.executeBindingUpdate(bean.getWriteConnection(), sql, objects, isCommit, isCloseConn);
    }

    public int executeBindingUpdate(Connection conn, String sql, Object[] objects, boolean isCommit, boolean isCloseConn) throws JoymeDBException {
        return executeBindingUpdate(conn, sql, objects, isCommit, isCloseConn, true);
    }

    /**
     * 执行插入方法
     * 
     * @param conn
     * @param sql
     * @param objects
     * @param isCommit
     * @param isCloseConn
     * @return
     * @throws JoymeDBException
     */
    public int executeBindingUpdate(Connection conn, String sql, Object[] objects, boolean isCommit, boolean isCloseConn, boolean isDisplayErrorMessage)
            throws JoymeDBException {
        if (conn == null) {
            try {
                conn = this.getConnection();
            } catch (Exception e) {
                LogService.errorBaseDaoLog("获取链接出错executeBindingUpdate", e);
            }
        }

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            setStateMent(stmt, objects);

            // if(SystemContainer.isDebugMode()){
            // String str = getSQLMessage(sql , objects , false);
            // System.out.println(str);
            // LogService.infoLog(str);
            // }
            if (isDebug)
                LogService.infoBaseDaoLog(getSQLMessage(sql, objects, false) , true);
            int result = stmt.executeUpdate();
            if (isCommit) {
                conn.commit();
            }
            return result;
        } catch (Exception e) {
            if(isDisplayErrorMessage)
                LogService.errorBaseDaoLog(this.getSQLMessage(sql, objects, true), e);
            throw new JoymeDBException(e, BaseJDBCDAOErrorCode.EXECUTE_UPDATE_ERROR);
        } finally {
            if (isCloseConn) {
                this.cleanup(conn, stmt, null);
            } else {
                this.cleanup(null, stmt, null);
            }
        }
    }

    public DataBean executeBindingQueryWithPage(Connection conn, String sql, Object[] objects, int pageNum, int pageCount) throws JoymeDBException {
        return executeBindingQueryWithPage(conn, sql, objects, pageNum, pageCount, false);
    }

    public DataBean executeBindingQueryWithPage(ConnectionBean conn, String sql, Object[] objects, int pageNum, int pageCount, boolean nextPageFlag)
            throws JoymeDBException {
        return this.executeBindingQueryWithPage(conn.getReadConnection(), sql, objects, pageNum, pageCount, nextPageFlag);
    }

    public DataBean executeBindingQueryWithPage(ConnectionBean conn, String sql, Object[] objects, int pageNum, int pageCount)
            throws JoymeDBException {
        return this.executeBindingQueryWithPage(conn.getReadConnection(), sql, objects, pageNum, pageCount, false);
    }

    /**
     * 带分页的查询
     * 
     * @param conn
     * @param sql
     * @param objects
     * @param pageNum
     * @param pageCount
     * @param nextPageFlag
     * @return
     * @throws JoymeDBException
     */
    public DataBean executeBindingQueryWithPage(Connection conn, String sql, Object[] objects, int pageNum, int pageCount, boolean nextPageFlag)
            throws JoymeDBException {
        if (pageNum < 1)
            pageNum = 1;

        DataBean bean = new DataBean();
        sql = sql + " LIMIT ? , ?";
        int start = (pageNum - 1) * pageCount;
        if (nextPageFlag) {
            pageCount += 1;
        }
        if (conn == null) {
            try {
                conn = this.getConnection();
            } catch (Exception e) {
                LogService.errorBaseDaoLog("获取链接出错executeBindingQuery", e);
                throw new JoymeDBException(e, BaseJDBCDAOErrorCode.OPEN_CONNECTION_ERROR);
            }
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            setStateMent(stmt, objects);
            stmt.setInt(objects.length + 1, start);
            stmt.setInt(objects.length + 2, pageCount);

            rs = stmt.executeQuery();
            bean.setRs(rs);
            bean.setConn(conn);
            bean.setStmt(stmt);
            return bean;
        } catch (SQLException e) {
            LogService.errorBaseDaoLog(this.getSQLMessage(sql, objects, true), e);
            throw new JoymeDBException(e, BaseJDBCDAOErrorCode.EXECUTE_UPDATE_ERROR);
        }
    }

    /**
     * 通过主读取源查询
     * 
     * @param bean
     * @param sql
     * @param objects
     * @return
     * @throws JoymeDBException
     */
    public DataBean executeBindingQuery(ConnectionBean bean, String sql, Object[] objects) throws JoymeDBException {
        return this.executeBindingQuery(bean.getReadConnection(), sql, objects);
    }

    /**
     * 通过备读取源查询
     * 
     * @param bean
     * @param sql
     * @param objects
     * @return
     * @throws JoymeDBException
     */
    public DataBean executeBindingQueryByBackupDS(ConnectionBean bean, String sql, Object[] objects) throws JoymeDBException {
        return this.executeBindingQuery(bean.getBackupReadConnection(), sql, objects);
    }

    /**
     * 通过主数据库源查询
     * 
     * @param bean
     * @param sql
     * @param objects
     * @return
     * @throws JoymeDBException
     */
    public DataBean executeBindingQueryByMainDS(ConnectionBean bean, String sql, Object[] objects) throws JoymeDBException {
        return this.executeBindingQuery(bean.getWriteConnection(), sql, objects);
    }

    public DataBean executeBindingQuery(Connection conn, String sql, Object[] objects) throws JoymeDBException {
        DataBean bean = new DataBean();
        if (conn == null) {
            try {
                conn = this.getConnection();
            } catch (Exception e) {
                LogService.errorBaseDaoLog("获取链接出错executeBindingQuery", e);
                throw new JoymeDBException(e, BaseJDBCDAOErrorCode.OPEN_CONNECTION_ERROR);
            }
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            // stmt = conn.prepareStatement(sql);

            /*
             * updated by weiyuanyong on 2011/12/7 可以更新的 ResultSet 对象的并发模式
             */
            stmt = conn.prepareStatement(sql);
            setStateMent(stmt, objects);

            if (isDebug)
                System.out.println(getSQLMessage(sql, objects, false));
            rs = stmt.executeQuery();
            bean.setRs(rs);
            bean.setConn(conn);
            bean.setStmt(stmt);
            return bean;
        } catch (SQLException e) {
            LogService.errorBaseDaoLog(this.getSQLMessage(sql, objects, true), e);
            throw new JoymeDBException(e, BaseJDBCDAOErrorCode.EXECUTE_UPDATE_ERROR);
        }
    }

    // public DataBean executeBindingQuery(String sql, Object[] objects) throws
    // MobcentDBException {
    // return this.executeBindingQuery(null, sql, objects);
    // }

    private String getSQLMessage(String sql, Object[] objects, boolean isError) {
        String str = "";
        if (isError)
            str += "操作数据库出错,";
        str += "sql语句为:" + sql + NEXT_LINE;
        if (objects != null) {
            for (int i = 0, len = objects.length; i < len; i++) {
                if (objects[i] instanceof ParameterBean) {
                    str += "第" + i + "个参数类型为:" + ((ParameterBean) objects[i]).getType() + NEXT_LINE;
                    str += "第" + i + "个参数值为:" + ((ParameterBean) objects[i]).getValue() + NEXT_LINE;
                } else {
                    str += "第" + i + "个参数为:" + objects[i] + NEXT_LINE;
                }
            }
        }

        if (isError) {
            System.out.println(str);
        }
        return str;
    }

    public DataBean getKeyFromExecuteBindingUpdate(ConnectionBean conn, String sql, Object[] objects, boolean isCommit) throws JoymeDBException {
        return getKeyFromExecuteBindingUpdate(conn.getWriteConnection(), sql, objects, isCommit);
    }

    /**
     * 执行插入或更新语句后，返回主键值
     * 
     * @param conn
     *            数据库连接
     * @param sql
     *            执行sql语句
     * @param objects
     *            参数数据
     * @param isCommit
     *            是否提交
     * @author tangliang
     * @return
     * @throws JoymeDBException
     */
    public DataBean getKeyFromExecuteBindingUpdate(Connection conn, String sql, Object[] objects, boolean isCommit) throws JoymeDBException {
        if (conn == null) {
            try {
                conn = this.getConnection();
            } catch (Exception e) {
                LogService.errorBaseDaoLog("连接数据库出错BaseDAOImpl.executeBinding()", e);
                throw new JoymeDBException(e, BaseJDBCDAOErrorCode.OPEN_CONNECTION_ERROR);
            }
        }

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setStateMent(stmt, objects);

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            DataBean bean = new DataBean();
            bean.setRs(rs);
            bean.setConn(conn);
            bean.setStmt(stmt);

            if (isDebug)
                LogService.infoBaseDaoLog(getSQLMessage(sql, objects, false), true);
            if (isCommit) {
                conn.commit();
            }

            return bean;
        } catch (Exception e) {
            LogService.errorBaseDaoLog(this.getSQLMessage(sql, objects, true), e);
            throw new JoymeDBException(e, BaseJDBCDAOErrorCode.GET_KEY_AFTER_EXECUTE_ERROR);
        }
    }

    public long getLongKeyFromExecuteBindingUpdate(ConnectionBean conn, String sql, Object[] objects, boolean isCommit) throws JoymeDBException,
            SQLException {
        return this.getLongKeyFromExecuteBindingUpdate(conn.getWriteConnection(), sql, objects, isCommit);
    }

    /**
     * 插入后返回long型值
     * 
     * @param conn
     * @param sql
     * @param objects
     * @param isCommit
     * @return
     * @throws JoymeDBException
     * @throws SQLException
     */
    public long getLongKeyFromExecuteBindingUpdate(Connection conn, String sql, Object[] objects, boolean isCommit) throws JoymeDBException,
            SQLException {
        if (conn == null) {
            try {
                conn = this.getConnection();
            } catch (Exception e) {
                LogService.errorBaseDaoLog("连接数据库出错BaseDAOImpl.executeBinding()", e);
                throw new JoymeDBException(e, BaseJDBCDAOErrorCode.OPEN_CONNECTION_ERROR);
            }
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setStateMent(stmt, objects);

            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (Exception e) {
            LogService.errorBaseDaoLog(this.getSQLMessage(sql, objects, true), e);
            throw new JoymeDBException(e, BaseJDBCDAOErrorCode.GET_KEY_AFTER_EXECUTE_ERROR);
        } finally {
            if (isCommit && !conn.isClosed())
                conn.commit();

            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
        }
    }

    public boolean executeBindingBatch(ConnectionBean conn, String sql, Object[] objects, boolean isCommit, boolean isCloseConn)
            throws JoymeDBException {
        return this.executeBindingBatch(conn.getWriteConnection(), sql, objects, isCommit, isCloseConn);
    }

    /**
     * 批量插入
     * 
     * @param conn
     * @param sql
     * @param objects
     * @param isCommit
     * @param isCloseConn
     * @return 是否成功
     * @throws JoymeDBException
     */
    public boolean executeBindingBatch(Connection conn, String sql, Object[] objects, boolean isCommit, boolean isCloseConn) throws JoymeDBException {
        if (conn == null) {
            try {
                conn = this.getConnection();
            } catch (Exception e) {
                LogService.errorBaseDaoLog("连接数据库出错BaseDAOImpl.executeBinding()", e);
            }
        }

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            for (int i = 0, i1 = objects.length; i < i1; i++) {
                Object[] obj = (Object[]) objects[i];
                setStateMent(stmt, obj);
                stmt.addBatch();
            }
            int result[] = stmt.executeBatch();

            if (isCommit)
                conn.commit();

            return checkIsSuccess(result);
        } catch (Exception e) {
            LogService.errorBaseDaoLog(this.getSQLMessage(sql, (Object[]) objects[0], true), e);
            throw new JoymeDBException(e, BaseJDBCDAOErrorCode.EXECUTE_BATCH_ERROR);
        } finally {
            if (isCloseConn) {
                this.cleanup(conn, stmt, null);
            } else {
                this.cleanup(null, stmt, null);
            }
        }
    }

    // public boolean executeBindingBatch(String sql, Object[] objects) throws
    // MobcentDBException {
    // return executeBindingBatch(null, sql, objects, true, true);
    // }

    private boolean checkIsSuccess(int[] result) {
        for (int i = 0; i < result.length; i++) {
            if (result[i] == 0)
                return false;
        }
        return true;
    }

    /**
     * @param stmt
     * @param objects
     * @throws SQLException
     */
    private void setStateMent(PreparedStatement stmt, Object[] objects) throws SQLException {
        if (objects != null) {
            for (int i = 0, len = objects.length; i < len; i++) {
                Object o = null;
                if (objects[i] instanceof ParameterBean) {
                    o = ((ParameterBean) objects[i]).getValue();
                } else {
                    o = objects[i];
                }
                stmt.setObject(i + 1, o);
            }
        }
    }

    public String key = null;

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isDebug = false;

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    /**
     * 制作in中需要的?
     * 
     * @param list
     * @return
     */
    public String makeInStr(List list) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0, j = list.size(); i < j; i++) {
            sb.append("?,");
        }
        String retStr = sb.toString();
        if (retStr.endsWith(","))
            retStr = retStr.substring(0, retStr.length() - 1);

        return retStr;
    }

    /**
     * 制作update中需要的sql
     * 
     * @param columnList
     * @return
     */
    public String setUpdate(List columnList, List objectList) {
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = columnList.iterator(); iterator.hasNext();) {
            NotNullColumnBean bean = (NotNullColumnBean) iterator.next();
            sb.append(bean.getColumnName() + " = ?,");
            objectList.add(bean.getObj());
        }

        return StringUtil.removeLastCharacter(sb.toString(), ",");
    }

    public void closeConnection(ConnectionBean bean, boolean isCommit) {
        if (bean != null) {
            this.closeConnection(bean.getReadConnection(), false);
            bean.setReadConnection(null);
            this.closeConnection(bean.getWriteConnection(), isCommit);
            bean.setWriteConnection(null);
            this.closeConnection(bean.getBackupReadConnection(), false);
            bean.setBackupReadConnection(null);
        }
    }

    public void closeConnection(ConnectionBean bean) {
        this.closeConnection(bean, true);
    }

}
