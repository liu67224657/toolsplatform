package com.enjoyf.autobuilder.dao;

import com.enjoyf.autobuilder.bean.AndroidTemplate;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AndroidTemplateDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-12-16 12:45:45
     *
     * @param templateId
     * @return AndroidTemplate
     * @author shenqiv0.1
     */
    public AndroidTemplate queryAndroidTemplatebyId(Connection conn, Integer templateId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM android_template  WHERE template_id = ?";
            List objectList = new ArrayList();
            objectList.add(templateId);
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
     * date 2013-12-16 12:45:45
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertAndroidTemplate(Connection conn, AndroidTemplate bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO android_template(template_code,template_version,template_url,platform) VALUES (?,?,?,?)";
            Object[] objects = new Object[]{bean.getTemplateCode(), bean.getTemplateVersion(), bean.getTemplateUrl(),bean.getPlatform()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-12-16 12:45:45
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateAndroidTemplate(Connection conn, AndroidTemplate bean) throws JoymeDBException {
        try {
            String sql = "UPDATE android_template SET $updateStr  WHERE template_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getTemplateId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-12-16 12:45:45
     *
     * @param templateId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteAndroidTemplate(Connection conn, Integer templateId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM android_template  WHERE template_id = ?";
            List objectList = new ArrayList();
            objectList.add(templateId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public List queryTemplate(Connection conn, Integer platform) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM android_template WHERE platform=?";
            List objectList = new ArrayList();
            objectList.add(platform);
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

    public List queryAndroidTemplate(Connection conn, String templateCode, String templateVersion, int platform) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM android_template WHERE template_code = ? AND template_version =? AND platform=?";
            List objectList = new ArrayList();
            objectList.add(templateCode);
            objectList.add(templateVersion);
            objectList.add(platform);
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

    private AndroidTemplate rsToObject(ResultSet rs) throws SQLException {
        AndroidTemplate androidTemplate = new AndroidTemplate();
        androidTemplate.setTemplateId(rs.getInt("template_id"));
        androidTemplate.setTemplateCode(rs.getString("template_code"));
        androidTemplate.setTemplateVersion(rs.getString("template_version"));
        androidTemplate.setTemplateUrl(rs.getString("template_url"));
        androidTemplate.setPlatform(rs.getInt("platform"));
        return androidTemplate;
    }


}