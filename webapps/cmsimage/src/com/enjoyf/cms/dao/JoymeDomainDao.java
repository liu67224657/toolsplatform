package com.enjoyf.cms.dao;

import com.enjoyf.cms.bean.JoymeDomain;
import com.enjoyf.cms.bean.JoymeSubDomain;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;
import com.enjoyf.util.StringUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhitaoshi on 2015/7/1.
 */
public class JoymeDomainDao extends BaseJDBCDAOImpl {


    public JoymeDomain insertJoymeDomain(JoymeDomain joymeDomain, Connection conn) throws JoymeDBException {
        try {
            String sql = "INSERT INTO cmsimage.joyme_domain(domainname,domaindesc,buydate,expiredate,buymerchant," +
                    "buyuser,createdate,createuser,modifydate,modifyuser,removestatus) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            Object[] objects = new Object[]{joymeDomain.getDomainName(),
                    joymeDomain.getDomainDesc(),
                    joymeDomain.getBuyDate() == null ? null : new Timestamp(joymeDomain.getBuyDate().getTime()),
                    joymeDomain.getExpireDate() == null ? null : new Timestamp(joymeDomain.getExpireDate().getTime()),
                    joymeDomain.getBuyMerchant(),
                    joymeDomain.getBuyUser(),
                    joymeDomain.getCreateDate() == null ? new Timestamp(System.currentTimeMillis()) : new Timestamp(joymeDomain.getCreateDate().getTime()),
                    joymeDomain.getCreateUser(),
                    joymeDomain.getModifyDate() == null ? null : new Timestamp(joymeDomain.getModifyDate().getTime()),
                    joymeDomain.getModifyUser(),
                    joymeDomain.getRemoveStatus()
            };
            int result = this.executeBindingUpdate(conn, sql, objects, false, false);
            if (result > 0) {
                return joymeDomain;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public PageRows<JoymeDomain> queryJoymeDomain(Pagination pagination, Connection conn) throws JoymeDBException {
        List<JoymeDomain> list = new ArrayList<JoymeDomain>();
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM cmsimage.joyme_domain LIMIT " + pagination.getStartRowIdx() + "," + pagination.getPageSize();
            dbean = this.executeBindingQuery(conn, sql, null);
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymeDomain joymeDomain = rsToJoymeDomain(rs);
                list.add(joymeDomain);
            }
            String countSql = "SELECT COUNT(*) FROM cmsimage.joyme_domain";
            dbean = this.executeBindingQuery(conn, countSql, null);
            ResultSet countRs = dbean.getRs();
            if (countRs.next()) {
                pagination.setTotalRows(countRs.getInt(1));
            }

            PageRows pageRows = new PageRows();
            pageRows.setRows(list);
            pageRows.setPage(pagination);
            return pageRows;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    private JoymeDomain rsToJoymeDomain(ResultSet rs) throws SQLException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        JoymeDomain joymeDomain = new JoymeDomain();
        joymeDomain.setDomainName(rs.getString("domainname"));
        Date buyDate = rs.getTimestamp("buydate") == null ? null : new Date(rs.getTimestamp("buydate").getTime());
        joymeDomain.setBuyDate(buyDate);
        if (buyDate != null) {
            joymeDomain.setBuyDateStr(df.format(buyDate));
        }
        joymeDomain.setBuyMerchant(rs.getString("buymerchant"));
        joymeDomain.setBuyUser(rs.getString("buyuser"));
        joymeDomain.setDomainDesc(rs.getString("domaindesc"));
        Date createDate = rs.getTimestamp("createdate") == null ? null : new Date(rs.getTimestamp("createdate").getTime());
        joymeDomain.setCreateDate(createDate);
        if (createDate != null) {
            joymeDomain.setCreateDateStr(df.format(createDate));
        }
        joymeDomain.setCreateUser(rs.getString("createuser"));
        Date expireDate = rs.getTimestamp("expiredate") == null ? null : new Date(rs.getTimestamp("expiredate").getTime());
        joymeDomain.setExpireDate(expireDate);
        if (expireDate != null) {
            joymeDomain.setExpireDateStr(df.format(expireDate));
        }
        Date modifyDate = rs.getTimestamp("modifydate") == null ? null : new Date(rs.getTimestamp("modifydate").getTime());
        joymeDomain.setModifyDate(modifyDate);
        if (modifyDate != null) {
            joymeDomain.setModifyDateStr(df.format(modifyDate));
        }
        joymeDomain.setModifyUser(rs.getString("modifyuser"));
        joymeDomain.setRemoveStatus(rs.getInt("removestatus"));
        return joymeDomain;
    }

    public JoymeDomain getJoymeDomain(String domainName, Connection conn) throws JoymeDBException {
        JoymeDomain joymeDomain = null;
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM cmsimage.joyme_domain WHERE domainname=?";
            Object[] objects = new Object[]{domainName};
            dbean = this.executeBindingQuery(conn, sql, objects);
            ResultSet rs = dbean.getRs();
            if (rs.next()) {
                joymeDomain = rsToJoymeDomain(rs);
            }
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
        return joymeDomain;
    }

    public boolean updateJoymeDomain(String updateName, JoymeDomain joymeDomain, Connection conn) throws JoymeDBException {
        try {
            String sql = "UPDATE cmsimage.joyme_domain SET domainname=? , domaindesc=? , buydate=? , expiredate=? , buymerchant=?" +
                    ", buyuser=? , modifydate=? , modifyuser=? WHERE domainname=?";
            Object[] objects = new Object[]{joymeDomain.getDomainName(),
                    joymeDomain.getDomainDesc(),
                    joymeDomain.getBuyDate() == null ? null : new Timestamp(joymeDomain.getBuyDate().getTime()),
                    joymeDomain.getExpireDate() == null ? null : new Timestamp(joymeDomain.getExpireDate().getTime()),
                    joymeDomain.getBuyMerchant(),
                    joymeDomain.getBuyUser(),
                    joymeDomain.getModifyDate() == null ? null : new Timestamp(joymeDomain.getModifyDate().getTime()),
                    joymeDomain.getModifyUser(),
                    updateName
            };
            return this.executeBindingUpdate(conn, sql, objects, false, false) > 0;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public boolean deleteJoymeDomain(String updateName, Connection conn) throws JoymeDBException {
        try {
            String sql = "UPDATE cmsimage.joyme_domain SET removestatus=0 WHERE domainname=?";
            Object[] objects = new Object[]{
                    updateName
            };
            return this.executeBindingUpdate(conn, sql, objects, false, false) > 0;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public PageRows<JoymeSubDomain> queryJoymeSubDomain(String mainDomain, String domainName, String orderBy, String orderType, Pagination pagination, Connection conn) throws JoymeDBException {
        List<JoymeSubDomain> list = new ArrayList<JoymeSubDomain>();
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM cmsimage.joyme_subdomain WHERE maindomain=?";
            String countSql = "SELECT COUNT(*) FROM cmsimage.joyme_subdomain WHERE maindomain=?";
            if (!StringUtil.isEmpty(domainName)) {
                sql += " AND domainname LIKE ?";
                countSql += " AND domainname LIKE ?";
            }
            if (!StringUtil.isEmpty(orderBy)) {
                sql += " ORDER BY " + orderBy + "";
                if (!StringUtil.isEmpty(orderType)) {
                    sql += " " + orderType.toLowerCase();
                } else {
                    sql += " DESC";
                }
            }
            sql += " LIMIT " + pagination.getStartRowIdx() + "," + pagination.getPageSize();

            List<Object> objects = new ArrayList<Object>();
            objects.add(mainDomain);
            if (!StringUtil.isEmpty(domainName)) {
                objects.add("%" + domainName + "%");
            }
            dbean = this.executeBindingQuery(conn, sql, objects.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymeSubDomain joymeDomain = rsToSubDomain(rs);
                list.add(joymeDomain);
            }

            dbean = this.executeBindingQuery(conn, countSql, objects.toArray());
            ResultSet countRs = dbean.getRs();
            if (countRs.next()) {
                pagination.setTotalRows(countRs.getInt(1));
            }

            PageRows<JoymeSubDomain> pageRows = new PageRows<JoymeSubDomain>();
            pageRows.setRows(list);
            pageRows.setPage(pagination);

            return pageRows;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    private JoymeSubDomain rsToSubDomain(ResultSet rs) throws SQLException {
        JoymeSubDomain subDomain = new JoymeSubDomain();
        subDomain.setDomainName(rs.getString("domainname"));
        subDomain.setMainDomain(rs.getString("maindomain"));
        subDomain.setIndexUrl(rs.getString("indexurl"));
        subDomain.setUseDept(rs.getString("department"));
        subDomain.setDomainDesc(rs.getString("domaindesc"));
        subDomain.setSeoDesc(rs.getString("seodesc"));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createDate = rs.getTimestamp("createdate") == null ? null : new Date(rs.getTimestamp("createdate").getTime());
        subDomain.setCreateDate(createDate);
        if (createDate != null) {
            subDomain.setCreateDateStr(df.format(createDate));
        }
        subDomain.setCreateUser(rs.getString("createuser"));
        Date modifyDate = rs.getTimestamp("modifydate") == null ? null : new Date(rs.getTimestamp("modifydate").getTime());
        subDomain.setModifyDate(modifyDate);
        if (modifyDate != null) {
            subDomain.setModifyDateStr(df.format(modifyDate));
        }
        subDomain.setModifyUser(rs.getString("modifyuser"));
        subDomain.setRemoveStatus(rs.getInt("removestatus"));
        return subDomain;
    }

    public JoymeSubDomain insertJoymeSubDomain(JoymeSubDomain subDomain, Connection conn) throws JoymeDBException {
        try {
            String sql = "INSERT INTO cmsimage.joyme_subdomain(domainname,indexurl,department," +
                    "domaindesc,seodesc,createdate,createuser,modifydate,modifyuser,removestatus,maindomain) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            Object[] objects = new Object[]{subDomain.getDomainName(),
                    subDomain.getIndexUrl(),
                    subDomain.getUseDept(),
                    subDomain.getDomainDesc(),
                    subDomain.getSeoDesc(),
                    subDomain.getCreateDate() == null ? new Timestamp(System.currentTimeMillis()) : new Timestamp(subDomain.getCreateDate().getTime()),
                    subDomain.getCreateUser(),
                    subDomain.getModifyDate() == null ? null : new Timestamp(subDomain.getModifyDate().getTime()),
                    subDomain.getModifyUser(),
                    subDomain.getRemoveStatus(),
                    subDomain.getMainDomain()
            };
            int result = this.executeBindingUpdate(conn, sql, objects, false, false);
            if (result > 0) {
                return subDomain;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public JoymeSubDomain getJoymeSubDomain(String domainName, Connection conn) throws JoymeDBException {

        JoymeSubDomain subDomain = null;
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM cmsimage.joyme_subdomain WHERE domainname=?";
            Object[] objects = new Object[]{domainName};
            dbean = this.executeBindingQuery(conn, sql, objects);
            ResultSet rs = dbean.getRs();
            if (rs.next()) {
                subDomain = rsToSubDomain(rs);
            }
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
        return subDomain;
    }

    public boolean updateJoymeSubDomain(String updateName, JoymeSubDomain subDomain, Connection conn) throws JoymeDBException {
        try {
            String sql = "UPDATE cmsimage.joyme_subdomain SET indexurl=? , department=? " +
                    " , domaindesc=? , seodesc=? , modifydate=? , modifyuser=?, removestatus=?" +
                    " WHERE domainname=?";
            Object[] objects = new Object[]{
                    subDomain.getIndexUrl(),
                    subDomain.getUseDept(),
                    subDomain.getDomainDesc(),
                    subDomain.getSeoDesc(),
                    subDomain.getModifyDate() == null ? null : new Timestamp(subDomain.getModifyDate().getTime()),
                    subDomain.getModifyUser(),
                    subDomain.getRemoveStatus(),
                    updateName
            };
            return this.executeBindingUpdate(conn, sql, objects, false, false) > 0;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public boolean removeJoymeSubDomain(String updateName, Integer status, Connection conn) throws JoymeDBException {
        try {
            String sql = "UPDATE cmsimage.joyme_subdomain SET removestatus=? WHERE domainname=?";
            Object[] objects = new Object[]{
                    status,
                    updateName
            };
            return this.executeBindingUpdate(conn, sql, objects, false, false) > 0;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public boolean deleteJoymeSubDomain(String updateName, Connection conn) throws JoymeDBException {
        try {
            String sql = "DELETE FROM cmsimage.joyme_subdomain WHERE domainname=?";
            Object[] objects = new Object[]{
                    updateName
            };
            return this.executeBindingUpdate(conn, sql, objects, false, false) > 0;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }
}
