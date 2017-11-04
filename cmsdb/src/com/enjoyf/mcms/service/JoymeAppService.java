package com.enjoyf.mcms.service;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.mcms.bean.ClientLineFlag;
import com.enjoyf.mcms.bean.ClientLineItem;
import com.enjoyf.mcms.dao.DedeAddonarticleDao;
import com.enjoyf.mcms.dao.JoymeAppDao;

import java.sql.Connection;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-7-15
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
public class JoymeAppService {
	private static JoymeAppDao subDao = new JoymeAppDao();

	public ClientLineFlag queryDedeArchiveListByCategoryId(Connection conn, String lineCode) throws Exception {
		boolean isCloseConn = (conn != null) ? false : true;
		try {
			if (conn == null)
				conn = subDao.getConnection();
			return subDao.queryJoymeImagebyId(conn, lineCode);
		} catch (JoymeDBException e) {
			throw e;
		} catch (Exception e) {
			throw new JoymeServiceException(e);
		} finally {
			if (conn != null && isCloseConn)
				subDao.closeConnection(conn);
		}
	}

	public ClientLineItem queryClientLineItembyId(Connection conn, Long itemId) throws Exception {
		boolean isCloseConn = (conn != null) ? false : true;
		try {
			if (conn == null)
				conn = subDao.getConnection();
			return subDao.queryClientLineItembyId(conn, itemId);
		} catch (JoymeDBException e) {
			throw e;
		} catch (Exception e) {
			throw new JoymeServiceException(e);
		} finally {
			if (conn != null && isCloseConn)
				subDao.closeConnection(conn);
		}
	}

	public ClientLineItem queryClientLineItembyDirectid(Connection conn, Long directid) throws Exception {
		boolean isCloseConn = (conn != null) ? false : true;
		try {
			if (conn == null)
				conn = subDao.getConnection();
			return subDao.queryClientLineItembyDirectid(conn, directid);
		} catch (JoymeDBException e) {
			throw e;
		} catch (Exception e) {
			throw new JoymeServiceException(e);
		} finally {
			if (conn != null && isCloseConn)
				subDao.closeConnection(conn);
		}
	}


}
