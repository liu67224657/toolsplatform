package com.enjoyf.mcms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.mcms.bean.JoymeImage;

public class JoymeImageDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-12-12 11:50:44
     *
     * @param imageId
     * @return JoymeImage
     * @author shenqiv0.1
     */
    public JoymeImage queryJoymeImagebyId(Connection conn, Integer imageId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_image  WHERE image_id = ?";
            List objectList = new ArrayList();
            objectList.add(imageId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                return queryJoymeImage(rs);
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }


    /**
     * date 2013-12-12 11:50:44
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertJoymeImage(Connection conn, JoymeImage bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_image(specid,title,pic,link,displayorder,redirect_type,image_type) VALUES (?,?,?,?,?,?,?)";
            Object[] objects = new Object[]{bean.getSpecid(), bean.getTitle(), bean.getPic(), bean.getLink(), bean.getDisplayorder(), bean.getRedirectType(), bean.getImageType()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-12-12 11:50:44
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateJoymeImage(Connection conn, JoymeImage bean) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_image SET $updateStr  WHERE image_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getImageId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-12-12 11:50:44
     *
     * @param imageId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteJoymeImage(Connection conn, Integer imageId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_image  WHERE image_id = ?";
            List objectList = new ArrayList();
            objectList.add(imageId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-12-12 12:26:07
     *
     * @param specid
     * @param imageType
     * @return List <JoymeImage>
     * @author shenqiv0.1
     */
    public List queryJoymeImage(Connection conn, Integer specid, Integer imageType) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_image  where specid = ? and image_type=? ORDER BY displayorder ASC";
            List objectList = new ArrayList();
            objectList.add(specid);
            objectList.add(imageType);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {

                retList.add(queryJoymeImage(rs));
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    private JoymeImage queryJoymeImage(ResultSet rs) throws SQLException {
        JoymeImage joymeImage = new JoymeImage();
        joymeImage.setImageId(rs.getInt("image_id"));
        joymeImage.setSpecid(rs.getInt("specid"));
        joymeImage.setTitle(rs.getString("title"));
        joymeImage.setPic(rs.getString("pic"));
        joymeImage.setLink(rs.getString("link"));
        joymeImage.setDisplayorder(rs.getInt("displayorder"));
        joymeImage.setRedirectType(rs.getInt("redirect_type"));
        joymeImage.setImageType(rs.getInt("image_type"));
        return joymeImage;
    }

}