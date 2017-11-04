package com.enjoyf.activity.facade;

import com.enjoyf.activity.bean.point.PointLog;
import com.enjoyf.activity.cache.PointRedis;
import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.activity.service.PointLogService;
import com.enjoyf.activity.util.PointUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
public class PointFacade {
    //    private PointService pointService = new PointService();
    private PointLogService pointLogService = new PointLogService();
    private PointRedis pointRedis = new PointRedis(PropertiesContainer.getRedisManager());

    /**
     * save
     *
     * @throws JoymeServiceException
     * @throws JoymeDBException
     */
    public boolean increasePoint(PointLog pointLog) throws JoymeServiceException, JoymeDBException {
        pointLogService.insertPointLog(null, pointLog);

        //add cache
        String pointId = PointUtil.getPointId(pointLog.getProfileid(), pointLog.getGamecode());
        if (pointRedis.existsPoint(pointId)) {
            pointRedis.increasePoint(pointLog.getPoint(), pointId);
        } else {
            int pointValue = pointLogService.querySumPointLogbyId(null, pointLog.getProfileid(), pointLog.getGamecode());
            pointRedis.increasePoint(pointValue, pointId);
        }
        return true;
    }

    public int getPointValueByProfileId(String profileId, String gameCode) throws JoymeServiceException, JoymeDBException {
        String pointId = PointUtil.getPointId(profileId, gameCode);
        int pointValue = pointRedis.getPoint(pointId);
        if (pointValue < 0) {
            pointValue = pointLogService.querySumPointLogbyId(null, profileId, gameCode);
            pointRedis.increasePoint(pointValue, pointId);
        }

        return pointValue;
    }

}
