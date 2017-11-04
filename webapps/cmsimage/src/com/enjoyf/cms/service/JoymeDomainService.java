package com.enjoyf.cms.service;

import com.enjoyf.cms.bean.JoymeDomain;
import com.enjoyf.cms.bean.JoymeSubDomain;
import com.enjoyf.cms.dao.JoymeDomainDao;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;

import java.sql.Connection;

/**
 * Created by zhitaoshi on 2015/7/1.
 */
public class JoymeDomainService {

    private static JoymeDomainDao domainDao = new JoymeDomainDao();

    public PageRows<JoymeDomain> queryJoymeDomain(Pagination pagination) throws JoymeDBException, JoymeServiceException {
        Connection conn = null;
        try {
            conn = domainDao.getConnection();
            return domainDao.queryJoymeDomain(pagination, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                domainDao.closeConnection(conn);
            }
        }
    }

    public JoymeDomain insertJoymeDomain(JoymeDomain joymeDomain) throws JoymeDBException, JoymeServiceException {
        Connection conn = null;
        try {
            conn = domainDao.getConnection();
            return domainDao.insertJoymeDomain(joymeDomain, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                domainDao.closeConnection(conn);
            }
        }
    }

    public JoymeDomain getJoymeDomain(String domainName) throws JoymeDBException, JoymeServiceException {
        Connection conn = null;
        try {
            conn = domainDao.getConnection();
            return domainDao.getJoymeDomain(domainName, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                domainDao.closeConnection(conn);
            }
        }
    }

    public boolean updateJoymeDomain(String updateName, JoymeDomain joymeDomain) throws JoymeDBException, JoymeServiceException {
        Connection conn = null;
        try {
            conn = domainDao.getConnection();
            return domainDao.updateJoymeDomain(updateName, joymeDomain, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                domainDao.closeConnection(conn);
            }
        }
    }

    public boolean deleteJoymeDomain(String updateName) throws JoymeDBException, JoymeServiceException {
        Connection conn = null;
        try {
            conn = domainDao.getConnection();
            return domainDao.deleteJoymeDomain(updateName, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                domainDao.closeConnection(conn);
            }
        }
    }

    public PageRows<JoymeSubDomain> queryJoymeSubDomain(String mainDomain, String domainName, String orderBy, String orderType, Pagination pagination) throws JoymeDBException, JoymeServiceException {
        Connection conn = null;
        try {
            conn = domainDao.getConnection();
            return domainDao.queryJoymeSubDomain(mainDomain, domainName, orderBy, orderType, pagination, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                domainDao.closeConnection(conn);
            }
        }

    }

    public JoymeSubDomain insertJoymeSubDomain(JoymeSubDomain subDomain) throws JoymeDBException, JoymeServiceException {
        Connection conn = null;
        try {
            conn = domainDao.getConnection();
            return domainDao.insertJoymeSubDomain(subDomain, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                domainDao.closeConnection(conn);
            }
        }
    }

    public JoymeSubDomain getJoymeSubDomain(String domainName) throws JoymeServiceException, JoymeDBException {
        Connection conn = null;
        try {
            conn = domainDao.getConnection();
            return domainDao.getJoymeSubDomain(domainName, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                domainDao.closeConnection(conn);
            }
        }
    }

    public boolean updateJoymeSubDomain(String updateName, JoymeSubDomain subDomain) throws JoymeDBException, JoymeServiceException {
        Connection conn = null;
        try {
            conn = domainDao.getConnection();
            return domainDao.updateJoymeSubDomain(updateName, subDomain, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                domainDao.closeConnection(conn);
            }
        }
    }

    public boolean removeJoymeSubDomain(String updateName, Integer status) throws JoymeDBException, JoymeServiceException {
        Connection conn = null;
        try {
            conn = domainDao.getConnection();
            return domainDao.removeJoymeSubDomain(updateName, status, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                domainDao.closeConnection(conn);
            }
        }
    }

    public boolean deleteJoymeSubDomain(String updateName) throws JoymeDBException, JoymeServiceException {
        Connection conn = null;
        try {
            conn = domainDao.getConnection();
            return domainDao.deleteJoymeSubDomain(updateName, conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null) {
                domainDao.closeConnection(conn);
            }
        }
    }
}
