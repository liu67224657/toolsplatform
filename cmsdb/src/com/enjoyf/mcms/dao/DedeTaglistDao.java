package com.enjoyf.mcms.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.bean.PageQueryBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.mcms.bean.DedeTagArchive;
import com.enjoyf.mcms.bean.DedeTaglist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DedeTaglistDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-11-13 18:10:00
     *
     * @return List <DedeTaglist>
     * @author shenqiv0.1
     */
    public PageQueryBean queryDedeTaglist(Connection conn, int tid, int pageNum, String plat) throws JoymeDBException {
        DataBean dbean = null;
        PageQueryBean retBean = new PageQueryBean();
        try {
            plat = getPlat(plat);
            String sql = "SELECT aid FROM dede_taglist WHERE tid = ? AND arcrank>-1 and "+plat+"=1 ORDER BY aid DESC";
            List objectList = new ArrayList();
            objectList.add(tid);
            int pageCount = 10;
            dbean = this.executeBindingQueryWithPage(conn, sql, objectList.toArray(), pageNum, pageCount, true);
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            int a = 0;
            while (rs.next()) {
                if (a < pageCount) {
                    retList.add(rs.getInt("aid"));
                } else {
                    retBean.setHasNextPage(true);
                }
                a++;
            }
            retBean.setResultList(retList);
            return retBean;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public List queryTagArchiveList(Connection conn, List list1) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT se.*,tp.typedir,tp.typename,tp.isdefault,tp.defaultname,tp.namerule,tp.namerule2,tp.ispart,tp.moresite,tp.siteurl,tp.sitepath";
            sql += " FROM dede_archives se LEFT JOIN dede_arctype tp ON se.typeid=tp.id WHERE  se.id IN($list1) ORDER BY se.sortrank DESC";

            sql = sql.replace("$list1", this.makeInStr(list1));
            List objectList = new ArrayList();
            objectList.addAll(list1);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                DedeTagArchive archive = new DedeTagArchive();
                archive.setId(rs.getInt("id"));
                archive.setChannel(rs.getInt("channel"));
                archive.setDescription(rs.getString("description"));
                archive.setLitpic(rs.getString("litpic"));
                archive.setNamerule(rs.getString("namerule"));
                archive.setPublishdate(rs.getLong("pubdate"));
                archive.setTitle(rs.getString("title"));
                archive.setTypedir(rs.getString("typedir"));
                archive.setWriter(rs.getString("writer"));
                retList.add(archive);
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public String queryDedeTagName(Connection conn, Integer id) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT tag FROM dede_tagindex WHERE id = ?";
            List objectList = new ArrayList();
            objectList.add(id);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            if (rs.next()) {
                return rs.getString("tag");
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public List queryDedeTagByAid(Connection conn, Integer aid) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM dede_taglist WHERE aid = ?";
            List objectList = new ArrayList();
            objectList.add(aid);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            List retList = new ArrayList();
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

    private DedeTaglist rsToObject(ResultSet rs) throws SQLException {
        DedeTaglist dedeTaglist = new DedeTaglist();
        dedeTaglist.setTid(rs.getInt("tid"));
        dedeTaglist.setAid(rs.getInt("aid"));
        dedeTaglist.setArcrank(rs.getInt("arcrank"));
        dedeTaglist.setTag(rs.getString("tag"));
        dedeTaglist.setTypeid(rs.getInt("typeid"));
        return dedeTaglist;
    }

    private String getPlat(String plat) {
        String returnColumu = "showandroid";
        if (plat.equals("android")) {
            returnColumu = "showandroid";
        }else if(plat.equals("iphone")){
            returnColumu = "showios";
        }else if(plat.equals("ipad")){
            returnColumu = "showios";
        }
        return returnColumu;
    }

}