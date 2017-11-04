package com.enjoyf.wiki.service;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.wiki.bean.CardOpinion;
import com.enjoyf.wiki.bean.PageBean;
import com.enjoyf.wiki.dao.CardOpinionDao;

import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-6-18
 * Time: 下午2:48
 * To change this template use File | Settings | File Templates.
 */
public class CardOpinionService {
    private static CardOpinionDao dao = new CardOpinionDao();

    public CardOpinion queryCardOpinionbyId(Connection conn, Integer opinion_id) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = dao.getConnection();
            return dao.queryCardOpinionbyId(conn, opinion_id);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                dao.closeConnection(conn);
        }
    }

    public int insertCardOpinion(Connection conn, CardOpinion bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = dao.getConnection();
            return dao.insertCardOpinion(conn, bean);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                dao.closeConnection(conn);
        }
    }

    public PageBean queryCardOpinionByPage(Connection conn, int pageNum, String wiki,Integer remove_status) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = dao.getConnection();
            return dao.queryCardOpinionByPage(conn, pageNum, wiki,remove_status);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                dao.closeConnection(conn);
        }
    }

    public int updateJoymeItem(Connection conn, CardOpinion cardOpinion) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = dao.getConnection();
            return dao.updateJoymeItem(conn, cardOpinion);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                dao.closeConnection(conn);
        }
    }

}
