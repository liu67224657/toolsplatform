package com.enjoyf.webcache.dao;import com.enjoyf.framework.jdbc.bean.DataBean;import com.enjoyf.framework.jdbc.exception.JoymeDBException;import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;import com.enjoyf.webcache.bean.WebCacheUrl;import java.sql.Connection;import java.sql.ResultSet;import java.sql.SQLException;import java.util.ArrayList;import java.util.Date;import java.util.List;public class WebCacheUrlDao extends BaseJDBCDAOImpl {    /**     * date 2015-08-26 17:25:00     *     * @param urlId     * @return WebcacheUrl     * @author shenqiv0.1     */    public WebCacheUrl queryWebcacheUrlbyId(Connection conn, String urlId) throws JoymeDBException {        DataBean dbean = null;        try {            String sql = "SELECT * FROM webcache_url  WHERE url_id = ?";            List objectList = new ArrayList();            objectList.add(urlId);            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());            ResultSet rs = dbean.getRs();            if (rs.next()) {                return rsToObject(rs);            }            return null;        } catch (Exception e) {            throw new JoymeDBException(e);        } finally {            this.cleanup(dbean, false);        }    }    /**     * date 2015-08-26 17:25:01     *     * @param bean     * @return int 1 success, 0 failed     * @author shenqiv0.1     */    public int insertWebcacheUrl(Connection conn, WebCacheUrl bean) throws JoymeDBException {        try {            String sql = "INSERT INTO webcache_url(url_id,url,url_key,url_type,channel,http_code,redirct_url,tags,rule_id,last_modify_time) VALUES (?,?,?,?,?,?,?,?,?,?)";            Object[] objects = new Object[]{bean.getUrlId(), bean.getUrl(), bean.getUrlKey(), bean.getUrlType() == null ? "" : bean.getUrlType(), bean.getChannel() == null ? "" : bean.getChannel(), bean.getHttpCode(), bean.getRedirctUrl() == null ? "" : bean.getRedirctUrl(), bean.getTags() == null ? "" : bean.getTags(), bean.getRule_id(), bean.getLastModifyTime()};            return this.executeBindingUpdate(conn, sql, objects, false, false);        } catch (Exception e) {            throw new JoymeDBException(e);        }    }    /**     * date 2015-08-26 17:25:01     *     * @param bean     * @param     * @return int 1 success, 0 failed     * @author shenqiv0.1     */    public int updateWebcacheUrl(Connection conn, WebCacheUrl bean) throws JoymeDBException {        try {            String sql = "UPDATE webcache_url SET $updateStr  WHERE url_id = ?";            List objectList = new ArrayList();            List columnList = bean.getNotNullColumnList();            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));            objectList.add(bean.getUrlId());            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);        } catch (Exception e) {            throw new JoymeDBException(e);        }    }    /**     * date 2015-08-26 17:25:01     *     * @param urlId     * @return int 1 success, 0 failed     * @author shenqiv0.1     */    public int deleteWebcacheUrl(Connection conn, String urlId) throws JoymeDBException {        try {            String sql = "DELETE FROM webcache_url  WHERE url_id = ?";            List objectList = new ArrayList();            objectList.add(urlId);            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);        } catch (Exception e) {            throw new JoymeDBException(e);        }    }    private WebCacheUrl rsToObject(ResultSet rs) throws SQLException {        WebCacheUrl webcacheUrl = new WebCacheUrl();        webcacheUrl.setUrlId(rs.getString("url_id"));        webcacheUrl.setUrl(rs.getString("url"));        webcacheUrl.setUrlKey(rs.getString("url_key"));        webcacheUrl.setUrlType(rs.getString("url_type"));        webcacheUrl.setChannel(rs.getString("channel"));        webcacheUrl.setHttpCode(rs.getInt("http_code"));        webcacheUrl.setRedirctUrl(rs.getString("redirct_url"));        webcacheUrl.setLastModifyTime(new Date(rs.getTimestamp("last_modify_time").getTime()));        webcacheUrl.setRule_id(rs.getString("rule_id"));        webcacheUrl.setTags(rs.getString("tags"));        return webcacheUrl;    }}