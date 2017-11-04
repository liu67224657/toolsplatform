package com.enjoyf.advertise.props;


public class Properties {
    public final static long DISABLE_TIME = 24 * 60 * 60 * 1000;
    public static java.util.Properties prop = new java.util.Properties();
    public static String[] replaceContext = null;

    public static String getMongoDBIP() {
        return prop.getProperty("mongo_db_ip");
    }

    public static String getMongoDBName() {
        return prop.getProperty("mongo_db_name");
    }

    public static String getMongoDBMaxConns() {
        return prop.getProperty("mongo_db_max_conns");
    }

    public static String getGameDownloadUrl() {
        return prop.getProperty("game_download_url");
    }

    public static int getMongoDBPort() {
        try {
            return Integer.parseInt(prop.getProperty("mongo_db_port"));
        } catch (Exception e) {
            return 0;
        }
    }
}
