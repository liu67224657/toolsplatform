package com.enjoyf.mcms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.mcms.bean.DedeAddonarticle;

public class DedeAddonarticleDao extends BaseJDBCDAOImpl {

   /**
    * date 2013-08-02 15:10:05
    * @author shenqiv0.1
    * @param aid
    * @return DedeAddonarticle 
    */
public DedeAddonarticle queryDedeAddonarticlebyId(Connection conn,  int aid) throws JoymeDBException{
  DataBean dbean = null;
  try {
      String sql = "SELECT * FROM dede_addonarticle  WHERE aid = ?";
      List objectList = new ArrayList();
      objectList.add(aid);
      dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
      ResultSet rs = dbean.getRs();
      while (rs.next()) {
           DedeAddonarticle dedeAddonarticle = new DedeAddonarticle();
           dedeAddonarticle.setAid(rs.getInt("aid"));
           dedeAddonarticle.setTypeid(rs.getInt("typeid"));
           dedeAddonarticle.setBody(rs.getString("body"));
           dedeAddonarticle.setRedirecturl(rs.getString("redirecturl"));
           dedeAddonarticle.setTemplet(rs.getString("templet"));
           dedeAddonarticle.setUserip(rs.getString("userip"));
           return dedeAddonarticle;
      }
    return null;
  } catch (Exception e) {
      throw new JoymeDBException(e);
  } finally {
      this.cleanup(dbean, false);
  }
}


   /**
    * date 2013-08-02 15:10:05
    * @author shenqiv0.1
    * @param bean
    * @return int 1 success, 0 failed
    */
public int insertDedeAddonarticle(Connection conn,  DedeAddonarticle bean)  throws JoymeDBException{
   try{
      String sql = "INSERT INTO dede_addonarticle(aid,typeid,body,redirecturl,templet,userip) VALUES (?,?,?,?,?,?)";
      Object[] objects = new Object[] {bean.getAid(),bean.getTypeid(),bean.getBody(),bean.getRedirecturl(),bean.getTemplet(),bean.getUserip()}; 
      return this.executeBindingUpdate(conn, sql, objects, false, false);
 }catch (Exception e) { 
      throw new JoymeDBException(e);
 }
}


   /**
    * date 2013-08-02 15:10:05
    * @author shenqiv0.1
    * @param bean
    * @param aid
    * @return int 1 success, 0 failed
    */
public int updateDedeAddonarticle(Connection conn,  DedeAddonarticle bean)  throws JoymeDBException{
  try{
        String sql = "UPDATE dede_addonarticle SET $updateStr  WHERE aid = ?";
        List objectList = new ArrayList();
        List columnList = bean.getNotNullColumnList();
        sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
      objectList.add(bean.getAid());
      return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
      }catch (Exception e) { 
        throw new JoymeDBException(e);
    }
   }


   /**
    * date 2013-08-02 15:10:05
    * @author shenqiv0.1
    * @param aid
    * @return int 1 success, 0 failed
    */
public int deleteDedeAddonarticle(Connection conn,  Object aid)  throws JoymeDBException{
   try{
      String sql = "DELETE FROM dede_addonarticle  WHERE aid = ?";
  List objectList = new ArrayList();
      objectList.add(aid);
      return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
 }catch (Exception e) { 
      throw new JoymeDBException(e);
 }
}


}