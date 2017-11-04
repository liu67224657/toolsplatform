package com.enjoyf.config;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-10-15
 * Time: 下午12:30
 * To change this template use File | Settings | File Templates.
 */
public class HotdeployConfigFactory {

    private static volatile HotdeployConfigFactory instance;

    private ConcurrentMap<String, AbstractHotdeployConfig> configs = new ConcurrentHashMap<String, AbstractHotdeployConfig>();

    private final static long HOTDEPLOY_TIMER_INTRAL=10l*1000l;

    public static HotdeployConfigFactory get() {
        if (instance == null) {
            synchronized (HotdeployConfigFactory.class) {
                if (instance == null) {
                    instance = new HotdeployConfigFactory();
                }
            }
        }

        return instance;
    }

    private HotdeployConfigFactory() {
        new Timer().scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        reload();
                    }
                },
                HOTDEPLOY_TIMER_INTRAL,
                HOTDEPLOY_TIMER_INTRAL
        );
    }

    @SuppressWarnings("unchecked")
   public <T extends AbstractHotdeployConfig> T getConfig(Class<T> clazz)  {
       if (!configs.containsKey(clazz.getName())) {
           try {
               T config = clazz.newInstance();

               configs.putIfAbsent(clazz.getName(), config);
           } catch (InstantiationException e) {
               throw new IllegalArgumentException("Class not supported: " + clazz);
           } catch (IllegalAccessException e) {
               throw new IllegalArgumentException("Class not supported: " + clazz);
           }
       }

       return (T) configs.get(clazz.getName());
   }


    public void reload(){
        //chech all the initialized hotdeploy configure.
        for (String clazzName : configs.keySet()) {
            AbstractHotdeployConfig config = configs.get(clazzName);
            //if changed.
            if (config.isModified()) {
                //reload it.
                config.reload();
            }
        }
    }

}
