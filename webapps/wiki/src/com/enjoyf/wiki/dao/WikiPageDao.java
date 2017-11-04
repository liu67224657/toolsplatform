package com.enjoyf.wiki.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.bean.PageQueryBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.util.StringUtil;
import com.enjoyf.wiki.bean.PageBean;
import com.enjoyf.wiki.bean.WikiPage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WikiPageDao extends BaseJDBCDAOImpl {
    /**
     * date 2013-06-19 14:22:07
     *
     * @param pageId
     * @return WikiPage
     * @author shenqiv0.1
     */
    public WikiPage queryWikiPagebyId(Connection conn, Long pageId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM wiki_page  WHERE page_id = ?";
            List objectList = new ArrayList();
            objectList.add(pageId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            if (rs.next()) {
                return getWikiPage(rs);
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    private WikiPage getWikiPage(ResultSet rs) throws SQLException {
        WikiPage wikiPage = new WikiPage();
        wikiPage.setPageId(rs.getLong("page_id"));
        wikiPage.setWikiKey(rs.getString("wiki_key"));
        wikiPage.setWikiUrl(rs.getString("wiki_url"));
        wikiPage.setCreateTime(rs.getTimestamp("create_time"));
        wikiPage.setPageStatus(rs.getInt("page_status"));
        return wikiPage;
    }

    // wiki_page插入DAO层

    /**
     * date 2013-06-19 14:22:07
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public long insertWikiPage(Connection conn, WikiPage bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO wiki_page(wiki_key,wiki_url,create_time,page_status) VALUES (?,?,?,?)";
            Object[] objects = new Object[]{bean.getWikiKey(), bean.getWikiUrl().trim().replaceAll(" ", "_"), bean.getCreateTime(), bean.getPageStatus()};
//            return this.executeBindingUpdate(conn, sql, objects, false, false);
            return this.getLongKeyFromExecuteBindingUpdate(conn, sql, objects, true);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    // wiki_page修改DAO层

    /**
     * date 2013-06-19 14:22:07
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateWikiPage(Connection conn, WikiPage bean) throws JoymeDBException {
        try {
            String sql = "UPDATE wiki_page SET $updateStr  WHERE page_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getPageId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    // wiki_page删除语句

    /**
     * date 2013-06-19 14:22:07
     *
     * @param pageId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteWikiPage(Connection conn, Long pageId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM wiki_page  WHERE page_id = ?";
            List objectList = new ArrayList();
            objectList.add(pageId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public WikiPage queryWikiPage(Connection conn, String key, String url) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM wiki_page WHERE wiki_key = ? and wiki_url = ?";
            List objectList = new ArrayList();
            objectList.add(key);
            objectList.add(url);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            if (rs.next()) {
                WikiPage wikiPage = this.getWikiPage(rs);
                return wikiPage;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }


    public PageBean searchWikiPageList(Connection conn, String key, String searchStr, int pageStatus, int pageNum, int pageCount) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "";
            if (StringUtil.isEmpty(key)) {
                sql = "SELECT * FROM wiki_page WHERE  page_status=? AND wiki_url LIKE ?";
            } else {
                sql = "SELECT * FROM wiki_page WHERE wiki_key = ? AND page_status=? AND wiki_url LIKE ?";
            }
            List objectList = new ArrayList();
            if (!StringUtil.isEmpty(key)) {
                objectList.add(key);
            }
            objectList.add(pageStatus);
            objectList.add("%" + searchStr + "%");

            dbean = this.executeBindingQueryWithPage(conn, sql, objectList.toArray(), pageNum, pageCount, true);
            ResultSet rs = dbean.getRs();
            List retList = new ArrayList();
            int a = 0;
            boolean hasNextPage = false;
            while (rs.next()) {
                WikiPage wikiPage = this.getWikiPage(rs);
                String httpUrl = "../" + key + "/" + rs.getInt("page_id") + ".shtml";
                wikiPage.setHttpUrl(httpUrl);
                retList.add(wikiPage);
                a++;
                if (a == pageCount)
                    break;
            }

            if (rs.next())
                hasNextPage = true;

            PageBean bean = new PageBean();
            bean.setRetList(retList);
            bean.setHasNextPage(hasNextPage);
            return bean;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public List<WikiPage> getWikiPageListByWikiKey(Connection conn, String key) throws JoymeDBException {
        DataBean dbean = null;
        List<WikiPage> returnList = new ArrayList<WikiPage>();
        try {
            String sql = "SELECT * FROM wiki_page WHERE page_status=1  AND wiki_key=? ORDER BY wiki_key";
            List objectList = new ArrayList();
            objectList.add(key);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                WikiPage wikiPage = this.getWikiPage(rs);
                returnList.add(wikiPage);
            }
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
        return returnList;
    }


    public List<WikiPage> getWikiPageCount(Connection conn) throws JoymeDBException {
        DataBean dbean = null;
        List<WikiPage> returnList = new ArrayList<WikiPage>();
        try {
            String sql = "SELECT COUNT(page_id) page_id,wiki_key FROM wiki_page WHERE page_status=1 GROUP BY wiki_key ORDER BY wiki_key";
            List objectList = new ArrayList();
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                WikiPage wikiPage = new WikiPage();
                wikiPage.setPageId(rs.getLong("page_id"));
                wikiPage.setWikiKey(rs.getString("wiki_key"));
                returnList.add(wikiPage);
            }
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
        return returnList;
    }


    /**
     * date 2015-07-16 14:23:38
     *
     * @param pageStatus
     * @return int
     * @author shenqiv0.1
     */
    public int countWikiPage(Connection conn, String wikiKey, Integer pageStatus) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT count('X') FROM wiki_page where wiki_key=? and page_status=?";
            List objectList = new ArrayList();
            objectList.add(wikiKey);
            objectList.add(pageStatus);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }


    /**
     * date 2015-07-16 14:23:40
     *
     * @param pageStatus
     * @param pageNum    The number of page
     * @param pageCount  The count of each page
     * @return PageQueryBean
     * @author shenqiv0.1
     */
    public PageQueryBean queryWikiPage(Connection conn, String wikiKey, Integer pageStatus, int pageNum, int pageCount) throws JoymeDBException {
        DataBean dbean = null;
        PageQueryBean retBean = new PageQueryBean();
        try {
            String sql = "SELECT * FROM wiki_page where wiki_key=? and page_status=?";
            List objectList = new ArrayList();
            objectList.add(wikiKey);
            objectList.add(pageStatus);
            dbean = this.executeBindingQueryWithPage(conn, sql, objectList.toArray(), pageNum, pageCount, true);
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            int a = 0;
            while (rs.next()) {
                if (a < pageCount) {
                    WikiPage wikiPage = new WikiPage();
                    wikiPage.setPageId(rs.getLong("page_id"));
                    wikiPage.setWikiKey(rs.getString("wiki_key"));
                    wikiPage.setWikiUrl(rs.getString("wiki_url"));
                    wikiPage.setCreateTime(rs.getTimestamp("create_time"));
                    wikiPage.setPageStatus(rs.getInt("page_status"));
                    retList.add(wikiPage);
                } else {
                    retBean.setHasNextPage(true);
                }
                a++;
            }
            retBean.setResultList(retList);
            return retBean;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }
}
