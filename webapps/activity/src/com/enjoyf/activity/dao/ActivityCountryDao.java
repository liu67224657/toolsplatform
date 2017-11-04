package com.enjoyf.activity.dao;

import com.enjoyf.activity.bean.ActivityCountry;
import com.enjoyf.activity.bean.ValidStatus;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhimingli on 2016/8/1 0001.
 */
public class ActivityCountryDao extends BaseJDBCDAOImpl {

    public ActivityCountry getActivityCountry(Connection conn, String profileid,String activityCode) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM activity_country  WHERE profileid = ? and activity_code = ? ";
            List objectList = new ArrayList();
            objectList.add(profileid);
            objectList.add(activityCode);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                return rsToObjet(rs);
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }


    private ActivityCountry rsToObjet(ResultSet rs) throws SQLException {
        ActivityCountry activityCountry = new ActivityCountry();
        activityCountry.setId(rs.getInt("id"));
        activityCountry.setProfileid(rs.getString("profileid"));
        activityCountry.setScore(rs.getInt("score"));
        activityCountry.setCountry(rs.getString("country"));
        activityCountry.setValid_status(ValidStatus.getByCode(rs.getString("valid_status")));
        activityCountry.setCreate_date(new Date(rs.getTimestamp("create_date").getTime()));
        activityCountry.setCreate_ip(rs.getString("create_ip"));
        activityCountry.setNickname(rs.getString("nickname"));
        activityCountry.setHeadimgurl(rs.getString("headimgurl"));
        activityCountry.setActivity_code(rs.getString("activity_code"));
        return activityCountry;

    }

    public int insertActivityCountry(Connection conn, ActivityCountry bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO activity_country(profileid,score,country,valid_status,create_date,create_ip,nickname,headimgurl,activity_code) VALUES (?,?,?,?,?,?,?,?,?)";
            Object[] objects = new Object[]{
                    bean.getProfileid(),
                    bean.getScore(),
                    bean.getCountry(),
                    bean.getValid_status().getCode(),
                    bean.getCreate_date(),
                    bean.getCreate_ip(),
                    bean.getNickname(),
                    bean.getHeadimgurl(),
                    bean.getActivity_code()
            };
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public int updateActivityCountry(Connection conn, ActivityCountry bean, String profileid,String activityCode) throws JoymeDBException {
        try {

            String sql = "UPDATE activity_country SET $updateStr  WHERE profileid = ? and activity_code = ?";

            List objectList = new ArrayList();

            List columnList = bean.getNotNullColumnList();

            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));

            objectList.add(profileid);
            objectList.add(activityCode);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);

        } catch (Exception e) {

            throw new JoymeDBException(e);

        }
    }

    public int deleteActivityCountry(Connection conn, int id) throws JoymeDBException {
        try {
            String sql = "DELETE FROM activity_country  WHERE id = ?";
            List objectList = new ArrayList();
            objectList.add(id);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public List queryActivityCountry(Connection conn, int id) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM activity_country where id=?";
            List objectList = new ArrayList();
            objectList.add(id);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                retList.add(rsToObjet(rs));
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public List queryAllActivityCountry(Connection conn) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM activity_country  order by score desc limit 0,5";
            dbean = this.executeBindingQuery(conn, sql, null);
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                retList.add(rsToObjet(rs));
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }


    public List<ActivityCountry> findTopHundredActivityCountryByActivityCode(Connection conn, String activityCode) throws JoymeDBException{
        DataBean dbean = null;
        try {
            String sql = "SELECT * from activity_country where activity_code = ? ORDER BY score desc,create_date asc limit 0,100";
            List objectList = new ArrayList();
            objectList.add(activityCode);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                retList.add(rsToObjet(rs));
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public Long findCountBigThanScore(Connection conn, Date create_date, String activityCode,int score)  throws JoymeDBException{

        DataBean dbean = null;
        try {
            String countSql = "SELECT count(*) from activity_country  where activity_code = ? and score >= ? and create_date <=?";
            List objectList = new ArrayList();
            objectList.add(activityCode);
            objectList.add(score);
            objectList.add(create_date);
            long count = 0;
            dbean = this.executeBindingQuery(conn, countSql, objectList.toArray());
            ResultSet countRs = dbean.getRs();
            if (countRs.next()) {
                count = countRs.getLong(1);
            }
            return count;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public List<ActivityCountry> findRanking(Connection conn, String activityCode,int topNum) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * from activity_country where activity_code = ? ORDER BY score desc,create_date asc limit 0,?";
            List objectList = new ArrayList();
            objectList.add(activityCode);
            objectList.add(topNum);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                retList.add(rsToObjet(rs));
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }
}
