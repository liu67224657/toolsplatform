package com.enjoyf.mcms.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.mcms.bean.DedeArctype;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DedeArctypeDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-08-01 17:04:14
     *
     * @param id
     * @return List <DedeArctype>
     * @author shenqiv0.1
     */
    public DedeArctype queryDedeArctype(Connection conn, Integer id) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM dede_arctype where id = ?";
            List objectList = new ArrayList();
            objectList.add(id);
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

    public DedeArctype queryDedeArctypeByTypeDir(Connection conn, String typeDir) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM dede_arctype where typeDir = ?";
            List objectList = new ArrayList();
            objectList.add(typeDir);
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

    private DedeArctype rsToObject(ResultSet rs) throws SQLException {
        DedeArctype dedeArctype = new DedeArctype();
        dedeArctype.setId(rs.getInt("id"));
        dedeArctype.setReid(rs.getInt("reid"));
        dedeArctype.setTopid(rs.getInt("topid"));
        dedeArctype.setSortrank(rs.getInt("sortrank"));
        dedeArctype.setTypename(rs.getString("typename"));
        dedeArctype.setTypedir(rs.getString("typedir"));
        dedeArctype.setIsdefault(rs.getInt("isdefault"));
        dedeArctype.setDefaultname(rs.getString("defaultname"));
        dedeArctype.setIssend(rs.getInt("issend"));
        dedeArctype.setChanneltype(rs.getInt("channeltype"));
        dedeArctype.setMaxpage(rs.getInt("maxpage"));
        dedeArctype.setIspart(rs.getInt("ispart"));
        dedeArctype.setCorank(rs.getInt("corank"));
        dedeArctype.setTempindex(rs.getString("tempindex"));
        dedeArctype.setTemplist(rs.getString("templist"));
        dedeArctype.setTemparticle(rs.getString("temparticle"));
        dedeArctype.setNamerule(rs.getString("namerule"));
        dedeArctype.setNamerule2(rs.getString("namerule2"));
        dedeArctype.setModname(rs.getString("modname"));
        dedeArctype.setDescription(rs.getString("description"));
        dedeArctype.setKeywords(rs.getString("keywords"));
        dedeArctype.setSeotitle(rs.getString("seotitle"));
        dedeArctype.setMoresite(rs.getBoolean("moresite"));
        dedeArctype.setSitepath(rs.getString("sitepath"));
        dedeArctype.setSiteurl(rs.getString("siteurl"));
        dedeArctype.setIshidden(rs.getInt("ishidden"));
        dedeArctype.setCross(rs.getBoolean("cross"));
        dedeArctype.setCrossid(rs.getString("crossid"));
        dedeArctype.setContent(rs.getString("content"));
        dedeArctype.setSmalltypes(rs.getString("smalltypes"));
        return dedeArctype;
    }
}
