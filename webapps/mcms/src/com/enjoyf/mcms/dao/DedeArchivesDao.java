package com.enjoyf.mcms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.mcms.bean.DedeAddonspec;
import com.enjoyf.mcms.bean.DedeArchives;

public class DedeArchivesDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-07-30 15:50:20
     * 
     * @author shenqiv0.1
     * @param typeid
     * @param filename
     * @return List <DedeArchives>
     */
    public List queryDedeArchivesByFileName(Connection conn, Integer typeid, String filename) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM dede_archives WHERE typeid=? AND filename=?";
            List objectList = new ArrayList();
            objectList.add(typeid);
            objectList.add(filename);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                DedeArchives dedeArchives = getDedeArchives(rs);
                retList.add(dedeArchives);
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    private DedeArchives getDedeArchives(ResultSet rs) throws SQLException {
        DedeArchives dedeArchives = new DedeArchives();
        dedeArchives.setId(rs.getInt("id"));
        dedeArchives.setTypeid(rs.getInt("typeid"));
        dedeArchives.setTypeid2(rs.getString("typeid2"));
        dedeArchives.setSortrank(rs.getInt("sortrank"));
        dedeArchives.setFlag(rs.getObject("flag"));
        dedeArchives.setIsmake(rs.getInt("ismake"));
        dedeArchives.setChannel(rs.getInt("channel"));
        dedeArchives.setArcrank(rs.getInt("arcrank"));
        dedeArchives.setClick(rs.getObject("click"));
        dedeArchives.setMoney(rs.getInt("money"));
        dedeArchives.setTitle(rs.getString("title"));
        dedeArchives.setShorttitle(rs.getString("shorttitle"));
        dedeArchives.setColor(rs.getString("color"));
        dedeArchives.setWriter(rs.getString("writer"));
        dedeArchives.setSource(rs.getString("source"));
        dedeArchives.setLitpic(rs.getString("litpic"));
        dedeArchives.setPubdate(rs.getInt("pubdate"));
        dedeArchives.setSenddate(rs.getInt("senddate"));
        dedeArchives.setMid(rs.getObject("mid"));
        dedeArchives.setKeywords(rs.getString("keywords"));
        dedeArchives.setLastpost(rs.getInt("lastpost"));
        dedeArchives.setScores(rs.getObject("scores"));
        dedeArchives.setGoodpost(rs.getObject("goodpost"));
        dedeArchives.setBadpost(rs.getObject("badpost"));
        dedeArchives.setNotpost(rs.getBoolean("notpost"));
        dedeArchives.setDescription(rs.getString("description"));
        dedeArchives.setFilename(rs.getString("filename"));
        dedeArchives.setDutyadmin(rs.getObject("dutyadmin"));
        dedeArchives.setTackid(rs.getInt("tackid"));
        dedeArchives.setMtype(rs.getObject("mtype"));
        dedeArchives.setVoteid(rs.getInt("voteid"));
        dedeArchives.setWeight(rs.getInt("weight"));
        return dedeArchives;
    }

    /**
     * @param conn
     * @param aid
     * @return
     * @throws JoymeDBException
     */
    public DedeAddonspec queryDedeAddonspecbyId(Connection conn, int aid) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM dede_addonspec WHERE aid = ?";
            List objectList = new ArrayList();
            objectList.add(aid);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                DedeAddonspec dedeAddonspec = getDedeAddonspec(rs);
                return dedeAddonspec;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    private DedeAddonspec getDedeAddonspec(ResultSet rs) throws SQLException {
        DedeAddonspec dedeAddonspec = new DedeAddonspec();
        dedeAddonspec.setAid(rs.getInt("aid"));
        dedeAddonspec.setTypeid(rs.getInt("typeid"));
        dedeAddonspec.setNote(rs.getString("note"));
        dedeAddonspec.setTemplet(rs.getString("templet"));
        dedeAddonspec.setUserip(rs.getString("userip"));
        dedeAddonspec.setRedirecturl(rs.getString("redirecturl"));
        dedeAddonspec.setBgpicture(rs.getString("bgpicture"));
        dedeAddonspec.setBgpicture1(rs.getString("bgpicture1"));
        dedeAddonspec.setZhuanti1(rs.getObject("zhuanti1"));
        dedeAddonspec.setZhuanti2(rs.getObject("zhuanti2"));
        dedeAddonspec.setZhuanti3(rs.getObject("zhuanti3"));
        dedeAddonspec.setZhuanti4(rs.getObject("zhuanti4"));
        dedeAddonspec.setZhuanti5(rs.getObject("zhuanti5"));
        dedeAddonspec.setZhuanti6(rs.getObject("zhuanti6"));
        dedeAddonspec.setZhuanti7(rs.getObject("zhuanti7"));
        dedeAddonspec.setZhuanti8(rs.getObject("zhuanti8"));
        dedeAddonspec.setZhuanti9(rs.getObject("zhuanti9"));
        dedeAddonspec.setZhuanti10(rs.getObject("zhuanti10"));
        dedeAddonspec.setZhuanti11(rs.getObject("zhuanti11"));
        dedeAddonspec.setZhuanti12(rs.getObject("zhuanti12"));
        return dedeAddonspec;
    }

    /**
     * date 2013-08-01 12:04:59
     * 
     * @author shenqiv0.1
     * @param arcrank
     * @param list1
     *            <Object <int , String , long..>>
     * @return List <DedeArchives>
     */
    public List queryDedeArchivesByTypeid(Connection conn, Integer arcrank, List list1, int maxId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM dede_archives WHERE arcrank = ? AND typeid IN ($list1) AND id > ? ORDER BY id LIMIT 0 , 200";
            sql = sql.replace("$list1", this.makeInStr(list1));
            List objectList = new ArrayList();
            objectList.add(arcrank);
            objectList.addAll(list1);
            objectList.add(maxId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                DedeArchives dedeArchives = this.getDedeArchives(rs);
                retList.add(dedeArchives);
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    /**
     * 获得当前最大的seq_id
     * 
     * @param conn
     * @return
     * @throws JoymeDBException
     */
    public int getMaxSeqId(Connection conn, String filePath) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT MAX(seq_id) as count FROM joyme_point_archive WHERE is_active = 1 AND spec_file_path = ?";
            dbean = this.executeBindingQuery(conn, sql, new Object[] { filePath });
            ResultSet rs = dbean.getRs();
            if (rs.next())
                return rs.getInt(1);
            return 0;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    /**
     * 通过aid获得dede_archive的对象
     * 
     * @param conn
     * @param id
     * @return
     * @throws JoymeDBException
     */
    public DedeArchives queryDedeArchivesbyId(Connection conn, int aid) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM dede_archives WHERE id = ?";
            List objectList = new ArrayList();
            objectList.add(aid);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                return this.getDedeArchives(rs);
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }
}
