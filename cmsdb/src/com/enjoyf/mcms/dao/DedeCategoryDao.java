package com.enjoyf.mcms.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.mcms.bean.DedeCategory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DedeCategoryDao extends BaseJDBCDAOImpl {

	/**
	 * date 2014-06-20 17:29:29
	 *
	 * @param id
	 * @return DedeCategory
	 * @author shenqiv0.1
	 */
	public DedeCategory queryDedeCategorybyId(Connection conn, Integer id) throws JoymeDBException {
		DataBean dbean = null;
		try {
			String sql = "SELECT * FROM dede_category  WHERE typeStatus=1 and id = ?";
			List objectList = new ArrayList();
			objectList.add(id);
			dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
			List retList = new ArrayList();
			ResultSet rs = dbean.getRs();
			if (rs.next()) {
				return rsToObject(rs);
			}
			return null;
		} catch (Exception e) {
			throw new JoymeDBException(e);
		} finally {
			this.cleanup(dbean, false);
		}
	}

	/**
	 * date 2014-06-20 17:29:29
	 *
	 * @return DedeCategory
	 * @author shenqiv0.1
	 */
	public List<DedeCategory> queryDedeCategoryList(Connection conn) throws JoymeDBException {
		DataBean dbean = null;
		try {
			String sql = "SELECT * FROM dede_category where typeStatus=1";
			List objectList = new ArrayList();
			dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
			List<DedeCategory> retList = new ArrayList<DedeCategory>();
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

	private DedeCategory rsToObject(ResultSet rs) throws SQLException {
		DedeCategory dedeCategory = new DedeCategory();
		dedeCategory.setId(rs.getInt("id"));
		dedeCategory.setTypeName(rs.getString("typeName"));
		dedeCategory.setTypeColor(rs.getString("typeColor"));
		dedeCategory.setTypeStatus(rs.getInt("typeStatus"));
		dedeCategory.setTypeArticle(rs.getInt("typeArticle"));
		dedeCategory.setUrl(rs.getString("url"));
		return dedeCategory;
	}

	/**
	 * date 2014-06-20 17:29:29
	 *
	 * @param bean
	 * @return int 1 success, 0 failed
	 * @author shenqiv0.1
	 */
	public int insertDedeCategory(Connection conn, DedeCategory bean) throws JoymeDBException {
		try {
			String sql = "INSERT INTO dede_category(typeName,typeColor,typeStatus) VALUES (?,?,?)";
			Object[] objects = new Object[]{bean.getTypeName(), bean.getTypeColor(), bean.getTypeStatus()};
			return this.executeBindingUpdate(conn, sql, objects, false, false);
		} catch (Exception e) {
			throw new JoymeDBException(e);
		}
	}


	/**
	 * date 2014-06-20 17:29:29
	 *
	 * @param bean
	 * @return int 1 success, 0 failed
	 * @author shenqiv0.1
	 */
	public int updateDedeCategory(Connection conn, DedeCategory bean) throws JoymeDBException {
		try {
			String sql = "UPDATE dede_category SET $updateStr  WHERE id = ?";
			List objectList = new ArrayList();
			List columnList = bean.getNotNullColumnList();
			sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
			objectList.add(bean.getId());
			return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
		} catch (Exception e) {
			throw new JoymeDBException(e);
		}
	}


	/**
	 * date 2014-06-20 17:29:29
	 *
	 * @param id
	 * @return int 1 success, 0 failed
	 * @author shenqiv0.1
	 */
	public int deleteDedeCategory(Connection conn, Integer id) throws JoymeDBException {
		try {
			String sql = "DELETE FROM dede_category  WHERE id = ?";
			List objectList = new ArrayList();
			objectList.add(id);
			return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
		} catch (Exception e) {
			throw new JoymeDBException(e);
		}
	}


}