package com.enjoyf.wiki.dao;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.wiki.bean.CardOpinion;
import com.enjoyf.wiki.bean.PageBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-6-18
 * Time: 下午2:19
 * To change this template use File | Settings | File Templates.
 */
public class CardOpinionDao extends BaseJDBCDAOImpl {
    private static String TABLE_NAME = "wiki_card_opinion";

    public CardOpinion queryCardOpinionbyId(Connection conn, Integer id) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM " + TABLE_NAME + "  WHERE opinion_id = ? ";
            List objectList = new ArrayList();
            objectList.add(id);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                CardOpinion joymeItem = getCardOpinion(rs);
                return joymeItem;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public int insertCardOpinion(Connection conn, CardOpinion bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO " + TABLE_NAME + "(wiki_source,wiki,title,opinion_key,opinion_value,nick_name,contacts,createtime) VALUES (?,?,?,?,?,?,?,?)";
            Object[] objects = new Object[]{bean.getWikiSource(), bean.getWiki(), bean.getTitle(),bean.getOpinionKey(), bean.getOpinionValue(), bean.getNickName(), bean.getContacts(), bean.getCreatetime()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public PageBean queryCardOpinionByPage(Connection conn, int pageNum, String wiki, Integer remove_status) throws JoymeDBException {
        int pageCount = 40;
        DataBean dbean = null;
        try {
            StringBuffer sql = new StringBuffer("SELECT * FROM " + TABLE_NAME + " WHERE 1=1 ");
            List objectList = new ArrayList();
            if (wiki != null && !wiki.equals("")) {
                sql.append(" AND wiki = ?");
                objectList.add(wiki);
            }
            if (remove_status != null) {
                sql.append(" AND remove_state = ?");
                objectList.add(remove_status);
            }
            sql.append(" order by opinion_id desc");
            dbean = this.executeBindingQueryWithPage(conn, sql.toString(), objectList.toArray(), pageNum, pageCount, true);
            ResultSet rs = dbean.getRs();
            List retList = new ArrayList();
            int a = 0;
            boolean hasNextPage = false;

            while (rs.next()) {
                CardOpinion cardOpinion = getCardOpinion(rs);
                retList.add(cardOpinion);
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

    /**
     * date 2013-09-17 12:32:20
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateJoymeItem(Connection conn, CardOpinion cardOpinion) throws JoymeDBException {
        try {
            String sql = "UPDATE " + TABLE_NAME + " SET $updateStr  WHERE opinion_id = ?";
            List objectList = new ArrayList();
            List columnList = cardOpinion.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(cardOpinion.getOpinionId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    private CardOpinion getCardOpinion(ResultSet rs) throws SQLException {
        CardOpinion cp = new CardOpinion();
        cp.setOpinionId(rs.getLong("opinion_id"));
        cp.setWikiSource(rs.getString("wiki_source"));
        cp.setWiki(rs.getString("wiki"));
        cp.setTitle(rs.getString("title"));
        cp.setOpinionKey(rs.getString("opinion_key"));
        cp.setOpinionValue(rs.getString("opinion_value"));
        cp.setNickName(rs.getString("nick_name"));
        cp.setContacts(rs.getString("contacts"));
        cp.setCreatetime(rs.getTimestamp("createtime"));
        cp.setRemoveState(rs.getInt("remove_state"));
        return cp;
    }
}
