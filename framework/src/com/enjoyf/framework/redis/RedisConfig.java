package com.enjoyf.framework.redis;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 14/11/18
 * Description:
 */
public class RedisConfig {
    private String host;
    private int maxActive = 100;
    private int maxWait = 20;
    private int maxIdel = 1000;
    private String passowrd;

    public RedisConfig(String host, int maxActive, int maxWait, int maxIdel) {
        this(host, maxActive, maxWait, maxIdel, null);
    }

    public RedisConfig(String host, int maxActive, int maxWait, int maxIdel, String password) {
        this.host = host;
        this.maxActive = maxActive;
        this.maxWait = maxWait;
        this.maxIdel = maxIdel;
        this.passowrd = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getMaxIdel() {
        return maxIdel;
    }

    public void setMaxIdel(int maxIdel) {
        this.maxIdel = maxIdel;
    }

    public String getPassowrd() {
        return passowrd;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }
}
