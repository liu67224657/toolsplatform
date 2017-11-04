package com.enjoyf.webcache.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;
import com.enjoyf.util.StringUtil;
import com.enjoyf.webcache.bean.JoymeWikiKeyword;

/**
 * 
 * @author huazhang
 *
 */
public class JoymeWikiKeywordDAO extends BaseJDBCDAOImpl {

	private static final String TABLE_NAME = "wiki_keyword";
	

	public int insertWikiKeyword(JoymeWikiKeyword joymeWikiKeyword) throws JoymeDBException {
		Connection conn=null;
		try {
			conn=this.getConnection();

			String sql = "INSERT INTO " + TABLE_NAME + "(keyword_id,wiki_id,keyword,status,url) VALUES (?,?,?,?,?)";
			Object[] objects = new Object[] { joymeWikiKeyword.getKeywordId(), joymeWikiKeyword.getWikiId(),
					joymeWikiKeyword.getKeyword(), joymeWikiKeyword.getStatus(),joymeWikiKeyword.getUrl() };
			return this.executeBindingUpdate(conn, sql, objects, false, false);
		} catch (Exception e) {
			throw new JoymeDBException(e);
		} finally{
			if(conn != null){
				this.closeConnection(conn);
			}
		}
	}

	private JoymeWikiKeyword rsToObject(ResultSet rs) throws SQLException {
		JoymeWikiKeyword joymeWikiKeyword = new JoymeWikiKeyword();
		joymeWikiKeyword.setKeywordId(rs.getString("keyword_id"));
		joymeWikiKeyword.setWikiId(rs.getString("wiki_id"));
		joymeWikiKeyword.setKeyword(rs.getString("keyword"));
		joymeWikiKeyword.setStatus(rs.getInt("status"));
		joymeWikiKeyword.setCreateTime(rs.getTimestamp("create_time"));
		joymeWikiKeyword.setUrl(rs.getString("url"));

		return joymeWikiKeyword;
	}

	public JoymeWikiKeyword getJoymeWikiKeyword(String wikiId,String keyword) throws JoymeDBException {
		Connection conn=null;
		try {
			conn=this.getConnection();

			String sql = "SELECT * FROM " + TABLE_NAME + " WHERE wiki_id='"+wikiId+"' and keyword='" + keyword + "'";
			DataBean dbean = this.executeBindingQuery(conn, sql, null);

			ResultSet rs = dbean.getRs();
			while (rs.next()) {
				return rsToObject(rs);
			}
			return null;
		} catch (Exception e) {
			throw new JoymeDBException(e);
		} finally{
			if (conn != null) {
				this.closeConnection(conn);
			}
		}
	}

	public JoymeWikiKeyword getJoymeWikiKeywordByKeywordId(String keywordId) throws JoymeDBException {
		Connection conn=null;
		try {
			conn=this.getConnection();

			String sql = "SELECT * FROM " + TABLE_NAME + " WHERE keyword_id='" + keywordId + "'";

			DataBean dbean = this.executeBindingQuery(conn, sql, null);

			ResultSet rs = dbean.getRs();
			while (rs.next()) {
				return rsToObject(rs);
			}
			return null;
		} catch (Exception e) {
			throw new JoymeDBException(e);
		} finally{
			if (conn != null) {
				this.closeConnection(conn);
			}
		}
	}
	
	public PageRows<JoymeWikiKeyword> queryJoymeWikiKeyword(Pagination page, String wikiId,String name) throws JoymeDBException {
		PageRows<JoymeWikiKeyword> pageRows = null;
		DataBean dbean = null;
		Connection conn=null;
		try {
			conn=this.getConnection();
			String sql ="SELECT * FROM " + TABLE_NAME+" where wiki_id='"+wikiId+"'";
			if (!StringUtil.isEmpty(name)) {
				sql +=" and keyword like '%"+name+"%'";
			}

			sql+="  order by status asc,create_time desc";
			if (page != null) {
				sql += " LIMIT ?,?";
			}
			List objectList = new ArrayList();

			if (page != null) {
				objectList.add(page.getStartRowIdx());
				objectList.add(page.getPageSize());
			}
			dbean = this.executeBindingQuery(conn, sql, objectList.toArray());

			List<JoymeWikiKeyword> retList = new ArrayList<JoymeWikiKeyword>();
			ResultSet rs = dbean.getRs();
			while (rs.next()) {
				retList.add(rsToObject(rs));
			}

			String countSql = "SELECT COUNT(keyword_id) FROM " + TABLE_NAME+" where wiki_id='"+wikiId+"'";
			if (!StringUtil.isEmpty(name)) {
				countSql +=" and keyword like '%"+name+"%'";
			}
			dbean = this.executeBindingQuery(conn, countSql, null);
			ResultSet countRs = dbean.getRs();
			if (countRs.next()) {
				page.setTotalRows(countRs.getInt(1));
			}

			pageRows = new PageRows<JoymeWikiKeyword>();
			pageRows.setPage(page);
			pageRows.setRows(retList);
			return pageRows;
		} catch (Exception e) {
			throw new JoymeDBException(e);
		} finally {
			this.cleanup(dbean, false);
			if (conn != null) {
				this.closeConnection(conn);
			}
		}
	}

	public int deleteKeyword(String keywordIds) throws JoymeDBException {
		
		Connection conn=null;
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE keyword_id in(" + keywordIds + ")";
		try {
			conn=this.getConnection();

			return this.executeBindingUpdate(conn, sql, null, false, false);
		} catch (Exception e) {
			throw new JoymeDBException(e);
		} finally{
			if(conn != null){
				this.closeConnection(conn);
			}
		}
	}

	public int updateKeywordStatus(String keywordIds,int type) throws JoymeDBException {
		Connection conn=null;
		try {
			conn=this.getConnection();
			
			String sql = "UPDATE " + TABLE_NAME +" SET status=1";

			if(type==1){
				sql += " WHERE keyword_id in(" + keywordIds + ")";
			}else if (type==2) {
				sql += " WHERE status=0";
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
	
	public int updateKeyword(JoymeWikiKeyword joymeWikiKeyword) throws JoymeDBException {
		Connection conn=null;
		try {
			conn=this.getConnection();
			
			String sql = "UPDATE " + TABLE_NAME;

			if (!StringUtil.isEmpty(joymeWikiKeyword.getUrl())) {
				sql+=" set url='"+joymeWikiKeyword.getUrl()+"'";
			}
			sql+=",status=0 where keyword_id='"+joymeWikiKeyword.getKeywordId()+"'";
			
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
