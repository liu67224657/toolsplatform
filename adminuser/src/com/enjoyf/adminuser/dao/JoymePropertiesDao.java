package com.enjoyf.adminuser.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.adminuser.bean.JoymeProperties;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

public class JoymePropertiesDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-09-29 12:37:04
     * 
     * @author shenqiv0.1
     * @param joymeType
     * @param joymeFatherPropId
     * @return List <JoymeProperties>
     */
    public List queryJoymeProperties(Connection conn, Integer joymeType, Integer joymeFatherPropId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_admin_user.joyme_properties WHERE joyme_type = ? AND joyme_father_prop_id = ? ORDER BY joyme_display_order ASC";
            List objectList = new ArrayList();
            objectList.add(joymeType);
            objectList.add(joymeFatherPropId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymeProperties joymeProperties = getJoymeProperties(rs);
                retList.add(joymeProperties);
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    private JoymeProperties getJoymeProperties(ResultSet rs) throws SQLException {
        JoymeProperties joymeProperties = new JoymeProperties();
        joymeProperties.setJoymePropertiesId(rs.getInt("joyme_properties_id"));
        joymeProperties.setJoymePropertiesKey(rs.getString("joyme_properties_key"));
        joymeProperties.setJoymePropertiesKeyName(rs.getString("joyme_properties_key_name"));
        joymeProperties.setJoymeBelong(rs.getInt("joyme_belong"));
        joymeProperties.setJoymeType(rs.getInt("joyme_type"));
        joymeProperties.setJoymeFatherPropId(rs.getInt("joyme_father_prop_id"));
        joymeProperties.setJoymeDisplayOrder(rs.getInt("joyme_display_order"));
        joymeProperties.setJoymeLink(rs.getString("joyme_link"));
        return joymeProperties;
    }
}