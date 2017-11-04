package com.enjoyf.framework.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.enjoyf.framework.jdbc.bean.ConnectionBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;

public interface BaseJDBCDAO {

	final String mainReadJndiName = "jdbc/joymeReadDS_main";

	final String backupReadJndiName = "jdbc/joymeReadDS_backup";

	final String updateJndiName = "jdbc/joymeUpdateDS";

	final String joymeappReadJndiName = "jdbc/joymeAppRead_main";

	/**
	 * 获得查询的连接
	 *
	 * @return
	 * @throws JoymeDBException
	 */
	Connection getConnection() throws JoymeDBException;

	/**
	 * 关闭单个连接
	 *
	 * @param conn
	 */
	void closeConnection(Connection conn);

	/**
	 * 关闭读写连接
	 *
	 * @param bean
	 */
	void closeConnection(ConnectionBean bean);

	/**
	 * @param conn
	 * @param stmt
	 * @param rs
	 * @throws JoymeDBException
	 */
	void cleanup(Connection conn, Statement stmt, ResultSet rs) throws JoymeDBException;

	/**
	 * 关闭单个连接
	 *
	 * @param conn
	 * @param isCommit 是否提交
	 */
	void closeConnection(Connection conn, boolean isCommit);


	/**
	 * 关闭读写连接
	 *
	 * @param conn
	 * @param isCommit 是否提交
	 */
	void closeConnection(ConnectionBean conn, boolean isCommit);

	/**
	 * 获得更新的连接
	 *
	 * @return
	 * @throws JoymeDBException
	 */
	Connection getUpdateConnection() throws JoymeDBException;

	/**
	 * 获得两种连接
	 *
	 * @return
	 * @throws JoymeDBException
	 */
	ConnectionBean getRWConnection() throws JoymeDBException;

	/**
	 * 获得备用数据库源链接
	 *
	 * @return
	 * @throws SQLException
	 */
	Connection getBackupReadConnnection() throws SQLException;
}
