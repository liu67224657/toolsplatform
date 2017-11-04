package com.enjoyf.mcms.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.mcms.bean.DedeAddonrelevance;
import com.enjoyf.mcms.bean.DedeAddonspec;
import com.enjoyf.mcms.bean.DedeArchives;
import com.enjoyf.mcms.bean.temp.DedeArchivePageBean;
import com.enjoyf.util.StringUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DedeArchivesDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-07-30 15:50:20
     *
     * @param typeid
     * @param filename
     * @return List <DedeArchives>
     * @author shenqiv0.1
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
        dedeArchives.setClientpic(rs.getString("clientpic"));
        dedeArchives.setClientnote(rs.getString("clientnote"));
        dedeArchives.setCategoryid(rs.getInt("categoryid"));
        dedeArchives.setClientpic(rs.getString("clientpic"));
        dedeArchives.setShowios(rs.getInt("showios"));
        dedeArchives.setShowandroid(rs.getInt("showandroid"));
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
     * @param arcrank
     * @param list1   <Object <int , String , long..>>
     * @return List <DedeArchives>
     * @author shenqiv0.1
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
            dbean = this.executeBindingQuery(conn, sql, new Object[]{filePath});
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
     * @param aid
     * @return
     * @throws JoymeDBException
     */
    public DedeArchives queryDedeArchivesbyId(Connection conn, int aid) throws JoymeDBException {
        DataBean dbean = null;
        try {
            DedeArchives dedeArchives = null;
            String sql = "SELECT * FROM dede_archives where id = ?";
            List objectList = new ArrayList();
            objectList.add(aid);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                dedeArchives = this.getDedeArchives(rs);
            }

            if (dedeArchives != null) {
                // 查typeName typeColor
                dedeArchives = this.getDedeCategory(dedeArchives, conn, dbean, rs);

                //查相关新闻
                dedeArchives = this.getDedeAddonrelevance(dedeArchives, conn, dbean, rs);
            }
            return dedeArchives;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    //查相关新闻
    private DedeArchives getDedeAddonrelevance(DedeArchives dedeArchives, Connection conn, DataBean dbean, ResultSet rs) throws JoymeDBException {
        String sqlDedeAddonrelevance = "SELECT * FROM dede_addonrelevance WHERE status=1 and aid = ?";
        List objectListDedeAddonrelevance = new ArrayList();
        try {
            objectListDedeAddonrelevance.add(dedeArchives.getId());
            dbean = this.executeBindingQuery(conn, sqlDedeAddonrelevance, objectListDedeAddonrelevance.toArray());
            rs = dbean.getRs();
            List<DedeAddonrelevance> listDedeAddonrelevance = new ArrayList<DedeAddonrelevance>();
            while (rs.next()) {


                DedeAddonrelevance dedeAddonrelevance = this.getDedeAddonrelevance(conn, rs);
                listDedeAddonrelevance.add(dedeAddonrelevance);
            }
            dedeArchives.setRelatedNewsList(listDedeAddonrelevance);


        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
        return dedeArchives;
    }

    // 查typeName typeColor
    private DedeArchives getDedeCategory(DedeArchives dedeArchives, Connection conn, DataBean dbean, ResultSet rs) throws JoymeDBException {
        String sqlCategory = "SELECT * FROM dede_category WHERE id = ?";
        List objectListCategory = new ArrayList();
        try {
            objectListCategory.add(dedeArchives.getCategoryid());
            dbean = this.executeBindingQuery(conn, sqlCategory, objectListCategory.toArray());
            rs = dbean.getRs();
            while (rs.next()) {
                dedeArchives.setTypeName(rs.getString("typeName"));
                dedeArchives.setTypeColor(rs.getString("typeColor"));
            }
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
        return dedeArchives;
    }

    private DedeAddonrelevance getDedeAddonrelevance(Connection conn, ResultSet rs) throws SQLException {
        DedeAddonrelevance dedeAddonrelevance = new DedeAddonrelevance();
        dedeAddonrelevance.setAid(rs.getInt("aid"));
        dedeAddonrelevance.setTitle(rs.getString("title"));
        dedeAddonrelevance.setUrl(rs.getString("url"));
        dedeAddonrelevance.setType(rs.getInt("type"));
        dedeAddonrelevance.setStatus(rs.getInt("status"));

        try {

            DedeArchives dedeArchives = getDedeArchivesbyId(conn, DedeArchives.getArchiveId(rs.getString("url")));
            if (dedeArchives != null) {
                dedeAddonrelevance.setDescription(StringUtil.isEmpty(dedeArchives.getDescription()) ? "" : dedeArchives.getDescription());
                String clientPic = dedeArchives.getClientpic();
                //如果是完整地址则不替换
                if (StringUtil.isEmpty(clientPic) || !clientPic.startsWith("http://")) {
                    clientPic = StringUtil.isEmpty(clientPic) ? "http://marticle.joyme.com/img/default_client_pic.png" : "http://article.joyme.com" + clientPic;
                }
                dedeAddonrelevance.setClientpic(clientPic);
            }
        } catch (JoymeDBException e) {
            e.printStackTrace();
        }
        return dedeAddonrelevance;
    }

    public DedeArchivePageBean queryDedeArchiveListByTypeId(Connection conn, int typeid, int pageNum) throws JoymeDBException {
        DataBean dbean = null;
        DedeArchivePageBean retBean = new DedeArchivePageBean();
        try {
            String sql = "SELECT * FROM dede_archives WHERE typeid = ?";
            List objectList = new ArrayList();
            objectList.add(typeid);
            int pageCount = 10;
            dbean = this.executeBindingQueryWithPage(conn, sql, objectList.toArray(), pageNum, pageCount, true);
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            int a = 0;
            while (rs.next()) {
                if (a < pageCount) {
                    retList.add(getDedeArchives(rs));
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

    public List queryTagArchives(Connection conn, List tagNameList, List typeIdList, int maxId, int pageNum) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT t1.* FROM dede_taglist t0 INNER JOIN dede_archives t1 ON t0.aid = t1.id WHERE t0.tag IN ($list1) AND t1.typeid IN ($list2) AND t1.id > ? LIMIT 0 , ?";
            sql = sql.replace("$list1", this.makeInStr(tagNameList));
            sql = sql.replace("$list2", this.makeInStr(typeIdList));
            List objectList = new ArrayList();
            objectList.addAll(tagNameList);
            objectList.addAll(typeIdList);
            objectList.add(maxId);
            objectList.add(pageNum);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                DedeArchives archives = this.getDedeArchives(rs);
                retList.add(archives);
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public DedeArchivePageBean queryDedeArchiveListByCategoryId(Connection conn, int platform, int categoryId, List cmsIdList, int pageNum, int pageCount) throws JoymeDBException {
        DataBean dbean = null;
        DedeArchivePageBean retBean = new DedeArchivePageBean();
        try {
            String sql = "";
            List objectList = new ArrayList();
            if (cmsIdList.size() == 0) {
                sql = "SELECT * FROM dede_archives WHERE categoryid = ? and typeid= ? and arcrank!=-2 order by pubdate desc ";
                objectList.add(categoryId);
                objectList.add(platform);
            } else {
                sql = "SELECT * FROM dede_archives WHERE categoryid = ? and typeid= ? and arcrank!=-2 and id not in($list1) order by pubdate desc ";
                objectList.add(categoryId);
                objectList.add(platform);
                sql = sql.replace("$list1", this.makeInStr(cmsIdList));
                objectList.addAll(cmsIdList);
            }
            dbean = this.executeBindingQueryWithPage(conn, sql, objectList.toArray(), pageNum, pageCount, true);
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            int a = 0;
            while (rs.next()) {
                if (a < pageCount) {
                    retList.add(getDedeArchives(rs));
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

    public int updateDedearchivesPubdate(Connection conn, DedeArchives dedeArchives) throws JoymeDBException {
        try {
            String sql = "UPDATE dede_archives SET $updateStr  WHERE id = ?";
            List objectList = new ArrayList();
            List columnList = dedeArchives.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(dedeArchives.getId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    public DedeArchives getDedeArchivesbyId(Connection conn, int aid) throws JoymeDBException {
        DataBean dbean = null;
        try {
            DedeArchives dedeArchives = null;
            String sql = "SELECT * FROM dede_archives where id = ?";
            List objectList = new ArrayList();
            objectList.add(aid);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                dedeArchives = this.getDedeArchives(rs);
            }
            return dedeArchives;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }
}
