package com.enjoyf.webcache.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.util.CollectionUtil;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;
import com.enjoyf.util.StringUtil;
import com.enjoyf.webcache.bean.JoymeWikiInfo;
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
 * 
 * @author huazhang
 *
 */
public class JoymeWikiInfoDAO extends BaseJDBCDAOImpl {

    private static final String TABLE_NAME = "wiki_info";
    
    
    public int insertWikiTitle(JoymeWikiInfo JoymeWikiInfo) throws JoymeDBException {
    	Connection conn=null;
        try {
        	conn=this.getConnection();
            String sql = "INSERT INTO " + TABLE_NAME + "(wiki_id,wiki_name,keyword_num,wiki_key) VALUES (?,?,?,?)";
            Object[] objects = new Object[]{JoymeWikiInfo.getWikiId(),JoymeWikiInfo.getWikiName(),JoymeWikiInfo.getKeywordNum(),JoymeWikiInfo.getWikiKey()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally{
        	if (conn!=null) {
				this.closeConnection(conn);
			}
        }
    }

    private JoymeWikiInfo rsToObject(ResultSet rs) throws SQLException {
    	JoymeWikiInfo JoymeWikiInfo = new JoymeWikiInfo();
    	JoymeWikiInfo.setWikiId(rs.getString("wiki_id"));
    	JoymeWikiInfo.setWikiName(rs.getString("wiki_name"));
    	JoymeWikiInfo.setKeywordNum(rs.getInt("keyword_num"));
    	JoymeWikiInfo.setCreateTime(rs.getTimestamp("create_time"));
    	JoymeWikiInfo.setWikiKey(rs.getString("wiki_key"));

        return JoymeWikiInfo;
    }


    public JoymeWikiInfo getJoymeWikiInfoByName(String wikiName) throws JoymeDBException{
    	Connection conn=null;
        try {
			conn=this.getConnection();

			String sql = "SELECT * FROM " + TABLE_NAME + " WHERE wiki_name=?";
            List objectList = new ArrayList();

            objectList.add(wikiName);

            DataBean dbean = this.executeBindingQuery(conn, sql, objectList.toArray());

            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                return rsToObject(rs);
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally{
        	if(conn != null){
        		this.closeConnection(conn);
        	}
        }
    }
    
    public PageRows<JoymeWikiInfo> queryJoymeWikiInfo(Pagination page, String name) throws JoymeDBException {
        PageRows<JoymeWikiInfo> pageRows = null;
        DataBean dbean = null;
        Connection conn=null;
        try {
			conn=this.getConnection();

			String sql = "SELECT * FROM " + TABLE_NAME;
			if (!StringUtil.isEmpty(name)) {
				sql+=" where wiki_name like '%"+name+"%'";
			}
			sql+=" order by create_time desc";

            if (page != null) {
                sql += " LIMIT ?,?";
            }
            List objectList = new ArrayList();

            if (page != null) {
                objectList.add(page.getStartRowIdx());
                objectList.add(page.getPageSize());
            }
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());

            List<JoymeWikiInfo> retList = new ArrayList<JoymeWikiInfo>();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                retList.add(rsToObject(rs));
            }

            String countSql = "SELECT COUNT(wiki_id) FROM " + TABLE_NAME;
			if (!StringUtil.isEmpty(name)) {
				countSql+=" where wiki_name like '%"+name+"%'";
			}
            dbean = this.executeBindingQuery(conn, countSql, null);
            ResultSet countRs = dbean.getRs();
            if (countRs.next()) {
                page.setTotalRows(countRs.getInt(1));
            }

            pageRows = new PageRows<JoymeWikiInfo>();
            pageRows.setPage(page);
            pageRows.setRows(retList);
            return pageRows;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
        	if (conn != null ) {
  				this.closeConnection(conn);
  			}
            this.cleanup(dbean, false);
        }
    }

    public int updateWikiKeywordNumById(String wikiId, int type) throws JoymeDBException {
		Connection conn=null;
		try {
			conn=this.getConnection();
			
			String sql = "";
			if (type==1) {
				sql="UPDATE " + TABLE_NAME +" SET keyword_num=keyword_num+1 where wiki_id='"+wikiId+"'";
			}else if (type==2) {
				sql="UPDATE " + TABLE_NAME +" SET keyword_num=keyword_num-1 where wiki_id='"+wikiId+"'";
			}
			
			return this.executeBindingUpdate(conn, sql, null, false, false);
		} catch (Exception e) {
			throw new JoymeDBException(e);
		} finally{
			if (conn != null) {
				this.closeConnection(conn);
			}
		}

	}
}
