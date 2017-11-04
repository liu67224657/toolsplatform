package com.enjoyf.activity.dao;

import com.enjoyf.activity.bean.zlmc.Userinfo;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserinfoDao extends BaseJDBCDAOImpl {

   /**
    * date 2016-09-20 10:19:26
    * @author shenqiv0.1
    * @param profileid
    * @return Userinfo 
    */
public Userinfo queryUserinfobyId(Connection conn, String profileid) throws JoymeDBException {
  DataBean dbean = null;
  try {
      String sql = "SELECT * FROM userinfo  WHERE profileid = ?";
      List objectList = new ArrayList();
      objectList.add(profileid);
      dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
      List retList = new ArrayList();
      ResultSet rs = dbean.getRs();
      while (rs.next()) {
           Userinfo userinfo = new Userinfo();
           userinfo.setProfileid(rs.getString("profileid"));
           userinfo.setUsername(rs.getString("username"));
           userinfo.setNickname(rs.getString("nickname"));
           userinfo.setTelephone(rs.getString("telephone"));
           userinfo.setAddress(rs.getString("address"));
          userinfo.setDuty(rs.getString("duty"));
          userinfo.setCompany(rs.getString("company"));
          userinfo.setRepresentativeWork(rs.getString("representative_work"));
           userinfo.setCreateTime(rs.getTimestamp("create_time"));
           return userinfo;
      }
    return null;
  } catch (Exception e) {
      throw new JoymeDBException(e);
  } finally {
      this.cleanup(dbean, false);
  }
}


   /**
    * date 2016-09-20 10:19:26
    * @author shenqiv0.1
    * @param bean
    * @return int 1 success, 0 failed
    */
public int insertUserinfo(Connection conn,  Userinfo bean)  throws JoymeDBException{
   try{
      String sql = "INSERT INTO userinfo(profileid,username,nickname,telephone,address,duty,company,representative_work,create_time) VALUES (?,?,?,?,?,?,?,?,?)";
      Object[] objects = new Object[] {bean.getProfileid(),bean.getUsername(),bean.getNickname(),bean.getTelephone(),bean.getAddress(),bean.getDuty(),bean.getCompany(),bean.getRepresentativeWork(),bean.getCreateTime()};
      return this.executeBindingUpdate(conn, sql, objects, false, false);
 }catch (Exception e) { 
      throw new JoymeDBException(e);
 }
}


   /**
    * date 2016-09-20 10:19:26
    * @author shenqiv0.1
    * @param bean
    * @param bean
    * @return int 1 success, 0 failed
    */
public int updateUserinfo(Connection conn,  Userinfo bean)  throws JoymeDBException{
  try{
        String sql = "UPDATE userinfo SET $updateStr  WHERE profileid = ?";
        List objectList = new ArrayList();
        List columnList = bean.getNotNullColumnList();
        sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
      objectList.add(bean.getProfileid());
      return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
      }catch (Exception e) { 
        throw new JoymeDBException(e);
    }
   }


   /**
    * date 2016-09-20 10:19:26
    * @author shenqiv0.1
    * @param profileid
    * @return int 1 success, 0 failed
    */
public int deleteUserinfo(Connection conn,  String profileid)  throws JoymeDBException{
   try{
      String sql = "DELETE FROM userinfo  WHERE profileid = ?";
  List objectList = new ArrayList();
      objectList.add(profileid);
      return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
 }catch (Exception e) { 
      throw new JoymeDBException(e);
 }
}


    public Userinfo queryUserinfobyUserInfo(Connection conn, String username) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM userinfo  WHERE username = ?";
            List objectList = new ArrayList();
            objectList.add(username);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                Userinfo userinfo = new Userinfo();
                userinfo.setProfileid(rs.getString("profileid"));
                userinfo.setUsername(rs.getString("username"));
                userinfo.setNickname(rs.getString("nickname"));
                userinfo.setTelephone(rs.getString("telephone"));
                userinfo.setAddress(rs.getString("address"));
                userinfo.setDuty(rs.getString("duty"));
                userinfo.setCompany(rs.getString("company"));
                userinfo.setRepresentativeWork(rs.getString("representative_work"));
                userinfo.setCreateTime(rs.getTimestamp("create_time"));
                return userinfo;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }
}