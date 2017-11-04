package com.enjoyf.mcms.service;

import java.sql.Connection;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.mcms.dao.DedeMemberDao;

public class DedeMemberService {
    private static DedeMemberDao subDao = new DedeMemberDao();
    
    /**
     * 检查用户名和密码
     * @param conn
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    public boolean checkPassword(Connection conn , String userName , String password) throws Exception{
        if(!userName.equals("joyme-admin"))
            return false;
        
        boolean isCloseConn = (conn != null) ? false : true;
        	try {
        	    if (conn == null)
        	        conn = subDao.getConnection();
        	    return subDao.checkPassword(conn, userName, password);
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
