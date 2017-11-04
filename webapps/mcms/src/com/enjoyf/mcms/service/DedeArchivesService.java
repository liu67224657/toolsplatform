package com.enjoyf.mcms.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.mcms.bean.DedeAddonspec;
import com.enjoyf.mcms.bean.DedeArchives;
import com.enjoyf.mcms.bean.DedePoint;
import com.enjoyf.mcms.dao.DedeArchivesDao;

public class DedeArchivesService {

    private static DedeArchivesDao subDao = new DedeArchivesDao();
    private final static String SEPEARTOR = "'";

    /**
     * date 2013-07-31 16:18:52
     * 
     * @author shenqiv0.1
     * @param filename
     * @return List <dedeArchives>
     */
    public List queryDedeArchivesByFileName(Connection conn, Integer typeid, String filename) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryDedeArchivesByFileName(conn, typeid, filename);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    /**
     * 获得专题的详细内容
     * 
     * @param conn
     * @param aid
     * @return
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public DedeAddonspec queryDedeAddonspecbyId(Connection conn, int aid) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryDedeAddonspecbyId(conn, aid);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    /**
     * 获得一个专区的节点
     * 
     * @return 第一个是aid ,第二个是pointList
     * @throws JoymeServiceException
     * @throws JoymeDBException
     */
    public Object[] getArchivesPointList(Connection conn, String filename) throws JoymeDBException, JoymeServiceException {
        List archiveList = this.queryDedeArchivesByFileName(conn, 0, filename);
        DedeArchives archive = null;
        if (archiveList != null && archiveList.size() >= 1) {
            archive = (DedeArchives) archiveList.get(0);
        }
        
        if(archive == null){
            System.out.println("Find archiveList is null which filename is : " + filename);
            return null;
        }

        int aid = archive.getId();
        DedeAddonspec spec = this.queryDedeAddonspecbyId(conn, aid);
        String note = spec.getNote();

        String[] points = note.split("\\{dede:specnote");
        List list = new ArrayList();
        for (int i = 0; i < points.length; i++) {
            String point = points[i];
            int position = point.indexOf("}");
            if (position >= 0) {
                point = point.substring(0, position);
                String name = getValueFromPoint(point, "name");
                String typeid = getValueFromPoint(point, "typeid");
                String keywords = getValueFromPoint(point, "keywords");
                String rownum = getValueFromPoint(point, "rownum");
                DedePoint dedePoint = new DedePoint();
                dedePoint.setName(name);
                dedePoint.setTypeid(typeid);
                dedePoint.setKeywords(keywords);
                dedePoint.setRownum(rownum);
                list.add(dedePoint);
            }
        }

        Object[] objects = new Object[] { aid, list };
        return objects;
    }

    /**
     * 从节点中获得信息。
     * 
     * @param point
     * @param name
     * @return
     */
    private String getValueFromPoint(String point, String name) {
        String key = name + "=" + SEPEARTOR;
        int position = point.indexOf(key);
        String temp = null;
        if (position > 0) {
            temp = point.substring(position + key.length(), point.length());
            int position1 = temp.indexOf("'");
            if (position1 > 0) {
                temp = temp.substring(0, position1);
            }
        }
        return temp;
    }

    /**
     * 通过栏目获得
     * 
     * @param conn
     * @param arcrank
     * @param list1
     * @return
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public List queryDedeArchivesByTypeid(Connection conn, Integer arcrank, List list1, int maxId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryDedeArchivesByTypeid(conn, arcrank, list1, maxId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    /**
     * 获得active = 1最大的seq_id
     * 
     * @param conn
     * @return
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public int getMaxSeqId(Connection conn, String filePath) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.getMaxSeqId(conn, filePath);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    /**
     * 通过aid获得dede_archive的对象
     * 
     * @param conn
     * @param id
     * @return
     * @throws Exception 
     * @throws JoymeDBException
     */
    public DedeArchives queryDedeArchivesbyId(Connection conn, int aid) throws Exception {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryDedeArchivesbyId(conn, aid);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }
}
