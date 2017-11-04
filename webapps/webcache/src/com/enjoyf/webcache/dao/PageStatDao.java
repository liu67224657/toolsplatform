package com.enjoyf.webcache.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.util.CollectionUtil;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;
import com.enjoyf.util.StringUtil;
import com.enjoyf.webcache.bean.PageStat;
import com.enjoyf.webcache.bean.WebCacheSrcType;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/11/20.
 */
public class PageStatDao extends BaseJDBCDAOImpl {

    private static final String TABLE_NAME = "page_stat";

    public int insertPageStat(Connection conn, PageStat pageStat) throws JoymeDBException {
        try {
            String sql = "INSERT INTO " + TABLE_NAME + "(stat_id,page_id,page_type,pc_pv,m_pv,wanba_pv,pv_sum,reply_sum,contenttype) VALUES (?,?,?,?,?,?,?,?,?)";
            String pageId = null;
            try {
                pageId = pageStat.getPageId().contains("%") ? URLDecoder.decode(pageStat.getPageId(), "UTF-8") : pageStat.getPageId();
            } catch (Exception e) {
                pageId = pageStat.getPageId();
            }
            Object[] objects = new Object[]{pageStat.getStatId(), pageId, pageStat.getPageType().getCode(), pageStat.getPcPv(), pageStat.getmPv(), pageStat.getWanbaPv(), pageStat.getPvSum(), pageStat.getReplySum(), pageStat.getContentType()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public PageStat getPageStat(Connection conn, String statId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM " + TABLE_NAME + "  WHERE stat_id = ?";

            List objectList = new ArrayList();

            objectList.add(statId);

            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());

            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                return rsToObject(rs);
            }
            return null;

        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    private PageStat rsToObject(ResultSet rs) throws SQLException {
        PageStat pageStat = new PageStat();
        pageStat.setStatId(rs.getString("stat_id"));
        pageStat.setPageId(rs.getString("page_id"));
        pageStat.setPageType(WebCacheSrcType.getByCode(rs.getInt("page_type")));
        pageStat.setPcPv(rs.getInt("pc_pv"));
        pageStat.setmPv(rs.getInt("m_pv"));
        pageStat.setWanbaPv(rs.getInt("wanba_pv"));
        pageStat.setPvSum(rs.getInt("pv_sum"));
        pageStat.setReplySum(rs.getInt("reply_sum"));
        pageStat.setContentType(rs.getInt("contenttype"));
        return pageStat;
    }

    public List<PageStat> queryPageStatByIdSet(Connection conn, List<String> idList) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM " + TABLE_NAME + "  WHERE stat_id IN($ids)";
            sql = sql.replace("$ids", this.makeInStr(idList));

            List objectList = new ArrayList();
            objectList.addAll(idList);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());

            List<PageStat> retList = new ArrayList<PageStat>();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                retList.add(rsToObject(rs));

            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public int increasePageStat(Connection conn, PageStat pageStat) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "UPDATE " + TABLE_NAME + " SET pc_pv=pc_pv+? , m_pv=m_pv+? , wanba_pv=wanba_pv+? , pv_sum=pv_sum+? , reply_sum=reply_sum+? WHERE stat_id = ?";

            List objectList = new ArrayList();

            objectList.add(pageStat.getPcPv());
            objectList.add(pageStat.getmPv());
            objectList.add(pageStat.getWanbaPv());
            objectList.add(pageStat.getPvSum());
            objectList.add(pageStat.getReplySum());
            objectList.add(pageStat.getStatId());

            return this.executeBindingUpdate(conn, sql, objectList.toArray(), true, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public int updatePageStatByColumn(Connection conn, Map<String, Integer> map, PageStat pageStat) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "UPDATE " + TABLE_NAME + " SET ";
            for (String column : map.keySet()) {
                sql += (column + "=" + map.get(column)) + ",";
            }
            sql = StringUtil.removeLastCharacter(sql, ",");
            sql += " WHERE stat_id='" + pageStat.getStatId() + "'";
            return this.executeBindingUpdate(conn, sql, null, true, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public PageRows<PageStat> queryPageStatByPage(Connection conn, Map<String, Object> paramMap, Pagination page) throws JoymeDBException {
        PageRows<PageStat> pageRows = null;
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM " + TABLE_NAME;
            if (!CollectionUtil.isEmpty(paramMap)) {
                sql += " WHERE ";
                for (String column : paramMap.keySet()) {
                    sql += (column + "=? AND ");
                }
                sql = StringUtil.removeLastCharacter(sql, "AND ");
            }
            if (page != null) {
                sql += " LIMIT ?,?";
            }
            List objectList = new ArrayList();
            if (!CollectionUtil.isEmpty(paramMap)) {
                for (String column : paramMap.keySet()) {
                    objectList.add(paramMap.get(column));
                }
            }
            if (page != null) {
                objectList.add(page.getStartRowIdx());
                objectList.add(page.getPageSize());
            }
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());

            List<PageStat> retList = new ArrayList<PageStat>();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                retList.add(rsToObject(rs));
            }

            String countSql = "SELECT COUNT(*) FROM " + TABLE_NAME;
            if (!CollectionUtil.isEmpty(paramMap)) {
                countSql += " WHERE ";
                for (String column : paramMap.keySet()) {
                    countSql += (column + "=? AND ");
                }
                countSql = StringUtil.removeLastCharacter(countSql, "AND ");
            }
            List countList = new ArrayList();
            if (!CollectionUtil.isEmpty(paramMap)) {
                for (String column : paramMap.keySet()) {
                    countList.add(paramMap.get(column));
                }
            }
            dbean = this.executeBindingQuery(conn, countSql, countList.toArray());
            ResultSet countRs = dbean.getRs();
            if (countRs.next()) {
                page.setTotalRows(countRs.getInt(1));
            }

            pageRows = new PageRows<PageStat>();
            pageRows.setPage(page);
            pageRows.setRows(retList);
            return pageRows;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public List<PageStat> queryPageStatByOrder(Connection conn, Map<String, Integer> param, String orderColumn, String order, Pagination page) throws JoymeDBException {
        List<PageStat> list = new ArrayList<PageStat>();
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM " + TABLE_NAME;
            if (!CollectionUtil.isEmpty(param)) {
                sql += " WHERE ";
                for (String column : param.keySet()) {
                    sql += (column + "=? AND ");
                }
                sql = StringUtil.removeLastCharacter(sql, "AND ");
            }
            sql += " ORDER BY " + orderColumn + " " + order.toUpperCase();
            if (page != null) {
                sql += " LIMIT ?,?";
            }
            List objectList = new ArrayList();
            if (!CollectionUtil.isEmpty(param)) {
                for (String column : param.keySet()) {
                    objectList.add(param.get(column));
                }
            }
            if (page != null) {
                objectList.add(page.getStartRowIdx());
                objectList.add(page.getPageSize());
            }
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());

            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                list.add(rsToObject(rs));
            }
            return list;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }
}
