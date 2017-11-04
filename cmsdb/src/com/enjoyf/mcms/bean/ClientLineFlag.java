package com.enjoyf.mcms.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-7-15
 * Time: 下午3:40
 * To change this template use File | Settings | File Templates.
 */
public class ClientLineFlag  implements Serializable {
	private Long flag_id;
	private String flag_desc;
	private Long line_id;
	private String line_code;
	private Long max_item_id;
	public List getNotNullColumnList() {
		List columnList = new ArrayList();
		if (flag_id != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("flag_id");
			bean.setObj(flag_id);
			columnList.add(bean);
		}
		if (flag_desc != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("flag_desc");
			bean.setObj(flag_desc);
			columnList.add(bean);
		}
		if (line_id != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("line_id");
			bean.setObj(line_id);
			columnList.add(bean);
		}
		if (line_code != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("line_code");
			bean.setObj(line_code);
			columnList.add(bean);
		}
		if (max_item_id != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("max_item_id");
			bean.setObj(max_item_id);
			columnList.add(bean);
		}
		return columnList;
	}
	public long getFlag_id() {
		return flag_id;
	}

	public void setFlag_id(long flag_id) {
		this.flag_id = flag_id;
	}

	public String getFlag_desc() {
		return flag_desc;
	}

	public void setFlag_desc(String flag_desc) {
		this.flag_desc = flag_desc;
	}

	public long getLine_id() {
		return line_id;
	}

	public void setLine_id(long line_id) {
		this.line_id = line_id;
	}

	public String getLine_code() {
		return line_code;
	}

	public void setLine_code(String line_code) {
		this.line_code = line_code;
	}

	public long getMax_item_id() {
		return max_item_id;
	}

	public void setMax_item_id(long max_item_id) {
		this.max_item_id = max_item_id;
	}
}
