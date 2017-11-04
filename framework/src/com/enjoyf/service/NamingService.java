package com.enjoyf.service;

import com.enjoyf.framework.log.LogService;
import com.enjoyf.framework.redis.RedisManager;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.List;
import java.util.Set;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 15/8/3
 * Description:
 */
public class NamingService {
    private static NamingService instance;

    private static final String REDISKEY_NAMING_CONTAINER = "tp_naming_ct_";

    private static final String REDISKEY_SERV_QUEUE = "tp_serv_queue_";
    private static final String REDISKEY_SERV_QUEUE_BLOCK = "tp_queue_block_";

    private RedisManager redisManager;

    private NamingService(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public static NamingService getInstance(RedisManager redisManager) {
        if (instance == null) {
            synchronized (NamingService.class) {
                if (instance == null) {
                    instance = new NamingService(redisManager);
                }
            }
        }
        return instance;
    }

    /**
     * 在服务启动时候调用，将服务上报到中心
     *
     * @param service
     */
    public void register(Service service) {
        redisManager.sadd(REDISKEY_NAMING_CONTAINER + service.getServiceType(), service.getServiceId());
    }

    /**
     * 更具服务类型获取服务ID
     *
     * @param serviceType
     * @return
     */
    public Set<String> getServiceInfoList(String serviceType) {
        return redisManager.smembers(REDISKEY_NAMING_CONTAINER + serviceType);
    }

    /**
     * 更具服务类型发送消息
     *
     * @param serviceType
     * @param obj
     */
    public void pushAllServQueue(String serviceType, String obj) {
        for (String serviceId : getServiceInfoList(serviceType)) {
            pushServQueue(serviceId, obj);
        }
    }

    /**
     * 发送消息
     *
     * @param serviceId
     * @param obj
     */
    public void pushServQueue(String serviceId, String obj) {
        try {
            redisManager.lpush(REDISKEY_SERV_QUEUE + serviceId, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取消息
     *
     * @param service
     * @return
     */
    public List<String> blockpopServQueue(Service service) {
        return redisManager.brpop(0, REDISKEY_SERV_QUEUE + service.getServiceId());
    }
}
