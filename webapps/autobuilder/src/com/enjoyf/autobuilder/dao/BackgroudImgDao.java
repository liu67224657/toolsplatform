package com.enjoyf.autobuilder.dao;

import com.enjoyf.autobuilder.bean.BatchClientImage;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BackgroudImgDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-12-16 14:27:37
     *
     * @param id
     * @return BackgroudImg
     * @author shenqiv0.1
     */
    public BatchClientImage queryBackgroudImgbyId(Connection conn, Integer id) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM backgroud_img  WHERE id = ?";
            List objectList = new ArrayList();
            objectList.add(id);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
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
     * date 2013-12-16 14:27:37
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertBackgroudImg(Connection conn, BatchClientImage bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO backgroud_img(pic,platform,pic_b,pic_m,pic_s,pic_xb,pic_type) VALUES (?,?,?,?,?,?,?)";
            Object[] objects = new Object[]{bean.getPic(), bean.getPlatform(), bean.getPicB(), bean.getPicM(), bean.getPicS(),bean.getPicXB(),bean.getPicType()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-12-16 14:27:37
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateBackgroudImg(Connection conn, BatchClientImage bean) throws JoymeDBException {
        try {
            String sql = "UPDATE backgroud_img SET $updateStr  WHERE id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2013-12-16 14:27:37
     *
     * @param id
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteBackgroudImg(Connection conn, Integer id) throws JoymeDBException {
        try {
            String sql = "DELETE FROM backgroud_img  WHERE id = ?";
            List objectList = new ArrayList();
            objectList.add(id);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public List queryBackgroudImg(Connection conn, Integer platform,Integer picType) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM backgroud_img WHERE platform=? AND pic_type=?";
            List objectList = new ArrayList();
            objectList.add(platform);
            objectList.add(picType);
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

    private BatchClientImage rsToObject(ResultSet rs) throws SQLException {
        BatchClientImage backgroudImg = new BatchClientImage();
        backgroudImg.setId(rs.getInt("id"));
        backgroudImg.setPic(rs.getString("pic"));
        backgroudImg.setPicB(rs.getString("pic_b"));
        backgroudImg.setPicM(rs.getString("pic_m"));
        backgroudImg.setPicS(rs.getString("pic_s"));
        backgroudImg.setPlatform(rs.getInt("platform"));
        backgroudImg.setPicXB(rs.getString("pic_xb"));
        backgroudImg.setPicType(rs.getInt("pic_type"));
        return backgroudImg;
    }

}