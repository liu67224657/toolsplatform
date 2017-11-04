package com.enjoyf.mcms.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.JoymeappBaseJDBCDAOImpl;
import com.enjoyf.mcms.bean.ClientLineFlag;
import com.enjoyf.mcms.bean.ClientLineItem;
import com.enjoyf.mcms.bean.JoymeImage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-7-15
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
public class JoymeAppDao extends JoymeappBaseJDBCDAOImpl {

	public ClientLineFlag queryJoymeImagebyId(Connection conn, String lineCode) throws JoymeDBException {
		DataBean dbean = null;
		try {
			String sql = "SELECT * FROM client_line_flag  WHERE line_code = ?";
			List objectList = new ArrayList();
			objectList.add(lineCode);
			dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
			ResultSet rs = dbean.getRs();
			while (rs.next()) {
				return queryClientLineFlag(rs);
			}
			return null;
		} catch (Exception e) {
			throw new JoymeDBException(e);
		} finally {
			this.cleanup(dbean, false);
		}
	}


	public ClientLineItem queryClientLineItembyId(Connection conn, Long itemId) throws JoymeDBException {
		DataBean dbean = null;
		try {
			String sql = "SELECT * FROM client_line_item  WHERE item_id = ?";
			List objectList = new ArrayList();
			objectList.add(itemId);
			dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
			ResultSet rs = dbean.getRs();
			while (rs.next()) {
				return queryClientLineItem(rs);
			}
			return null;
		} catch (Exception e) {
			throw new JoymeDBException(e);
		} finally {
			this.cleanup(dbean, false);
		}
	}

	public ClientLineItem queryClientLineItembyDirectid(Connection conn, Long directid) throws JoymeDBException {
		DataBean dbean = null;
		try {
			String sql = "SELECT * FROM client_line_item  WHERE valid_status='valid' and direct_id = ?";
			List objectList = new ArrayList();
			objectList.add(directid);
			dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
			ResultSet rs = dbean.getRs();
			while (rs.next()) {
				return queryClientLineItem(rs);
			}
			return null;
		} catch (Exception e) {
			throw new JoymeDBException(e);
		} finally {
			this.cleanup(dbean, false);
		}
	}

	private ClientLineItem queryClientLineItem(ResultSet rs) throws SQLException {
		ClientLineItem clientLineItem = new ClientLineItem();
		clientLineItem.setItem_id(rs.getLong("item_id"));
		clientLineItem.setLine_id(rs.getLong("line_id"));
		clientLineItem.setTitle(rs.getString("title"));
		clientLineItem.setDirect_id(rs.getLong("direct_id"));
		return clientLineItem;
	}


	private ClientLineFlag queryClientLineFlag(ResultSet rs) throws SQLException {
		ClientLineFlag clientLineFlag = new ClientLineFlag();
		clientLineFlag.setFlag_id(rs.getLong("flag_id"));
		clientLineFlag.setFlag_desc(rs.getString("flag_desc"));
		clientLineFlag.setLine_id(rs.getLong("line_id"));
		clientLineFlag.setLine_code(rs.getString("line_code"));
		clientLineFlag.setMax_item_id(rs.getLong("max_item_id"));
		return clientLineFlag;
	}


}
