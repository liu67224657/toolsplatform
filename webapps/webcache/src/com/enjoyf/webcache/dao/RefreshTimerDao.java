package com.enjoyf.webcache.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.webcache.bean.RefreshTimerUrl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhitaoshi on 2016/5/12.
 */
public class RefreshTimerDao extends BaseJDBCDAOImpl {

    public int createRefreshUrl(Connection conn, RefreshTimerUrl refreshTimerUrl) throws JoymeDBException {
        try {
            String sql = "INSERT INTO refresh_url(urlid,refreshurl,refreshtype) VALUES (?,?,?)";
            Object[] objects = new Object[]{refreshTimerUrl.getUrlId(), refreshTimerUrl.getUrl(), refreshTimerUrl.getType()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public RefreshTimerUrl getRefreshUrl(Connection conn, String urlId) throws JoymeDBException {
        RefreshTimerUrl refreshTimerUrl = null;
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM refresh_url WHERE urlid=?";
            Object[] objects = new Object[]{urlId};
            dbean = this.executeBindingQuery(conn, sql, objects);
            ResultSet rs = dbean.getRs();
            if (rs.next()) {
                refreshTimerUrl = rsToObject(rs);
            }
            return refreshTimerUrl;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public List<RefreshTimerUrl> queryRefreshUrl(Connection conn) throws JoymeDBException {
        List<RefreshTimerUrl> list = new ArrayList<RefreshTimerUrl>();
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM refresh_url";
            dbean = this.executeBindingQuery(conn, sql, null);
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                RefreshTimerUrl url = rsToObject(rs);
                if(url != null){
                    list.add(url);
                }
            }
            return list;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public int delRefreshUrl(Connection conn, String urlId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM refresh_url WHERE urlid=?";
            Object[] objects = new Object[]{urlId};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    private RefreshTimerUrl rsToObject(ResultSet rs) throws SQLException {
        RefreshTimerUrl url = new RefreshTimerUrl();
        url.setUrlId(rs.getString("urlid"));
        url.setType(rs.getInt("refreshtype"));
        url.setUrl(rs.getString("refreshurl"));
        return url;
    }

}
