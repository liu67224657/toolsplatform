package com.enjoyf.wiki.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.wiki.bean.WikiPage;
import com.enjoyf.wiki.bean.WikiRecommend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-7-22
 * Time: 下午3:19
 * To change this template use File | Settings | File Templates.
 */
public class WikiRecommendDao extends BaseJDBCDAOImpl {

	public int insertWikiRecommend(Connection conn, WikiRecommend bean) throws JoymeDBException {
		try {
			String sql = "INSERT INTO joyme_wiki_recommend(wiki_key,title,pageid,php_url,type,pv_count,create_time) VALUES (?,?,?,?,?,?,?)";
			Object[] objects = new Object[]{bean.getWiki_key(), bean.getTitle(), bean.getPageid(), bean.getPhp_url(), bean.getType(), bean.getPv_count(), bean.getCreate_time()};
			return this.executeBindingUpdate(conn, sql, objects, false, false);
		} catch (Exception e) {
			throw new JoymeDBException(e);
		}
	}

	public WikiRecommend queryWikiRecommend(Connection conn, String key, String title) throws JoymeDBException {
		DataBean dbean = null;
		try {
			String sql = "SELECT * FROM joyme_wiki_recommend  where wiki_key=? AND  title=? ORDER BY id DESC  limit 0,1";
			List objectList = new ArrayList();
			objectList.add(key);
			objectList.add(title);
			dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
			ResultSet rs = dbean.getRs();
			while (rs.next()) {
				return getWikiRecommend(rs);
			}
			return null;
		} catch (Exception e) {
			throw new JoymeDBException(e);
		} finally {
			this.cleanup(dbean, false);
		}
	}


	public WikiRecommend queryWikiRecommend(Connection conn, String key, Date start, Date end, String url) throws JoymeDBException {
		DataBean dbean = null;
		try {
			String sql = "SELECT * FROM joyme_wiki_recommend  where type=1 and wiki_key=? AND php_url=? and create_time>=? and create_time<=?";
			List objectList = new ArrayList();
			objectList.add(key);
			objectList.add(url);
			objectList.add(start);
			objectList.add(end);
			dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
			ResultSet rs = dbean.getRs();
			while (rs.next()) {
				return getWikiRecommend(rs);
			}
			return null;
		} catch (Exception e) {
			throw new JoymeDBException(e);
		} finally {
			this.cleanup(dbean, false);
		}
	}

	private WikiRecommend getWikiRecommend(ResultSet rs) throws SQLException {
		WikiRecommend wikiRecommend = new WikiRecommend();
		wikiRecommend.setId(rs.getLong("id"));
		wikiRecommend.setWiki_key(rs.getString("wiki_key"));
		wikiRecommend.setTitle(rs.getString("title"));
		wikiRecommend.setPageid(rs.getLong("pageid"));
		wikiRecommend.setPhp_url(rs.getString("php_url"));
		wikiRecommend.setType(rs.getInt("type"));
		wikiRecommend.setPv_count(rs.getInt("pv_count"));
		wikiRecommend.setCreate_time(rs.getTimestamp("create_time"));
		wikiRecommend.setPage_status(rs.getInt("page_status"));
		return wikiRecommend;
	}

	public List<WikiRecommend> queryWikiRecommend(Connection conn, String joymeWikiKey, int type, Date start, Date end) throws JoymeDBException {
		DataBean dbean = null;
		try {
			String sql = "SELECT * FROM joyme_wiki_recommend WHERE page_status=1 AND wiki_key=? and type=?";
			List objectList = new ArrayList();
			objectList.add(joymeWikiKey);
			objectList.add(type);

			if (type == 1) {
				sql = sql + " and create_time>=? and create_time<=? order by pv_count desc LIMIT 0,10 ";
				objectList.add(start);
				objectList.add(end);
			} else if (type == 2) {
				String sql2 = "SELECT * FROM joyme_wiki_recommend WHERE page_status=1 AND wiki_key=? and type=?";
				sql = sql2 + " GROUP BY php_url order by id desc LIMIT 0,10 ";
			}

			dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
			List<WikiRecommend> retList = new ArrayList<WikiRecommend>();
			ResultSet rs = dbean.getRs();
			while (rs.next()) {
				retList.add(getWikiRecommend(rs));
			}
			return retList;
		} catch (Exception e) {
			throw new JoymeDBException(e);
		} finally {
			this.cleanup(dbean, false);
		}
	}

	public List<WikiRecommend> queryWikiRecommend(Connection conn, String joymeWikiKey, int type) throws JoymeDBException {
		DataBean dbean = null;
		try {
//			String sql = "SELECT MAX(id) id,wiki_key,title,pageid,php_url FROM joyme_wiki_recommend WHERE page_status=1 AND wiki_key=? and type=?";
//			sql = sql+" GROUP BY php_url order by id ASC LIMIT 0,10";
			String sql = "SELECT * FROM `joyme_wiki_recommend` WHERE page_status=1 AND wiki_key=? AND TYPE=? ORDER BY  id DESC LIMIT 0,10";
			List objectList = new ArrayList();
			objectList.add(joymeWikiKey);
			objectList.add(type);
			dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
			List<WikiRecommend> retList = new ArrayList<WikiRecommend>();
			ResultSet rs = dbean.getRs();
			while (rs.next()) {
				WikiRecommend wikiRecommend = new WikiRecommend();
				wikiRecommend.setId(rs.getLong("id"));
				wikiRecommend.setWiki_key(rs.getString("wiki_key"));
				wikiRecommend.setTitle(rs.getString("title"));
				wikiRecommend.setPageid(rs.getLong("pageid"));
				wikiRecommend.setPhp_url(rs.getString("php_url"));
				retList.add(wikiRecommend);
			}
			return retList;
		} catch (Exception e) {
			throw new JoymeDBException(e);
		} finally {
			this.cleanup(dbean, false);
		}
	}
}
