package com.enjoyf.mcms.container;

import java.util.ArrayList;
import java.util.List;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class MemCachedContainer {
    // 是否打开memcached

    public static MemCachedClient mcc;

    // 设置与缓存服务器的连接池
    static {
        initMemcached();
    }

    public static void initMemcached() {
        // 服务器列表和其权重

        List serverList = getMemcachedConfigList();

        int size = serverList.size();

        String[] servers = new String[size];

        Integer[] weights = new Integer[size];


//        for (Entry<String, Integer> entry : serverEntries) {
//            servers[i] = entry.getKey();
//            weights[i++] = entry.getValue();
//        }
        
        for (int i = 0; i < serverList.size(); i++) {
            String[] temp = (String[])serverList.get(i);
            servers[i] = temp[0];
            weights[i] = Integer.parseInt(temp[1]);
        }
        
        // 获取socke连接池的实例对象
        SockIOPool pool = SockIOPool.getInstance();

        // 设置服务器信息

        pool.setServers(servers);

        pool.setWeights(weights);

        // 设置初始连接数、最小和最大连接数以及最大处理时间

        pool.setInitConn(5);

        pool.setMinConn(5);

        pool.setMaxConn(4000);

        pool.setMaxIdle(1000 * 60 * 60 * 6);

        // 设置主线程的睡眠时间

        pool.setMaintSleep(30);

        // 设置TCP的参数，连接超时等

        pool.setNagle(false);
        pool.setAliveCheck(false);
        pool.setFailover(false);

        pool.setSocketTO(3000);

        pool.setSocketConnectTO(0);

        pool.setHashingAlg(SockIOPool.NEW_COMPAT_HASH);

        // pool.setBufferSize(600000000);

        // 初始化连接池

        pool.initialize();

        // 压缩设置，超过指定大小（单位为K）的数据都会被压缩

        // mcc.setCompressEnable( true );
        // mcc.setCompressThreshold( 256 * 1024 );

        mcc = new MemCachedClient();
    }

    private static List getMemcachedConfigList() {
        List list = new ArrayList();
        try {
            String memcachedConfig = ConfigContainer.getConfigValue("memcached_server");
            String[] servers = memcachedConfig.split(",");

            for (int i = 0; i < servers.length; i++) {
                String[] temp = servers[i].split("_");
                list.add(temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

}
