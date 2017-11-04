package com.enjoyf.activity.service;

import com.enjoyf.activity.bean.ActivityCountry;
import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.activity.dao.ActivityCountryDao;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.StringUtil;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zhimingli on 2016/8/1 0001.
 */
public class ActivityCountryService {
    private static ActivityCountryDao activityCountryDao = new ActivityCountryDao();


    public ActivityCountry getActivityCountry(Connection conn, String  profileid, String activityCode) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = activityCountryDao.getConnection();

            return activityCountryDao.getActivityCountry(conn, profileid,activityCode);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                activityCountryDao.closeConnection(conn);
        }
    }


    public int insertActivityCountry(Connection conn, ActivityCountry bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = activityCountryDao.getConnection();

            return activityCountryDao.insertActivityCountry(conn, bean);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                activityCountryDao.closeConnection(conn);
        }

    }


    public int updateActivityCountry(Connection conn, ActivityCountry bean, String profileid,String activityCode) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = activityCountryDao.getConnection();

            return activityCountryDao.updateActivityCountry(conn, bean, profileid,activityCode);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                activityCountryDao.closeConnection(conn);
        }

    }


    public int deleteActivityCountry(Connection conn, int id) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = activityCountryDao.getConnection();
            return activityCountryDao.deleteActivityCountry(conn, id);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                activityCountryDao.closeConnection(conn);
        }

    }

    public List queryActivityCountry(Connection conn, int id) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = activityCountryDao.getConnection();
            return activityCountryDao.queryActivityCountry(conn, id);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                activityCountryDao.closeConnection(conn);
        }
    }

    /**
     * 判断是否达到增加的次数
     * @param userId
     * @return
     */
    public boolean checkAddScoreTime(String userId)  {
        boolean addFlag = true;
            long  currentTimeMillis = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH) + 1);
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            long survivalTime = (calendar.getTime().getTime() - currentTimeMillis)/1000;
            String strTime = PropertiesContainer.getRedisManager().get("activity-" + userId + "-Times");
           if (!StringUtil.isEmpty(strTime)){
               int time = Integer.parseInt(strTime);
               if (time == 3){
                   addFlag = false;
               }else {
                   PropertiesContainer.getRedisManager().incr("activity-" + userId + "-Times",1L,Integer.parseInt(String.valueOf(survivalTime)));
               }
           }else{
               PropertiesContainer.getRedisManager().incr("activity-" + userId + "-Times",1L,Integer.parseInt(String.valueOf(survivalTime)));
           }
        return addFlag;
    }
public List queryAllActivityCountry(Connection conn) throws JoymeDBException, JoymeServiceException  {
    boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = activityCountryDao.getConnection();
            return activityCountryDao.queryAllActivityCountry(conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                activityCountryDao.closeConnection(conn);
        }
    }

    public List<ActivityCountry> findTopHundredActivityCountryByActivityCode(Connection conn, String activityCode)throws JoymeDBException, JoymeServiceException  {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = activityCountryDao.getConnection();
            return activityCountryDao.findTopHundredActivityCountryByActivityCode(conn, activityCode);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                activityCountryDao.closeConnection(conn);
        }
    }

    public Long findCountBigThanScore(Connection conn,Date create_date, String activityCode,int score) throws JoymeDBException, JoymeServiceException{
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = activityCountryDao.getConnection();
            return activityCountryDao.findCountBigThanScore(conn,create_date, activityCode,score);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                activityCountryDao.closeConnection(conn);
        }
    }

    public List<ActivityCountry> findRanking(Connection conn, String activityCode, int topNum) throws JoymeServiceException, JoymeDBException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = activityCountryDao.getConnection();
            return activityCountryDao.findRanking(conn, activityCode,topNum);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                activityCountryDao.closeConnection(conn);
        }
    }
}
