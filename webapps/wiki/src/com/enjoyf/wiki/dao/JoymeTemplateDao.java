package com.enjoyf.wiki.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.wiki.bean.JoymeItem;
import com.enjoyf.wiki.bean.JoymeTemplate;
import com.enjoyf.wiki.bean.PageBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JoymeTemplateDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-09-17 17:47:37
     *
     * @param joymeTemplateId
     * @return JoymeTemplate
     * @author shenqiv0.1
     */
    public JoymeTemplate queryJoymeTemplatebyId(Connection conn, Integer joymeTemplateId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_template  WHERE joyme_template_id = ?";
            List objectList = new ArrayList();
            objectList.add(joymeTemplateId);
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


    public JoymeTemplate getJoymeTemplate(Connection conn, String channel, String wiki, Integer isIndex, String contextPath) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_template WHERE channel=? AND wiki=? AND is_index=? AND context_path=?";
            List objectList = new ArrayList();
            objectList.add(channel);
            objectList.add(wiki);
            objectList.add(isIndex);
            objectList.add(contextPath);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
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

    public List queryJoymeTemplate(Connection conn, String contextPath) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_template WHERE context_path=?";
            List objectList = new ArrayList();
            objectList.add(contextPath);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
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


     public List queryJoymeTemplate(Connection conn) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_template";
            List objectList = new ArrayList();
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
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

    /**
     * date 2013-09-17 17:47:37
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertJoymeTemplate(Connection conn, JoymeTemplate bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_template(template_name,channel,wiki,is_index,template_context,create_time,is_enable,prase_factory,context_path) VALUES (?,?,?,?,?,?,?,?,?)";
            Object[] objects = new Object[]{bean.getTemplateName(), bean.getChannel(), bean.getWiki(), bean.getIsIndex(), bean.getTemplateContext(), bean.getCreateTime(), bean.getIsEnable(), bean.getPraseFactory(),bean.getContextPath()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-09-17 17:47:37
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateJoymeTemplate(Connection conn, JoymeTemplate bean) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_template SET $updateStr  WHERE joyme_template_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getJoymeTemplateId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-09-17 17:47:37
     *
     * @param joymeTemplateId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteJoymeTemplate(Connection conn, Integer joymeTemplateId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_template  WHERE joyme_template_id = ?";
            List objectList = new ArrayList();
            objectList.add(joymeTemplateId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public PageBean queryJoymeItemByCondition(Connection conn, String wiki, String channel, Integer isIndex, String itemKey, int pageNum, String contextPath) throws JoymeDBException {
        int pageCount = 30;
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_template WHERE 1=1 ";
            List objectList = new ArrayList();
            if (wiki != null && !wiki.equals("")) {
                sql += " AND wiki = ?";
                objectList.add(wiki);
            }
            if (channel != null && !channel.equals("")) {
                sql += " AND channel = ?";
                objectList.add(channel);
            }
            if (isIndex != 0) {
                sql += " AND is_index = ?";
                objectList.add(isIndex);
            }
            if (itemKey != null && !itemKey.equals("")) {
                sql += " AND template_name = ?";
                objectList.add(itemKey);
            }
            if (contextPath != null && !contextPath.equals("")) {
                sql += " AND context_path = ?";
                objectList.add(contextPath);
            }
//            objectList.add(contextPath);

//            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            dbean = this.executeBindingQueryWithPage(conn, sql, objectList.toArray(), pageNum, pageCount, true);
            ResultSet rs = dbean.getRs();
            List retList = new ArrayList();
            int a = 0;
            boolean hasNextPage = false;

            while (rs.next()) {
                JoymeTemplate template = rsToObject(rs);
                retList.add(template);
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


    private JoymeTemplate rsToObject(ResultSet rs) throws SQLException {
        JoymeTemplate joymeTemplate = new JoymeTemplate();
        joymeTemplate.setJoymeTemplateId(rs.getInt("joyme_template_id"));
        joymeTemplate.setTemplateName(rs.getString("template_name"));
        joymeTemplate.setChannel(rs.getString("channel"));
        joymeTemplate.setWiki(rs.getString("wiki"));
        joymeTemplate.setIsIndex(rs.getInt("is_index"));
        joymeTemplate.setTemplateContext(rs.getObject("template_context"));
        joymeTemplate.setCreateTime(rs.getTimestamp("create_time"));
        joymeTemplate.setIsEnable(rs.getInt("is_enable"));
        joymeTemplate.setPraseFactory(rs.getString("prase_factory"));
        joymeTemplate.setContextPath(rs.getString("context_path"));
        return joymeTemplate;
    }


}